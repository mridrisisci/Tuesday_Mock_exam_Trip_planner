package app.controllers;

import app.dao.CrudDAO;
import app.dao.GenericDAO;
import app.dto.ErrorMessage;
import app.dto.NasaDTO;
import app.dto.TripDTO;
import app.entities.Trip;
import app.utils.DataAPIReader;
import app.utils.Populator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NasaController implements IController
{
    private final CrudDAO dao;
    private final String BASE_URL = "https://api.nasa.gov/planetary/apod?api_key=";
    private final String API_KEY = System.getenv("NASA_KEY");
    private final DataAPIReader dataAPIReader;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(NasaController.class);

    public NasaController(EntityManagerFactory emf, DataAPIReader dataAPIReader)
    {
        dao = new GenericDAO(emf);
        this.dataAPIReader = dataAPIReader;
        this.objectMapper.registerModule(new JavaTimeModule());
    }


    public NasaController(CrudDAO dao, DataAPIReader dataAPIReader)
    {
        this.dao = dao;
        this.dataAPIReader = dataAPIReader;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void getNasaDTO(Context ctx)
    {
        NasaDTO dto;
        try
        {
            String json = dataAPIReader.getDataFromClient(BASE_URL + API_KEY);
            JsonNode node = objectMapper.readValue(json, JsonNode.class);
            dto = objectMapper.convertValue(node, new TypeReference<NasaDTO>() {});
            ctx.json(dto);
        } catch (Exception e)
        {
            logger.error("Error getting NASA data", e);
            ErrorMessage error = new ErrorMessage("Error getting NASA data");
            ctx.status(404).json(error);
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
