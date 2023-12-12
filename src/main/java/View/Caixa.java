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

import Connection.ClientesDAO;
import Connection.EstoqueDAO;
import Controller.VendasControl;
import Model.Cliente;
import Model.Estoque;
import View.JanelaCadastro;
import View.JanelaClientes;

public class Caixa extends JFrame {
    // Atributos
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton  compraButton, adicionaProduto, verificaCPF;
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

    // Construtor
    public Caixa() {
        // Inicializando componentes
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
        String[] metodosPagamento = {"Dinheiro", "Crédito", "Débito"};
        metodoPagamentoComboBox = new JComboBox<>(metodosPagamento);

        clienteVIP.setBackground(Color.WHITE);
        adicionaProduto.setBackground(Color.white);
        compraButton.setBackground(Color.white);
        clienteVIP.setForeground(new Color(65, 166, 18));
        verificaCPF.setBackground(Color.WHITE);
        verificaCPF.setForeground(Color.black);
        // Adicionando o mainPanel ao JFrame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);



            tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produto", "Quantidade", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        mainPanel.add(jSPane);

        totalPanel.setLayout(new GridLayout(1, 3));
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(quantidadeDeItens);
        totalPanel.add(valorFinal);
        valorFinal.setEditable(false);
        quantidadeDeItens.setEditable(false);
        atualizaQuantidadeEValorTotal();
        mainPanel.add(totalPanel);


         
        cpfPanel.setLayout(new GridLayout(1, 3, 5, 4));
        cpfPanel.add(verificaCPF);
        cpfPanel.add(inputCPF);
        buttonPanel.add(metodoPagamentoComboBox);
        mainPanel.add(cpfPanel);

        
        produtoPanel.setLayout(new GridLayout(1, 2, 4, 5));
        produtoPanel.add(adicionaProduto);
        produtoPanel.add(inputProduto);
        mainPanel.add(produtoPanel);

        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(compraButton);
        mainPanel.add(buttonPanel);

        adicionaProduto.addActionListener(e -> {
            if (!inputProduto.getText().isEmpty()) {
                buscarProduto(Integer.parseInt(inputProduto.getText()));
                inputProduto.setText("");
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

        compraButton.addActionListener(e -> {
            // Adicionando a lógica de realizar a venda ao botão
            int decisao = JOptionPane.showConfirmDialog(null, "Deseja realmente finalizar a compra?", "Finalizar Compra", JOptionPane.YES_NO_OPTION);
            if (decisao == JOptionPane.YES_OPTION) {
                // Obter o método de pagamento selecionado
                String metodoPagamentoSelecionado = (String) metodoPagamentoComboBox.getSelectedItem();

                // Lógica para finalizar a venda
                // Você pode precisar chamar métodos específicos para atualizar o estado da venda,
                // como atualizar o banco de dados, realizar cálculos finais, etc.
                // No exemplo, estou apenas chamando o método realizarVenda no VendasControl.

                int idVenda = 1; // Substitua pelo ID real da venda
                String cliente = ""; // Substitua pelo cliente real
                String quantidadeDeProdutos = ""; // Substitua pela quantidade real
                String valor = ""; // Substitua pelo valor real
                String data = ""; // Substitua pela data real

                VendasControl vendasControl = new VendasControl(null, null, null); // Substitua pelos valores reais

                vendasControl.realizarVenda(idVenda, cliente, quantidadeDeProdutos, valor, data, pagamento);
            }
        });
   

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
                tableModel.addRow(new Object[] {
                        produto.getNomeDoProduto(), contProduto, produto.getPreco()
                });
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
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        // Obtém os carros atualizados do banco de dados
        for (Estoque compra : listaDeCompra) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    public void cadastraNovoCliente() {
        int res = JOptionPane.showConfirmDialog(null, "Ir para Lista de Produtos",
                "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            JFrame janela = new JFrame();
            janela.setVisible(true);
            janela.setDefaultCloseOperation(2);
            janela.setBounds(0, 0, 500, 300);

            janela.add(new JanelaEstoque());
        }
    }

    public void atualizaQuantidadeEValorTotal() {
        valorTotal = 0;
        for (Estoque compra : listaDeCompra) {
            int soma = compra.getQuantidadeCompra() * compra.getPrecoCompra();
            valorTotal += soma;
        }
        valorFinal.setText("R$ " + String.valueOf(valorTotal));

        quantidadeTotal = 0;
        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
        }
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));
    }

    public void run() {
        pack();
        setVisible(true);
        setSize(650, 450);
        setResizable(false);
    }
}
