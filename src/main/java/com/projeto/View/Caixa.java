package com.projeto.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.projeto.Connection.ClientesDAO;
import com.projeto.Connection.EstoqueDAO;
import com.projeto.Connection.VendasDAO;
import com.projeto.Controller.ClientesControl;
import com.projeto.Model.Cliente;
import com.projeto.Model.Estoque;

// Classe que representa a interface gráfica do caixa
public class Caixa extends JPanel {
    // Componentes da interface gráfica
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton compraButton, adicionaProduto, verificaCPF, listaProdutos, atualizarCliente;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel ;
    private JLabel clienteVIP;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos;
    private List<Cliente> clientes;
    private List<Estoque> listaDeCompra = new ArrayList<>();
    private JScrollPane jSPane;
    private boolean produtoNaoEncontrado = true;
    private int contProduto = 1;
    private int quantidadeTotal = 0;
    private int valorTotal = 0;
    private JComboBox<String> metodoPagamentoComboBox;
    private JComboBox<String> clienteComboBox;
    private int valorProdutoAdicionado = 0;

    // Construtor da classe
    public Caixa() {
        // Inicializa os componentes
        inicializarComponentes();
        // Configura o layout da interface
        configurarLayout();
        // Atualiza automaticamente os totais
        atualizarTotaisAutomaticamente();
        // Adiciona os ouvintes de eventos aos botões
        adicionarListeners();
        // Configura a janela
        configurarJanela();
    }

    // Inicializa os componentes da interface
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
        atualizarCliente = new JButton("Atualizar Cliente");
        listaProdutos = new JButton("Exibir Produtos");
        String[] metodosPagamento = { "Dinheiro", "Crédito", "Débito" };
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
        atualizarCliente.setBackground(Color.WHITE);
        atualizarCliente.setForeground(Color.black);
    }

    // Configura o layout da interface
    private void configurarLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        clientes = new ClientesDAO().listarTodos();
        String[] nomesClientes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        String[] nomesClientesComNulo = new String[nomesClientes.length + 1];
        nomesClientesComNulo[0] = "Sem Cadastro";
        System.arraycopy(nomesClientes, 0, nomesClientesComNulo, 1, nomesClientes.length);
        clienteComboBox = new JComboBox<>(nomesClientesComNulo);
        cpfPanel.add(atualizarCliente);
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

    // Adiciona os ouvintes de eventos aos botões
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

        // Ouvinte para o botão "Verificação Cliente (CPF)"
        verificaCPF.addActionListener(e -> {
            String cpfText = inputCPF.getText();
            ClientesControl clientesC = new ClientesControl(clientes, tableModel, table);
            clientesC.verificarCPF(cpfText);
        });

        // Ouvinte para o botão "Finalizar Compra"
        compraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VendasDAO().cadastrar("13/12/2023", clienteComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(quantidadeTotal), metodoPagamentoComboBox.getSelectedItem().toString().trim(),
                        String.valueOf(valorTotal));
                JOptionPane.showMessageDialog(null, "Venda realizada!", null, JOptionPane.INFORMATION_MESSAGE);
                listaDeCompra.clear();
                atualizaTabela();
                atualizarTotaisAutomaticamente();
        
                // Chama o método para aplicar desconto
                aplicarDesconto();
            }
        });

        // Ouvinte para o botão "Exibir Produtos"
        listaProdutos.addActionListener(e -> {
            listarProdutos();
        });

 atualizarCliente.addActionListener(e -> {
            atualizarClientes();
        });

    }

    // Configura a janela principal
    private void configurarJanela() {
        setVisible(true);
        setSize(800, 550);
    }

    // Atualiza automaticamente os totais com base nos produtos da compra
    private void atualizarTotaisAutomaticamente() {
        quantidadeTotal = 0;
        valorTotal = 0;
    
        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
            valorTotal += compra.getQuantidadeCompra() * compra.getPrecoCompra();
            aplicarDesconto();
        }
    
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));
        valorFinal.setText(String.format("R$ %.2f", (double) valorTotal));
    }

    // Busca um produto pelo ID e o adiciona à lista de compra
    public void buscarProduto(int id) {
        contProduto = 1;
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            if (produto.getId() == id) {
                produtoNaoEncontrado = false;
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

                if (contProduto <= Integer.parseInt(produto.getQuantidade())) {
                    int novaQuantidade = Integer.parseInt(produto.getQuantidade()) - contProduto;

                    System.out.println(novaQuantidade);

                    new EstoqueDAO().atualizarQuantidade(produto.getId(),
                            String.valueOf(novaQuantidade));

                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida", null, JOptionPane.ERROR_MESSAGE);
                    break;
                }
                tableModel.addRow(new Object[] {
                        produto.getNomeDoProduto(), contProduto, produto.getPreco()
                });

                Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(),
                        Double.parseDouble(produto.getPreco()),
                        contProduto);
                listaDeCompra.add(produtoComprado);
            }
        }

        if (produtoNaoEncontrado) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Mercado", JOptionPane.ERROR_MESSAGE);
        }
        atualizarTotaisAutomaticamente();
    }

    private void aplicarDesconto() {
        String clienteSelecionado = clienteComboBox.getSelectedItem().toString().trim();
    
        if (!clienteSelecionado.equals("Sem Cadastro")) {
            // Calcula o desconto (10%)
            double desconto = valorTotal * 0.10;
            
            // Aplica o desconto ao valorTotal
            valorTotal -= desconto;
    
            // Atualiza o campo de valorFinal
            valorFinal.setText(String.format("R$ %.2f", (double) valorTotal ));
        }
    }


    // Atualiza a tabela com a lista de compra atual
    public void atualizaTabela() {
        tableModel.setRowCount(0);
        for (Estoque compra : listaDeCompra) {
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    private void atualizarClientes() {
        clientes = new ClientesDAO().listarTodos();
        String[] nomesClientes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        String[] nomesClientesComNulo = new String[nomesClientes.length + 1];
        nomesClientesComNulo[0] = "Sem Cadastro";
        System.arraycopy(nomesClientes, 0, nomesClientesComNulo, 1, nomesClientes.length);
        clienteComboBox.setModel(new DefaultComboBoxModel<>(nomesClientesComNulo));
    }

    // Exibe a lista de produtos em uma janela separada
    public void listarProdutos() {
        int res = JOptionPane.showConfirmDialog(null, "Visualizar a lista de Produtos?",
                "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            JFrame janela = new JFrame();
            janela.setVisible(true);
            janela.setDefaultCloseOperation(2);
            janela.setBounds(0, 0, 500, 300);
            janela.add(new JanelaProdutos());
        }
    }
}
