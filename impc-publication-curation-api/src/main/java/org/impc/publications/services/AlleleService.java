package org.impc.publications.services;

import org.impc.publications.models.Allele;
import org.impc.publications.repositories.AlleleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class AlleleService {

    private AlleleRepository alleleRepository;

    @Autowired
    AlleleService(AlleleRepository alleleRepository) {
        assert(alleleRepository != null);
        this.alleleRepository = alleleRepository;
    }

    public Iterable<Allele> findAll(){
        return alleleRepository.findAll(new PageRequest(0, 100));
    }
    public Page<Allele> findBySymbol(String text) { return alleleRepository.findAllelesByAlleleSymbolContaining(text, new PageRequest(0, 100)); }


}
