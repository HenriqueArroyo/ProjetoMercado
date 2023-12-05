package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaFuncionarios {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new JanelaFuncionarios();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JanelaFuncionarios() {
        frame = new JFrame("Cadastro de Funcionário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createFormPanel(), "form");

        frame.getContentPane().add(cardPanel);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField(5);

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField(5);

        JLabel idadeLabel = new JLabel("Idade:");
        JTextField idadeField = new JTextField(5);

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField(5);

        JButton cadastrarButton = new JButton("Cadastrar");

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui você pode adicionar lógica para lidar com o cadastro
                // Por exemplo, obter os valores dos campos e processá-los
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                String idade = idadeField.getText();
                char[] senha = senhaField.getPassword();

                // Exemplo de exibição dos dados (substitua isso com sua lógica de armazenamento)
                JOptionPane.showMessageDialog(frame,
                        "Nome: " + nome + "\nCPF: " + cpf + "\nTelefone: " + telefone +
                                "\nEmail: " + email + "\nIdade: " + idade,
                        "Cadastro Efetuado",
                        JOptionPane.INFORMATION_MESSAGE);

                // Limpar os campos após o cadastro
                nomeField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
                emailField.setText("");
                idadeField.setText("");
                senhaField.setText("");
            }
        });

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(cpfLabel);
        panel.add(cpfField);
        panel.add(telefoneLabel);
        panel.add(telefoneField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(idadeLabel);
        panel.add(idadeField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(new JLabel()); // Espaçamento
        panel.add(cadastrarButton);

        return panel;
    }
}
