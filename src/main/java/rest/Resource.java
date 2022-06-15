package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.Facade;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class Resource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Facade facade = Facade.getFacade(EMF_Creator.createEntityManagerFactory());
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    @GET
    @Path("/show")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllShows() {
        List<MainShowDTO> c = facade.getAllShows();
        return Response
                .ok()
                .entity(gson.toJson(c))
                .build();
    }
    @GET
    @Path("/guestShow/{guest}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOwnerByBoat(@PathParam("guest") String guest) {
        return Response
                .ok()
                .entity(gson.toJson(facade.getMyShow(guest)))
                .build();
    }

    @GET
    @Path("/allGuest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGuest() {
        List<GuestDTO> c = facade.getGuest();
        return Response
                .ok()
                .entity(gson.toJson(c))
                .build();
    }
    @POST
    @Path("createGuest")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGuest(String content) {
        GuestDTO guestDTO = gson.fromJson(content, GuestDTO.class);
        return Response
                .ok()
                .entity(gson.toJson(facade.createAGuest(guestDTO)))
                .build();
    }
    @POST
    @Path("createShow")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createShow(String content) {
        MainShowDTO mainShowDTO = gson.fromJson(content, MainShowDTO.class);
        return Response
                .ok()
                .entity(gson.toJson(facade.createAShow(mainShowDTO)))
                .build();
    }
    @POST
    @Path("createFestival")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createFestival(String content) {
        FestivalDTO festivalDTO = gson.fromJson(content, FestivalDTO.class);
        return Response
                .ok()
                .entity(gson.toJson(facade.createAFestival(festivalDTO)))
                .build();
    }
}