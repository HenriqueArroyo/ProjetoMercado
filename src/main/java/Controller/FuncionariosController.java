package Controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Connection.FuncionariosDAO;
import Model.Funcionarios;

public class FuncionariosController {
    
    // Atributos
    private List<Funcionarios> funcionarios; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public FuncionariosControl(List<Funcionarios> funcionarios, DefaultTableModel tableModel, JTable table) {
        this.funcionarios = funcionarios; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        funcionarios = new FuncionariosDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Funcionarios funcionarios : funcionarios) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { funcionarios.getNome(), funcionarios.getCpf(), funcionarios.getSenha(), funcionarios.getIdade(), funcionarios.getEmail(), funcionarios.getTelefone() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String cpf, String nome, String telefone, String senha, String idade, String email) {
        new FuncionariosDAO().cadastrar(cpf, nome, telefone, senha, idade, email);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String cpf, String nome, String telefone, String senha, String idade, String email) {
        new FuncionariosDAO().atualizar(cpf, nome, telefone, senha, idade, email);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new FuncionariosDAO().apagar(cpf); 
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

}
