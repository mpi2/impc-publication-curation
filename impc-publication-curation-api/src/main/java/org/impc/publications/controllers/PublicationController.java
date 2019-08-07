package org.impc.publications.controllers;

import org.bson.Document;
import org.impc.publications.models.Allele;
import org.impc.publications.models.Journal;
import org.impc.publications.repositories.PublicationRepository;
import org.impc.publications.services.AlleleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class
PublicationController {

    private AlleleService alleleService;
    private PublicationRepository publicationRepository;

    @Autowired
    public PublicationController(AlleleService alleleService, PublicationRepository publicationRepository) {
        assert (alleleService != null);
        assert (publicationRepository != null);
        this.alleleService = alleleService;
        this.publicationRepository = publicationRepository;
    }

    @CrossOrigin
    @RequestMapping(value = "/submit/{pmid}", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> submitPublication(@PathVariable("pmid") String pmid, HttpEntity<String> httpEntity) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", publicationRepository.insertPublicationJson(pmid, httpEntity.getBody()));
        return response;
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
