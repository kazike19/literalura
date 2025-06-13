package com.alura.literalura.literalura.services;

import com.alura.literalura.literalura.models.Autor;
import com.alura.literalura.literalura.models.DatosLibros;
import com.alura.literalura.literalura.models.Idioma;
import com.alura.literalura.literalura.models.Libro;
import com.alura.literalura.literalura.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repositorioLibro;

    @Autowired
    private AutorService autorService;

    @Transactional
    public void guardarLibroDesdeDatos(DatosLibros datosLibros){
        if (datosLibros == null) {
            System.out.println("Datos del libro son nulos.");
            return;
        }

        Autor autor = null;
        if(datosLibros != null && !datosLibros.autor().isEmpty()){
            autor = autorService.obtenerOCrearAutor(datosLibros.autor().get(0));
        }else {
            System.out.println("No se encontraron datos de autor para este libro, intente con otro.");
            return;
        }

        if (autor== null){
            return;
        }

        // Creamos el idioma
        Idioma idioma = null;
        if(datosLibros.idiomas() !=null && !datosLibros.idiomas().isEmpty()){
            try {
                idioma = Idioma.fromString(datosLibros.idiomas().get(0));
            }catch (IllegalArgumentException e){
                System.out.println("Idioma no encontrado para: "+datosLibros.idiomas());
                idioma = null;
            }
        }

        // buscamos o creamos el nuevo libro
        Optional<Libro> libroExistente = repositorioLibro.findByTituloContainsIgnoreCase(datosLibros.titulo());

        if (libroExistente.isPresent()){
            System.out.println("El libro '"+datosLibros.titulo()+"' ya esta registrado, intente nuevamente");
            return;
        }

        Libro libro = new Libro(datosLibros,autor,idioma);

        if(autor.getLibros()== null){
            autor.setLibros(new ArrayList<>());
        }
        autor.getLibros().add(libro);

        repositorioLibro.save(libro);

        System.out.println("Libro guardado: "+libro.getTitulo());
    }

    public List<Libro> listarTodosLosLibros() {
        return repositorioLibro.findAll();
    }

    public Optional<List<Libro>> listarLibrosPorIdioma(Idioma idioma) {
        return repositorioLibro.findByIdioma(idioma);
    }

    public List<Libro> obtenerTopLibrosDescargados() {

        List<Libro> todosLosLibros = repositorioLibro.findAll();

        return todosLosLibros.stream()
                .sorted(Comparator.comparing(Libro::getNumero_de_descargas).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Libro> obtenerLibroPorAutor(Autor autor) {
        List<Libro> libroAutor = repositorioLibro.findByAutor(autor);
        return libroAutor;
    }
}
