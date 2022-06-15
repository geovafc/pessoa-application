package com.backendsucesso.pessoa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.backendsucesso.pessoa.domain.PessoaFisica} entity.
 */
public class PessoaFisicaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private String idade;

    @NotNull
    private String email;

    @NotNull
    private String telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaFisicaDTO)) {
            return false;
        }

        PessoaFisicaDTO pessoaFisicaDTO = (PessoaFisicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pessoaFisicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaFisicaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", idade='" + getIdade() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
