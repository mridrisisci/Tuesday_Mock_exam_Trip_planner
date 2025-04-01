package app.controllers;

import app.dao.CrudDAO;
import app.dao.GenericDAO;
import app.dto.ErrorMessage;
import app.dto.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.utils.Populator;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripController implements IController
{
    private final CrudDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    public TripController(EntityManagerFactory emf)
    {
        dao = new GenericDAO(emf);
    }

    public TripController(CrudDAO dao)
    {
        this.dao = dao;
    }

    public void populateDB(EntityManagerFactory emf)
    {
        Populator populator = new Populator();
        try (EntityManager em = emf.createEntityManager())
        {
            populator.resetAndPersistEntities(em);
            logger.info("Populated database with dummy data");
        } catch (Exception e)
        {
            logger.error("Error populating database: " + e.getMessage());
        }

    }

    @Override
    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(dao.getAll(Trip.class));
        }
        catch (Exception ex)
        {
            logger.error("Error getting entities", ex);
            ErrorMessage error = new ErrorMessage("Error getting entities");
            ctx.status(404).json(error);
        }
    }

    @Override
    public void getById(Context ctx)
    {

        try {
            //long id = Long.parseLong(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                .check(i -> i>0, "id must be at least 0")
                .getOrThrow((valiappor) -> new BadRequestResponse("Invalid id"));
            TripDTO foundEntity = new TripDTO(dao.getById(Trip.class, id));
            ctx.json(foundEntity);

        } catch (Exception ex){
            logger.error("Error getting entity", ex);
            ErrorMessage error = new ErrorMessage("No entity with that id");
            ctx.status(404).json(error);
        }
    }

    @Override
    public void create(Context ctx)
    {
        try
        {
            TripDTO incomingTest = ctx.bodyAsClass(TripDTO.class);
            Trip entity = new Trip(incomingTest);
            Trip createdEntity = dao.create(entity);
            for (Trip trip : entity.getGuide().getTrips())
            {
               //trip.setGuide(createdEntity);
                dao.update(trip);
            }
            ctx.json(new TripDTO(createdEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error creating entity", ex);
            ErrorMessage error = new ErrorMessage("Error creating entity");
            ctx.status(404).json(error);
        }
    }

    @Override
    public void update(Context ctx)
    {

    }

    @Override
    public void delete(Context ctx)
    {

    }




}
