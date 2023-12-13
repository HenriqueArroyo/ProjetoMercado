package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import Connection.ClientesDAO;
import Connection.EstoqueDAO;
import Connection.VendasDAO;
import Model.Cliente;
import Model.Estoque;

public class Caixa extends JFrame {
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton compraButton, adicionaProduto, verificaCPF, listaProdutos;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel;
    private JLabel clienteVIP;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos;
    private List<Cliente> clientes;
    private List<Estoque> listaDeCompra = new ArrayList<>();
    private JScrollPane jSPane;
    private boolean isClienteVIP;
    private boolean produtoNaoEncontrado = true;
    private int contProduto = 1;
    private int quantidadeTotal = 0;
    private int valorTotal = 0;
    private JComboBox<String> metodoPagamentoComboBox;
    private JComboBox<String> clienteComboBox;

    public Caixa() {
        inicializarComponentes();
        configurarLayout();
        atualizarTotaisAutomaticamente();
        adicionarListeners();
        configurarJanela();
    }

    private void inicializarComponentes() {
        jSPane = new JScrollPane();
        mainPanel = new JPanel();
        totalPanel = new JPanel();
        cpfPanel = new JPanel();
        produtoPanel = new JPanel();
        buttonPanel = new JPanel();
        inputCPF = new JTextField(20);
        inputProduto = new JTextField(20);
        valorFinal = new JTextField();
        quantidadeDeItens = new JTextField();
        clienteVIP = new JLabel("Cliente VIP");
        adicionaProduto = new JButton("Adicionar Produtos");
        compraButton = new JButton("Finalizar Compra");
        verificaCPF = new JButton("Verificação Cliente (CPF)");
        listaProdutos = new JButton("Exibir Produtos");
        String[] metodosPagamento = {"Dinheiro", "Crédito", "Débito"};
        metodoPagamentoComboBox = new JComboBox<>(metodosPagamento);
        quantidadeDeItens.setEditable(false);
        valorFinal.setEditable(false);
        listaProdutos.setBackground(Color.WHITE);
        listaProdutos.setForeground(Color.black);
        clienteVIP.setBackground(Color.WHITE);
        adicionaProduto.setBackground(Color.white);
        adicionaProduto.setForeground(Color.black);
        compraButton.setBackground(Color.white);
        compraButton.setForeground(Color.black);
        clienteVIP.setForeground(Color.black);
        verificaCPF.setBackground(Color.WHITE);
        verificaCPF.setForeground(Color.black);
    }

    private void configurarLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        clientes = new ClientesDAO().listarTodos();
        String[] nomesClientes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        clienteComboBox = new JComboBox<>(nomesClientes);
        cpfPanel.add(clienteComboBox);
        cpfPanel.add(metodoPagamentoComboBox);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produto", "Quantidade", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        mainPanel.add(jSPane);
        totalPanel.setLayout(new GridLayout(1, 3));
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(quantidadeDeItens);
        totalPanel.add(valorFinal);
        mainPanel.add(totalPanel);
        cpfPanel.setLayout(new GridLayout(1, 3, 5, 4));
        mainPanel.add(cpfPanel);
        produtoPanel.setLayout(new GridLayout(1, 2, 4, 5));
        produtoPanel.add(listaProdutos);
        produtoPanel.add(adicionaProduto);
        produtoPanel.add(inputProduto);
        mainPanel.add(produtoPanel);
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(compraButton);
        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

    private void adicionarListeners() {
        adicionaProduto.addActionListener(e -> {
            if (!inputProduto.getText().isEmpty()) {
                buscarProduto(Integer.parseInt(inputProduto.getText()));
                inputProduto.setText("");
                atualizarTotaisAutomaticamente();
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "Mercado",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        verificaCPF.addActionListener(e -> {
            isClienteVIP = validaCpf(inputCPF.getText());
            System.out.println(isClienteVIP);
            if (isClienteVIP) {
                JOptionPane.showMessageDialog(null, "Cliente VIP!");
                cpfPanel.add(clienteVIP);
            }
            inputCPF.setText("");
        });

        compraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VendasDAO().cadastrar("13/12/2023", clienteComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(quantidadeTotal), metodoPagamentoComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(valorFinal));
                JOptionPane.showMessageDialog(null, "Venda realizada!", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                listaDeCompra.clear();
                atualizaTabela();
                atualizarTotaisAutomaticamente();
            }
        });

        listaProdutos.addActionListener(e -> {
            listarProdutos();
        });
    }

    private void configurarJanela() {
        pack();
        setVisible(true);
        setSize(800, 550);
        setResizable(false);
    }

    public boolean validaCpf(String cpf) {
        clientes = new ClientesDAO().listarTodos();
        for (Cliente cliente : clientes) {
            if (cpf.trim().equals(cliente.getCpf())) {
                return true;
            }
        }
        return false;
    }

    private void atualizarTotaisAutomaticamente() {
        quantidadeTotal = 0;
        valorTotal = 0;
    
        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
            valorTotal += compra.getQuantidadeCompra() * compra.getPrecoCompra();
        }
    
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));
        valorFinal.setText(String.format("R$ %.2f", (double) valorTotal / 100));
    }

    private void buscarProduto(int id) {
        contProduto = 1;
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            if (produto.getId() == id) {
                int res = JOptionPane.showConfirmDialog(null, "A quantidade do produto é maior que 1?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    try {
                        contProduto = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade do produto:"));
                    } catch (Exception err) {
                        System.out.println(err);
                        contProduto = 1;
                    }
                }
                tableModel.addRow(new Object[]{produto.getNomeDoProduto(), contProduto, produto.getPreco()});
                
                // Ajuste nesta linha
                Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(), produto.getPrecoCompra(), contProduto);
                
                atualizarTotaisAutomaticamente();

                listaDeCompra.add(produtoComprado);
                produtoNaoEncontrado = false;
            }
        }
    
        if (produtoNaoEncontrado) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Mercado", JOptionPane.ERROR_MESSAGE);
        }
        atualizarTotaisAutomaticamente();
    }
    

    public void atualizaTabela() {
        tableModel.setRowCount(0);
        for (Estoque compra : listaDeCompra) {
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    public void listarProdutos() {
        int res = JOptionPane.showConfirmDialog(null, "Visualizar a lista de Produtos?",
                "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            JFrame janela = new JFrame();
            janela.setVisible(true);
            janela.setDefaultCloseOperation(2);
            janela.setBounds(0, 0, 500, 300);
            janela.add(new Produtos());
        }
    }

    public void run() {
        pack();
        setVisible(true);
        setSize(800, 550);
        setResizable(false);
    }
}
