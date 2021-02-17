package com.adobe.aem.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * This is the domain object to be returned in the response.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 */
public class Numeral {

    @JsonProperty("input")
    private final String integer;
    @JsonProperty("output")
    private final String roman;

    public Numeral(String integer, String roman) {
        this.integer = integer;
        this.roman = roman;
    }

    public String getInteger() {
        return integer;
    }

    public String getRoman() {
        return roman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Numeral)) {
            return false;
        }
        Numeral numeral = (Numeral) o;
        return Objects.equals(this.integer, numeral.integer) && Objects.equals(this.roman, numeral.roman);
    }

    @Override
    public String toString() {
        return String.format("Numeral{integer=\"%s\", roman=\"%s\"}", this.integer, this.roman);
    }

}
