package com.corinto.primeiroexemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corinto.primeiroexemplo.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}