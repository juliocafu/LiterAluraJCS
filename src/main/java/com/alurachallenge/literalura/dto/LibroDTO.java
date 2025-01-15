package com.alurachallenge.literalura.dto;

import java.util.List;
import java.util.Map;

public record LibroDTO(
        Long id,
        String titulo,
        List<AutorDTO> autores,
        List<String> generos,
        List<String> idiomas,
        Double numeroDescargas,
        Map<String, String> formatos) {



    @Override
    public String toString() {
        return "LibroDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", generos=" + generos +
                ", idiomas=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                ", formatos=" + formatos +
                '}';
    }
}
