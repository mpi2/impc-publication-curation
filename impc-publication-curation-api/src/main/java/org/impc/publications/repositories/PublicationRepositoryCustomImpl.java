package org.impc.publications.repositories;

import com.mongodb.DBCollection;
import org.bson.Document;
import org.impc.publications.models.Allele;
import org.impc.publications.models.AlleleRef;
import org.impc.publications.models.Journal;
import org.impc.publications.models.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PublicationRepositoryCustomImpl implements PublicationRepositoryCustom  {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PublicationRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Page<Publication> findPublications(Pageable pageable, String status, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search) {
        Query query = generateQuery(status, keywords, consortiumPaper, hasAlleles, citeConsortiumPaper, pubYearFrom, pubYearTo, search);
        query.with(pageable.getSort());
        List<Publication> publications = mongoTemplate.find(query.limit(pageable.getPageSize()).skip(pageable.getOffset()), Publication.class);
        return PageableExecutionUtils.getPage(publications, pageable, () -> mongoTemplate.count(query, Publication.class));
    }

    private Query generateQuery(String status, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        if(status != null) {
            criteria.add(Criteria.where("status").is(status));
        }

        if(keywords != null) {
            criteria.add(Criteria.where("keyword").in(keywords));
        }
        if(consortiumPaper != null) {
            criteria.add(Criteria.where("consortiumPaper").is(consortiumPaper));
        }
        if(hasAlleles != null) {
            criteria.add(new Criteria().orOperator(Criteria.where("alleles").gt(new ArrayList<Allele>()), Criteria.where("alleleCandidates").gt(new ArrayList<Allele>())));
        }
        if(citeConsortiumPaper != null) {
            criteria.add(Criteria.where("cites").gt(new ArrayList<Allele>()));
        }
        if(search != null) {
            criteria.add(new Criteria().orOperator(
                    Criteria.where("title").regex(search),
                    Criteria.where("fragments.mentions").regex(search),
                    Criteria.where("authorString").regex(search),
                    Criteria.where("journalInfo.journal.title").regex(search),
                    Criteria.where("pmid").is(search)
                    )
            );
        }
        if(pubYearFrom != null) {
            criteria.add(Criteria.where("pubYear").gte(pubYearFrom));
        }
        if(pubYearTo != null && pubYearTo != 0) {
            criteria.add(Criteria.where("pubYear").lte(pubYearTo));
        }

        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return query;
    }

    @Override
    public Long countPublications(String status, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search) {
        Query query = generateQuery(status, keywords, consortiumPaper, hasAlleles, citeConsortiumPaper,  pubYearFrom,  pubYearTo, search);
        return this.mongoTemplate.count(query, Publication.class);
    }
    @Override
    public List<String> getJournalNames() {
        return this.mongoTemplate.findDistinct("journalInfo.journal.title", Publication.class, String.class);
    }

    @Override
    public List<String> getCitedGenes() {
        return this.mongoTemplate.findDistinct("alleles.geneSymbol", Publication.class, String.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public boolean updatedStatus(String pmid, String status, ArrayList<AlleleRef> alleles,
                                 boolean consortiumPaper, ArrayList<AlleleRef> alleleCandidates, String comment) {
        System.out.println(alleles);
        Query query = new Query(new Criteria("pmid").is(pmid));
        Update update = new Update().set("status", status)
                .set("alleles", alleles)
                .set("consortiumPaper", consortiumPaper)
                .set("alleleCandidates", alleleCandidates)
                .set("comment", comment);
        return mongoTemplate.updateFirst(query, update, "references").getModifiedCount() == 1;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public String insertPublicationJson(String pmid, String publicationJson) {
        String response = "";
        if(!this.mongoTemplate.exists(new Query(new Criteria("pmid").is(pmid)), "references")) {
            Document doc = Document.parse(publicationJson);
            String publicationDateStr = (String) doc.remove("firstPublicationDate");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                doc.put("firstPublicationDate", df.parse(publicationDateStr));
                mongoTemplate.insert(doc, "references");
                response = "created";
            } catch (ParseException e) {
                e.printStackTrace();
                response = "failed";
            }
        } else {
            response = "exists";
        }
        return response;
    }
}
