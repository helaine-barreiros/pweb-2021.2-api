package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.tos;

import java.beans.Transient;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;

    private LocalDateTime dataInclusao;
    private LocalDateTime dataUltimaAlteracao;

    @Transient
    @JsonIgnore
    public Usuario getUsuario() {
        return Usuario.builder().id(this.id).nome(this.nome).email(this.email).senha(this.senha)
                .dataInclusao(this.dataInclusao).dataUltimaAlteracao(this.dataUltimaAlteracao)
                .build();
    }
}
