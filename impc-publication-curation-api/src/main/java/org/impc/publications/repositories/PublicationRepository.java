package org.impc.publications.repositories;

import org.bson.types.ObjectId;
import org.impc.publications.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PublicationRepository extends MongoRepository<Publication, ObjectId>, PublicationRepositoryCustom {
    Publication findPublicationById(String id);
    Publication findPublicationByPmid(String pmid);
    Page<Publication> findPublicationsByReviewed(boolean reviewed, Pageable pageable);
    int countPublicationsByReviewed(boolean reviewed);

}
