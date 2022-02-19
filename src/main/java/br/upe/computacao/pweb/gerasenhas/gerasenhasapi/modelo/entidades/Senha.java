package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class Senha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Informe o rotulo da senha")
    private String rotulo;

    @NotEmpty(message = "Informe a senha")
    private String senha;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataUltimaAlteracao;

    @NotNull(message = "Informe o usuario associado a senha")
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
