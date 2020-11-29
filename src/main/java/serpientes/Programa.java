package serpientes;

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

    //Declaracion de variables
    public static String lectura;
    public static int n, k;
    public static Candidato[] candidato;

    //Método main con estructura lógica principal del programa
    public static void main(String[] args) {
        do {
            lectura = "";
            n = solicitaDato("cantidad de mediciones de temperatura. (1-" + NMAX + ")", 1, NMAX);
            k = solicitaDato("número de mediciones soportadas bajo umbral de temperatura. (0-" + n + ")", 0, n);
            tipoListado(n);
            creaCandidatos();
            limpiaCandidatos();
            resultado();
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

        String mensaje = "<html>Las mediciones obtenidas son:<br/>"
                + "<p align='center'>" + lectura + "</p></html>";
        JOptionPane.showOptionDialog(
                null,
                new JLabel(mensaje),
                "Mediciones",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ICONO,
                new Object[]{"Ok"}, "Ok");
    }

    //Método para introducir listado manualmente
    public static void listadoManual(int n) {
        for (int i = 0; i < n; i++) {
            lectura = lectura + solicitaDato("medición de temperatura " + (i + 1) + ":\n"
                    + "\n0 para temperatura bajo umbral soportado."
                    + "\n1 para temperatura sobre umbral soportado.", 0, 1);
        }
    }

    //Método para introducir listado aleatoriamente
    public static void listadoRandom(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            lectura = lectura + random.nextInt(2);
        }
    }

    //Inicializa el array de candidatos, busca k+1 ceros consecutivos y fragmenta el String lecturas
    public static void creaCandidatos() {
        candidato = new Candidato[n ];
        String ceros = "";
        for (int i = 0; i < k + 1; i++) {
            ceros = ceros + 0;
        }
        String[] partes = lectura.split(ceros);
        int i = 0;
        for (String parte : partes) {
            candidato[i] = new Candidato();
            candidato[i].setSubstring(parte);
            i++;
        }
        int indice = 0;
        String tmp;
        for (int j = 0; j < candidato.length; j++) {
            if (candidato[j] != null) {
                candidato[j].setIndice(lectura.indexOf(candidato[j].getSubstring(), indice));
                tmp = candidato[j].getSubstring();
                candidato[j].setLongitud(tmp.length());
                indice = candidato[j].getLongitud() + candidato[j].getIndice();
            }
        }

//        for (int j = 0; j < candidato.length; j++) {
//            if (candidato[j] != null) {
//                System.out.println(candidato[j].getSubstring());
//                System.out.println(candidato[j].getIndice());
//                System.out.println(candidato[j].getLongitud());
//            }
//        }
    }

    //Elimina los ceros iniciales y finales de los candidatos, modificando indice y longitud en consecuencia
    public static void limpiaCandidatos() {
        for (int i = 0; i < candidato.length; i++) {
            if (candidato[i] != null) {
                while (candidato[i].getSubstring().startsWith("0")) {
                    candidato[i].setSubstring(candidato[i].getSubstring().substring(1));
                    candidato[i].setIndice(candidato[i].getIndice() + 1);
                    candidato[i].setLongitud(candidato[i].getLongitud() - 1);
                }
                while (candidato[i].getSubstring().endsWith("0")) {
                    candidato[i].setSubstring(candidato[i].getSubstring().substring(0, candidato[i].getLongitud() - 1));
                    candidato[i].setLongitud(candidato[i].getLongitud() - 1);
                }
            }
        }
//        for (int j = 0; j < candidato.length; j++) {
//            if (candidato[j] != null) {
//                System.out.println(candidato[j].getSubstring());
//                System.out.println(candidato[j].getIndice());
//                System.out.println(candidato[j].getLongitud());
//            }
//        }
    }

    //Comprueba cual es el candidato más largo e imprime por pantalla la información para el usuario
    public static void resultado() {
        int elMejor = 0;
        for (int i = 1; i < candidato.length; i++) {
            if (candidato[i] != null) {
                if (candidato[i].getLongitud() > candidato[elMejor].getLongitud()) {
                    elMejor = i;
                }
            }
        }
        String mensaje = "<html>El periodo óptimo tiene longitud <font color='green'>" + candidato[elMejor].getLongitud() + "</font>"
                + " y comienza en la medición <font color='red'>" + (candidato[elMejor].getIndice() + 1) + "</font>:<br/>"
                + "<p align='center'>" + lectura.substring(0, candidato[elMejor].getIndice()) + "<font color='red'>"
                + candidato[elMejor].getSubstring() + "</font>" + lectura.substring(candidato[elMejor].getIndice() + candidato[elMejor].getLongitud())
                + "</p></html>";

        JOptionPane.showOptionDialog(
                null,
                new JLabel(mensaje),
                "Resultado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                ICONO,
                new Object[]{"Ok"}, "Ok");
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
