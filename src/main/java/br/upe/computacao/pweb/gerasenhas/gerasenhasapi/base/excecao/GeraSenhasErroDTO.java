package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.base.excecao;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeraSenhasErroDTO {

    private Integer status;
    private String titulo;
    private LocalDateTime horario;
    private String erro;

}
