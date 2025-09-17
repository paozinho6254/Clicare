package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, Integer> {

    /**
     * Verifica de forma otimizada se já existe um paciente com o CPF informado.
     * Gera uma query mais performática (SELECT COUNT(...) ou similar) do que buscar a entidade inteira.
     *
     * @param cpf O CPF a ser verificado.
     * @return true se o CPF já existir, false caso contrário.
     */
    boolean existsByCpf(String cpf);

    /**
     * Busca um paciente completo pelo seu CPF.
     * Útil para funcionalidades onde o usuário pode se identificar pelo CPF.
     *
     * @param cpf O CPF do paciente a ser buscado.
     * @return um Optional contendo o Paciente se encontrado.
     */
    Optional<Pacient> findByCpf(String cpf);
}