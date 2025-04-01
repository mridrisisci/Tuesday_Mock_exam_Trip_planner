package app.routes;

import app.config.HibernateConfig;
import app.controllers.GuideController;
import app.utils.Populator;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.controllers.HotelController;
import app.controllers.SecurityController;
import app.enums.Roles;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final GuideController guideController;
    private final SecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public Routes(GuideController guideController, SecurityController securityController)
    {
        this.guideController = guideController;
        this.securityController = securityController;
    }

    public  EndpointGroup getRoutes()
    {
        return () -> {
            path("auth", authRoutes());
            path("protected", protectedRoutes());
            path("guide", guideRoutes());
            path("populate", populateDB(HibernateConfig.getEntityManagerFactory()));
        };
    }

    private  EndpointGroup guideRoutes()
    {
        return () -> {
            get("/all", guideController::getAll);
            post("/create", guideController::create);
            get("/{id}", guideController::getById);
            put("/{id}", guideController::update);
            delete("/{id}", guideController::delete);
        };
    }

    private EndpointGroup populateDB(EntityManagerFactory emf)
    {
        return () -> {
            get("/populate", ctx -> {
                EntityManager em = emf.createEntityManager();
                Populator populator = new Populator();
                populator.resetAndPersistEntities(em);
                ctx.json(jsonMapper.createObjectNode().put("msg", "Database populated with dummy data"));
            });
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
