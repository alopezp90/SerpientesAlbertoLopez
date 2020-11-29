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
    
    public static void main(String[] args) {
        
        int opcion = JOptionPane.showOptionDialog(
                    null,
                    "Pueba",
                    "Menú Principal",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    ICONO,
                    new Object[]{"Modo Limpieza", "Modo Carga", "Configuración", "Salir"}, null);
    }
}
