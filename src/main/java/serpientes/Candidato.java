package serpientes;

/**
 * Esta clase guarda informacion sobre las subcadenas que son canditas a ser
 * optimas para la serpiente.
 *
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Candidato {

    //instaciacion de variables
    private String substring;
    private int indice;
    private int longitud;

    /**
     * Constructor por defecto de Candidato. Compuesto por un String vacio,
     * indice = 0 y longitud = 0.
     */
    public Candidato() {
        this.substring = "";
        this.indice = 0;
        this.longitud = 0;
    }

    /**
     * Constructor parametrizado de Candidato.
     *
     * @param substring String candidata a ser optima.
     * @param indice int con la posicion del primer caracter del Candidato
     * dentro de la cadena principal.
     * @param longitud int con la longitud de la subcadena
     */
    public Candidato(String substring, int indice, int longitud) {
        this.substring = substring;
        this.indice = indice;
        this.longitud = longitud;
    }

    //metodos getter y setter
    public String getSubstring() {
        return substring;
    }

    public void setSubstring(String substring) {
        this.substring = substring;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
