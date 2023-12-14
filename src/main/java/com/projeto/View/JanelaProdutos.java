package com.projeto.View;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.*;
import com.projeto.Controller.*;
import com.projeto.Model.*;

public class JanelaProdutos extends JPanel {
    EstoqueControl control;
    // Componentes
    private JPanel buttonPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos = new ArrayList<>();

    private JScrollPane jSPane;

    public JanelaProdutos() {
        super();

        // Configuração do layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Título
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Lista Produtos"));
        add(title);

        // Painel de botões
        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Código", "Nome do Produto", "Preço" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        buttonPanel = new JPanel();

        add(buttonPanel);

        // Criação da tabela no banco de dados
        new EstoqueDAO().criaTabela();
        // Atualização inicial da tabela
        atualizarTabela();

        // Instância do controlador
        control = new EstoqueControl(produtos, tableModel, table);

    }

    // Método para atualizar a tabela
    public void atualizarTabela() {
        tableModel.setRowCount(0);
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            tableModel.addRow(new Object[] { produto.getId(), produto.getNomeDoProduto(), produto.getPreco(),
            });
        }
    }
}