package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.controle;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Senha;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.tos.SenhaTO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico.ISenhaServico;

@RequestMapping("/api/v1/")
@RestController
@CrossOrigin(origins = "*")
public class SenhaControle {

    @Autowired
    private ISenhaServico servico;

    @GetMapping("/senhas/{idUsuario}")
    public ResponseEntity<?> listar(@Valid @PathVariable(value = "idUsuario") Long idUsuario) {
        ResponseEntity<?> resposta = null;

        List<Senha> senhas = servico.listar(idUsuario);

        if (senhas != null && !senhas.isEmpty()) {

            List<SenhaTO> tos =
                    senhas.stream().map(senha -> getSenhaTO(senha)).collect(Collectors.toList());

            resposta = ResponseEntity.ok().body(tos);
        } else {
            resposta = ResponseEntity.noContent().build();
        }

        return resposta;
    }

    @PostMapping("/senha")
    public ResponseEntity<SenhaTO> incluir(@Valid @RequestBody SenhaTO senha) {
        Senha registro = this.servico.incluir(senha.getSenha());

        return new ResponseEntity<SenhaTO>(getSenhaTO(registro), HttpStatus.CREATED);
    }

    @PutMapping("/senha/{id}")
    public ResponseEntity<SenhaTO> alterar(@Valid @RequestBody SenhaTO senha) {
        Senha registro = this.servico.alterar(senha.getSenha());

        return ResponseEntity.ok().body(getSenhaTO(registro));
    }

    @DeleteMapping({"/senha/{id}"})
    public ResponseEntity<SenhaTO> excluir(@Valid @PathVariable(value = "id") Long id) {
        this.servico.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private SenhaTO getSenhaTO(Senha senha) {

        return SenhaTO.builder().id(senha.getId()).rotulo(senha.getRotulo()).senha(senha.getSenha())
                .idUsuario(senha.getUsuario().getId()).dataInclusao(senha.getDataInclusao())
                .dataUltimaAlteracao(senha.getDataUltimaAlteracao()).build();

    }
}
