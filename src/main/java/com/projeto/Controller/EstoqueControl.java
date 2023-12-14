package com.projeto.Controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.*;
import com.projeto.Model.*;

public class EstoqueControl {
    // CRUD
    private List<Estoque> produtos;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public EstoqueControl(List<Estoque> produtos, DefaultTableModel tableModel, JTable table) {
        this.produtos = produtos;
        this.tableModel = tableModel;
        this.table = table;
    }

  

    // Métodos

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            tableModel.addRow(new Object[] { produto.getId(), produto.getNomeDoProduto(), produto.getPreco(),
                    produto.getQuantidade() });
        }
    }

    public void cadastrarProduto(int id, String nomeDoProduto, String preco, String quantidade) {
        try {
            if (validaPreco(preco) && validaQuantidade(quantidade)) {
                Estoque produto = new Estoque(id, nomeDoProduto.trim().toUpperCase(), preco.trim().toUpperCase(),
                        String.valueOf(quantidade));
                produtos.add(produto);
                new EstoqueDAO().cadastrar(id, nomeDoProduto, preco, quantidade);
                atualizarTabela();
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void apagar(int id) {
        new EstoqueDAO().apagar(id);
        atualizarTabela();
        JOptionPane.showMessageDialog(table, "Produto removido!", null, JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(int id, String nomeDoProduto, String preco, String quantidade) {
        try {
            if (validaPreco(preco) && validaQuantidade(quantidade)) {
                new EstoqueDAO().atualizar(id, nomeDoProduto, preco, quantidade);
                JOptionPane.showMessageDialog(null, "Produto atualizado", null, JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validaPreco(String preco) {
        if (preco.matches("[0-9]+([,.][0-9]{1,2})?")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaQuantidade(String quantidade) {
        if (quantidade.matches("[0-9]+") && Integer.parseInt(quantidade) > 0) {
            return true;
        }
        return false;
    }
}