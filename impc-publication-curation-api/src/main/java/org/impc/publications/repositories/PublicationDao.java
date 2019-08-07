package org.impc.publications.repositories;

import org.bson.types.ObjectId;
import org.impc.publications.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
public interface PublicationDao extends PagingAndSortingRepository<Publication, ObjectId> {

    public Publication findByPmid(@Param("pmid") long pmid);
    public Page findByStatus(@Param("status") String status, Pageable p);
}