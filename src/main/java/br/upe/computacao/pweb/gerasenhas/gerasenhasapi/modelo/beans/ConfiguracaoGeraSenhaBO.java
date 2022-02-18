package br.upe.computacao.pweb.gerasenhas.gerasenhasapi.modelo.beans;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Range;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguracaoGeraSenhaBO {

    @Range(min = 8, max = 40, message = "O tamanho da senha deve ser entre 8 e 40 caracteres")
    @NotNull(message = "O tamanho da senha deve ser informado")
    private Integer tamanho;

    @NotNull(message = "Informe se a senha deve conter caracteres maiúsculos")
    private Boolean maiuscula;

    @NotNull(message = "Informe se a senha deve conter caracteres minúsculos")
    private Boolean minuscula;

    @NotNull(message = "Informe se a senha deve conter caracteres numéricos")
    private Boolean numeros;

    @NotNull(message = "Informe se a senha deve conter caracteres especiais")
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

    @JsonIgnore
    public boolean isNaoConfigurado() {
        return this.maiuscula == null && this.minuscula == null && this.especiais == null
                && this.numeros == null && this.tamanho == null;
    }

    @JsonIgnore
    public boolean isConfigurado() {
        return !this.isNaoConfigurado();
    }
}
