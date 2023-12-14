package com.projeto.Model;

public class Estoque {
    // Atributos
    int id;
    String nomeDoProduto;
    String preco;
    double precoCompra;
    String quantidade;
    int quantidadeCompra;

    // Construtor
    public Estoque(int id, String nomeDoProduto, String preco, String quantidade) {
        this.id = id;
        this.nomeDoProduto = nomeDoProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Estoque(String nomeDoProduto, double precoCompra, int quantidadeCompra) {
        this.nomeDoProduto = nomeDoProduto;
        this.precoCompra = precoCompra;
        this.quantidadeCompra = quantidadeCompra;
    }

    // Getters and Setters
    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(int quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(int precoCompra) {
        this.precoCompra = precoCompra;
    }

}