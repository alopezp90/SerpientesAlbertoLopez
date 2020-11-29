package serpientes;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Test {

    public static Candidato[] candidato;
    public static int n = 26, k = 2;

    public static void main(String[] args) {
        String lectura = "11000111000000111000011001";

        candidato = new Candidato[n / k];
        String[] parts = lectura.split("000");

        int i = 0;
        for (String part : parts) {
            candidato[i] = new Candidato();
            candidato[i].setSubstring(part);
            i++;
        }

        for (int j = 0; j < candidato.length; j++) {
            if (candidato[j] != null) {
                System.out.println(candidato[j].getSubstring());
            }
        }

    }
}
