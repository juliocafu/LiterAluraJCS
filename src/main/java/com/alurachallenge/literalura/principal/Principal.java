package com.alurachallenge.literalura.principal;

import com.alurachallenge.literalura.dto.AutorDTO;
import com.alurachallenge.literalura.excepciones.AutorNoEncontrado;
import com.alurachallenge.literalura.excepciones.AutorVivoNoEncontrado;
import com.alurachallenge.literalura.modelos.DatosLibro;
import com.alurachallenge.literalura.modelos.Libro;
import com.alurachallenge.literalura.repositorio.AutorRepository;
import com.alurachallenge.literalura.repositorio.LibroRepository;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.ConvierteDatos;
import com.alurachallenge.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI= new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos convierteDatos= new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroService libroService;

    public Principal() {

    }

    public void muestraMenu(){
        var opcion= -1;
        while (opcion !=0){
            var menu= """
                    1.Buscar libro por titulo
                    2.Mostrar lista de libros registrados
                    3.Mostrar lista de autores registrados
                    4.Buscar autores vivos por determinado año
                    5.Buscar libros por idioma
                    
                    0.Salir
                    """;

            System.out.println(menu);
            opcion= teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    mostrarListaDeLibros();
                    break;
                case 3:
                    mostrarListaDeAutores();
                    break;
                case 4:
                    mostrarListaDeAutoresVivos();
                    break;
                case 5:
                    mostrarListaDeLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("la opción ingresada no es invalida");
            }
        }
    }



    private void mostrarListaDeLibrosPorIdioma() {
        List<String> idiomasValidos = Arrays.asList("es", "en", "fr", "it", "pt");
        System.out.println("""
                Elija un idioma de la lista:
                es - Español
                en - Inglés
                fr - Francés
                it - Italiano
                pt - Portugués
                """);
        String idioma = "[" + teclado.nextLine() + "]"; // Añadimos corchetes al valor ingresado
        try{
            List<Libro> libros = libroService.obtenerLibrosPorIdioma(idioma);
            System.out.println("Libros en idioma " + idioma + ":");
            libros.forEach(libro -> System.out.println(libro.getTitulo()));
        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarListaDeAutoresVivos() {
        System.out.println("Ingresa el año para buscar autores vivos: ");
        var ano= teclado.nextInt();
        teclado.nextLine();
        try {
            List<AutorDTO> autoresVivos = libroService.obtenerAutoresVivosPorAno(ano);
            autoresVivos.forEach(System.out::println);
        } catch (AutorVivoNoEncontrado e){
            System.out.println(e.getMessage());
        }
    }


    private void mostrarListaDeAutores() {
        try {
            List<AutorDTO> autorDTOS= libroService.obtenerAutoresRegistrados();
            autorDTOS.forEach(System.out::println);
        } catch (AutorNoEncontrado e){
            System.out.println(e.getMessage());
        }

    }


    public void mostrarListaDeLibros() {
        List<Libro> listaLibros = libroRepository.obtenerLibrosRegistrados();
        System.out.println("--------LIBROS----------");
        listaLibros.forEach(libro -> {
                    System.out.println("Titulo: " +libro.getTitulo());
                    System.out.println("Autor: "+libro.getAutorList());
                    System.out.println("Idioma: "+libro.getIdioma());
                    System.out.println("Número de descargas: "+libro.getNumeroDescargas());
                    System.out.println("--------------------------------------");
                });
    }


    private void buscarLibroTitulo() {
        System.out.println("Escribe el titulo del libro que deseas encontrar");
        var tituloLibro = teclado.nextLine();
        try {
            // Codifica el título del libro en formato URL
            String tituloCodificado = URLEncoder.encode(tituloLibro, StandardCharsets.UTF_8.toString());
            var json = consumoAPI.obtenerDatos(URL_BASE + tituloCodificado.replace(" ","+"));
            System.out.println(json);
            DatosLibro datosLibro = convierteDatos.obtenerDatos(json, DatosLibro.class);
            Libro libro = new Libro(datosLibro.datosLibro().get(0));
            libroRepository.save(libro);
        } catch (DataIntegrityViolationException e){
            System.out.println("Error: Uno de los campos excede el tamaño permitido");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error al codificar el título: " + e.getMessage());
        }
    }


}