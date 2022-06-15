package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;
import entities.Mainshow;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import errorhandling.API_Exception;
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
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
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
    public Response getMyShow(@PathParam("guest") String guest) {
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
    @GET
    @Path("/allFestival")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFestival() {
        List<FestivalDTO> c = facade.getFestivals();
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

    @DELETE
    @Path("/delete")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteShow(String content) throws API_Exception {
        MainShowDTO mainShow = gson.fromJson(content, MainShowDTO.class);
        String idName;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            idName = json.get("id").getAsString();


        } catch (Exception e) {
            throw new API_Exception("Malformed JSON", 400, e);
        }
        return Response
                .ok()
                .entity(gson.toJson(facade.deleteAShow(Long.parseLong(idName))))
                .build();

    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setShow")
    public Response addToShow(String jsonString) throws API_Exception {

        EntityManager em = EMF.createEntityManager();

        String showName;
        String guestName;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            showName = json.get("showName").getAsString();
            guestName= json.get("guestName").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON", 400, e);
        }
        return Response
                .ok()
                .entity(gson.toJson(facade.signMeToAShow(showName,guestName)))
                .build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edit")
    public Response editFestival(String jsonString) throws API_Exception {

        EntityManager em = EMF.createEntityManager();

        String festivalName;
        String cityName;
        String startDate;
        String duration;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            festivalName = json.get("festivalName").getAsString();
            cityName= json.get("cityName").getAsString();
            startDate= json.get("startDate").getAsString();
            duration= json.get("duration").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON", 400, e);
        }
        return Response
                .ok()
                .entity(gson.toJson(facade.editFestival(festivalName,cityName,startDate,duration)))
                .build();
    }
}
