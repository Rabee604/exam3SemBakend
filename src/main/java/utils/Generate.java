package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Generate {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "test123");
        User admin = new User("admin", "test123");
        User both = new User("user_admin", "test123");
        Guest guest1= new Guest("Name1","Phone1","Email1","Status1");
        Guest guest2= new Guest("Name2","Phone2","Email2","Status2");
        Guest guest3= new Guest("Name3","Phone3","Email3","Status3");
        Festival festival1= new Festival("FestName1","City1","StartDate1","Duration1");
        Festival festival2= new Festival("FestName2","City2","StartDate2","Duration2");
        Mainshow mainshow1=new Mainshow("Show1","Duration1","Location1","StartDate1","StartTime1");
        Mainshow mainshow2=new Mainshow("Show2","Duration2","Location2","StartDate2","StartTime2");
        Mainshow mainshow3=new Mainshow("Show3","Duration3","Location3","StartDate3","StartTime3");
        guest1.addShow(mainshow1);
        guest1.addShow(mainshow2);
        guest3.addShow(mainshow1);
        guest1.setFestival(festival1);
        guest2.setFestival(festival1);
        em.getTransaction().begin();

        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
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
    }

}
