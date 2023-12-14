package com.projeto.Controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.*;
import com.projeto.Model.*;;

public class VendasControl {
    private List<Venda> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    public VendasControl(List<Venda> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

    
    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[] { venda.getData(), venda.getCliente(), venda.getQuantidadeDeProdutos(), venda.getPagamento(),
                    venda.getValor() });
        }
    }

    public void realizarVenda(int id, String cliente, String quantidadeDeProdutos, String valor, String data, String pagamento) {
        try {
            Venda venda = new Venda(id, cliente.trim().toUpperCase(), valor.trim(), data.trim(),
                    quantidadeDeProdutos.trim(), pagamento.trim());
            vendas.add(venda);
            new VendasDAO().cadastrar(cliente.trim().toUpperCase(), quantidadeDeProdutos.trim(), valor.trim(),
                    data.trim(), pagamento.trim());
            atualizarTabela();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
        } catch (Exception err) {
            System.out.println(err.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}