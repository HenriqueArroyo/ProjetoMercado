package com.projeto.View;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

public class JanelaEscolha extends JFrame {
    private JButton btnFuncionario, btnCliente;

    public JanelaEscolha() {
        setTitle("DEV Marketplace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel escolhaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        escolhaPanel.setPreferredSize(new Dimension(500, 500));



        // Carregando os ícones do diretório de recursos
        ClassLoader classLoader = getClass().getClassLoader();

        URL funcionarioIconUrl = classLoader.getResource("administrador.png");
        ImageIcon funcionarioIcon = new ImageIcon(funcionarioIconUrl);

        URL clienteIconUrl = classLoader.getResource("cliente.png");
        ImageIcon clienteIcon = new ImageIcon(clienteIconUrl);

        btnFuncionario = new JButton("Login como Funcionário", funcionarioIcon);
        btnFuncionario.setContentAreaFilled(false);
        btnFuncionario.setFocusPainted(false);
        btnFuncionario.setOpaque(false);
        btnFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCliente = new JButton("Cadastrar-se como cliente", clienteIcon);
        btnCliente.setContentAreaFilled(false);
        btnCliente.setFocusPainted(false);
        btnCliente.setOpaque(false);
        btnCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnFuncionario.addActionListener(e -> {
            LoginFuncionario loginF = new LoginFuncionario();
            loginF.setSize(800, 600);
            loginF.setLocationRelativeTo(null);
            loginF.setVisible(true);
            dispose();
        });

        btnCliente.addActionListener(e -> {
            JanelaCadastra loginC = new JanelaCadastra();
            loginC.setSize(600, 200);
            loginC.setLocationRelativeTo(null);
            loginC.setVisible(true);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10); // Espaçamento entre os botões
        gbc.anchor = GridBagConstraints.CENTER;

        escolhaPanel.add(btnFuncionario, gbc);

        gbc.gridx = 1;
        escolhaPanel.add(btnCliente, gbc);

        add(escolhaPanel);
    }

    public void run() {
        pack();
        setVisible(true);
        setSize(650, 450);
        setResizable(false);
        setLocationRelativeTo(null); 
    }
}
