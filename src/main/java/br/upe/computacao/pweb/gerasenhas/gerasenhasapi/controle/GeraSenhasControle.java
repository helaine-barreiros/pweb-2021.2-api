package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.controle;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.ConfiguracaoGeraSenhaBO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.SenhaBO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico.GeraSenhaServico;

@RequestMapping("/api/v1/")
@RestController
@CrossOrigin(origins = "*")
public class GeraSenhasControle {

    @Autowired
    private GeraSenhaServico servico;

    @PostMapping("/senha")
    public ResponseEntity<SenhaBO> gerarSenha(
            @Valid @RequestBody ConfiguracaoGeraSenhaBO configuracao) {
        return new ResponseEntity<SenhaBO>(servico.gerarSenha(configuracao), HttpStatus.CREATED);
    }
}
