package pro.gsilva.catalogo.service;

import java.util.List;

import pro.gsilva.catalogo.model.Musica;

public interface CatalogoService {
    List<Musica> findAll();
    Musica findById(long id);
    Musica save(Musica musica);
    void excluir(long id);

    List<Musica> findByTituloAndCategory(String titulo, long id_category);
}
