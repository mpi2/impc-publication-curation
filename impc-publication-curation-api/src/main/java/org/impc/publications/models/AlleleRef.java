package org.impc.publications.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlleleRef {
    private String acc;
    private String gacc;
    private String geneSymbol;
    private String project;
    private String alleleName;
    private String alleleSymbol;
    private String orderId;
    private String emmaId;
}
