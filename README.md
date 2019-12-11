# Final Project - Connect 4 Game


## Description

We decided to create Connect Four Online, by implementing a server-client connection architecture. How it works is two players (clients) connect to the server, and then starting with player 1 picks a column. Each player goes back and forth picking a column to drop their chip into. After every chip drop, the server will look for a connect 4 match. If a connect 4 match is found by the server, then it sends a command to the winning and losing clients. Sending a cooresponding win message to the winning player, and a lose message to the losing player. Then the server ends the game.

## Getting Started

## Install
In order to install do the following:
 1. Create a java project folder that has a src folder inside.
 2. Then copy the git link from the git repository, and go to eclipse and import it using the git link.
 3. It then should download the three java files and the README needed, in order to run the game properly.
 
 ## Run
 In order to run do the following:
  1. First run ConnectFourServer.java
  2. Next run ConnectFourClient.java twice in order to get two clients running or two players.
    - If you are running it from separate computers, make sure the ports are matching and then run the Client from both computers. This       should create the same effect as running two instances of the Client on one computer.

## Features 
 
1. Fully functional Connect 4 game.

2. Allows for two players to join into the game via connecting to the server.

3. Allows for a 2-player full Connect 4 experience, where the two clients and the server send commands to eachother on updated chip        placements, and if a match was made.

## Demo Video
Link:

## References
https://medium.com/@ssaurel/creating-a-connect-four-game-in-java-f45356f1d6ba
https://cs.lmu.edu/~ray/notes/javanetexamples/

## Team members
* Greg Golden, Co-Team Lead
* Paul Couture, Co-Team Lead
