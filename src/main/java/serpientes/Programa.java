package serpientes;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Programa {

    public static final ImageIcon ICONO = new ImageIcon("src/main/resources/snake_96x.jpg");
    public static final int NMAX = 100;

    public static int[] lectura;

    public static void main(String[] args) {

        do {
            int n = solicitaDato("cantidad de mediciones de temperatura. (1-" + NMAX + ")", 1, NMAX);
            int k = solicitaDato("número de mediciones soportadas bajo umbral de temperatura. (0-" + (n - 2) + ")", 0, n - 2);
            tipoListado();
            buscaOptimo();
        } while (menuSalir());

    }

    public static int solicitaDato(String mensaje, int min, int max) {
        int valor;
        do {
            Object objeto = JOptionPane.showInputDialog(null,
                    "Introduce " + mensaje,
                    "Dato necesario:",
                    JOptionPane.OK_OPTION,
                    ICONO,
                    null,
                    null);
            String texto = objeto.toString();
            if (isParsable(texto)) {
                valor = Integer.parseInt(texto);
            } else {
                valor = max + 1;
            }
            if (valor < min || valor > max) {
                JOptionPane.showOptionDialog(
                        null,
                        "El valor introducino no es válido.\n"
                        + "Valores válidos: " + min + "-" + max + ".",
                        "ERROR",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        ICONO,
                        new Object[]{"Ok"}, null);
            }
        } while (valor < min || valor > max);
        return valor;
    }

    public static boolean isParsable(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
