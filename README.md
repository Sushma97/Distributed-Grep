# Distributed log analyzer
This project has the ability to perform distributed grep which will help us in querying 
distributed log files across all machines, from any one of the machines.

## Design
We have used client-server architecture with multithreading. 

All machines are treated as servers, waiting for grep requests. Any machine can act as a client and send grep requests to all the servers. 

Client employs multithreading to connect to each server and send them grep queries, receive the response and print them locally. 
Even if any machine is offline/crashed, it does not affect the response from other machines. Server executes the grep query locally 
and can handle multiple client grep requests at once through multithreading. This way, there is no delay in the response to grep request, 
even when there are multiple clients querying at the same time. Thus, our system is fault tolerant, scalable and quick.

## Package Dependencies
- Java 8
- Maven
- Unix4j
- Apache commons cli 
- Junit 
- Mockito

## Instructions
- Step 1: Update the server.json file in resources to server/machine details
- Step 2: Run the server. Server should be running in all the machines with log files.
  * ssh into the machine ```ssh <NETID>@fa22-cs425-ggXX.cs.illinois.edu``` 
  * Clone the project ```git clone https://gitlab.engr.illinois.edu/sushmam3/mp1_cs425_grep.git```
  * Build the project ```mvn -DskipTests package```
  * cd to scripts folder and run the server.sh ```./server.sh 9876``` .
  9876 is the port number
  * Server should be running and waiting for grep request from client
- Step 3: Run the client. Any machine can run the grep query by running the client. 
  * ssh into the machine from which we want to run the grep query.
  * cd to scripts folder in project directory. 
  * run the client.sh. ```./client.sh -pattern "" -c -v -F -i -n -l -x"```. Pattern to be searched for is given through -pattern argument (required argument). Grep options are also supported. We can pass multiple grep options together. 
  * Result is output to stdout. The result can also be piped into a file. 
- Step 4: Run unit tests (Optional): We have developed unit tests for each function and also distributed unit test to verify the distributed grep functionality. Distributed unit test can be tested once all the machines have the server running. ```mvn test```
