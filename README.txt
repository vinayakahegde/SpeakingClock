The project is a spring boot project

#Build
To build the project run the below command
mvn clean install

#Run
To run the project run the below command
mvn spring-boot:run

#Application
The application is a web application. It has two apis as mentioned below

Api 1 : To get the current time in text format
Curl request : curl -X GET "http://localhost:8080/v1/currentTime"

Api 2 : To convert user time into text format
Curl request : curl -X POST -H "Content-Type:application/json" -d '{"time" : "07:27"}' "http://localhost:8080/v1/userTime"