package server;
import server.clienti.Cliente;
import server.clienti.ListaClienti;
import server.premi.ListaPremi;
import server.premi.Premio;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

/**
 *
 * @author paolo
 */
@Path("/api")
public class RestResources{
    private final ListaClienti listaClienti = ListaClienti.getLista();
    private final ListaPremi listaPremi = ListaPremi.getLista();

    @GET
    @Path("/cliente/all")
    @Produces("application/json")
    public Response getClienti(){
        return Response.ok(listaClienti.getClienti()).build();
    }

    @GET
    @Path("/cliente/{id}")
    @Produces("application/json")
    public Response getCliente(@PathParam("id") int id){
        Cliente target = listaClienti.getCliente(id);
        if(target == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(target).build();
    }

    @POST
    @Path("/cliente/new")
    @Produces("application/json")
    public Response postCliente(){
        int id = listaClienti.addCliente();
        String path = "/cliente/" + id;
        URI destination = URI.create(path);
        return Response.created(destination).build();
    }

    @DELETE
    @Path("/cliente/{id}")
    @Produces("application/json")
    public Response cancellaCarta(@PathParam ("id") int id){
        return Response.ok("{\"status\":" + listaClienti.delCliente(id) + "}").build();
    }

    @PUT
    @Path("/cliente/{id}")
    @Produces("application/json")
    public Response addPunti(@PathParam("id") int id, @QueryParam("spesa") double spesa){
        int punti = (int)Math.floor(spesa / 10);
        listaClienti.getCliente(id).incrementPunti(punti);

        return Response.ok("{" +
                "\"incremento\":" + punti + "," +
                "\"totale\":" + listaClienti.getCliente(id).getPunti()
                + "}").build();
    }
    
    @GET
    @Path("/premio/all")
    @Produces("application/json")
    public Response getPremi(){
        return Response.ok(listaPremi.getPremi()).build();
    }

    @POST
    @Path("/premio/new")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createPremio(Premio premio) {
        int id = listaPremi.addPremio(premio);
        String path = "/premio/" + id;
        URI destinationURI = URI.create(path);
        return Response.created(destinationURI).build();
    }

    @GET
    @Path("/premio/{id}")
    @Produces("application/json")
    public Response getPremio(@PathParam("id") int id) {
        Premio target = listaPremi.getPremio(id);
        if (target == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(target).build();
    }

    @PUT
    @Path("/premio/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response editPremio(@PathParam("id") int id, Premio premio){
        Premio toEdit = listaPremi.getPremio(id);

        if (toEdit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        toEdit.setNome(premio.getNome());
        toEdit.setValore(premio.getValore());

        return Response.ok(toEdit).build();
    }

    @DELETE
    @Path("/premio/{id}")
    @Produces("application/json")
    public Response deletePremio(@PathParam("id") int id){
        return Response.ok("{\"status\":" + listaPremi.deletePremio(id) + "}").build();
    }

    @PUT
    @Path("/premio/riscatta/{id}")
    @Produces("application/json")
    public Response riscattaPremio(@PathParam("id") int id, @QueryParam("clienteId") int clienteId){
        int costoPremio = listaPremi.getPremio(id).getValore();
        int saldo = listaClienti.getCliente(clienteId).getPunti();

        if(costoPremio > saldo){
            return Response.ok("{\"errore\": \"saldo insufficiente\"}").build();
        }

        listaClienti.getCliente(clienteId).incrementPunti(-costoPremio);

        return Response.ok("{" +
                "\"decremento\":" + costoPremio + "," +
                "\"nuovoSaldo\":"+listaClienti.getCliente(clienteId).getPunti() +
                "}").build();
    }
}