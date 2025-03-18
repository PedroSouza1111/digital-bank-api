package br.com.fiap.digital_bank_api.model;

import java.util.Date;
import java.util.List;

public class Count {
    
    private Long id;
    private int agencia;
    private String nome;
    private Long cpf;
    private Date dataAbertura;
    private Double saldo;
    private boolean ativa;
    private String tipo;

    private static final List<String> tiposPermitidos = List.of("corrente", "poupança", "salário");

    public Count(Long id, int agencia, String nome, Long cpf, Date dataAbertura, Double saldo, boolean ativa, String tipo) {

        validarNome(nome);
        validarCpf(cpf);
        validarDataAbertura(dataAbertura);
        validarSaldo(saldo);
        validarTipo(tipo);

        this.id = id;
        this.agencia = agencia;
        this.nome = nome;
        this.cpf = cpf;
        this.dataAbertura = dataAbertura;
        this.saldo = saldo;
        this.ativa = ativa;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public int getAgencia() {
        return agencia;
    }

    public String getNome() {
        return nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public Double getSaldo() {
        if (saldo < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo!");
        }
        return saldo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório e não pode estar vazio!");
        }
    }

    private void validarCpf(Long cpf) {
        if (cpf <= 0) {
            throw new IllegalArgumentException("O CPF é obrigatório e deve ser um número válido!");
        }
    }

    private void validarSaldo(Double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("O saldo não pode ser negativo!");
        }
    }

    private void validarDataAbertura(Date dataAbertura) {
        Date hoje = new Date();
        if (dataAbertura.after(hoje)) {
            throw new IllegalArgumentException("A data de abertura não pode estar no futuro!");
        }
    }

    private void validarTipo(String tipo) {
        if (!tiposPermitidos.contains(tipo)) {
            throw new IllegalArgumentException("Tipo inválido! Os tipos permitidos são: " + tiposPermitidos);
        }
    }
    
    public void depositar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo!");
        }
        this.saldo += valor;
    }

}
