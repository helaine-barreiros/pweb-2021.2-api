package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.controle;

import java.util.List;
import java.util.stream.Collectors;
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
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.tos.UsuarioTO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico.UsuarioServico;

@RequestMapping("/api/v1/")
@RestController
@CrossOrigin(origins = "*")
public class UsuarioControle {

    @Autowired
    private UsuarioServico servico;

    @GetMapping("/usuarios")
    public ResponseEntity<?> listar() {
        ResponseEntity<?> resposta = null;

        List<Usuario> usuarios = servico.listar();

        if (usuarios != null && !usuarios.isEmpty()) {

            List<UsuarioTO> tos = usuarios.stream().map(usuario -> getUsuarioTO(usuario))
                    .collect(Collectors.toList());

            resposta = ResponseEntity.ok().body(tos);
        } else {
            resposta = ResponseEntity.noContent().build();
        }

        return resposta;
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioTO> incluir(@RequestBody UsuarioTO usuario) {
        Usuario registro = this.servico.incluir(usuario.getUsuario());

        return new ResponseEntity<UsuarioTO>(getUsuarioTO(registro), HttpStatus.CREATED);
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioTO> alterar(@RequestBody UsuarioTO usuario) {
        Usuario registro = this.servico.alterar(usuario.getUsuario());

        return ResponseEntity.ok().body(getUsuarioTO(registro));
    }

    @DeleteMapping({"/usuario/{id}"})
    public ResponseEntity<UsuarioTO> excluir(@PathVariable(value = "id") Long id) {
        this.servico.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UsuarioTO getUsuarioTO(Usuario usuario) {
        return UsuarioTO.builder().id(usuario.getId()).nome(usuario.getNome())
                .email(usuario.getEmail()).senha(usuario.getSenha())
                .dataInclusao(usuario.getDataInclusao())
                .dataUltimaAlteracao(usuario.getDataUltimaAlteracao()).build();
    }
}
