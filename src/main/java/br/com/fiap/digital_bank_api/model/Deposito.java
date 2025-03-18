package br.com.fiap.digital_bank_api.model;

public class Deposito {
    private Long id;
    private Double valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
