package app;

import javax.swing.JOptionPane;

import View.JanelaPrincipal;
import View.Caixa;

public class Main {
    public static void main(String[] args) {
           int decisao = Integer.parseInt(JOptionPane.showOptionDialog());
        switch (decisao) {
        case 1:
        new Caixa().run();
        break;
        case 2:
        new JanelaPrincipal().run();
        break;

        default: JOptionPane.showMessageDialog(null, "Digite um valor válido");
        break;
        }
    }
}
