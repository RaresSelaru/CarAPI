# CarAPI
This is my first project using Spring Boot

 - Features

    -Creates cars with specific features like starting the engine, stopping the engine, driving, shifting gears, refilling the fuel tank, etc.
    
    -Builds a database with three tables (Car, FuelTank, and Gearbox) based on the attributes of the respective classes, 
     such as brand, model, color, engine state (on/off), fuel tank capacity, remaining fuel, idle fuel consumption, in-motion fuel consumption,
     number of gears, and current gear.
     
    -Can display all cars in the database as a JSON code using REST protocols and a third-party program like Postman.
   
    -Can display specific cars using their unique ID.
    
    -Can call the functions of specific cars using their unique ID.
    
    -Allows the consumption of fuel of one or more cars of the user's choice.

- Technology Used

    -Spring Boot
    
    -Hibernate

- Installation

    -Clone the repository to your local machine
    
    -Import the project into your preferred IDE
    
    -Set the properties in the application.properties file according to the user's own properties (database name, port, username, password).
    
    
- Known Issues
  
   -Fuel consumption is not yet programmed efficiently, using the Scheduled class to call the consume() method that contains the logic of consumption, once per minute.
    In case the car runs out of fuel, the engine state will remain true and other functions that depend on the presence of fuel in the tank will also be affected.
    
   -Incorrect fuel consumption display is also yet to be resolved.
   
 - Usage
 
   -Use REST protocols and a third-party program like Postman to access the endpoint URIs in the Controller class
