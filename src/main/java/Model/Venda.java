package Model;
 
public class Venda {
    // Atributos
    int id;
    String cliente;
    String valor;
    String data;
    String quantidadeDeProdutos;

    // Construtor
    public Venda(int id, String cliente, String valor, String data, String quantidadeDeProdutos) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.data = data;
        this.quantidadeDeProdutos = quantidadeDeProdutos;
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

    
}
