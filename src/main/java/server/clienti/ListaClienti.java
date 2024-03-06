package server.clienti;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
@XmlRootElement
public class ListaClienti {
    // array dinamico che tiene traccia dei clienti
    private final ArrayList<Cliente> lista;

    // intero che tiene l'id dei clienti, come auto increment in mysql
    private int nCliente;

    // riferimento singleton
    private static ListaClienti singletonLista;


    /**
     * Costruttore privato per inizializzare il singleton
     * @throws Exception se il singleton viene inizializzato più volte.
     */
    private ListaClienti() throws Exception {
        if (singletonLista != null) {
            throw new Exception("singleton problem");
        } else {
            lista = new ArrayList<>();
            nCliente = 0;
        }

    }


    /**
     * @return il singleton
     */
    public static ListaClienti getLista(){
        if(singletonLista == null){
            try{
                singletonLista = new ListaClienti();
            }catch(Exception e){
                System.out.println("eccezione singleton");
            }
        }
        return singletonLista;
    }


    public synchronized int addCliente(){
        nCliente++;
        Cliente cliente = new Cliente(nCliente);
        lista.add(cliente);
        return nCliente;
    }

    public synchronized Cliente getCliente(int n){
        for(Cliente cliente: lista){
            if(cliente.getNumerocarta() == n){
                return cliente;
            }
        }
        return null;
    }

    public synchronized boolean delCliente(int n){
        return lista.removeIf(cliente -> cliente.getNumerocarta() == n);
    }

    public synchronized ArrayList<Cliente> getClienti(){
        return lista;
    }
}