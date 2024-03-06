package server.clienti;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author rove
 */
@XmlRootElement
public class Cliente implements Serializable {
    int numerocarta;
    int punti;
    public Cliente(int numerocarta){
        this.numerocarta = numerocarta;
        punti = 0;
    }

    public int getNumerocarta() {
        return numerocarta;
    }

    public void setNumerocarta(int numerocarta) {
        this.numerocarta = numerocarta;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void incrementPunti(int punti) {
        this.punti += punti;
    }
}