package com.projeto.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.projeto.Model.*;;

public class FuncionariosDAO {

    // atributos
    private Connection connection;
    private List<Funcionario> funcionarios;

    // construtor
    public FuncionariosDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // Criar a Tabela no banco de dados
    public void criaTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS funcionarios_mercado (NOME VARCHAR(255), CPF VARCHAR(255) PRIMARY KEY, DATANASCIMENTO VARCHAR(255), TELEFONE VARCHAR(255) )";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    public List<Funcionario> listarTodos() {
        PreparedStatement stmt = null; // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null; // Declaração do objeto ResultSet para armazenar os resultados da consulta
        funcionarios = new ArrayList<>(); // Cria uma lista para armazenar os carros recuperados do banco de dados

        try {
            stmt = connection.prepareStatement("SELECT * FROM funcionarios_mercado");
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet

            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro
                Funcionario funcionarios = new Funcionario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("DATANASCIMENTO"));
                funcionarios.add(funcionarios);
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs); // Fecha a conexão, o PreparedStatement e o
                                                                     // ResultSet
        }
        return funcionarios; // Retorna a lista de carros recuperados do banco de dados
    }

    // Cadastrar Carro no banco
    public void cadastrar(String cpf, String nome, String telefone, String DATANASCIMENTO) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO funcionarios_mercado (cpf, nome, telefone, DATANASCIMENTO ) VALUES (?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, DATANASCIMENTO);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com Sucesso✅");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "\"Erro: O CPF inserido já existe na tabela.\"");
            } else {
                throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
            }
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String cpf, String nome, String telefone, String DATANASCIMENTO) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE funcionarios_mercado SET nome = ?, DATANASCIMENTO = ?, telefone = ?,    WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, DATANASCIMENTO);
            stmt.setString(3, telefone);
            // placa é chave primaria não pode ser alterada.
            stmt.setString(4, cpf);

            stmt.executeUpdate();

            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String cpf) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela placa
        String sql = "DELETE FROM funcionarios_mercado WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);

        }
    }

    // Verificar se o CPF está presente no banco de dados
    public boolean verificarCPFExistente(String cpf) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement("SELECT COUNT(*) FROM funcionarios_mercado WHERE CPF = ?");
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }

        return false;
    }

}