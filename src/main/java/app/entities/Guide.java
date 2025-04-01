package app.entities;


import app.dto.GuideDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guide")
public class Guide
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private Double yearsOfExperience;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "guide", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Trip> trips = new ArrayList<>();

    public Guide(GuideDTO guideDTO)
    {
        this.id = guideDTO.getId();
        this.firstName = guideDTO.getFirstName();
        this.lastName = guideDTO.getLastName();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
    }

    public Guide(List<Trip> trips)
    {
        this.trips = trips;
    }

    public void addTrip(Trip trip)
    {
        if (trip != null)
        {
            trips.add(trip);
            trip.setGuide(this);
        }
    }

    public void deleteTrip(Trip trip)
    {
        if (trips != null)
        {
            trips.remove(trip);
            trip.setGuide(null);
        }
    }
}
