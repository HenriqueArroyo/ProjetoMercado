package com.projeto.Model;

public class Cliente {
    

    private String nome;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getdataNascimento() {
        return dataNascimento;
    }
    public void setdataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente(String nome, String cpf, String dataNascimento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
       
    }
    public void add(Cliente clientes) {
    }
}