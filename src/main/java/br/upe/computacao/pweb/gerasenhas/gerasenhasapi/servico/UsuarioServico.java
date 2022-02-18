package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.base.excecao.GeraSenhasException;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.base.excecao.NaoEncontradoException;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.dao.IUsuarioDAO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;

@Service
public class UsuarioServico {

    @Autowired
    private IUsuarioDAO usuarioDAO;
    private Validator validator;

    public UsuarioServico() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public List<Usuario> listar() {
        return (List<Usuario>) usuarioDAO.findAll();
    }

    public Usuario incluir(Usuario usuario) {
        String erros = this.obterViolacoes(usuario);

        if (StringUtils.hasText(erros)) {
            throw new GeraSenhasException("Ocorreu um erro ao incluir o usuário: " + erros);
        }

        Optional<Usuario> existente = usuarioDAO.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao incluir o usuário: já existe usuário cadastrado com o email "
                            + usuario.getEmail());
        }

        usuario.setId(null);
        usuario.setDataInclusao(LocalDateTime.now());

        return usuarioDAO.save(usuario);
    }

    public Usuario alterar(Usuario usuario) {
        String erros = this.obterViolacoes(usuario);

        if (StringUtils.hasText(erros)) {
            throw new GeraSenhasException("Ocorreu um erro ao alterar o usuário: " + erros);
        }

        if (usuario.getId() == null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao alterar o usuário: informe o identificador");
        }

        Optional<Usuario> anterior = usuarioDAO.findById(usuario.getId());

        if (!anterior.isPresent()) {
            throw new NaoEncontradoException("Usuário não encontrado");
        }

        Usuario usuarioExistente =
                this.usuarioDAO.findByEmailAndIdNot(usuario.getEmail(), usuario.getId());

        if (usuarioExistente != null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao alterar o usuário: já existe usuário cadastrado com o email "
                            + usuario.getEmail());
        }

        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        return usuarioDAO.save(usuario);
    }

    public void excluir(Long id) {
        if (id == null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao excluir o usuário: informe o identificador");
        }

        Optional<Usuario> usuario = usuarioDAO.findById(id);

        if (!usuario.isPresent()) {
            throw new NaoEncontradoException("Usuário não encontrado");
        }

        usuarioDAO.deleteById(id);
    }

    private String obterViolacoes(Usuario usuario) {
        String mensagem = null;

        if (usuario == null) {
            mensagem = "dados nulos";
        } else {
            mensagem = this.validator.validate(usuario).stream()
                    .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
                    .collect(Collectors.joining(", "));
        }

        return mensagem;
    }
}
