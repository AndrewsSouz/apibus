## Apibus

Api that expose some endpoints to manipulate bus lines data stored in a MongoDb database, the api have basic endpoints to CRUD operations
and have one more endpoint to search by lines given a central point(latitude,longitude) and a range, then will retrieve all lines that cover the range.
Have one more function to search by address too, missing only an external api to retrieve the coordinates.

For store the data was used MongoDb, because is the database i'm have more knowlege and because GeoQueries function too.

Frameworks and apis used:  
SpringBoot  
Spring Web to develop rest api.  
Spring Data MongoDb to interact with database with simple named methods  
For the Api Request was used RestTemplate, is simple to make requests with it.  

Example request to find all lines

![image](https://user-images.githubusercontent.com/66229329/112041653-66d1ac00-8b25-11eb-893b-a0301b233ee8.png)

Example query to find a line by code

![image](https://user-images.githubusercontent.com/66229329/112042882-cf6d5880-8b26-11eb-8b48-88b804297971.png)

Example request to search lines within a radius
![image](https://user-images.githubusercontent.com/66229329/112041259-f1fe7200-8b24-11eb-9fa3-2c16603157b6.png)



