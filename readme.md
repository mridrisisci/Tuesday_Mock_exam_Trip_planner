Remember to add a config.properties file in the resources directory. The config.properties file should contain the following properties:
```
DB_NAME=tripplanner
DB_USERNAME=postgres
DB_PASSWORD=your_pw
SECRET_KEY=your_key
ISSUER="TripPlannerAPI"
TOKEN_EXPIRE_TIME=1800000
```
The DB_NAME, DB_USERNAME, DB_PASSWORD, ISSUER, and TOKEN_EXPIRE_TIME properties should be filled in with the appropriate values. The SECRET_KEY property should be a minimum of 32 characters long.


Status 

Gennemført:
2/4 Crud-ops virker på endpoints.
populate endpoint virker.
API-fejlhåndtering
Logger-fejlhndtering


Mangler:
Rest-assured test 
JWT authentication