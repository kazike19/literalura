package com.alura.literalura.literalura.models;

import com.alura.literalura.literalura.repositories.AutorRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name")
        String nombre,

        @JsonAlias("birth_year")
        Integer fechaNacimiento,

        @JsonAlias("death_year")
        Integer fechaMuerte
        ) {
}


/*package com.alura.literalura.literalura.services;


import com.alura.literalura.literalura.repositories.AutorRepository;



public class ObtenerDatosAutorService {
        AutorRepository repository;

        public void obtenerDatosAutor(String nombre){
        }
}*/
