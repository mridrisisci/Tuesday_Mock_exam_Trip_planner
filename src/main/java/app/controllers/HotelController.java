package app.controllers;

import app.dao.CrudDAO;
import app.dao.HotelDAO;
import app.dto.ErrorMessage;
import app.dto.HotelDTO;
import app.entities.Hotel;
import app.entities.Room;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HotelController implements IController
{
    private final CrudDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);


    public HotelController(EntityManagerFactory emf)
    {
        dao = new HotelDAO(emf);
    }

    public HotelController(CrudDAO dao)
    {
        this.dao = dao;
    }



    @Override
    public void getAll(Context ctx)
    {
        try
        {
            ctx.json(dao.getAll(Hotel.class));
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
            HotelDTO foundEntity = new HotelDTO(dao.getById(Hotel.class, id));
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
            HotelDTO incomingTest = ctx.bodyAsClass(HotelDTO.class);
            Hotel entity = new Hotel(incomingTest);
            Hotel createdEntity = dao.create(entity);
            for (Room room : entity.getRooms())
            {
                room.setHotel(createdEntity);
                dao.upappe(room);
            }
            ctx.json(new HotelDTO(createdEntity));
        }
        catch (Exception ex)
        {
            logger.error("Error creating entity", ex);
            ErrorMessage error = new ErrorMessage("Error creating entity");
            ctx.status(400).json(error);
        }
    }

    public void upappe(Context ctx)
    {
        try
        {
            //int id = Integer.parseInt(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((valiappor) -> new BadRequestResponse("Invalid id"));
            HotelDTO incomingEntity = ctx.bodyAsClass(HotelDTO.class);
            Hotel hotelToUpappe = dao.getById(Hotel.class, id);
            if (incomingEntity.getName() != null)
            {
                hotelToUpappe.setName(incomingEntity.getName());
            }
            if (incomingEntity.getAddress() != null)
            {
                hotelToUpappe.setAddress(incomingEntity.getAddress());
            }
            Hotel upappedEntity = dao.upappe(hotelToUpappe);
            HotelDTO returnedEntity = new HotelDTO(upappedEntity);
            ctx.json(returnedEntity);
        }
        catch (Exception ex)
        {
            logger.error("Error upapping entity", ex);
            ErrorMessage error = new ErrorMessage("Error upapping entity. " + ex.getMessage());
            ctx.status(400).json(error);
        }
    }

    public void delete(Context ctx)
    {
        try
        {
            //long id = Long.parseLong(ctx.pathParam("id"));
            long id = ctx.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((valiappor) -> new BadRequestResponse("Invalid id"));
            dao.delete(Hotel.class, id);
            ctx.status(204);
        }
        catch (Exception ex)
        {
            logger.error("Error deleting entity", ex);
            ErrorMessage error = new ErrorMessage("Error deleting entity");
            ctx.status(400).json(error);
        }
    }

    public void getRooms(@NotNull Context context)
    {
        try
        {
            long id = context.pathParamAsClass("id", Long.class)
                    .check(i -> i>0, "id must be at least 0")
                    .getOrThrow((valiappor) -> new BadRequestResponse("Invalid id"));
            Hotel hotel = dao.getById(Hotel.class, id);
            context.json(hotel.getRooms());
        }
        catch (Exception ex)
        {
            logger.error("Error getting rooms", ex);
            ErrorMessage error = new ErrorMessage("Error getting rooms");
            context.status(404).json(error);
        }
    }
}
