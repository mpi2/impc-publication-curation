package org.impc.publications.repositories;

import org.impc.publications.models.Allele;
import org.impc.publications.models.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

public class PublicationRepositoryCustomImpl implements PublicationRepositoryCustom  {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public PublicationRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Page<Publication> findPublications(Pageable pageable, Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper) {
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
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        query.with(pageable.getSort());
        List<Publication> publications = mongoTemplate.find(query.limit(pageable.getPageSize()).skip(pageable.getOffset()), Publication.class);
        return PageableExecutionUtils.getPage(publications, pageable, () -> mongoTemplate.count(query, Publication.class));
    }

    @Override
    public Long countPublications(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper) {
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
        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return this.mongoTemplate.count(query, Publication.class);
    }
}
