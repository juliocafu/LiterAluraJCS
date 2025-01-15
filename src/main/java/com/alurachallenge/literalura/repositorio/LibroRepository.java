package com.alurachallenge.literalura.repositorio;

import com.alurachallenge.literalura.modelos.Autor;
import com.alurachallenge.literalura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdioma(String idioma);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l ")
    List<Libro> obtenerLibrosRegistrados();

}