package com.projeto.Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.projeto.Connection.*;
import com.projeto.Model.*;
import com.projeto.View.LoginFuncionario;

public class FuncionariosControl {

    // Atributos
    private List<Funcionario> funcionarios; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public FuncionariosControl(List<Funcionario> funcionarios, DefaultTableModel tableModel, JTable table) {
        this.funcionarios = funcionarios; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        funcionarios = new FuncionariosDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Funcionario funcionarios : funcionarios) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { funcionarios.getNome(), funcionarios.getCpf(),
                    funcionarios.getdataNascimento(), funcionarios.getTelefone() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String nome, String cpf, String telefone, String idade) {
        new FuncionariosDAO().cadastrar(nome, cpf, telefone, idade);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String cpf, String nome, String telefone, String idade) {
        new FuncionariosDAO().atualizar(cpf, nome, telefone, idade);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new FuncionariosDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }
     // Método para verificar se o CPF existe
     public boolean verificarCPF(String cpf) {
        FuncionariosDAO funcionariosDAO = new FuncionariosDAO();
        if (funcionariosDAO.verificarCPFExistente(cpf)) {
            // Lógica para login bem-sucedido
            LoginFuncionario loginFuncionario = new LoginFuncionario();
            loginFuncionario.dispose();

            JOptionPane.showMessageDialog(null, "Seja bem-vindo, portador do CPF: " + cpf);
            return true;
        } else {
            // Lógica para login falhado
            JOptionPane.showMessageDialog(null, "CPF não encontrado. Por favor, informe um CPF válido.");
            return false;
        }
    }

}