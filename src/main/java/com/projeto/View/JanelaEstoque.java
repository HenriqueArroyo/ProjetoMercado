package com.projeto.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.*;
import com.projeto.Controller.EstoqueControl;
import com.projeto.Model.Estoque;

public class JanelaEstoque extends JPanel {

    // Componentes
    private JPanel buttonPanel;
    private JButton cadastraProduto, editaProduto, apagaProduto;
    private JTextField inputNomeProduto, inputPreco, inputQuantidade, inputId;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public JanelaEstoque() {
        super();

        // Configuração do layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inicialização dos componentes
        cadastraProduto = new JButton("Cadastrar");
        apagaProduto = new JButton("Excluir");
        editaProduto = new JButton("Editar");
        inputNomeProduto = new JTextField(10);
        inputPreco = new JTextField(10);
        inputQuantidade = new JTextField(10);
        inputId = new JTextField(10);

        // Título
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Controle de Estoque"));
        add(title);

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 2, 4));
        inputPanel.add(new JLabel("Código:"));
        inputPanel.add(inputId);
        inputPanel.add(new JLabel("Nome do Produto:"));
        inputPanel.add(inputNomeProduto);
        inputPanel.add(new JLabel("Preço (Inteiro):"));
        inputPanel.add(inputPreco);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(inputQuantidade);
        add(inputPanel);

        // Painel de botões
        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Código", "Nome do Produto", "Preço", "Quantidade" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        buttonPanel = new JPanel();
        buttonPanel.add(cadastraProduto);
        buttonPanel.add(editaProduto);
        buttonPanel.add(apagaProduto);
        add(buttonPanel);

        // Criação da tabela no banco de dados
        new EstoqueDAO().criaTabela();
        // Atualização inicial da tabela
        atualizarTabela();

        // Estilização
        apagaProduto.setBackground(Color.WHITE);
        apagaProduto.setForeground(Color.BLACK);
        cadastraProduto.setBackground(Color.WHITE);
        cadastraProduto.setForeground(Color.BLACK);
        editaProduto.setBackground(Color.WHITE);
        editaProduto.setForeground(Color.BLACK);

        // Tratamento de eventos
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputId.setText(String.valueOf(table.getValueAt(linhaSelecionada, 0)));
                    inputNomeProduto.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputPreco.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        // Instância do controlador
        EstoqueControl control = new EstoqueControl(produtos, tableModel, table);

        // Cadastrar um produto
        cadastraProduto.addActionListener(e -> {
            if (!inputNomeProduto.getText().isEmpty() && !inputPreco.getText().isEmpty()
                    && !inputQuantidade.getText().isEmpty() && !inputId.getText().isEmpty()) {

                control.cadastrarProduto(Integer.parseInt(inputId.getText()), inputNomeProduto.getText(),
                        inputPreco.getText(), inputQuantidade.getText());

                inputNomeProduto.setText("");
                inputPreco.setText("");
                inputQuantidade.setText("");
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um produto!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Editar um produto
        editaProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja atualizar as informações deste produto?",
                        "Editar", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.atualizar(Integer.parseInt(inputId.getText()), inputNomeProduto.getText(),
                            inputPreco.getText(), inputQuantidade.getText());
                }
            }
        });

        // Apagar um produto
        apagaProduto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja excluir este produto?",
                        "Excluir", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.apagar(Integer.parseInt(inputId.getText()));
                    inputNomeProduto.setText("");
                    inputPreco.setText("");
                    inputQuantidade.setText("");
                }
            }
        });
    }

    // Método para atualizar a tabela
    public void atualizarTabela() {
        tableModel.setRowCount(0);
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            tableModel.addRow(new Object[] { produto.getId(), produto.getNomeDoProduto(), produto.getPreco(),
                    produto.getQuantidade() });
        }
    }
}