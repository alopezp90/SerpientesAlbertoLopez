package serpientes;

/**
 * @author Alberto López Puertas
 * <alopezp90@gmail.com>
 */
public class Candidato {

    private String substring;
    private int indice;
    private int longitud;

    public Candidato(String substring, int indice, int longitud) {
        this.substring = substring;
        this.indice = indice;
        this.longitud = longitud;
    }

    public Candidato() {
        this.substring = "";
        this.indice = 0;
        this.longitud = 0;
    }

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
