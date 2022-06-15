package facades;

import dto.FestivalDTO;
import dto.MainShowDTO;
import entities.Mainshow;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public List<MainShowDTO> getMyShow(){
        return null;
    }
    @Override
   public MainShowDTO  signMeToAShow(){
        return null;
    }
    @Override
   public MainShowDTO  createAShow(){
        return null;
    }
    @Override
    public FestivalDTO createAFestival(){
        return null;
    }
    @Override
    public MainShowDTO deleteAShow(){
     return null;
    }
}
