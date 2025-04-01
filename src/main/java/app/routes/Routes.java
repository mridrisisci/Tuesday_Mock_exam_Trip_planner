package app.routes;

import app.config.HibernateConfig;
import app.controllers.TripController;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.controllers.SecurityController;
import app.enums.Roles;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final TripController tripController;
    private final SecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final Logger logger = LoggerFactory.getLogger(Routes.class);

    public Routes(TripController tripController, SecurityController securityController)
    {
        this.tripController = tripController;
        this.securityController = securityController;
    }

    public  EndpointGroup getRoutes()
    {
        return () -> {
            path("auth", authRoutes());
            path("protected", protectedRoutes());
            path("guide", guideRoutes());
            logger.info("Routes initialized ( getRoutes() )");
        };
    }

    private  EndpointGroup guideRoutes()
    {
        return () -> {
            get("/all", tripController::getAll);
            post("/create", tripController::create);
            get("/{id}", tripController::getById);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            post("/populate", ctx -> tripController.populateDB(emf));
            logger.info("inside guideRoutes() method");
        };
    }

    private  EndpointGroup authRoutes()
    {
        return () -> {
            get("/test", ctx->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from Open")), Roles.ANYONE);
            get("/healthcheck", securityController::healthCheck, Roles.ANYONE);
            post("/login", securityController::login, Roles.ANYONE);
            post("/register", securityController::register, Roles.ANYONE);
            get("/verify", securityController::verify , Roles.ANYONE);
            get("/tokenlifespan", securityController::timeToLive , Roles.ANYONE);
        };
    }

    private  EndpointGroup protectedRoutes()
    {
        return () -> {
            get("/user_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from USER Protected")), Roles.USER);
            get("/admin_demo",(ctx)->ctx.json(jsonMapper.createObjectNode().put("msg",  "Hello from ADMIN Protected")), Roles.ADMIN);
        };
    }

}
