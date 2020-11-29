package serpientes;

/**
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
import java.util.Arrays;
import java.util.Random;

public class Test2 {

    public static int n = 16, k = 2;
    public static int[] lectura = new int[n];
    public static String lecturaString;

    public static void main(String[] args) {
        
        Random random = new Random();
        for (int i = 0; i < lectura.length - 1; i++) {
            lectura[i] = random.nextInt(2);
        }
        
        lecturaString = Arrays.toString(lectura);

    }
}
