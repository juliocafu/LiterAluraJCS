package com.alurachallenge.literalura.excepciones;

import com.alurachallenge.literalura.modelos.Autor;

public class AutorNoEncontrado extends RuntimeException {
    public AutorNoEncontrado (String mensaje){
        super(mensaje);
    }
}