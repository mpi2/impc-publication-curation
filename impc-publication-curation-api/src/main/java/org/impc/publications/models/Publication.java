package org.impc.publications.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "references")
public class Publication {

    private ObjectId id;

    private String title;
    private String pmid;
    private String pmcid;
    private String doi;
    private String authorString;
    private Integer pubYear;
    private String pageInfo;
    private String abstractText;
    private JournalInfo journalInfo;
    public ArrayList<Author> authorList;
    private ArrayList<Grant> grantsList;
    private ArrayList<FullTextUrl> fullTextUrlList;
    private ArrayList<AlleleRef> alleles;
    private String datasource;
    private Boolean consortiumPaper;
    private ArrayList<Fragment> fragments;
    private ArrayList<Citation> citations;
    private ArrayList<String> cites;
    private String keyword;
    private Date firstPublicationDate;
    private ArrayList<AlleleRef> alleleCandidates;
    private ArrayList<Correspondence> correspondence;
    private String status;
    private String comment;

}
