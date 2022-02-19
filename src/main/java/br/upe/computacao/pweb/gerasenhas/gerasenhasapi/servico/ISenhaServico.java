package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico;

import java.util.List;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Senha;

public interface ISenhaServico {

    List<Senha> listar(Long idUsuario);

    Senha incluir(Senha senha);

    Senha alterar(Senha senha);

    void excluir(Long id);

}
