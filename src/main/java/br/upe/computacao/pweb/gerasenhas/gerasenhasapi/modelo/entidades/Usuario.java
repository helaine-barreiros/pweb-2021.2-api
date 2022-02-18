package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Informe o nome do usuário")
    private String nome;

    @NotEmpty(message = "Informe o email do usuário")
    @Email(message = "Infome o email em um formato válido")
    private String email;

    @NotEmpty(message = "Informe a senha do usuário")
    private String senha;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataUltimaAlteracao;
}
