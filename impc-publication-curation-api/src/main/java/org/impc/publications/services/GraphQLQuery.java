package org.impc.publications.services;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.impc.publications.models.Publication;
import org.impc.publications.repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GraphQLQuery implements GraphQLQueryResolver {

    @Autowired
    private PublicationRepository publicationRepository;

    public List<Publication> getPublications(int start, int size, Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords, Boolean consortiumPaper, Boolean alleles, Boolean cites) {
        List<Publication> target = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(start, size, new Sort(Sort.Direction.DESC, "firstPublicationDate"));
        publicationRepository.findPublications(pageRequest, reviewed, falsePositive, keywords, consortiumPaper, alleles, cites).forEach(target::add);
        return target;
    }

    public List<Publication> getPublicationsByReviewed(int start, int size, boolean reviewed) {
        List<Publication> target = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(start, size, new Sort(Sort.Direction.DESC, "firstPublicationDate"));
        publicationRepository.findPublicationsByReviewed(reviewed, pageRequest).forEach(target::add);
        return target;
    }

    public int getCount(Boolean reviewed, Boolean falsePositive, ArrayList<String> keywords,  Boolean consortiumPaper, Boolean alleles, Boolean cites) {
        return java.lang.Math.toIntExact(publicationRepository.countPublications(reviewed, falsePositive, keywords, consortiumPaper, alleles, cites));
    }

    public Publication getPublication(String id) {
        return publicationRepository.findPublicationById(id);
    }

    public Publication getPublicationByPmid(String pmid) {
        return publicationRepository.findPublicationByPmid(pmid);
    }

}
