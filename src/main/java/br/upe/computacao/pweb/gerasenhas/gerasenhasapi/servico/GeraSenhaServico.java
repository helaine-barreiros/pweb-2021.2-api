package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.servico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.base.excecao.GeraSenhasException;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.ConfiguracaoGeraSenhaBO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans.SenhaBO;
import br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.enumeracao.CaracterEspecialDataEnum;

@Service
public class GeraSenhaServico implements IGeraSenhaServico {

    public SenhaBO gerarSenha(ConfiguracaoGeraSenhaBO configuracao) {
        PasswordGenerator gen = new PasswordGenerator();


        if (configuracao == null) {
            throw new GeraSenhasException(
                    "Você precisa informar os parâmetros de configuração para gerar uma senha!");
        }

        if (configuracao.getMaiuscula() == null && configuracao.getMinuscula() == null
                && configuracao.getEspeciais() == null && configuracao.getNumeros() == null
                && configuracao.getTamanho() == null) {
            throw new GeraSenhasException(
                    "Você precisa configurar ao menos um dos parâmetros de configuração para gerar uma senha!");
        }

        List<CharacterRule> regras = new ArrayList<CharacterRule>();

        int sobra = configuracao.getTamanho() % configuracao.obterQuantidadeConfiguracoes();

        if (configuracao.getMinuscula()) {
            regras.add(new CharacterRule(EnglishCharacterData.LowerCase,
                    (sobra + configuracao.qtdCaracteresPorToken())));
            sobra = 0;
        }

        if (configuracao.getMaiuscula()) {
            regras.add(new CharacterRule(EnglishCharacterData.LowerCase,
                    (sobra + configuracao.qtdCaracteresPorToken())));
            sobra = 0;
        }

        if (configuracao.getNumeros()) {
            regras.add(new CharacterRule(EnglishCharacterData.Digit,
                    (sobra + configuracao.qtdCaracteresPorToken())));
            sobra = 0;
        }

        if (configuracao.getEspeciais()) {
            regras.add(new CharacterRule(CaracterEspecialDataEnum.Special_Unicode,
                    (sobra + configuracao.qtdCaracteresPorToken())));
        }

        return SenhaBO.builder().senha(gen.generatePassword(configuracao.getTamanho(), regras))
                .geracao(LocalDateTime.now()).configuracao(configuracao).build();
    }
}

