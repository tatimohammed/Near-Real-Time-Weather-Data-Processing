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

