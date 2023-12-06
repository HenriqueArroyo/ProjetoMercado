package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import Connection.FuncionariosDAO;
import Controller.FuncionariosController;
import Model.Funcionario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JanelaFuncionarios extends JPanel {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private JPanel panel;

    public JanelaFuncionarios() {
        super();
        frame = new JFrame("Cadastro de Funcionário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        frame.add(cardPanel);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel cadastroPanel = new JPanel(new GridLayout(7, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();

        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();

        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneField = new JTextField();

        JLabel dataNascimentoLabel = new JLabel("Data de Nascimento:");
        JTextField dataNascimentoField = new JTextField();

        JLabel senhaLabel = new JLabel("Senha:");
        JPasswordField senhaField = new JPasswordField();

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(Color.WHITE);

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cpf = cpfField.getText();
                String telefone = telefoneField.getText();
                String dataNascimento = dataNascimentoField.getText();
                char[] senha = senhaField.getPassword();

                if (isValidDate(dataNascimento)) {
                    // Lógica para cadastro
                    FuncionariosController operacoes = new FuncionariosController(nome, cpf, senha);
                    operacoes.cadastrarFuncionario(dataNascimento, telefone);

                    // Limpar os campos após o cadastro
                    nomeField.setText("");
                    cpfField.setText("");
                    telefoneField.setText("");
                    dataNascimentoField.setText("");
                    senhaField.setText("");

                    // Atualizar a tabela
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(frame, "Data de Nascimento inválida. Use o formato dd/MM/yyyy.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cadastroPanel.add(nomeLabel);
        cadastroPanel.add(nomeField);
        cadastroPanel.add(cpfLabel);
        cadastroPanel.add(cpfField);
        cadastroPanel.add(telefoneLabel);
        cadastroPanel.add(telefoneField);
        cadastroPanel.add(dataNascimentoLabel);
        cadastroPanel.add(dataNascimentoField);
        cadastroPanel.add(senhaLabel);
        cadastroPanel.add(senhaField);
        cadastroPanel.add(new JLabel()); // Espaçamento
        cadastroPanel.add(cadastrarButton);

        cardPanel.add(cadastroPanel, "cadastro");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("CPF");
        tableModel.addColumn("Senha");
        tableModel.addColumn("Data de Nascimento");
        tableModel.addColumn("Telefone");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        cardPanel.add(scrollPane, "tabela");

        new FuncionariosDAO().criaTabela();
        atualizarTabela();
    }

    private boolean isValidDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        funcionarios = new FuncionariosDAO().listarTodos();
        for (Funcionario funcionario : funcionarios) {
            tableModel.addRow(new Object[]{funcionario.getNome(), funcionario.getCpf(), funcionario.getSenha(),
                    funcionario.getDataNascimento(), funcionario.getTelefone()});
        }
    }

    public void run() {
        setVisible(true);
    }
}