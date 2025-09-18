package com.clicare.sistema_clinica.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object (DTO) para receber os dados de cadastro de um novo Médico.
 * Contém validações para garantir que os dados recebidos pela API sejam consistentes.
 */
@Data // Gera Getters, Setters, toString, etc.
public class MedicoCadastroDTO {

    // --- Dados para a entidade Usuario ---

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O formato do email é inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;


    // --- Dados para a entidade Medico ---

    @NotBlank(message = "O número do CRM é obrigatório")
    @Pattern(regexp = "\\d+", message = "O CRM deve conter apenas números")
    private String crm;

    @NotBlank(message = "A UF do CRM é obrigatória")
    @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres (ex: SP, RJ)")
    private String crmUf;

    // O telefone pode ser opcional, dependendo da regra de negócio
    private String telefone;
}