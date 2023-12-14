package com.projeto.Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.projeto.Model.*;
import com.projeto.Connection.*;

public class ClientesControl {

    // Atributos
    private List<Cliente> clientes; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public ClientesControl(List<Cliente> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        if (tableModel != null) {
            tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
            clientes = new ClientesDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
            for (Cliente cliente : clientes) {
                // Adiciona os dados de cada carro como uma nova linha na tabela Swing
                tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(), cliente.getdataNascimento(),
                        cliente.getTelefone() });
            }
        } else {
            System.out.println("O modelo da tabela não foi inicializado corretamente.");
        }

        if (table != null) {
            tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
            clientes = new ClientesDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
            for (Cliente cliente : clientes) {
                // Adiciona os dados de cada carro como uma nova linha na tabela Swing
                tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(), cliente.getdataNascimento(),
                        cliente.getTelefone() });
            }
        } else {
            System.out.println("A tabela não foi inicializada corretamente.");
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String nome, String cpf, String telefone, String dataNascimento) {
        new ClientesDAO().cadastrar(nome, cpf, telefone, dataNascimento);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String nome, String cpf, String telefone, String dataNascimento) {
        new ClientesDAO().atualizar(nome, cpf, telefone, dataNascimento);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

    public boolean verificarCPF(String cpf) {
        ClientesDAO clientesDAO = new ClientesDAO();
        if (clientesDAO.verificarCPFExistente(cpf)) {
            JOptionPane.showMessageDialog(null, "DEU GREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEN");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "CPF não encontrado. Por favor, informe um CPF válido.");
            return false;
        }
    }

    public void limpar(String text, String text2, String text3, String text4) {
    }
}