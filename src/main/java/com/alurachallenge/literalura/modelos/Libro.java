package com.alurachallenge.literalura.modelos;


import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String genero;
    private String idioma;
    private Double numeroDescargas;
    private String portada;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autorList;

    public Libro() {
    }

    public Libro(ResultsLibro datosLibro) {
        this.id = datosLibro.id();
        this.idioma = String.valueOf(datosLibro.idioma());
        this.numeroDescargas = datosLibro.numeroDescargas();
        this.portada = datosLibro.formatos().get("image/jpeg");
        this.genero = datosLibro.genero().toString();
        this.titulo = datosLibro.titulo();

        this.autorList = datosLibro.autor().stream()
                .map(original -> {
                    Autor autor = new Autor(original.nombreAutor(), original.fechaNacimiento(), original.fechaMuerte());
                    autor.setLibro(this);  // Establece la relación libro-autor
                    return autor;
                })
                .collect(Collectors.toList());

    }

    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero
    ) {
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdoma(String idoma) {
        this.idioma = idoma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    @Override
    public String toString() {
        String autores = autorList.stream()
                .map(Autor::getNombreAutor)
                .collect(Collectors.joining(","));
        return String.format("Libro {id=%d, titulo='%s', genero='%s',idioma='%s', numeroDescargas=%.1f," +
                        " portada='%s', autores='%s'}",
                id,titulo,genero,idioma,numeroDescargas,portada,autores);

    }
}
