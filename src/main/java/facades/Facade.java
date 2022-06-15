package facades;

import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;
import entities.Festival;
import entities.Guest;
import entities.Mainshow;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class Facade implements Ifacade {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Facade instance;

    public Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            em = emf.createEntityManager();
            instance = new Facade();
        }
        return instance;


    }
    @Override
    public List<MainShowDTO> getAllShows(){
        List<Mainshow> mainShows=em.createQuery("select m from Mainshow m", Mainshow.class).getResultList();
        return MainShowDTO.getShowDTOs(mainShows);

    }
    @Override
    public List<GuestDTO> getGuest(){
        List<Guest> guestList=em.createQuery("select g from Guest g", Guest.class).getResultList();
        return GuestDTO.getGuestDTOs(guestList);

    }

    public List<FestivalDTO> getFestivals(){
        List<Festival> festivalList= em.createQuery("select f from Festival f",Festival.class).getResultList();
        return FestivalDTO.getFestivalDTOs(festivalList);
    }
    @Override
    public List<MainShowDTO> getMyShow(String name){
        TypedQuery<Guest> query= em.createQuery("select g from Guest g where g.name= :name", Guest.class);
        query.setParameter("name",name);
        Guest guest1 =query.getSingleResult();
        return MainShowDTO.getShowDTOs(guest1.getShowList());
    }
    @Override
   public MainShowDTO signMeToAShow(){
        return null;
    }
    @Override
   public MainShowDTO  createAShow(MainShowDTO mainShowDTO){
        Mainshow mainShow = new Mainshow(mainShowDTO.getName(), mainShowDTO.getDuration(), mainShowDTO.getLocation(), mainShowDTO.getStartDate(), mainShowDTO.getStartTime());
        em.getTransaction().begin();
        em.persist(mainShow);
        em.getTransaction().commit();
        return new MainShowDTO(mainShow);
    }
    @Override
    public FestivalDTO createAFestival(FestivalDTO festivalDTO){
        Festival festival= new Festival(festivalDTO.getName(), festivalDTO.getCity(), festivalDTO.getStartDate(), festivalDTO.getDuration());
        em.getTransaction().begin();
        em.persist(festival);
        em.getTransaction().commit();
        return new FestivalDTO(festival);
    }
    public GuestDTO createAGuest(GuestDTO guestDTO){
        Guest guest= new Guest( guestDTO.getName(),guestDTO.getPhone(),guestDTO.getEmail(),guestDTO.getStatus());
        em.getTransaction().begin();
        em.persist(guest);
        em.getTransaction().commit();
        return new GuestDTO(guest);
    }
    @Override
    public MainShowDTO deleteAShow(Long id){
        Mainshow mainshow = em.find(Mainshow.class, id);
        em.getTransaction().begin();

        em.remove(mainshow);
        em.getTransaction().commit();

        return new MainShowDTO(mainshow);

    }
}
