package facades;

import dto.FestivalDTO;
import dto.MainShowDTO;

import java.util.List;

public interface Ifacade {

    List<MainShowDTO> getAllShows();
    List<MainShowDTO> getMyShow();
    MainShowDTO  signMeToAShow();
    MainShowDTO  createAShow();
    FestivalDTO createAFestival();
    MainShowDTO deleteAShow();

}
