package br.com.alura.comex.repositories;

import br.com.alura.comex.entities.Categoria;
import br.com.alura.comex.repositories.projections.CategoriaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNome(String nome);
    @Query(value = "SELECT categorias.nome, SUM(produtos.preco_unitario) AS montante, COUNT(produtos.id) AS quantidade " +
            "FROM categorias JOIN produtos ON categorias.id = produtos.categoria_id GROUP BY categorias.nome;", nativeQuery = true)
    List<CategoriaProjection> findCategoriaRelatorio();
}
