package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.entidades.Senha;

@Repository
public interface ISenhaDAO extends CrudRepository<Senha, Long> {

    Optional<Senha> findByRotuloIgnoreCase(String rotulo);

    Senha findByRotuloIgnoreCaseAndIdNot(String rotulo, Long id);
}
