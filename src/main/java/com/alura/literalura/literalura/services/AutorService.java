package com.alura.literalura.literalura.services;

import com.alura.literalura.literalura.models.Autor;
import com.alura.literalura.literalura.models.DatosAutor;
import com.alura.literalura.literalura.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository reposiorioAutor;

    @Transactional
    public Autor obtenerOCrearAutor(DatosAutor datosAutor){
        if(datosAutor == null){
            System.out.println("Los datos del autor son nulos");
            return null;
        }

        String nombreAutorBuscado = datosAutor.nombre();
        Optional<Autor> autorOpt = reposiorioAutor.findByNombreContainsIgnoreCase(nombreAutorBuscado);

        if(autorOpt.isPresent()){
          Autor autorExistente = autorOpt.get();
            System.out.println("Autor existente encontrado: "+autorExistente.getNombre());
            return autorExistente;
        }else {
            Autor nuevoAutor = new Autor(datosAutor);
            Autor autorGuardado = reposiorioAutor.save(nuevoAutor);
            System.out.println("Nuevo autor guardado: "+autorGuardado.getNombre());
            return autorGuardado;
        }
    }

    public List<Autor> listarTodosLosAutores() {
        return reposiorioAutor.findAll();
    }

    public List<Autor> listarAutoresVivosEnAnio(Integer anio) {
        return reposiorioAutor.encontrarAutoresVivos(anio);
    }

    public Optional<Autor> buscarPorAutor(String nombre){
        return reposiorioAutor.findByNombreContainsIgnoreCase(nombre);
    }
}
