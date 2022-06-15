package dto;

import entities.Festival;
import entities.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FestivalDTO {

    private Long id;
    private String name;
    private String city;
    private String startDate;
    private String duration;

    public FestivalDTO(Long id, String name, String city, String startDate, String duration) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }

    public FestivalDTO(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.city = festival.getCity();
        this.startDate = festival.getStartDate();
        this.duration = festival.getDuration();
    }

    public static List<FestivalDTO> getFestivalDTOs(List<Festival> festivalList) {
        List<FestivalDTO> festivalDTOS = new ArrayList<>();
        festivalList.forEach(p -> festivalDTOS.add(new FestivalDTO(p)));
        return festivalDTOS;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FestivalDTO that = (FestivalDTO) o;
        return name.equals(that.name) && city.equals(that.city) && startDate.equals(that.startDate) && duration.equals(that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, startDate, duration);
    }
}
