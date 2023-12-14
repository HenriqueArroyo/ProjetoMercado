package com.projeto.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.FuncionariosDAO;
import com.projeto.Controller.FuncionariosControl;
import com.projeto.Model.Funcionario;

public class JanelaCadastroFuncionario extends JPanel {
    // Atributos
    private JButton cadastrar, apagar;
    private JTextField funcionarioNomeField, funcionarioCpfField, funcionarioTelefoneField, funcionarioDataNascimentoField;
    private List<Funcionario> funcionarios;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // Construtor
    public JanelaCadastroFuncionario() {
        super();

        // Entrada de dados
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome"));
        funcionarioNomeField = new JTextField(20);
        inputPanel.add(funcionarioNomeField);
        inputPanel.add(new JLabel("CPF"));
        funcionarioCpfField = new JTextField(20);
        inputPanel.add(funcionarioCpfField);
        inputPanel.add(new JLabel("Data de nascimento (dd/mm/aaaa)"));
        funcionarioDataNascimentoField = new JTextField(20);
        inputPanel.add(funcionarioDataNascimentoField);
        inputPanel.add(new JLabel("Telefone"));
        funcionarioTelefoneField = new JTextField(20);
        inputPanel.add(funcionarioTelefoneField);

        JPanel botoesPanel = new JPanel();
        cadastrar = new JButton("Cadastrar");
        apagar = new JButton("Apagar");
        botoesPanel.add(cadastrar);
        botoesPanel.add(apagar);
        

        // Tabela de clientes
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "CPF", "Idade", "Telefone" });
        table = new JTable(tableModel);
        JScrollPane jSPane = new JScrollPane(table);

        // Adicionando componentes ao JFrame
        setLayout(new BorderLayout(8, 8));
        add(inputPanel, BorderLayout.NORTH);
        add(botoesPanel, BorderLayout.CENTER);
        add(jSPane, BorderLayout.SOUTH);

        new FuncionariosDAO().criaTabela();

        atualizarTabela();

        // Tratamento
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    funcionarioNomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    funcionarioCpfField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    funcionarioDataNascimentoField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    funcionarioTelefoneField.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        FuncionariosControl operacoesFuncionarios = new FuncionariosControl(funcionarios, tableModel, table);

        cadastrar.addActionListener(e -> {
            
                operacoesFuncionarios.cadastrar(
                        funcionarioNomeField.getText(),
                        funcionarioCpfField.getText(),
                        funcionarioTelefoneField.getText(),
                        funcionarioDataNascimentoField.getText());
                        atualizarTabela();
            
        });

        apagar.addActionListener(e->{
            operacoesFuncionarios.apagar(funcionarioCpfField.getText());
        });
    }

    // Método para atualizar a tabela de clientes
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        funcionarios = new FuncionariosDAO().listarTodos();

        for (Funcionario funcionario : funcionarios) {
            tableModel.addRow(new Object[] { funcionario.getNome(), funcionario.getCpf(),
                    funcionario.getdataNascimento(), funcionario.getTelefone() });
        }
    }

    public void setLocationRelativeTo(Object object) {
    }

}
