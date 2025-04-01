package app.dao;

import app.entities.Guide;
import app.entities.Trip;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TripDAO extends GenericDAO implements ITripDAO
{
    public TripDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    @Override
    public Guide getTripById(Long id)
    {
        return super.getById(Guide.class, id);
    }
    @Override
    public Guide addTrip(Guide guide, Trip trip)
    {
        guide.addTrip(trip);
        return this.update(guide);
    }
    @Override
    public Guide deleteTrip(Guide guide, Trip trip)
    {
        guide.deleteTrip(trip);
        return this.update(guide);
    }
    @Override
    public List<Trip> getTripsForGuide(Guide guide)
    {
        return guide.getTrips();
    }

}
