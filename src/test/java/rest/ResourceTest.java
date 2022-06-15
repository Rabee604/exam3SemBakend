package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;
import entities.*;
import facades.Facade;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class ResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static Guest guest1, guest2, guest3;
    private static Festival festival1, festival2;
    private static Mainshow mainShow1, mainShow2, mainShow3;
    private static GuestDTO guest1DTO, guest2DTO, guest3DTO;
    private static MainShowDTO mainShow1DTO, mainShow2DTO, mainShow3DTO;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        EntityManager em = emf.createEntityManager();


        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        guest1 = new Guest("Name1", "Phone1", "Email1", "Status1");
        guest2 = new Guest("Name2", "Phone2", "Email2", "Status2");
        guest3 = new Guest("Name3", "Phone3", "Email3", "Status3");
        festival1 = new Festival("Name1", "City1", "StartDate1", "Duration1");
        festival2 = new Festival("Name2", "City2", "StartDate2", "Duration2");
        mainShow1 = new Mainshow("Show1", "Duration1", "Location1", "StartDate1", "StartTime1");
        mainShow2 = new Mainshow("Show2", "Duration2", "Location2", "StartDate2", "StartTime2");
        mainShow3 = new Mainshow("Show3", "Duration3", "Location3", "StartDate3", "StartTime3");
        guest1.addShow(mainShow1);
        guest1.addShow(mainShow2);
        guest3.addShow(mainShow1);
        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        try {

            em.getTransaction().begin();

            em.createQuery("delete from Guest ").executeUpdate();
            em.createQuery("delete from Mainshow ").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Festival").executeUpdate();


            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(guest1);
            em.persist(guest2);
            em.persist(guest3);
            em.persist(festival1);
            em.persist(festival2);
            em.persist(mainShow1);
            em.persist(mainShow2);
            em.persist(mainShow3);
            em.getTransaction().commit();

        } finally {
            em.close();
        }


        guest1DTO = new GuestDTO(guest1);
        guest2DTO = new GuestDTO(guest2);
        guest3DTO = new GuestDTO(guest3);
        mainShow1DTO = new MainShowDTO(mainShow1);
        mainShow2DTO = new MainShowDTO(mainShow2);
        mainShow3DTO = new MainShowDTO(mainShow3);
    }

    @AfterEach
    void tearDown() {
        emf.close();
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void getAllShows() {
        System.out.println("Testing all Shows");

        List<MainShowDTO> actualMainShowDTO = given()
                .contentType("application/json")
                .when()
                .get("/info/show")
                .then()
                .extract().body().jsonPath().getList("", MainShowDTO.class);

        assertThat(actualMainShowDTO, containsInAnyOrder(mainShow1DTO, mainShow2DTO, mainShow3DTO));
    }

    @Test
    void getMyShows() {
        System.out.println("Testing to get getMyShows");
        List<MainShowDTO> actualMainShowDTOs = given()
                .contentType("application/json")
                .when()
                .get("/info/guestShow/" + guest1.getName())
                .then()
                .extract().body().jsonPath().getList("", MainShowDTO.class);
        assertThat(actualMainShowDTOs, containsInAnyOrder(mainShow1DTO, mainShow2DTO));
    }

    @Test
    public void getAllGuest() {
        System.out.println("Testing all guest");

        List<GuestDTO> actualGuestDTOs = given()
                .contentType("application/json")
                .when()
                .get("/info/allGuest")
                .then()
                .extract().body().jsonPath().getList("", GuestDTO.class);
        assertThat(actualGuestDTOs, containsInAnyOrder(guest3DTO,guest2DTO,guest1DTO));
    }
    @Test
    void createGuest() {
        System.out.println("Testing to create a guest");

        //Facade facade = Facade.getFacade(emf);
        Guest guest4 = new Guest("Name4", "Phone4", "Email4", "Status4");
        GuestDTO actualGuestDTO = new GuestDTO(guest4);
        String s = GSON.toJson(actualGuestDTO);

        GuestDTO expectGuestDTO = given()
                .contentType("application/json").body(s)
                .when()
                .post("/info/createGuest")
                .then()
                .extract().body().jsonPath().getObject("", GuestDTO.class);

        assertThat(expectGuestDTO.getEmail(), equalTo(actualGuestDTO.getEmail()));
    }
    @Test
    void createShow() {
        System.out.println("Testing to create a show");


        Mainshow mainshow= new Mainshow("Show4", "Duration4", "Location4", "StartDate4", "StartTime4");
        MainShowDTO actualMainShowDTO = new MainShowDTO(mainshow);

        String s = GSON.toJson(actualMainShowDTO);

        MainShowDTO expectShowDTO = given()
                .contentType("application/json").body(s)
                .when()
                .post("/info/createShow")
                .then()
                .extract().body().jsonPath().getObject("", MainShowDTO.class);

        assertThat(expectShowDTO.getName(), equalTo(actualMainShowDTO.getName()));
    }
    @Test
    void createFestival() {
        System.out.println("Testing to create a Festival");
        Festival festival3 = new Festival("Name3", "City3", "StartDate3", "Duration3");
        FestivalDTO actualFestivalDTO = new FestivalDTO(festival3);

        String s = GSON.toJson(actualFestivalDTO);

        FestivalDTO expectFestivalDTO = given()
                .contentType("application/json").body(s)
                .when()
                .post("/info/createFestival")
                .then()
                .extract().body().jsonPath().getObject("", FestivalDTO.class);

        assertThat(expectFestivalDTO.getName(), equalTo(actualFestivalDTO.getName()));
    }
   /* @Test
    void deleteAShowById()
    {
        System.out.println("Testing to delete a show by id");
        MainShowDTO mainShowDTO = given()
                .contentType("application/json")
                .when()
                .delete("/info/delete/" +  mainShow1DTO.getId())
                .then()
                .extract().body().jsonPath().getObject("", MainShowDTO.class);

        assertThat(mainShowDTO.getName(), equalTo(new MainShowDTO(mainShow1).getName()));
    }*/
}
