package com.alura.literalura.literalura.repositories;

import com.alura.literalura.literalura.models.Autor;
import com.alura.literalura.literalura.models.Idioma;
import com.alura.literalura.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    List<Libro> findAll();

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    Optional<List<Libro>> findByIdioma(Idioma idioma);

    List<Libro> findByAutor(Autor autor);
}
