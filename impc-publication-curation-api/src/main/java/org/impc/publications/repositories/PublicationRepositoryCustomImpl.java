package org.impc.publications.repositories;

import com.mongodb.DBCollection;
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

import java.util.ArrayList;
import java.util.List;

public class PublicationRepositoryCustomImpl implements PublicationRepositoryCustom  {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PublicationRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Page<Publication> findPublications(Pageable pageable, Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search, Boolean pendingEmailConfirmation) {
        Query query = generateQuery(reviewed, falsePositive, keywords, consortiumPaper, hasAlleles, citeConsortiumPaper, pubYearFrom, pubYearTo, search, pendingEmailConfirmation);
        query.with(pageable.getSort());
        List<Publication> publications = mongoTemplate.find(query.limit(pageable.getPageSize()).skip(pageable.getOffset()), Publication.class);
        return PageableExecutionUtils.getPage(publications, pageable, () -> mongoTemplate.count(query, Publication.class));
    }

    private Query generateQuery(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search, Boolean pendingEmailConfirmation) {
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        if(reviewed != null) {
            criteria.add(Criteria.where("reviewed").is(reviewed));
        }
        if(falsePositive != null) {
            criteria.add(Criteria.where("falsePositive").is(falsePositive));
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
            criteria.add(Criteria.where("title").regex(search));
        }
        if(pubYearFrom != null) {
            criteria.add(Criteria.where("pubYear").gte(pubYearFrom));
        }
        if(pubYearTo != null && pubYearTo != 0) {
            criteria.add(Criteria.where("pubYear").lte(pubYearTo));
        }

        if(pendingEmailConfirmation != null) {
            criteria.add(Criteria.where("pendingEmailConfirmation").is(pendingEmailConfirmation));
        }

        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return query;
    }

    @Override
    public Long countPublications(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search, Boolean pendingEmailConfirmation) {
        Query query = generateQuery(reviewed, falsePositive, keywords, consortiumPaper, hasAlleles, citeConsortiumPaper,  pubYearFrom,  pubYearTo, search, pendingEmailConfirmation);
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
    public boolean updatedStatus(String pmid, boolean reviewed, ArrayList<AlleleRef> alleles, boolean falsePositive, boolean consortiumPaper, boolean pendingEmailConfirmation, ArrayList<AlleleRef> alleleCandidates, String orderId) {
        Query query = new Query(new Criteria("pmid").is(pmid));
        Update update = new Update().set("reviewed", reviewed)
                .set("alleles", alleles)
                .set("falsePositive", falsePositive)
                .set("consortiumPaper", consortiumPaper)
                .set("pendingEmailConfirmation", pendingEmailConfirmation)
                .set("alleleCandidates", alleleCandidates)
                .set("orderId", orderId);
        return mongoTemplate.updateFirst(query, update, "references").getModifiedCount() == 1;
    }
}
