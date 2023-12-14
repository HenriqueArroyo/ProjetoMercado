package com.projeto.Model;
 
public class Venda {
    // Atributos
    int id;
    String cliente;
    String valor;
    String data;
    String quantidadeDeProdutos;
    String pagamento;

    // Construtor
    public Venda(int id, String cliente, String valor, String data, String quantidadeDeProdutos, String pagamento) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.data = data;
        this.quantidadeDeProdutos = quantidadeDeProdutos;
        this.pagamento = pagamento;
    }

    // Getters and Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuantidadeDeProdutos() {
        return quantidadeDeProdutos;
    }

    public void setQuantidadeDeProdutos(String quantidadeDeProdutos) {
        this.quantidadeDeProdutos = quantidadeDeProdutos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPagamento() {
       return pagamento;
    }

    public void  setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

}