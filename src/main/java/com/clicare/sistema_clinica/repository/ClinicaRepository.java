package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Integer> {

    /**
     * Busca uma clínica pelo seu CNPJ único.
     *
     * @param cnpj O CNPJ da clínica a ser buscado.
     * @return um Optional contendo a Clinica se encontrada.
     */
    Optional<Clinica> findByCnpj(String cnpj);

    /**
     * Permite uma busca por clínicas cujo nome de exibição contenha o texto pesquisado.
     * A cláusula 'ContainingIgnoreCase' gera uma query com "LIKE '%texto%'" e ignora maiúsculas/minúsculas.
     *
     * @param nome O texto a ser pesquisado no nome da clínica.
     * @return Uma lista de clínicas que correspondem ao critério de busca.
     */
    List<Clinica> findByUsuarioNomeExibicaoContainingIgnoreCase(String nome);
}