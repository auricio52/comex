package br.com.alura.comex.repositories.projections;

import java.math.BigDecimal;

public interface CategoriaProjection {
    String getNome();
    BigDecimal getMontante();
    Long getQuantidade();
}
