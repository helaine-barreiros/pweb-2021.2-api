package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico;

import java.util.List;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;

public interface IUsuarioServico {

    List<Usuario> listar();

    Usuario incluir(Usuario usuario);

    Usuario alterar(Usuario usuario);

    void excluir(Long id);
}
