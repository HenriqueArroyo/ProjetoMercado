package com.projeto.View;

import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Sistema supermercado");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(650, 650);
        // ---------------------*
        // Aplicativo principal:
        JTabbedPane abas = new JTabbedPane();
        abas.add("Caixa", new Caixa());
        abas.add("Estoque", new JanelaEstoque()); // Adiciona o painel de estoque ao TabbedPane 
        abas.add("Vendas", new JanelaVendas()); // Adiciona o painel de vendas ao TabbedPane 
        abas.add("Clientes VIP", new JanelaCadastroCliente());// Adiciona o painel de cadastro de clientes ao TabbedPane 
        abas.add("Funcionários", new JanelaCadastroFuncionario()); // Adiciona o painel de cadastro de funcionários ao TabbedPane
        add(abas);
        // ---------------------*

        // ---------------------*
        addWindowListener(new WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                }
            } // Questiona o usuário se realmente ele deseja fechar a aplicação

        });
    }
}