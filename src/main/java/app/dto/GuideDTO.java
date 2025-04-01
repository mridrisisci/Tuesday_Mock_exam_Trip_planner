package app.dto;

import app.entities.Guide;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO
{
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private Double yearsOfExperience;
    private List<TripDTO> trips = new ArrayList<>();

    public GuideDTO(Guide guide)
    {
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        this.trips = guide.getTrips().stream().map(TripDTO::new).toList();
    }
}
