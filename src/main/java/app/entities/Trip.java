package app.entities;

import app.dto.TripDTO;
import jakarta.persistence.*;
import lombok.*;
import app.enums.TripType;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip")
@Builder
public class Trip
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Double longitude;
    private Double latitude;
    private String name;
    private Integer price;
    private TripType category;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    @ToString.Exclude
    private Guide guide;

    public Trip(Double latitude, Double Longitude)
    {
        this.latitude = latitude;
        this.longitude = Longitude;
    }
    public Trip(TripDTO tripDTO)
    {
        this.startTime = LocalTime.parse(tripDTO.getStartTime());
        this.endTime = LocalTime.parse(tripDTO.getEndTime());
        this.latitude = tripDTO.getLatitude().doubleValue();
        this.longitude = tripDTO.getLongitude().doubleValue();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
    }


}
