**How to Use and Deploy the Network - Service - Client Application:**
The Given Code Base Represents a RestFul Client-Server-Remote Server communication that is based in two modules:

ClientServer - clientserv
RemoteServer - remoteserv

ClientServer Application takes 3 arguments REST call to exposed endpoint called `calculate`, running on port 8080
RemoteServer Application accepts 3 arguments REST call to exposed endpoint called `doCalculate`, running on port 8090, does computational work and returns it to the first server that responds to the first made REST call with result

*The 3 arguments are as follow, a valid Double numInputOne, a valid Double numInputTwo and a valid String computType "multiply" (multiply is for now the only valid command that executes)

**Technologies Used:**

1. Java 8
2. Spring Framework | Spring Boot with following modules
 - spring-cloud-starter-hystrix
 - spring-boot-starter-web
 - spring-boot-starter-test
3. Gradle


**!NB! Follow the below given as step by step instructions in order to run the Application(s)**

`$ git clone https://github.com/sbaychev/spring/spring-network-service-client.git`git
`$ cd spring-network-service-client`

# Execute the gradlew shell command as is below, the script would run and execute, no need to have Gradle Installed
`$ ./gradlew build` (or for Windows `gradlew build`)

# running the above first time, downloads the gradle 3.1..
# then downloads project dependencies
# then it runs the gradle build task

# run the bootRun task - it would deploy and start the client serv application on port 8080
`$ ./gradlew bootRun` (or for Windows `gradlew bootRun`)

# open new shell terminal window within the spring-network-service-client path
`$ cd remoteserv `

# run the bootRun task - it would deploy and start the remote serv application on port 8090
`$ ./gradlew bootRun` (or for Windows `gradlew bootRun`)

# execute the following REST command call from within any web browser terminal client
`localhost:8080/calculate?numInputOne=1.0&numInputTwo=2.0&computType=multiply`

# execute the following curl command from within any shell terminal client
`curl "localhost:8080/calculate?numInputOne=1.0&numInputTwo=2.0&computType=multiply"`