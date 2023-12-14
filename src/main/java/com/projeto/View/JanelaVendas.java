package com.projeto.View;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.VendasDAO;
import com.projeto.Model.Venda;

public class JanelaVendas extends JPanel {

    private JPanel buttonPanel;
    private JButton atualizar, cancelaVenda;
    private JTextField inputProduto, inputQuantidade, inputTotal, inputCliente, inputData;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Venda> vendas = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public JanelaVendas() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        atualizar = new JButton("Atualizar");
        cancelaVenda = new JButton("Cancelar Venda");
        inputProduto = new JTextField(10);
        inputQuantidade = new JTextField(10);
        inputTotal = new JTextField(10);
        inputCliente = new JTextField(10);
        inputData = new JTextField(10);

        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Registro de Vendas"));
        add(title);
        add(atualizar);
        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Data","Cliente", "Quantidade", "Pagamento", "Total" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new VendasDAO().criaTabela();
        atualizarTabela();


        atualizar.addActionListener(e ->{
atualizarTabela();
            
        }

        );

    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[] { venda.getData(), venda.getCliente(), venda.getQuantidadeDeProdutos(),
                    venda.getPagamento(), venda.getValor() });
        }
    }
}