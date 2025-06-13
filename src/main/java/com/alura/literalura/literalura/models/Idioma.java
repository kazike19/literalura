package com.alura.literalura.literalura.models;

public enum Idioma {

    INGLES("en", "Ingles"),

    ESPANIOL("es", "Español"),

    FRANCES("fr", "Frances"),

    ALEMAN("de", "Aleman"),

    PORTUGUES("pt", "Portugues"),

    ITALIANO("it","Italiano");

    private String idiomaGutenx;
    private String idiomaEs;

    Idioma(String idiomaGutenx, String idiomaEs){
        this.idiomaGutenx = idiomaGutenx;
        this.idiomaEs = idiomaEs;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGutenx.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna idioma encontrado: " + text);
    }

    public static Idioma fromEsp(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEs.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna idioma encontrado: " + text);
    }

    public String getIdiomaGutenx() {
        return idiomaGutenx;
    }

    public String getIdiomaEs() {
        return idiomaEs;
    }
}
