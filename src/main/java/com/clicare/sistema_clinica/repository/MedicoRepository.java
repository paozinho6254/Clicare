package com.clicare.sistema_clinica.repository;

import com.clicare.sistema_clinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    /**
     * Busca um médico pela sua combinação única de CRM e UF.
     *
     * @param crm O número do CRM.
     * @param crmUf A Unidade Federativa do CRM (ex: "SP").
     * @return um Optional contendo o Medico se encontrado.
     */
    Optional<Medico> findByCrmAndCrmUf(String crm, String crmUf);

    /**
     * Encontra todos os médicos associados a uma especialidade específica, buscando pelo nome da especialidade.
     * Esta consulta com JPQL é necessária para navegar pela relação Many-to-Many.
     *
     * @param nomeEspecialidade O nome da especialidade (ex: "Cardiologia").
     * @return Uma lista de médicos que possuem a especialidade informada.
     */
    @Query("SELECT m FROM Medico m JOIN m.especialidades e WHERE e.nome = :nomeEspecialidade")
    List<Medico> findAllByEspecialidade(@Param("nomeEspecialidade") String nomeEspecialidade);
}
