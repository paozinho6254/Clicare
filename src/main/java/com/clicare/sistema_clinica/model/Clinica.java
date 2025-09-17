package com.clicare.sistema_clinica.model;

import com.clicare.sistema_clinica.model.Endereco;
import com.clicare.sistema_clinica.model.Medico;
import com.clicare.sistema_clinica.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "clinica")
public class Clinica {

    @Id
    @Column(name = "id_clinica")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_clinica")
    private User usuario;

    @Column(name = "telefone_clinica", nullable = false)
    private String telefone;

    @Column(name = "cnpj_clinica", unique = true, nullable = false)
    private String cnpj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    // Lado inverso da relação N-N com Medico
    @ManyToMany(mappedBy = "clinicas")
    private Set<Medico> medicos;
}