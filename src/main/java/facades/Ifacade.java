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
    List<FestivalDTO> getAllFestival();
    MainShowDTO  signMeToAShow(String name, String guestName);
    MainShowDTO  createAShow(MainShowDTO mainShowDTO);
    FestivalDTO createAFestival(FestivalDTO festivalDTO);
    GuestDTO createAGuest(GuestDTO guestDTO);
    MainShowDTO deleteAShow(Long id);
    FestivalDTO editFestival (String name, String city, String startDate,String  duration);

}
