package Model;

public class Estoque {
    private String produto, qtd, id, preco, desconto;

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    

    public Estoque(String produto, String qtd, String id, String preco, String desconto) {
        this.produto = produto;
        this.qtd = qtd;
        this.id = id;
        this.preco = preco;
        this.desconto = desconto;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public void add(Estoque estoques) {
    }



    
}
