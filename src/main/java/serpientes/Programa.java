package serpientes;

import java.util.Arrays;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Programa {

    //Declaracion de constantes e icono
    public static final ImageIcon ICONO = new ImageIcon("src/main/resources/snake_96x.jpg");
    public static final int NMAX = 100;

    //Declaracion de array para datos de temperatura
    public static int[] lectura;

    //Método main con estructura lógica principal del programa
    public static void main(String[] args) {
        do {
            int n = solicitaDato("cantidad de mediciones de temperatura. (1-" + NMAX + ")", 1, NMAX);
            lectura = new int[n];
            int k = solicitaDato("número de mediciones soportadas bajo umbral de temperatura. (0-" + n + ")", 0, n);
            tipoListado(n);
            buscaOptimo();
        } while (!menuSalir());

    }

    //Método para solicitud de datos por teclado
    public static int solicitaDato(String mensaje, int min, int max) {
        int valor = max + 1;     //valor inicializado fuera del rango del bucle para repetir lo por defecto
        do {
            Object objeto = JOptionPane.showInputDialog(null,
                    "Introduce " + mensaje,
                    "Dato necesario:",
                    JOptionPane.OK_OPTION,
                    ICONO,
                    null,
                    null);
            if (objeto != null) {       //Obliga a introducir algun valor, no permite salida con "cancelar" o cerrar ventana
                String texto = objeto.toString();
                if (isParsable(texto)) {
                    valor = Integer.parseInt(texto);
                }

                if (valor < min || valor > max) {   //Mensaje de error cuando el dato esta fuera del rango
                    JOptionPane.showOptionDialog(
                            null,
                            "El valor introducino no es válido.\n"
                            + "Valores válidos: " + min + "-" + max + ".",
                            "ERROR",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            ICONO,
                            new Object[]{"Ok"}, "Ok");
                }
            }
        } while (valor < min || valor > max);
        return valor;
    }

    //Método auxiliar para evitar excepciones en entrada por teclado
    public static boolean isParsable(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Método para seleccionar introducir datos manualmente o aleatorios
    public static void tipoListado(int n) {
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "¿Cómo desea introducir los " + n + " datos de temperatura?",
                    "Elige",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    ICONO,
                    new Object[]{"Manualmente", "Aleatorios"}, "Aleatorios");

            switch (opcion) {
                case 0:
                    listadoManual(n);
                    break;
                case 1:
                    listadoRandom(n);
                    break;
            }
        } while (opcion != 0 && opcion != 1);
    }

    //Método para introducir listado manualmente
    public static void listadoManual(int n) {
        for (int i = 0; i < lectura.length - 1; i++) {
            solicitaDato("valor de temperatura " + i + ":"
                    + "\n0 para temperatura bajo umbral soportado."
                    + "\n1 para temperatura sobre umbral soportado.", 0, 1);
        }
        //System.out.println(Arrays.toString(lectura));
    }

    //Método para introducir listado aleatoriamente
    public static void listadoRandom(int n) {
        Random random = new Random();
        for (int i = 0; i < lectura.length - 1; i++) {
            lectura[i] = random.nextInt(2);
        }
        System.out.println(Arrays.toString(lectura));
    }

    //Método de búsqueda de secuencia óptima de temperatura
    public static void buscaOptimo() {

    }

    //Ventana de confirmación de saida del programa
    public static boolean menuSalir() {
        int opcion = JOptionPane.showOptionDialog(
                null,
                "¿Desea ejecutar de nuevo el programa?",
                "Salir",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ICONO,
                new Object[]{"Repetir", "Salir"}, null);

        return opcion != 0;     //true para salir, false para repetir
    }
}
