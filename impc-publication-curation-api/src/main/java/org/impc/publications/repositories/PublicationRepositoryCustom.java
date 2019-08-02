package org.impc.publications.repositories;

import org.impc.publications.models.AlleleRef;
import org.impc.publications.models.Journal;
import org.impc.publications.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface PublicationRepositoryCustom {

    Page<Publication> findPublications(Pageable pageable, Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search, Boolean pendingEmailConfirmation);
    Long countPublications(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search, Boolean pendingEmailConfirmation);
    List<String> getJournalNames();
    List<String> getCitedGenes();
    boolean updatedStatus(String pmid, boolean reviewed, ArrayList<AlleleRef> alleles, boolean falsePositive, boolean consortiumPaper, boolean pendingEmailConfirmation, ArrayList<AlleleRef> alleleCandidates, String orderId);
}
