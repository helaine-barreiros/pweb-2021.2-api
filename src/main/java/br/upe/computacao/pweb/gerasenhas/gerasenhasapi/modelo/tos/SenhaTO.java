package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.tos;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Senha;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SenhaTO {

    private Long id;

    @NotEmpty(message = "Informe o rotulo da senha")
    private String rotulo;

    @NotEmpty(message = "Informe a senha")
    private String senha;

    @NotNull(message = "Informe o identificador do usuário")
    private Long idUsuario;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataUltimaAlteracao;

    public Senha getSenha() {
        return Senha.builder().id(this.id).rotulo(this.rotulo).senha(this.senha)
                .usuario(Usuario.builder().id(this.idUsuario).build()).build();
    }
}
