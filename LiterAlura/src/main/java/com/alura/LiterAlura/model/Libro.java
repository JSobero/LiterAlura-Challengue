package com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private int descargas;
    @ManyToOne
    private Persona autor;

    public Libro(){

    }
    public Libro(DatosLibro datosLibro, Persona datosDelAutor) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idioma().get(0));
        this.autor = datosDelAutor;
        this.descargas = datosLibro.descargas();
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public String getAutor() {
        return autor.toString();
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "---------DATOS DEL LIBRO--------\n" +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor.toString() +
                "\nIdioma: " + idioma + "\n" +
                "Descargas: "+descargas +"\n";
    }
}
