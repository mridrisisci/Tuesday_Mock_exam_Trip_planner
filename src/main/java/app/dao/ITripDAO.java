package app.dao;

import app.entities.Guide;
import app.entities.Trip;

import java.util.List;

public interface ITripDAO
{
    Guide getTripById(Long id);
    Guide addTrip(Guide guide, Trip trip);
    Guide deleteTrip(Guide guide, Trip trip);
    List<Trip> getTripsForGuide(Guide guide);
}
