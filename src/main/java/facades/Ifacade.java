package facades;

import dto.FestivalDTO;
import dto.GuestDTO;
import dto.MainShowDTO;

import java.util.List;

public interface Ifacade {

    List<MainShowDTO> getAllShows();
    List<MainShowDTO> getMyShow(String name);
    List<GuestDTO> getGuest();
    List<FestivalDTO> getFestivals();
    MainShowDTO  signMeToAShow();
    MainShowDTO  createAShow(MainShowDTO mainShowDTO);
    FestivalDTO createAFestival(FestivalDTO festivalDTO);
    GuestDTO createAGuest(GuestDTO guestDTO);
    MainShowDTO deleteAShow(Long id);

}
