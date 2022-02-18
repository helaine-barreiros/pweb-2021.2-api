package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.enumeracao;

import org.passay.CharacterData;

public enum CaracterEspecialDataEnum implements CharacterData {
    Special_Unicode("INSUFFICIENT_SPECIAL", "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

    private final String errorCode;
    private final String characters;


    CaracterEspecialDataEnum(final String code, final String charString) {
        errorCode = code;
        characters = charString;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getCharacters() {
        return characters;
    }
}
