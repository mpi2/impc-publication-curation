package org.impc.publications.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fragments {

    private ArrayList<String> EUCOMM;
    private ArrayList<String> KOMP;
    private ArrayList<String> INFRAFRONTIER;
    private ArrayList<String> JAX;
    private ArrayList<String> IMPC;
    private ArrayList<String> EMMA;
    private ArrayList<AlleleMention> alleles;
}
