package app.dto;

import app.entities.Trip;
import app.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO
{
    private Integer id;
    private String startTime;
    private String endTime;
    private Integer longitude;
    private Integer latitude;
    private String name;
    private Integer price;
    private TripType category;

    public TripDTO(Trip trip)
    {
        this.id = trip.getId();
        this.startTime = trip.getStartTime().toString();
        this.endTime = trip.getEndTime().toString();
        this.longitude = trip.getLongitude().intValue();
        this.latitude = trip.getLatitude().intValue();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
    }
}
