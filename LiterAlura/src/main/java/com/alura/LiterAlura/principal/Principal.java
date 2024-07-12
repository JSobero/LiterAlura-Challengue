package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.model.*;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.repository.PersonaRepository;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    ConsumoAPI consumoAPI = new ConsumoAPI();
    ConvierteDatos convierteDatos = new ConvierteDatos();
    private PersonaRepository repositoryPersona;
    private LibroRepository repositoryLibro;
    private List<Libro> libros;

    public Principal(LibroRepository repositoryLibro, PersonaRepository repositoryPersona) {
        this.repositoryLibro = repositoryLibro;
        this.repositoryPersona = repositoryPersona;
    }

    public void mostrarMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    Elija la opcion a traves de su numero.
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    0. Salir
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    busquedaLibroPorTitulo();
                    break;
                case 2:
                    mostrarListaLibros();
                    break;
                case 3:
                    mostrarListaAutores();
                    break;
                case 4:
                    mostrarAutoresPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando aplicacion");
                    opcion =0;
                    break;
                default:
                    System.out.println("No existe está opción");
            }
        }
    }

    private DatosLibro getLibrosPorTitulo(){
        System.out.println("Ingrese el titulo del libro que quiere consultar: ");
        var libro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + libro.replace(" ", "+"));

        var datos = convierteDatos.obtenerDatos(json, Resultados.class);
        Optional<DatosLibro> busquedaLibro = datos.resultado().stream()
                .filter(l -> l.titulo().toUpperCase().contains(libro.toUpperCase()))
                .findFirst();

        if (busquedaLibro.isPresent()) {
            return busquedaLibro.get();
        } else {
            return null;
        }
    }

    private void busquedaLibroPorTitulo(){
        DatosLibro datosLibro = getLibrosPorTitulo();
        DatosPersona autor = datosLibro.autor().get(0);
        System.out.println(datosLibro);

        Persona datosDelAutor = new Persona(autor);
        repositoryPersona.save(datosDelAutor);
        Libro libro = new Libro(datosLibro, datosDelAutor);
        repositoryLibro.save(libro);
    }

    private void mostrarListaLibros(){
        libros = repositoryLibro.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarListaAutores(){
        libros = repositoryLibro.findAll();
        libros.stream()
                .forEach(a -> System.out.println("Autor: " + a.getAutor()));
    }

    private void mostrarAutoresPorAno(){
        System.out.println("Escriba el año en el que desea consultar los Autores disponible: ");
        int anoDeBusqueda = teclado.nextInt();

        List<Persona> autoresEncontrados = repositoryPersona.mostrarAutoresVivos(anoDeBusqueda);
        autoresEncontrados.forEach(a -> System.out.println("Autor: " + a.getNombre()));
    }

    private void mostrarLibrosPorIdioma(){
        System.out.println("Escriba el idioma para mostrar los libros disponibles: ");
        var idiomaBusqueda = teclado.nextLine();
        var idioma = Idioma.fromStringEspanol(idiomaBusqueda);
        List<Libro> libroPorIdioma = repositoryLibro.findByIdioma(idioma);
        System.out.println("Los libros en "+idiomaBusqueda+" son: ");

        libroPorIdioma.forEach(System.out::println);
    }
}

