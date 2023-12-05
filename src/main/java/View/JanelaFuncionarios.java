package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Connection.FuncionariosDAO;
import Controller.FuncionariosController;
import Model.Funcionario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaFuncionarios extends JPanel {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> estoques;

    public static void main(String[] args) {
       
            try {
                new JanelaFuncionarios();
            } catch (Exception e) {
                e.printStackTrace();
            }
   
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
        JTextField nomeField = new JTextField();

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();

        JLabel telefoneLabel = new JLabel("CPF:");
        JTextField telefoneField = new JTextField();


        JLabel idadeLabel = new JLabel("Idade:");
        JTextField idadeField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(Color.WHITE);

        cadastrarButton.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e) {
                // Aqui você pode adicionar lógica para lidar com o cadastro
                // Por exemplo, obter os valores dos campos e processá-los
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String idade = idadeField.getText();
                char[] senha = senhaField.getPassword();

                // Exemplo de exibição dos dados (substitua isso com sua lógica de armazenamento)
                JOptionPane.showMessageDialog(frame,
                        "Nome: " + nome + "\nCPF: " + cpf + "\nTelefone: " + telefone +
                                 "\nIdade: " + idade,
                        "Cadastro Efetuado",
                        JOptionPane.INFORMATION_MESSAGE);

                // Limpar os campos após o cadastro
                nomeField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
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
        panel.add(idadeLabel);
        panel.add(idadeField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(new JLabel()); // Espaçamento
        panel.add(cadastrarButton);

        new FuncionariosDAO().criaTabela();
        atualizarTabela();


        FuncionariosController operacoes = new FuncionariosController(null, null, null);

        return panel;
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        funcionarios = new FuncionariosDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Funcionario funcionarios : funcionarios) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { funcionarios.getNome(), funcionarios.getCpf(), funcionarios.getSenha(), funcionarios.getIdade(), funcionarios.getTelefone() });
        }
    }

}
