package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Connection.ClientesDAO;
import Connection.EstoqueDAO;
import Connection.VendasDAO;
import Model.Cliente;
import Model.Estoque;

public class Caixa extends JFrame {
    // Atributos
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

    // Construtor
    public Caixa() {
        // Etapa 1: Inicialização dos componentes
        inicializarComponentes();

        // Etapa 2: Configuração do layout
        configurarLayout();

        // Etapa 3: Adição de listeners aos componentes interativos
        adicionarListeners();

        // Etapa 4: Configuração da janela principal
        configurarJanela();
    }

    // Etapa 1: Inicialização dos componentes
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

    // Etapa 2: Configuração do layout
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

    // Etapa 3: Adição de listeners aos componentes interativos
    private void adicionarListeners() {
        adicionaProduto.addActionListener(e -> {
            if (!inputProduto.getText().isEmpty()) {
                buscarProduto(Integer.parseInt(inputProduto.getText()));
                inputProduto.setText("");
                atualizaQuantidadeEValorTotal();
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "Mercado",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        verificaCPF.addActionListener(e -> {
            isClienteVIP = validaCpf(inputCPF.getText());
            System.out.println(isClienteVIP);
            if (isClienteVIP == true) {
                JOptionPane.showMessageDialog(null, "Cliente VIP!");
                cpfPanel.add(clienteVIP);
            }
            inputCPF.setText("");
        });

        compraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VendasDAO().cadastrar("13/12/2023", clienteComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(quantidadeTotal), metodoPagamentoComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(valorTotal));
                JOptionPane.showMessageDialog(null, "Venda realizada!", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                listaDeCompra.clear();
                atualizaTabela();
                atualizaQuantidadeEValorTotal();
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

    public void buscarProduto(int id) {
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
                tableModel.addRow(new Object[] { produto.getNomeDoProduto(), contProduto, produto.getPreco() });
                Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(), Integer.parseInt(produto.getPreco()),
                        contProduto);
                listaDeCompra.add(produtoComprado);
                produtoNaoEncontrado = false;
            }
        }

        if (produtoNaoEncontrado) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Mercado", JOptionPane.ERROR_MESSAGE);
        }
        atualizaQuantidadeEValorTotal();
    }

    public void atualizaTabela() {
        tableModel.setRowCount(0);
        for (Estoque compra : listaDeCompra) {
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    private void atualizaQuantidadeEValorTotal() {
        atualizarTotais();
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));
        valorFinal.setText("R$ " + String.valueOf(valorTotal));
    }

    private void atualizarTotais() {
        quantidadeTotal = 0;
        valorTotal = 0;

        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
            valorTotal += compra.getQuantidadeCompra() * compra.getPrecoCompra();
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
