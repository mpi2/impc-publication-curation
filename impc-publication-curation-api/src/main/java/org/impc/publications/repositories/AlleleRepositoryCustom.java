package org.impc.publications.repositories;

import org.impc.publications.models.Allele;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AlleleRepositoryCustom {

    Page<Allele> findAllelesByAlleleSymbolContaining(String text, Pageable pageRequest);

}
