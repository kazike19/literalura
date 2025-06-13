package com.alura.literalura.literalura.principal;

import com.alura.literalura.literalura.models.*;
import com.alura.literalura.literalura.repositories.AutorRepository;
import com.alura.literalura.literalura.repositories.LibroRepository;
import com.alura.literalura.literalura.services.ConsumoAPI;
import com.alura.literalura.literalura.services.ConvierteDatos;
import com.alura.literalura.literalura.services.LibroService;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.alura.literalura.literalura.models.Idioma.fromEsp;
import static com.alura.literalura.literalura.models.Idioma.fromString;

public class Principal {

    private LibroService libroService;
    private AutorRepository repositorioAutor;
    private LibroRepository repositorioLibro;
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner entrada = new Scanner(System.in);
    private List<Libro> listaEncontrada;

    // Códigos de escape ANSI para colores
    final String YELLOW = "\033[0;33m"; // amarillo
    final String CYAN = "\033[0;36m"; // azul
    final String GREEN = "\033[0;32m"; // verde
    final String RESET = "\033[0m"; //

    public Principal(AutorRepository repositorioAutor, LibroRepository repositorioLibro, LibroService libroService) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLibro = repositorioLibro;
        this.libroService = libroService;
    }

    public void mostrarMenu(){

        var opcion = -1;
        while (opcion != 0) {
            menuPrincipal();
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listarAutoresPorAnio();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 6:
                    mostrarEstadisticasIdioma();
                    break;

                case 7:
                    topLibros();
                    break;

                case 8:
                    busquedaDeLibroPorAutor();
                    break;

                case 9:
                    mostrarEstadisticasDeDescargasPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibro() {
        System.out.println("Dame el nombre del libro que quieres buscar: ");
        String nombreLibro  = entrada.nextLine();
        String nombreLibroFiltro = nombreLibro.toUpperCase();
        nombreLibro = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);

        var json = consumoAPI.obtenerDatos(URL_BASE+"?search="+nombreLibro);

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        DatosLibros datosLibros = null;

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibroFiltro))
                .findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
            datosLibros = libroBuscado.get();
        }


        if (datosLibros !=null && datosLibros.titulo() !=null){
            libroService.guardarLibroDesdeDatos(datosLibros);
        }else{
            System.out.println("Libro no encontrado o con datos incompletos");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> listaDeLibros = repositorioLibro.findAll();
        listaDeLibros
                .forEach(l -> System.out.println(
                        "---------  Libro  ---------"+
                                "\nLibro: "+ l.getTitulo() +
                                "\nAutor: " + l.getAutor().getNombre() +
                                "\nIdioma: " + l.getIdioma() +
                                "\nNumero de descargas: " + l.getNumero_de_descargas()
                        ) );
    }

    private void listarAutoresRegistrados() {
        List<Autor> listaDeAutores = repositorioAutor.findAll();
        listaDeAutores
                .forEach(a -> System.out.println(
                        "---------  Autor  ---------"+
                        "\nNombre: " + a.getNombre() +
                        "\nAño de nacimiento: "+ a.getFechaNacimiento()+
                        "\nAño de fallecimiento: "+a.getFechaMuerte())
                );
    }

    private void listarAutoresPorAnio() {
        System.out.println("Dame el año en el que deseas buscar autores vivos: ");
        int anioBusqueda = entrada.nextInt();
        entrada.nextLine();

        List<Autor> autoresPorAnio = repositorioAutor.encontrarAutoresVivos(anioBusqueda);

        if (autoresPorAnio.isEmpty()){
            System.out.println("No hay autores presentes en este año: "+anioBusqueda);
        }else{
            autoresPorAnio.forEach(a -> System.out.println(
                    "---------  Autor  ---------"+
                            "\nNombre: " + a.getNombre() +
                            "\nAño de nacimiento: "+ a.getFechaNacimiento()+
                            "\nAño de fallecimiento: "+a.getFechaMuerte())
            );
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingresa el idioma de los libros que deseas buscar");
        mostrarIdiomas();
        String idiomaEntrada = entrada.nextLine();

        Idioma idiomaBusqueda = null;
        try {
            idiomaBusqueda = fromString(idiomaEntrada);
        } catch (IllegalArgumentException eGutenx) {
            try {
                idiomaBusqueda = fromEsp(idiomaEntrada);
            }catch (IllegalArgumentException eEsp){
                System.out.println("El idioma no esta permitido, o lo ingresaste incorrectamente. Por favor intenta nuevamente.");
                return;
            }
        }

        if (idiomaBusqueda == null) {
            System.out.println("No se pudo determinar el idioma de búsqueda. Por favor, verifica tu entrada.");
            return;
        }

        Optional<List<Libro>> librosPorIdiomaOpt = repositorioLibro.findByIdioma(idiomaBusqueda);
        if (librosPorIdiomaOpt.isPresent() && !librosPorIdiomaOpt.get().isEmpty() ){
            listaEncontrada = librosPorIdiomaOpt.get();
            listaEncontrada.forEach(l -> System.out.println(
                    "---------  Libros en '"+idiomaEntrada+"' ---------"+
                            "\nLibro: "+ l.getTitulo() +
                            "\nAutor: " + l.getAutor().getNombre() +
                            "\nIdioma: " + l.getIdioma() +
                            "\nNumero de descargas: " + l.getNumero_de_descargas()
            ) );
        }else {
            System.out.println("Actualmente no hay libros con ese idioma");
        }

    }

    private void mostrarEstadisticasIdioma() {
        int canditadDeLibros;
        System.out.println("Ingresa el idioma del que deseas saber cuantos hay registrados");
        mostrarIdiomas();

        String idiomaEntrada = entrada.nextLine();

        Idioma idiomaBusqueda = null;
        try {
            idiomaBusqueda = fromString(idiomaEntrada);
        } catch (IllegalArgumentException eGutenx) {
            try {
                idiomaBusqueda = fromEsp(idiomaEntrada);
            }catch (IllegalArgumentException eEsp){
                System.out.println("El idioma no esta permitido, o lo ingresaste incorrectamente. Por favor intenta nuevamente.");
                return;
            }
        }

        if (idiomaBusqueda == null) {
            System.out.println("No se pudo determinar el idioma de búsqueda. Por favor, verifica tu entrada.");
            return;
        }

        Optional<List<Libro>> librosPorIdiomaOpt = repositorioLibro.findByIdioma(idiomaBusqueda);
        if (librosPorIdiomaOpt.isPresent() && !librosPorIdiomaOpt.get().isEmpty() ){
            listaEncontrada = librosPorIdiomaOpt.get();
            canditadDeLibros = listaEncontrada.size();
            System.out.println("---------  Cantidad de libros  ---------");
            System.out.println("Idioma buscado: " + idiomaBusqueda.getIdiomaEs()+ " (" + idiomaBusqueda.getIdiomaGutenx() + ")");
            System.out.println("Cantidad de libros: " + canditadDeLibros);
            System.out.println("----------------------------------------");

        }else {
            System.out.println("Actualmente no hay libros con ese idioma");
        }

    }

    private void topLibros() {
        List<Libro> topLibros = libroService.obtenerTopLibrosDescargados();
        if (topLibros.isEmpty()){
            System.out.println("Actualmente no hay lirbso registrados");
        }else {
            System.out.println("---------  Top 10 de libros  ---------");
            topLibros.forEach(l -> System.out.println("Titulo: "+l.getTitulo()+", total de descargas: "+l.getNumero_de_descargas()));
        }
    }

    private void busquedaDeLibroPorAutor() {
        System.out.println("Dame el autor que deseas buscar en la base de datos: ");
        String nombreAutor = entrada.nextLine();
        Optional<Autor> autorBuscado = repositorioAutor.findByNombreContainsIgnoreCase(nombreAutor);

        if(autorBuscado.isPresent()){
            Autor autor = autorBuscado.get();
            System.out.println("Libros de "+autor.getNombre());
            List<Libro> librosDeAutor = libroService.obtenerLibroPorAutor(autor);
            if (!librosDeAutor.isEmpty()) {
                librosDeAutor.forEach(l -> System.out.println("Titulo: " + l.getTitulo()));
            } else{
                System.out.println("No se encontraron libros del autor");
            }
        }else {
            System.out.println("El autor "+nombreAutor+" no tiene registrado ningun libro en la base de datos");
        }

    }

    private void mostrarEstadisticasDeDescargasPorIdioma() {
        System.out.println("Ingresa el idioma para obtener estadísticas de descargas: ");
        mostrarIdiomas();

        String idiomaEntrada = entrada.nextLine();

        Idioma idiomaBusqueda = null;
        try {
            idiomaBusqueda = Idioma.fromString(idiomaEntrada);
        } catch (IllegalArgumentException eGutenx) {
            try {
                idiomaBusqueda = Idioma.fromEsp(idiomaEntrada);
            } catch (IllegalArgumentException eEsp) {
                System.out.println("El idioma no está permitido o lo ingresaste incorrectamente. Por favor, intenta nuevamente.");
                return;
            }
        }

        if (idiomaBusqueda == null) {
            System.out.println("No se pudo determinar el idioma de búsqueda. Por favor, verifica tu entrada.");
            return;
        }

        // Obtenemos los libros de la base de datos para el idioma dado
        Optional<List<Libro>> librosEnIdiomaOpt = libroService.listarLibrosPorIdioma(idiomaBusqueda);

        if (!librosEnIdiomaOpt.isEmpty()) {
            List<Libro> librosEnIdioma = librosEnIdiomaOpt.get();

            DoubleSummaryStatistics estadisticas = librosEnIdioma.stream()
                    .mapToDouble(Libro::getNumero_de_descargas)
                    .summaryStatistics();

            System.out.println("\n--- Estadísticas de Descargas para el idioma: " + idiomaBusqueda.getIdiomaEs() + " ---");
            System.out.println("Cantidad de libros: " + estadisticas.getCount());
            System.out.println("Suma total de descargas: " + estadisticas.getSum());
            System.out.println("Promedio de descargas: " + String.format("%.2f", estadisticas.getAverage()));
            System.out.println("Mínimo de descargas: " + estadisticas.getMin());
            System.out.println("Máximo de descargas: " + estadisticas.getMax());
            System.out.println("--------------------\n");

        } else {
            System.out.println("No se encontraron libros en el idioma '" + idiomaEntrada + "' para generar estadísticas de descargas.");
        }
    }

    public void menuPrincipal(){

        System.out.println(YELLOW + "\n***********************************************" + RESET);
        System.out.println(CYAN +   "          ¡Bienvenido al LiterAlura!           " + RESET);
        System.out.println(YELLOW + "***********************************************" + RESET);
        System.out.println();
        System.out.println(GREEN + "--- Menu de opciones ---" + RESET);
        System.out.println(YELLOW+ """
                    
                    1 - Buscar Libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Mostrar el numero de libros de un idioma
                    7 - Top 10 de descargas
                    8 - Buscar libro por autor en la base de datos
                    9 - Estadisticas de un idioma

                    0 - Salir
                    """ +RESET);
    }

    public void mostrarIdiomas(){

        System.out.println(CYAN+"""
                ---------  Menu de idiomas  ---------
                en - Ingles
                es - Español
                fr - Frances
                de - Aleman
                pt - Portugues
                it - Italiano
                
                Puedes usar 'es', 'en' o 'Español', 'Ingles':
                """ + RESET);
    }

}