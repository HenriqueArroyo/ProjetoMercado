package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import Model.Estoque;

public class EstoqueDAO {
    
    
    // atributos
    private Connection connection;
    private List<Estoque> estoques;

    // construtor
    public EstoqueDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // Criar a Tabela no banco de dados
    public void criaTabela() {

        String sql = "CREATE TABLE IF NOT EXISTS estoque_mercado (PRODUTO VARCHAR(255), ID VARCHAR(255) PRIMARY KEY, PRECO VARCHAR(255), DESCONTO VARCHAR(255), QTD VARCHAR(255) )";
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
    public List<Estoque> listarTodos() {
        PreparedStatement stmt = null; // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null; // Declaração do objeto ResultSet para armazenar os resultados da consulta
        estoques = new ArrayList<>(); // Cria uma lista para armazenar os carros recuperados do banco de dados
    
        try {
            stmt = connection.prepareStatement("SELECT * FROM estoque_mercado"); 
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery(); 
            // Executa a consulta e armazena os resultados no ResultSet
    
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do registro
                Estoque estoques = new Estoque(
                    rs.getString("produto"),
                    rs.getString("qtd"),
                    rs.getString("id"),
                    rs.getString ("preco"),
                    rs.getString ("desconto")
                );
                estoques.add(estoques); 
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs); // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return estoques; // Retorna a lista de carros recuperados do banco de dados
    }

    //Cadastrar Carro no banco
    public void cadastrar(String produto, String id, String qtd, String preco, String desconto) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO estoque_mercado (id, produto, qtd, preco, desconto ) VALUES (?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, produto);
            stmt.setString(3, qtd);
            stmt.setString(4, preco);
            stmt.setString(5, desconto);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com Sucesso✅");
        } catch (SQLException e) {
           if (e.getSQLState().equals("23505")) {
            JOptionPane.showMessageDialog(null, "\"Erro: O id inserido já existe na tabela.\"");
           } else {
             throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
           }
        } finally {
            ConnectionFactory.closeConnection(connection,stmt);
        }
    }

    //Atualizar dados no banco
    public void atualizar(String id, String produto, String qtd, String preco, String desconto) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE funcionarios_mercado SET produto = ?, qtd = ?, preco = ?, desconto = ?,    WHERE id = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto);
            stmt.setString(2, preco);
            stmt.setString(3, desconto);
            stmt.setString(4, qtd);
            //placa é chave primaria não pode ser alterada.
            stmt.setString(5, id);
            
            stmt.executeUpdate();
            
            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String id) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela placa
        String sql = "DELETE FROM funcionarios_mercado WHERE id = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);

        }
    }

}
