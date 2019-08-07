package org.impc.publications.repositories;

import org.bson.Document;
import org.impc.publications.models.AlleleRef;
import org.impc.publications.models.Journal;
import org.impc.publications.models.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PublicationRepositoryCustom {

    Page<Publication> findPublications(Pageable pageable, String status, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search);
    Long countPublications(String status, ArrayList<String> keywords, Boolean consortiumPaper, Boolean hasAlleles, Boolean citeConsortiumPaper, Integer pubYearFrom, Integer pubYearTo, String search);
    List<String> getJournalNames();
    List<String> getCitedGenes();
    boolean updatedStatus(String pmid, String status, ArrayList<AlleleRef> alleles, boolean consortiumPaper, ArrayList<AlleleRef> alleleCandidates, String orderId);
    String insertPublicationJson(String pmid, String publicationJson);
}
