package pro.gsilva.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import pro.gsilva.catalogo.model.Musica;

import java.util.List;

public interface CatalogoRepository extends JpaRepository<Musica, Long>, CustomCatalogoRepository {
    List<Musica> findAllByTitulo(String titulo);

    @Query("select m from Musica m join m.categoria c where c.id = :id_category and m.titulo like :titulo")
    List<Musica> findAllByTituloAndCategoryIsLike(String titulo, long id_category);

    @Query("select m from Musica m where m.titulo like :titulo")
    List<Musica> findAllWithTituloLike(String titulo);

//    @Query("select m from Musica m join m.categoria c where c.id = :categoriaId")
//    List<Musica> findAllByCategoriaId(Long categoriaId);
}
