package Controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.EstoqueDAO;
import Model.Estoque;

public class EstoqueController {
     // Atributos
    private List<Estoque> estoques; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public EstoqueController(List<Estoque> estoques, DefaultTableModel tableModel, JTable table) {
        this.estoques = estoques; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        estoques = new EstoqueDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Estoque estoques : estoques) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { estoques.getProduto(), estoques.getId(), estoques.getPreco(), estoques.getDesconto(), estoques.getQtd() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String id, String produto, String qtd, String preco, String desconto) {
        new EstoqueDAO().cadastrar(id, produto, qtd, preco, desconto);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String id, String produto, String qtd, String preco, String desconto) {
        new EstoqueDAO().atualizar(id, produto, qtd, preco, desconto);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String id) {
        new EstoqueDAO().apagar(id); 
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }
}
