package org.impc.publications.services;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.impc.publications.models.AlleleRef;
import org.impc.publications.models.Publication;
import org.impc.publications.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class GraphQLMutation implements GraphQLMutationResolver {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public Publication updateReviewed(String pmid, boolean reviewed, ArrayList<AlleleRef> alleles, boolean falsePositive, boolean consortiumPaper, ArrayList<AlleleRef> alleleCandidates) {
        Query query = new Query(new Criteria("pmid").is(pmid));
        Update update = new Update().set("reviewed", reviewed)
                .set("alleles", alleles)
                .set("falsePositive", falsePositive)
                .set("consortiumPaper", consortiumPaper)
                .set("alleleCandidates", alleleCandidates);
        mongoOperations.updateFirst(query, update, "references");
        return publicationRepository.findPublicationByPmid(pmid);
    }

}
