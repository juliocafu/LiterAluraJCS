package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.dto.AutorDTO;
import com.alurachallenge.literalura.dto.LibroDTO;
import com.alurachallenge.literalura.excepciones.AutorNoEncontrado;
import com.alurachallenge.literalura.excepciones.AutorVivoNoEncontrado;
import com.alurachallenge.literalura.modelos.Autor;
import com.alurachallenge.literalura.modelos.Libro;
import com.alurachallenge.literalura.repositorio.AutorRepository;
import com.alurachallenge.literalura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;



    public  List<Libro> obtenerLibrosRegistrados() {
        return libroRepository.obtenerLibrosRegistrados();
    }

//muestra lista de autores registrados

    public List<AutorDTO> obtenerAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()){
            throw  new AutorNoEncontrado("No se encontraron autores regisrados");
        }
        return autores.stream()
                .map(autor -> new AutorDTO(autor.getNombreAutor(), autor.getFechaNacimiento(), autor.getFechaMuerte()))
                .collect(Collectors.toList());
    }

    //muestra la lista de autores vivos por determinado año
    public List<AutorDTO> obtenerAutoresVivosPorAno(int ano) throws  AutorVivoNoEncontrado {
        List<Autor> autores = autorRepository.findAll();
        List<AutorDTO> autoresVivos = autores.stream()
                .filter(autor -> autor.getFechaNacimiento() <= ano &&
                        (autor.getFechaMuerte() == null || autor.getFechaMuerte() > ano))
                .map(autor -> new AutorDTO(autor.getNombreAutor(), autor.getFechaNacimiento(), autor.getFechaMuerte()))
                .collect(Collectors.toList());
        if (autoresVivos.isEmpty()) {
            throw new AutorVivoNoEncontrado("No se encontraron autores vivos en el año ingresado");
        }
        return autoresVivos;
    }
    public List<Libro> obtenerLibrosPorIdioma(String idioma) throws Exception {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            throw new Exception("No se encontraron libros en el idioma especificado: " + idioma);
        }
        return libros;
    }

}