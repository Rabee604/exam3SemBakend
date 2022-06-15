package dto;

import entities.Mainshow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainShowDTO {

    private Long id;
    private String name;
    private String duration;
    private String location;
    private String startDate;
    private String startTime;

    public MainShowDTO(Long id, String name, String duration, String location, String startDate, String startTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public MainShowDTO(Mainshow show) {
        this.id = show.getId();
        this.name = show.getName();
        this.duration = show.getDuration();
        this.location = show.getLocation();
        this.startDate = show.getStartDate();
        this.startTime = show.getStartTime();
    }

    public static List<MainShowDTO> getShowDTOs(List<Mainshow> showList) {
        List<MainShowDTO> mainShowDTOList = new ArrayList<>();
        showList.forEach(p -> mainShowDTOList.add(new MainShowDTO(p)));
        return mainShowDTOList;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainShowDTO mainShowDTO = (MainShowDTO) o;
        return name.equals(mainShowDTO.name) && duration.equals(mainShowDTO.duration) && location.equals(mainShowDTO.location) && startDate.equals(mainShowDTO.startDate) && startTime.equals(mainShowDTO.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, location, startDate, startTime);
    }
}
