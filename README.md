# Makersharks user registration assessment - Twarit Soni
## Steps to setup maven project: 
* Create a database using MySQL workbench and name it as: makersharks
* Download the project as zip and extract it.
* Open Eclipse IDE EE. Click on file then click on open project from file system and then select the project folder and import the maven project.
* Then open file MakersharksUserRegistrationApplication.java from src/main/java and then right click and choose run as java application.
* After successful execution to open swagger-ui, open your browser and paste http://localhost:8080/swagger-ui/index.html
### Endpoints Requests: 
#### POST /api/user/register :
* Request Body:  {
  "username": "twarit08",
  "password": "makersharks08",
  "email": "twarit.s88@gmail.com",
  "firstName": "Twarit",
  "lastName": "soni"
  }
#### POST /api/user/auth :
* Request Body: {
  "username": "twarit08",
  "password": "makersharks08"
}

#### GET /api/user/fetch :
* Request Param -> username : "twarit08"
* /api/user/fetch?username=twarit08
* The endpoint will fetch result after successful authorization of user.
* First register the user and then generate token using the above api(/api/user/auth) and paste the token generated in Authorization of swagger-ui.
  
