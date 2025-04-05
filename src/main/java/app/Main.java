package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.controllers.TripController;
import app.controllers.SecurityController;
import app.routes.Routes;
import app.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main
{
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    //private final static Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args)
    {
        DataAPIReader dataAPIReader = new DataAPIReader();
        TripController tripController = new TripController(emf);
        SecurityController securityController = new SecurityController(emf);
        Routes routes = new Routes(tripController, securityController);



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