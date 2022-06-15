package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mainShow")
public class Mainshow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String duration;
    private String location;
    private String startDate;
    private String startTime;
    @ManyToMany(mappedBy = "showList")
    private List<Guest> guestList = new ArrayList<>();
    public Mainshow() {
    }

    public Mainshow(Long id, String name, String duration, String location, String startDate, String startTime) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }
    public Mainshow(String name, String duration, String location, String startDate, String startTime) {
        this.name = name;
        this.duration = duration;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }
    public void addGuest(Guest guest){
        this.guestList.add(guest);
        if(!guest.getShowList().contains(this)){
            guest.addShow(this);
        }
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

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mainshow show = (Mainshow) o;
        return name.equals(show.name) && duration.equals(show.duration) && location.equals(show.location) && startDate.equals(show.startDate) && startTime.equals(show.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, location, startDate, startTime);
    }
}