package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico;

import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.ConfiguracaoGeraSenhaBO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.SenhaBO;

public interface IGeraSenhaServico {

    SenhaBO gerarSenha(ConfiguracaoGeraSenhaBO configuracao);

}
