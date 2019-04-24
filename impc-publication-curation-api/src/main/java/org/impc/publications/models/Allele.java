package org.impc.publications.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data
@NoArgsConstructor
@SolrDocument(solrCoreName = "allele2")
public class Allele {
    @Id
    @Field("allele_mgi_accession_id")
    private String acc;

    @Field("allele_symbol")
    private String alleleSymbol;

    @Field("mgi_accession_id")
    private String gacc;

    @Field("marker_symbol")
    private String geneSymbol;

    @Field("allele_name")
    private String alleleName;

    @Field("allele_design_project")
    private String project;
}
