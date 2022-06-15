package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String status;
    @ManyToOne
    @JoinColumn(name = "festival_id")
    private Festival festival;

    @ManyToMany
    @JoinTable(
            name = "guest_MainShow",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "MainShow_id"))
    private List<Mainshow> showList = new ArrayList<>();
    public Guest() {

    }
    public Guest(Long id, String name, String phone, String email, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }
    public Guest(String name, String phone, String email, String status) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }



    public void addShow(Mainshow show){
        this.showList.add(show);
        if(!show.getGuestList().contains(this)){
            show.addGuest(this);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return name.equals(guest.name) && phone.equals(guest.phone) && email.equals(guest.email) && status.equals(guest.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, status);
    }

    public List<Mainshow> getShowList() {
        return showList;
    }

    public void setShowList(List<Mainshow> showList) {
        this.showList = showList;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
        festival.addGuest(this);
    }

}