# KPI Reporting System - Producer 

## CS 5614: Big Data Engineering Project [Team 4]  

### Project Members:
1. Sarvesh Patil (sarveshpatil@vt.edu)
2. Ankit Parekh (ankitparekh@vt.edu)
3. Pranjal Ranjan (pranjalranjan@vt.edu)
4. Badhrinarayan Malolan (badhrinarayan@vt.edu)
5. Swati Lodha (swatil@vt.edu)

### Producer Project Setup:

#### Pre-requisites:
1. Install Java 8 with %JAVA_HOME% environment variable configured
   ![Alt text](docs/java_installation_verification.png?raw=true)
2. Install IntelliJ Idea Community Edition IDE
3. Install Docker-Desktop for Kafka+Zookeeper setup

#### Steps to run the producer:
1. Clone this repository from IntelliJ Idea IDE (Create project with "Get from VCS" feature using HTTPS GIT URL)
   ![Alt text](docs/spark_consumer_clone.png?raw=true) 
2. Run `docker compose -f ./docker-compose-project.yml up --detach` to start Kafka & Zookeeper containers
   ![Alt text](docs/kafka_zookeeper_start.png?raw=true)
3. Build and Run the sbt project (or Producer.scala) in IDE
4. When the whole streaming process is complete, to stop the Kafka+Zookeeper setup, run `docker compose -f ./docker-compose-project.yml down`