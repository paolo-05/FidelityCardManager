package server.premi;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class ListaPremi {
    private final ArrayList<Premio> lista;
    private int nPremio;
    private static ListaPremi singletonLista;

    private ListaPremi() throws Exception {
        if (singletonLista != null) {
            throw new Exception("singleton problem");
        }
        else {
            lista = new ArrayList<>();
            nPremio = 0;
        }
    }

    public static ListaPremi getLista(){
        if(singletonLista == null){
            try{
                singletonLista = new ListaPremi();
            }catch(Exception e){
                System.out.println("eccezione singleton");
            }
        }
        return singletonLista;
    }

    public synchronized int addPremio(Premio premio) {
        nPremio++;
        premio.setId(nPremio);
        lista.add(premio);
        return nPremio;
    }

    public synchronized Premio getPremio(int n) {
        for (Premio p: lista){
            if(p.getId() == n){
                return p;
            }
        }
        return null;
    }

    public synchronized boolean riscattaPremio () {return false;}

    public synchronized boolean deletePremio(int id){
        return lista.removeIf(premio -> premio.getId() == id);
    }

    public synchronized ArrayList<Premio> getPremi() { return lista; }
}
