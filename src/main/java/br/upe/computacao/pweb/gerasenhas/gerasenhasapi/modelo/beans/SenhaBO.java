package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SenhaBO {

    public String senha;
    private ConfiguracaoGeraSenhaBO configuracao;
    private LocalDateTime geracao;

}
