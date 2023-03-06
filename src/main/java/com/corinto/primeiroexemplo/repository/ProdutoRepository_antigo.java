package com.corinto.primeiroexemplo.repository;

import java.util.ArrayList;
//import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.corinto.primeiroexemplo.model.Produto;
import com.corinto.primeiroexemplo.model.exception.ResourceNotFoundException;

@Repository
public class ProdutoRepository_antigo {
    
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    public List<Produto> obterTodos() {
        return produtos;
    }

    public Optional<Produto> obterPorId(Integer id) {
        return produtos.stream().filter(produto -> produto.getId() == id).findFirst();
    }

     public Produto adicionar(Produto produto) {
        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);
        return produto;
    }

    public void deletar(Integer id) {
        produtos.removeIf(produto -> produto.getId() == id);
    }

    public Produto atualizar(Produto produto) {
        
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        
        deletar(produto.getId());        
        produtos.add(produto);

        return produto;
    }

}
