package org.impc.publications.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "alleles")
public class Allele {

    private ObjectId id;

    private String acc;

    private String alleleSymbol;

    private String gacc;

    private String geneSymbol;

    private String alleleName;

    private String project;
}
