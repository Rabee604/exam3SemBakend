package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "festival")
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String city;
   private String startDate;
   private String duration;
    @OneToMany(mappedBy = "festival")
    private List<Guest> guestLists = new ArrayList<>();
    public Festival() {
    }
    public void addGuest(Guest guest) {
        this.guestLists.add(guest);
    }
    public Festival(Long id, String name, String city, String startDate, String duration) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
    }
    public Festival(String name, String city, String startDate, String duration) {
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.duration = duration;
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
        Festival festival = (Festival) o;
        return name.equals(festival.name) && city.equals(festival.city) && startDate.equals(festival.startDate) && duration.equals(festival.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, startDate, duration);
    }

    public List<Guest> getGuestLists() {
        return guestLists;
    }

    public void setGuestLists(List<Guest> guestLists) {
        this.guestLists = guestLists;
    }
}