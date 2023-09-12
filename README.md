# Near Real-Time Weather Data Processing
`v1.0.1`

![Near Real-Time Weather Data Processing](https://github.com/tati2002med/Near-Real-Time-Weather-Data-Processing/assets/95311883/9e8e3249-a890-43b9-bb61-f1c7e5151aa7)

In this project we are trying to learn diffrent technologies from the `back-end` to the `front-end`. In this project we don't have a goal in the weather data itself but the most important is to gain some basic knowledge in near-real time data processing.
- Project Components:
    - `Kappa Architecture`: a data processing architecture that is designed to provide a flexible, fault-tolerant, and scalable architecture for processing large amounts of data in real-time. But in our case we will use it for near-real time because weather data can't be `real time` also can't be too accurate.
    - `Open Weather APIs`:
      - `Geocoding API`: a simple tool that `OpenWeather` developed to ease the search for locations while working with geographic names. We will use this api to get the `lat` & `lon` of the city that the user search for than we will get the weather data of that city. 
      - `Weather Current Data API`: Access current weather data for any location on Earth! `OpenWeather` collects and process weather data from different sources such as global and local weather models, satellites, radars and a vast network of weather stations. Data is available in JSON, XML, or HTML format.
    - `EC2 instance`: Amazon Elastic Compute Cloud is a part of Amazon.com's cloud-computing platform, Amazon Web Services, that allows users to rent virtual computers on which to run their own computer applications. We have use it to create a `Ubuntu VM` to contain our back-end code.
    - `AWS GATEWAY API`: an AWS service for creating, publishing, maintaining, monitoring, and securing REST, HTTP, and WebSocket APIs at any scale. We used it secure the connection between the VM and the front-end that is deployed over `GitHub pages`.
    - `Angular`: a TypeScript-based, free and open-source single-page web application framework. We used it to communicate between our `Spring Boot Api` and the `User Interface`.
    - `Kafka`, `Spark` and `Cassandra`: that is the near-real time data processing team :)
    - `Dashboard Update`: Still working on it. Not supported in the `v1.0.1`.

To try the app go to: https://tati2002med.github.io/weather-app/
### Developer Guide:
In order to run the code on your local machine you can clone the repository:
```bash
git clone https://github.com/tati2002med/Near-Real-Time-Weather-Data-Processing.git
cd Near-Real-Time-Weather-Data-Processing
```
we need some requirements `Zookeeper`, `Kafka`, `Spark` and `Cassandra` you can get them by running the docker compose file:
```bash
sudo docker-compose up -d
```
the **-d** just to not display the logs so you don't have to run another terminal.

For the spark container you can run this command:
```bash
sudo docker run --name spark-container --net put-the-same-network-name-that-the-services-running-in -it apache/spark:v3.2.3 /opt/spark/bin/spark-shell
```

if you don't know the network that the services are using you can run:
```bash
sudo docker inspect kafka-or-cassandra-container-name-just-one-of-them
```

To check that the the services are running use:
```bash
sudo docker ps
```
They should be up and running.

Now, you can choose to run the code by yourself or create a docker image from the app. here is how you can do it:
```bash
sudo docker build -t name-your-image:tag-image .
```
We told docker to create an image from a dockerfile exist in the current directory .

Now, lets create a container for our app:
```bash
sudo docker run --name weather-api-v1 --net put-the-same-network-name-that-the-services-running-in -p 8080:8080 name-your-image:tag-image
```
Now, you can access the app over: **http://localhost:8080**

- if you want to change the varibale envirements you can change them use **-e** tag in running command. here is the varibales that you can adjust:
  ```bash
    CASSANDRA_CONTACT_POINTS=cassandra
    CASSANDRA_KEYSPACE=weather
    CASSANDRA_PORT=9042
    
    KAFKA_BOOTSTRAP_SERVERS=localhost
    KAFKA_PORT=29092
    
    SPARK_MASTER=local[*]
  ```
  you can do it like this:
  ```bash
  sudo docker run --name weather-api-v1 --net put-the-same-network-name-that-the-services-running-in -e CASSANDRA_CONTACT_POINTS=new_cassandra -e KAFKA_PORT=new_port -p 8080:8080 name-your-image:tag-image
  ```
- The last thing don't forget to create a keyspace with same name in your cassandra container:
  ```bash
    sudo docker exec -it cassandra-container-ip cqlsh
  ```
  Now create it:
  ```bash
  CREATE KEYSPACE IF NOT EXISTS weather
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
  ```
    
Enjoy developing the app and happy learning :)
