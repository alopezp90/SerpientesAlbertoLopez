package serpientes;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

/**
 * Serpientes de cascabel. Dado un numero de mediciones entre 1 y 100, compuesto
 * por un String de unos y ceros; determinar la serie mas larga de mediciones
 * que, commenzando y acabando por 1, no contengan mas de un numero dado de
 * ceros consecutivos.
 *
 * @see <a href='https://www.aceptaelreto.com/problem/statement.php?id=444'>
 * Acepta el Reto</a>
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Programa {

    //declaracion de la constante ICONO para usar en JOptionPane (solo visual)
    public static final ImageIcon ICONO = new ImageIcon("src/main/resources/snake_96x.jpg");

    //declaracion de la constante que limita la longitud maxima del String
    public static final int NMAX = 100;

    //declaracion de variables
    public static String lectura;       //String con la serie de medicionnes
    public static int n, k;             //Int con longitud del String y ceros consecutivos permitidos
    public static Candidato[] candidato;//Array de Candidatos que almacena las posibles subcadenas y las analiza 1 a 1

    //metodo main con estructura lógica principal del programa
    public static void main(String[] args) {
        //bucle para repetir el programa mientras el usuario lo desee
        do {
            
            //DATOS
            //inicializa vacio el String en cada vuelta
            lectura = "";
            //solicita los parametros n y k mediante el metodo solicitaDato()
            n = solicitaDato("cantidad de mediciones de temperatura. (1-" + NMAX + ")", 1, NMAX);
            k = solicitaDato("número de mediciones soportadas bajo umbral de temperatura. (0-" + n + ")", 0, n);
            //da opcion al usuario a introducir manualmente el String o aleatoriamente
            tipoListado(n);
            //-----------------------------------------------------------------
            
            //CORTA STRING
            //inicializa el array de Candidato y guarda en el las subcadenas posibles
            creaCandidatos();
            //quita los ceros iniciales y finales que han podido quedar en los substring
            limpiaCandidatos();
            //-----------------------------------------------------------------
            
            //SOLUCION
            //busca entre los candidatos el optimo y muestra la solucion
            resultado();
            //-----------------------------------------------------------------
            
            //permite al usuario repetir la ejecucion o no
        } while (!menuSalir());

    }

    /**
     * Metodo para introducir parametros enteros mediante JOptionPane.
     *
     * @param mensaje mensaje a mostrar en el panel
     * @param min valor minimo permitido para el parametro
     * @param max valor maximo parmitido para el parametro
     * @return int con el valor del parametro introducido por el usuario
     */
    public static int solicitaDato(String mensaje, int min, int max) {
        int valor = max + 1;     //valor inicializado fuera del rango del bucle para repetirlo por defecto
        do {
            Object objeto = JOptionPane.showInputDialog(null,
                    "Introduce " + mensaje,
                    "Dato necesario:",
                    JOptionPane.OK_OPTION,
                    ICONO,
                    null,
                    null);
            if (objeto != null) {   //Obliga a introducir algun valor, no permite salida con "cancelar" o cerrar ventana
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

    /**
     * Metodo de comprobacion del parseo de enteros. Metodo auxiliar para
     * solicitaDato(), filtra que la entrada sea parseable.
     *
     * @param texto entrada de JOptioPane
     * @return true si es parseable como entero, false en caso contrario
     */
    public static boolean isParsable(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo para rellenar las lecturas. El metodo da a elegir entre introducir
     * el String de lecturas manualmente o random y llama al metodo adecuado.
     * Finalmente, muestra el listado una vez relleno.
     *
     * @param n numero de caracteres en el String
     */
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
            //Mantiene en bucle el JOptionPane hasta que haya elegido una opcion
        } while (opcion != 0 && opcion != 1);

        //codigo html para el Label del mensaje en JOptionPane (solo visual)
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

    /**
     * Metodo para introducir manualmente las lecturas. Mediante el metodo
     * solicitaDato() va pidiendo uno a uno los valores.
     *
     * @param n numero de caracteres necesarios
     */
    public static void listadoManual(int n) {
        for (int i = 0; i < n; i++) {
            lectura = lectura + solicitaDato("medición de temperatura " + (i + 1) + ":\n"
                    + "\n0 para temperatura bajo umbral soportado."
                    + "\n1 para temperatura sobre umbral soportado.", 0, 1);
        }
    }

    /**
     * Metodo para introducir aleatoriamente las lecturas. Genera un String de n
     * caracteres 0 y 1.
     *
     * @param n numero de caracteres necesarios
     */
    public static void listadoRandom(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            lectura = lectura + random.nextInt(2);
        }
    }

    //este metodo deberia estar en Candidato.java
    /**
     * Metodo para fraccionar el String en substrings Candidato. La condicion a
     * cumplir es que no contengan k+1 ceros consecutivos, por lo tanto, primero
     * se crea un String que contenga dichos k+1 ceros y posteriormente se
     * splittea el String antes y despues de dichos ceros. Guardando en cada
     * candidato el substring resultante, su posicion dentro del String y su
     * longitud.
     */
    public static void creaCandidatos() {
        //inicializa el array de Candidato
        candidato = new Candidato[n / k];//ANTERIORMENTE n
        //crea la cadena de k+1 ceros
        String ceros = "";
        for (int i = 0; i < k + 1; i++) {
            ceros = ceros + 0;
        }
        //splittea el String y lo guarda temporalmente en el array partes
        String[] partes = lectura.split(ceros);

        //recorre el array partes mientras crea los Candidatos y guarda cada substring en ellos
        int i = 0;
        for (String parte : partes) {
            candidato[i] = new Candidato();
            candidato[i].setSubstring(parte);
            i++;
        }
        //recorre el String buscando la posicion de los candidatos para guardar dicha informacion
        //esta parte del algoritmo es ineficaz, y problablemente pueda lograrse de forma mas sencilla
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
    }

    //este metodo deberia estar en Candidato.java
    /**
     * Metodo que elimina ceros iniciales y finales de los Candidatos. Puesto
     * que el split solo tiene en cuenta k+1 ceros, cualquier otro cero que
     * hubiera se queda guardado en los substrings, este metodo los repasa y
     * cambia su indice y longitud en consecuencia.
     */
    //este metodo no deberia ser necesario si creaCandidatos() fuera mejor.
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
    }

    /**
     * Metodo que busca de entre todos los candidatos el optimo y lo muestra
     * mediante JOptionPane.
     */
    public static void resultado() {
        int elMejor = 0;
        for (int i = 1; i < candidato.length; i++) {
            if (candidato[i] != null) {
                if (candidato[i].getLongitud() > candidato[elMejor].getLongitud()) {
                    elMejor = i;
                }
            }
        }
        //codigo html para el Label del mensaje (solo visual)
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

    /**
     * Metodo para elegir si salir del programa.
     *
     * @return true para salir, false para repetir.
     */
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
