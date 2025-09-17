package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {

    /**
     * Busca uma especialidade pelo seu nome exato (ignorando maiúsculas/minúsculas).
     * Usado para verificar se uma especialidade já existe antes de criar uma nova.
     *
     * @param nome O nome da especialidade.
     * @return um Optional contendo a Especialidade, se encontrada.
     */ 
    Optional<Especialidade> findByNomeIgnoreCase(String nome);
}