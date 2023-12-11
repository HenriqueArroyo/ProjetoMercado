package View;

import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javafx.stage.WindowEvent;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Sistema supermercado");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // ---------------------*
        // Aplicativo principal:
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", new JanelaEstoque()); // Adiciona o painel de estoque ao TabbedPane
        abas.add("Clientes", new JanelaClientes()); // Adiciona o painel de cliente ao TabbedPane
        abas.add("Vendas", new JanelaVendas()); // Adiciona o painel de vendas ao TabbedPane
        add(abas);
        // ---------------------*

        // ---------------------*
        addWindowListener(new WindowAdapter() {

           
            public void windowClosing(java.awt.event.WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                }
            } // Questiona o usuário se realmente ele deseja fechar a aplicação

        });
    }

    public void run() {
        pack();
        setVisible(true);
        setSize(650, 450);
        setResizable(false);
    }
}
