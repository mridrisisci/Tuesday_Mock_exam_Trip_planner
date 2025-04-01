package app.utils;

import app.entities.Guide;
import app.entities.Trip;
import app.enums.TripType;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Populator
{

    private final Guide guide1 = new Guide();
    private final Guide guide2 = new Guide();
    private final Trip trip1 = new Trip();
    private final Trip trip2 = new Trip();
    private final Trip trip3 = new Trip();
    private final Trip trip4 = new Trip();
    private final Trip trip5 = new Trip();
    private final Trip trip6 = new Trip();

    public Populator()
    {
        // Dummy guide 1
        Guide guide1 = new Guide();
        guide1.setFirstName("John");
        guide1.setLastName("Doe");
        guide1.setEmail("john.doe@example.com");
        guide1.setPhone(12345678);
        guide1.setYearsOfExperience(5.0);

        // Trip for guide1
        Trip trip1 = Trip.builder()
            .startTime(LocalTime.of(8, 0))
            .endTime(LocalTime.of(10, 0))
            .longitude(50.0)
            .latitude(10.0)
            .name("Morning Tour")
            .price(100)
            .category(TripType.CITY)
            .build();
        trip1.setGuide(guide1);
        guide1.addTrip(trip1);

        // Trip 2
        Trip trip2 = Trip.builder()
            .startTime(LocalTime.of(11, 0))
            .endTime(LocalTime.of(13, 0))
            .longitude(53.0)
            .latitude(13.0)
            .name("Afternoon Adventure")
            .price(120)
            .category(TripType.BEACH)
            .build();
        trip2.setGuide(guide1);
        guide1.addTrip(trip2);

        // Trip 3
        Trip trip3 = Trip.builder()
            .startTime(LocalTime.of(16, 0))
            .endTime(LocalTime.of(18, 0))
            .longitude(54.0)
            .latitude(14.0)
            .name("Evening Sightseeing")
            .price(150)
            .category(TripType.SNOW)
            .build();
        trip3.setGuide(guide1);
        guide1.addTrip(trip3);


        // Dummy guide 2
        Guide guide2 = new Guide();
        guide2.setFirstName("Jane");
        guide2.setLastName("Smith");
        guide2.setEmail("jane.smith@example.com");
        guide2.setPhone(87654321);
        guide2.setYearsOfExperience(8.0);

        // Trip 4
        Trip trip4 = Trip.builder()
            .startTime(LocalTime.of(7, 30))
            .endTime(LocalTime.of(10, 30))
            .longitude(55.0)
            .latitude(15.0)
            .name("Morning Hiking Tour")
            .price(130)
            .category(TripType.CITY)
            .build();
        trip4.setGuide(guide2);
        guide2.addTrip(trip4);

        // Trip 5
        Trip trip5 = Trip.builder()
            .startTime(LocalTime.of(12, 30))
            .endTime(LocalTime.of(14, 30))
            .longitude(56.0)
            .latitude(16.0)
            .name("City Heritage Walk")
            .price(110)
            .category(TripType.BEACH)
            .build();
        trip5.setGuide(guide2);
        guide2.addTrip(trip5);

        // Trip 6
        Trip trip6 = Trip.builder()
            .startTime(LocalTime.of(19, 0))
            .endTime(LocalTime.of(21, 0))
            .longitude(57.0)
            .latitude(17.0)
            .name("Night Sky Viewing")
            .price(140)
            .category(TripType.FOREST)
            .build();
        trip6.setGuide(guide2);
        guide2.addTrip(trip6);
    }

    public Map<String, Object> getTrips()
    {
        Map<String, Object> trips = new HashMap<>();
        trips.put("trip1", trip1);
        trips.put("trip2", trip2);
        trips.put("trip3", trip3);
        trips.put("trip4", trip4);
        trips.put("trip5", trip5);
        trips.put("trip6", trip6);
        return trips;
    }

    public Map<String, Object> getGuides()
    {
        Map<String, Object> guides = new HashMap<>();
        guides.put("guide1", guide1);
        guides.put("guide2", guide2);
        return guides;
    }


    public Map<String, Object> getEntites()
    {
        Map<String, Object> entities = new HashMap<>();
        entities.put("guide1", guide1);
        entities.put("guide2", guide2);
        entities.put("trip1", trip1);
        entities.put("trip2", trip2);
        entities.put("trip3", trip3);
        entities.put("trip4", trip4);
        entities.put("trip5", trip5);
        entities.put("trip6", trip6);
        return entities;
    }

    public void resetAndPersistEntities(EntityManager em)
    {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Guide").executeUpdate();
        for (Object entity : getGuides().values())
        {
            em.persist(entity);
        }
        for (Object entity : getTrips().values())
        {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }
}
