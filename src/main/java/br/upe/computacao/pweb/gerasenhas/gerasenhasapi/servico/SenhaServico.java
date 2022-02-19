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
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.dao.ISenhaDAO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.dao.IUsuarioDAO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Senha;

@Service
public class SenhaServico implements ISenhaServico {

    @Autowired
    private ISenhaDAO senhaDAO;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    private Validator validator;

    public SenhaServico() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public List<Senha> listar(Long idUsuario) {
        return (List<Senha>) senhaDAO.findAll();
    }

    @Override
    public Senha incluir(Senha senha) {
        String erros = this.obterViolacoes(senha);

        if (StringUtils.hasText(erros)) {
            throw new GeraSenhasException("Ocorreu um erro ao incluir a senha: " + erros);
        }

        if (!this.usuarioDAO.existsById(senha.getUsuario().getId())) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao incluir a senha: o usuário informado não existe.");
        }

        Optional<Senha> existente = senhaDAO.findByRotuloIgnoreCase(senha.getRotulo());
        if (existente.isPresent()) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao incluir a senha: já existe senha cadastrado com o rótulo "
                            + senha.getRotulo());
        }

        senha.setId(null);
        senha.setDataInclusao(LocalDateTime.now());

        return senhaDAO.save(senha);
    }

    @Override
    public Senha alterar(Senha senha) {
        String erros = this.obterViolacoes(senha);

        if (StringUtils.hasText(erros)) {
            throw new GeraSenhasException("Ocorreu um erro ao alterar a senha: " + erros);
        }

        if (senha.getId() == null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao alterar a senha: informe o identificador");
        }

        Optional<Senha> anterior = senhaDAO.findById(senha.getId());

        if (!anterior.isPresent()) {
            throw new NaoEncontradoException(
                    "Ocorreu um erro ao alterar a senha: senha não encontrada");
        }

        Senha senhaExistente =
                this.senhaDAO.findByRotuloIgnoreCaseAndIdNot(senha.getRotulo(), senha.getId());

        if (senhaExistente != null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao alterar a senha: já existe senha cadastrado com o rótulo "
                            + senha.getRotulo());
        }

        anterior.get().setRotulo(senha.getRotulo());
        anterior.get().setDataUltimaAlteracao(LocalDateTime.now());

        return senhaDAO.save(anterior.get());
    }

    @Override
    public void excluir(Long id) {
        if (id == null) {
            throw new GeraSenhasException(
                    "Ocorreu um erro ao excluir a senha: informe o identificador");
        }

        if (!senhaDAO.existsById(id)) {
            throw new NaoEncontradoException(
                    "Ocorreu um erro ao excluir a senha: senha não encontrada");
        }

        senhaDAO.deleteById(id);
    }

    private String obterViolacoes(Senha senha) {
        String mensagem = null;

        if (senha == null) {
            mensagem = "dados nulos";
        } else {
            mensagem = this.validator.validate(senha).stream()
                    .map(cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage())
                    .collect(Collectors.joining(", "));
        }

        return mensagem;
    }
}
