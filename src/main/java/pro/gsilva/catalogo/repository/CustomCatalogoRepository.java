package pro.gsilva.catalogo.repository;

import pro.gsilva.catalogo.model.Musica;

import java.util.List;

public interface CustomCatalogoRepository {
    List<Musica> findAllByTituloUpper(String titulo);
}
