package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.GuideController;
import app.controllers.HotelController;
import app.controllers.SecurityController;
import app.routes.Routes;
import app.utils.Populator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main
{
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args)
    {
        GuideController guideController = new GuideController(emf);
        SecurityController securityController = new SecurityController(emf);
        Routes routes = new Routes(guideController, securityController);



        ApplicationConfig
                .getInstance()
                .initiateServer()
                .setRoute(routes.getRoutes())
                .handleException()
                .setApiExceptionHandling()
                .checkSecurityRoles()
                .startServer(7070);
    }
}