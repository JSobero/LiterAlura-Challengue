package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Idioma;
import com.alura.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findByIdioma(Idioma idiomaBusqueda);
}
