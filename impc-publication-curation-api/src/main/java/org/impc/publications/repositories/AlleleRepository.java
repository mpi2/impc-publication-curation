package org.impc.publications.repositories;

import org.bson.types.ObjectId;
import org.impc.publications.models.Allele;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlleleRepository extends MongoRepository<Allele, ObjectId>, AlleleRepositoryCustom {
    Page<Allele> findAll(Pageable pageable);
}
