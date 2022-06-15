package dto;

import entities.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String status;

    public GuestDTO(Long id, String name, String phone, String email, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public GuestDTO(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.phone = guest.getPhone();
        this.email = guest.getEmail();
        this.status = guest.getStatus();

    }
    public static List<GuestDTO> getGuestDTOs(List<Guest> guestList) {
        List<GuestDTO> guestDTOList = new ArrayList<>();
        guestList.forEach(p -> guestDTOList.add(new GuestDTO(p)));
        return guestDTOList;

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
        GuestDTO guestDTO = (GuestDTO) o;
        return name.equals(guestDTO.name) && phone.equals(guestDTO.phone) && email.equals(guestDTO.email) && status.equals(guestDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, status);
    }
}
