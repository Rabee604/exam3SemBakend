package facades;

import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;
import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {
    Facade facade = Facade.getFacade(EMF_Creator.createEntityManagerFactoryForTest());
    static EntityManagerFactory emf;
    static EntityManager em;
    Mainshow mainshow1;
    Festival festival1;
    @BeforeEach
    void setUp() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        em = emf.createEntityManager();
        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");
        Guest guest1= new Guest("Name1","Phone1","Email1","Status1");
        Guest guest2= new Guest("Name2","Phone2","Email2","Status2");
        Guest guest3= new Guest("Name3","Phone3","Email3","Status3");
        festival1= new Festival("Festival1","City1","StartDate1","Duration1");
        Festival festival2= new Festival("Festival2","City2","StartDate2","Duration2");
        mainshow1=new Mainshow("Show1","Duration1","Location1","StartDate1","StartTime1");
        Mainshow mainshow2=new Mainshow("Show2","Duration2","Location2","StartDate2","StartTime2");
        Mainshow mainshow3=new Mainshow("Show3","Duration3","Location3","StartDate3","StartTime3");
        guest1.addShow(mainshow1);
        guest1.addShow(mainshow2);
        guest3.addShow(mainshow1);
        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
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
        em.persist(mainshow1);
        em.persist(mainshow2);
        em.persist(mainshow3);
        em.getTransaction().commit();

    }finally {
            em.close();
        }
        }

    @AfterEach
    void tearDown() {
        emf.close();
    }



    @Test
    void getAllShows() {
        System.out.println("Test for getting all show");
        int expected = 3;
        int actual = facade.getAllShows().size();
        assertEquals(expected, actual);
    }

    @Test
    void getMyShow() {
        System.out.println("Test for getting my show");
        int expected = 2;
        int actual = facade.getMyShow("Name1").size();
        assertEquals(expected, actual);
    }

    @Test
    void getAllGuest() {
        System.out.println("Test for getting all guest");
        int expected = 3;
        int actual = facade.getGuest().size();
        assertEquals(expected, actual);
    }


    @Test
    void allFestival(){
        System.out.println("Test for getting all festivals");
        int expected = 2;
        int actual = facade.getFestivals().size();
        assertEquals(expected, actual);

    }
    @Test
    void createAShow() {
        System.out.println("Test create a show");
        int expected = 4;
        Mainshow mainShow4=new Mainshow("Show4","Duration4","Location4","StartDate4","StartTime4");
        MainShowDTO mainShowDTO = new MainShowDTO(mainShow4);
        facade.createAShow(mainShowDTO);
        int actual = facade.getAllShows().size();
        assertEquals(expected, actual);

    }

    @Test
    void createAFestival() {
        System.out.println("Test create a festival");
        int expected = 3;
        Festival festival3= new Festival("Festival3","City3","StartDate3","Duration3");
        FestivalDTO festivalDTO = new FestivalDTO(festival3);
        facade.createAFestival(festivalDTO);
        int actual= facade.getFestivals().size();
        assertEquals(expected, actual);


    }
    @Test
    void createAGuest() {
        System.out.println("Test create a guest");
        int expected = 4;
        Guest guest4= new Guest("Name4","Phone4","Email4","Status4");
        GuestDTO guestDTO = new GuestDTO(guest4);
        facade.createAGuest(guestDTO);
        int actual = facade.getGuest().size();
        assertEquals(expected, actual);

    }

    @Test
    void deleteAShow() {
            System.out.println("Testing delete a showById()");
            int expected = 2;
            MainShowDTO mainShowDTO= facade.deleteAShow(mainshow1.getId());
            int actual = facade.getAllShows().size();
            assertEquals(expected, actual);

        }
        @Test
    void editFestival(){
            System.out.println("Testing editFestival()");
            String expected = "City100";
            FestivalDTO festivalDTO =facade.editFestival(festival1.getName(),"City100","StartDate100","Duration100");
            String actual= festivalDTO.getCity();
            assertEquals(expected, actual);



        }

}