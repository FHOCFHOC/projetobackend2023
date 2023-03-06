package com.corinto.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corinto.primeiroexemplo.model.Produto;
import com.corinto.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.corinto.primeiroexemplo.repository.ProdutoRepository;
import com.corinto.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> obterTodos() {
        
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> obterPorId(Integer id) {
        
        Optional<Produto> produto = produtoRepository.findById(id);
        
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");
        }
        
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);        
        return Optional.of(dto);
    }

    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
        
        produtoDto.setId(null);
        ModelMapper mapper = new ModelMapper();
        Produto produto = mapper.map(produtoDto, Produto.class);
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());

        return produtoDto;
    }

    
    public void deletar(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possivel deletar o produto com id: " + id
                    + " Produto não existe");
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
        produtoDto.setId(id);
        ModelMapper mapper = new ModelMapper();
        Produto produto = mapper.map(produtoDto, Produto.class);
        produtoRepository.save(produto);
        return produtoDto;
    }   
}
