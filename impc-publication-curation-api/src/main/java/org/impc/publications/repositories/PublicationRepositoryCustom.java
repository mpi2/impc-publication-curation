package org.impc.publications.repositories;

import org.impc.publications.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface PublicationRepositoryCustom {

    Page<Publication> findPublications(Pageable pageable, Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper);
    Long countPublications(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper);
}
