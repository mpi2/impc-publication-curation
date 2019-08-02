package org.impc.publications.controllers;

import org.impc.publications.models.Allele;
import org.impc.publications.models.Journal;
import org.impc.publications.repositories.PublicationRepository;
import org.impc.publications.services.AlleleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class
PublicationController {

    private AlleleService alleleService;

    @Autowired
    public PublicationController(AlleleService alleleService) {
        assert (alleleService != null);
        this.alleleService = alleleService;
    }

    @CrossOrigin
    @RequestMapping("/alleles")
    public Iterable<Allele> getAllAlleles() {
        return alleleService.findAll();
    }

    @CrossOrigin
    @RequestMapping("/alleles/{text}")
    public Iterable<Allele> getAllAllelesBySymbol(@PathVariable("text") String text) {
        return alleleService.findBySymbol(text);
    }


    @CrossOrigin
    @RequestMapping("/genes")
    public Iterable<String> getAllCitedAlleles() {
        return alleleService.getCitedGenes();
    }


    @CrossOrigin
    @RequestMapping("/journals")
    public Iterable<String> getAllJournals() {
        return this.alleleService.getAlljournals();
    }
}
