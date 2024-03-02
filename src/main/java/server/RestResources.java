package server;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author paolo
 */
@Path("/api")
public class RestResources{
    @GET
    @Produces("application/json")
    @Path("/hello")
    public Response hello(@DefaultValue("") @QueryParam("author")
                             String author){

        String response = "Hello " + author;

        return Response.ok(response).build();
    }
}
