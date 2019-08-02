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

import java.util.List;
import java.util.regex.Pattern;

public class AlleleRepositoryCustomImpl implements AlleleRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AlleleRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Allele> findAllelesByAlleleSymbolContaining(String text, Pageable pageRequest){
        final Query query = new Query();
        Criteria regex = Criteria.where("alleleSymbol").regex(Pattern.compile(".*" + Pattern.quote( text ) + ".*", Pattern.CASE_INSENSITIVE));
        query.with(pageRequest.getSort());
        query.addCriteria(regex);
        mongoTemplate.find(new Query().addCriteria(regex), Allele.class);
        List<Allele> alleles = mongoTemplate.find(query.limit(pageRequest.getPageSize()).skip(pageRequest.getOffset()), Allele.class);
        return PageableExecutionUtils.getPage(alleles, pageRequest, () -> mongoTemplate.count(query, Allele.class));
    }
}
