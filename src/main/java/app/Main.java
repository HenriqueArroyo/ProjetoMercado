package app;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import View.JanelaPrincipal;
import View.Caixa;

public class Main {
    public static void main(String[] args) {


        ImageIcon caixaIcon = new ImageIcon(Main.class.getResource("/img/cliente.png"));
        Object[] options = { "Caixa", "Janela Principal" };
        int decisao = JOptionPane.showOptionDialog(null, "Escolha uma aplicação", "Selecione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, caixaIcon, options, options[0]);

        switch (decisao) {
            case 0:
                new Caixa().run();
                break;
            case 1:
                new JanelaPrincipal().run();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Até Mais!!");
                break;
        }
    }
}
