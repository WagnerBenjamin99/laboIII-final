package ar.edu.utn.frbb.tup.persistence.comparators;

import se.sawano.java.text.AlphanumericComparator;

import java.util.Comparator;

public class CodeComparator implements Comparator<String> {
    private AlphanumericComparator comparator = new AlphanumericComparator();

    public CodeComparator(String getCodigo) {
    }

    @Override
    public int compare(String codigo1, String codigo2) {
        return comparator.compare(codigo1, codigo2);
    }

}
