package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Dependente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gerenciar a entidade Dependente.
 * Fornece métodos para acessar e manipular os dados dos dependentes no banco de dados.
 */
@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Integer> {

    /**
     * Encontra todos os dependentes associados a um paciente titular específico, buscando pelo ID do titular.
     * O Spring Data JPA cria a query SQL automaticamente a partir do nome do método, navegando
     * pela relação: Dependente -> Paciente -> ID.
     *
     * @param idPaciente O ID do paciente titular cujos dependentes devem ser listados.
     * @return Uma lista de Dependentes pertencentes ao paciente titular. A lista estará vazia se nenhum for encontrado.
     */
    List<Dependente> findByPacienteTitularId(Integer idPaciente);

    /**
     * Busca um dependente específico pelo seu número de CPF.
     * Útil para validações ou buscas onde o CPF é o identificador.
     *
     * @param cpf O CPF do dependente a ser pesquisado.
     * @return um Optional contendo o Dependente se encontrado, ou vazio caso contrário.
     */
    Optional<Dependente> findByCpf(String cpf);
}