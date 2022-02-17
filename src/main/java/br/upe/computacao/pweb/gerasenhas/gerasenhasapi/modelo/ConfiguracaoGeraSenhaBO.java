package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConfiguracaoGeraSenhaBO {

    private Integer tamanho;
    private Boolean maiuscula;
    private Boolean minuscula;
    private Boolean numeros;
    private Boolean especiais;

    public int obterQuantidadeConfiguracoes() {
        int qtd = 0;

        qtd += this.maiuscula != null && this.maiuscula ? 1 : 0;
        qtd += this.minuscula != null && this.minuscula ? 1 : 0;
        qtd += this.numeros != null && this.numeros ? 1 : 0;
        qtd += this.especiais != null && this.especiais ? 1 : 0;

        return qtd;
    }

    public int qtdCaracteresPorToken() {
        return (int) (this.tamanho / this.obterQuantidadeConfiguracoes());
    }

    public boolean isNaoConfigurado() {
        return this.maiuscula == null && this.minuscula == null && this.especiais == null
                && this.numeros == null && this.tamanho == null;
    }

    public boolean isConfigurado() {
        return !this.isNaoConfigurado();
    }
}
