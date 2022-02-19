package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.tos;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioTO {

    private Long id;

    @NotEmpty(message = "Informe o nome do usuário")
    private String nome;

    @NotEmpty(message = "Informe o email do usuário")
    private String email;

    private List<SenhaTO> senhas;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataUltimaAlteracao;

    @Transient
    @JsonIgnore
    public Usuario getUsuario() {
        return Usuario.builder().id(this.id).nome(this.nome).email(this.email)
                .dataInclusao(this.dataInclusao).dataUltimaAlteracao(this.dataUltimaAlteracao)
                .build();
    }
}
