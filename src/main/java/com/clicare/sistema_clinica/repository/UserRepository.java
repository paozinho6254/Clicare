package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Busca um usuário pelo seu endereço de email.
     * O Spring Data JPA "entende" o nome do método e cria a query SQL correspondente:
     * "SELECT * FROM usuarios WHERE email = ?"
     *
     * @param email O email a ser pesquisado.
     * @return um Optional contendo o Usuário se encontrado, ou vazio caso contrário.
     */
    Optional<User> findByEmail(String email);
}