package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Usuario;

@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCaseAndIdNot(String email, Long id);

    Optional<Usuario> findByEmailIgnoreCase(String email);
}
