package app;

import javax.swing.JOptionPane;
import View.JanelaPrincipal;
import View.Caixa;

public class Main {
    public static void main(String[] args) {
        Object[] options = { "Caixa", "Janela Principal" };
        int decisao = JOptionPane.showOptionDialog(null, "Escolha uma aplicação", "Selecione", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (decisao) {
            case 0:
                new Caixa().run();
                break;
            case 1:
                new JanelaPrincipal().run();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Digite um valor válido");
                break;
        }
    }
}
