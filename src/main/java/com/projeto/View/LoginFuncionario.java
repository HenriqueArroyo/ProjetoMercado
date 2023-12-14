package com.projeto.View;

import javax.swing.*;
import java.awt.*;

import com.projeto.Connection.FuncionariosDAO;
import com.projeto.Controller.FuncionariosControl;

public class LoginFuncionario extends JFrame {
    private JTextField cpfTextField;
    private FuncionariosControl funcionariosControl; // Adiciona a referência a ClientesControl

    public LoginFuncionario() {
        // Configuração da janela
        setTitle("Login do funcionário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FuncionariosDAO funcDAO = new FuncionariosDAO();

        funcDAO.criaTabela();

        // Cria uma instância de ClientesControl
        funcionariosControl = new FuncionariosControl(null, null, null);

        // Layout e componentes
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); // Espaçamento

        JLabel cpfLabel = new JLabel("Digite o CPF:");
        cpfTextField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(cpfLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(cpfTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        // Adiciona o ActionListener para o botão de login
        loginButton.addActionListener(e -> {
            realizarLogin();
        });

        // Adiciona o painel à janela
        add(panel);

        // Torna a janela visível
        setVisible(true);
    }

    private void realizarLogin() {
        String cpf = cpfTextField.getText();
        JanelaPrincipal jp = new JanelaPrincipal();
        jp.setSize(800, 800);
        jp.setLocationRelativeTo(null);
        jp.setVisible(true);
        funcionariosControl.verificarCPF(cpf);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFuncionario();
            }
        });
    }
}
