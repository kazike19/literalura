package com.alura.literalura.literalura.models;

import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private Double numero_de_descargas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public Double getNumero_de_descargas() {
        return numero_de_descargas;
    }


    public Libro(){}

    public Libro(DatosLibros datosLibro, Autor autor, Idioma idioma){
        this.titulo = datosLibro.titulo();

        if( datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty() ){
            try {
                this.idioma = Idioma.fromString(datosLibro.idiomas().get(0));
            } catch (IllegalArgumentException e){
                System.out.println("Idioma no encontrado para: "+datosLibro.idiomas().get(0) + ". El valor del idioma sera null");
                this.idioma = null;
            }
        }

        this.numero_de_descargas = datosLibro.numeroDescargas();
        this.autor = autor;
    }
}