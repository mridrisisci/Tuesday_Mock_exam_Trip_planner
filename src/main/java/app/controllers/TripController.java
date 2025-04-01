package app.controllers;

import app.dao.CrudDAO;
import app.dao.GenericDAO;
import app.utils.Populator;
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
        logger.info("Inside populateDB method");
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
    public void create(Context ctx)
    {

    }

    @Override
    public void getById(Context ctx)
    {

    }

    @Override
    public void getAll(Context ctx)
    {

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
