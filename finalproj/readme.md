Reflection: Susie Hodon and Ashby Steward-Nolan

***NOTE ON USER INPUT*** (also in journal)
****For user input****
*We were having trouble implementing a standard input scanner in our program that was compatible with gradle.
*Our solution was to create another txt file labeled userInput.txt, where the user can do and directly edit the text file with the stations they want to travel
*Then the user must COMMIT/save the userInput.txt and then can run the program with the desired stations
*We then created a second scanner to read in the User input, and assign Station and string variables for the users’ imputed start and final destination startions. 

Our Reflection:
We found this project over all pretty fun, engaging and we enjoyed how much variation and choice we had in our implementation and choices. We also loved the visal graphics display, it made the graphs so much more understandable, and we think it really added to our goal of visually displaying how to get from one station to another. We definitley struggled the most with implementing our Depth First Traversal method, it was very complex and had to be modified for our specifc project. Also recursion is getting more familiar, but still always hard to full comprehend. In addition, our depth first traversal was a bit trickier becuase we wanted to keep track of the correct path once it was found using our DFT method, but needed to make sure we were ONLY storing the correct path NOT just the first path, or order of stations.

Overall, we enjoyed this final a lot, and am really excited to keep learning about computer graphics in CS, and also hopefully continue to find ways to use our CS skills to display more datasets in interesting ways.

Development JOURNAL:

Phase 0: Choosing a Dataset

December 4, 2023
We decided to do a flight mapping graph that will choose the shortest flight path between two destinations
Started looking for datasets, specifically ones that had location and distance information
While some datasets had information on longitude and latitude that could be used to calculate distance, we preferred ones that just reported distance
We found a data set that we were able to parse down to just the original airport code, destination airport code, and the distance in miles of the flight between them.

Our Dataset:
Source: Kaggle
Author: ASHISHSHARMA
URL: https://www.kaggle.com/code/flashgordon/welcome-aboard-usa-airport-dataset-eda/input?select=Airports.csv

Number of Nodes:244
Number of Edges:250

Qualities of the Graph:
Directed, the graph will display the flight paths from one airport to another, each edge will represent the flight going one way.
There will be no self edges, because we will not allow for loops, or a flight taking off and ending up at the same airport it took off from
Duplicate Edges are okay, because there will be flights that take off from airport A and lands in airport B, as well as flights that take off in Airport B, and land in Airport A. These two flights will be counted as separate yet duplicate edges in our graph.
Our graph will have edges connecting the different nodes, or airports according to our data sets. 
Our graph will have edges that are also objects, because the edges will hold the data with the distance in miles from one node to the next.
This graph will not account for connections, it will be direct flights only. In our output section, we will give the user the information on how to get from one airport to another, and will state the connection needed if there is no direct flight.
December 5, 2023
We actually decided to scrap yesterday’s idea and use Amtrack station data instead. https://gist.github.com/brentajones/ced054626e8922cddd7009fdcea6b0a7#file-amtrak-csv


Our Dataset:
Source: National Transportation Atlas Database (2015)
Author: National Transportation Atlas Database (2015)
URL: https://gist.github.com/brentajones/ced054626e8922cddd7009fdcea6b0a7#file-amtrak-csv

Number of Nodes: 244
Number of Edges: 250

Qualities of the Graph:
Directed value graph, the graph will display the Amtrack routes from one station to another, each edge will represent the train going one way.
There will be no self edges, because we will not allow for loops, or a train leaving off and ending up at the same station 
Duplicate Edges are okay, because there will be trains that leave from station A and arrive in station B, as well as trains that leave from station B, and arrive in station A. These two trains will be counted as separate yet duplicate edges in our graph.
Our graph will have edges connecting the different nodes, or stations according to our data sets. 
Our graph will have edges that have values, because the edges will hold the data with the distance in miles from one node to the next (we will assume these distances are straight lines connecting each station since location data in our dataset is only listed as x and y coordinates, distance will be calculated using Pythagorean theorem).
This graph will not account for connections, it will be direct trains only. In our output section, we will give the user the information on how to get from one station to another, and will state the connections needed if there is no direct train.
In our documentation, there were some errors in the data formatting, not the data itself. For these issues in format, we deleted them ourselves.
For simplicity, we also decided to get rid of the address of the station, and only use the station 
** We also decided to begin by creating the graph so you could only travel forward through the stations
We had to parse down our data to make it more manageable, we decided to only make a graph of a few routes
The routes we choose:
Texas eagle
Vermonter
City of new orleans
Coast starlight
Because we were only using specific routes, we deleted all other information on stations that were not used

After reading in our data, we realized it didn’t have any information regarding connections. It only has the coordinates and names of stations individually. After looking for a different dataset and not finding any that met all of our needs, we decided to edit the dataset we have now to add connecting stations based on specific Amtrak routes (see above).
Now our data set contains the station, its previous station, and the station that comes next on the amtrack routes. 

Phase 1: Reading In

December 6, 2023
When reading in the data, we chose to read in all of the data on one line and used the split() method to put each piece of information into an ArrayList. From there, only certain indicies/columns were used when making the nodes. We only had one data file and also read in every line because we had already went through and deleted invalid or unused stations. 

In each line, there’s information on coordinates, previous/current/next stations, and addresses. In the Station objects, which act as our nodes, we only used data on the x coordinate, y coordinate, previous station, current station, and next station information. We originally made a hash set of the station objects to prevent duplicates.
Dec 10, 2023:
To further simplify our code, we decided to make separate data files for each Amtrack Route. The lines in the txt file are in order of the train route. This way our file reader will read all the necessary files, depending on the user’s desired locations and only display the necessary graphs. We also decided to change our hash set to an ArrayList instead. This way, we were able to keep the stations in the order that they’re read in which makes it easier to create the node objects with edges. 

To start with, we decided to work with just one train route at first to get the graph working. To make the valueGraph, we decided to use a for loop that goes through all of the Stations in the ArrayList. It will make an edge between the current station and the next one in the ArrayList and by using the makeEdge() method, it will also create new nodes if needed. 

Dec 12 2023:
After completing our project, we realized it made more sense to have all the nodes in the same file, so they are all under routes.txt now.
****For user input****
We were having trouble implementing a standard input scanner in our program that was compatible with gradle.
Our solution was to create another txt file labeled userInput.txt, where the user can do and directly edit the text file with the stations they want to travel
Then the user must COMMIT/save the userInput.txt and then can run the program with the desired stations
We then created a second scanner to read in the User input, and assign Station and string variables for the users’ imputed start and final destination startions. 

Phase 2: Computation

Dec 10 2023:
-We attempted to start adding a new scanner to ask the user which station they’re coming from and where they’re going but we ran into some gradle complications. 
-We also decided to use depth first traversal since our graph will be fairly linear with just a couple nodes with multiple edges. 
Dec 11, 2023
After running into the gradle issues with the other scanner, we decided to use a separate txt file as our ‘user input’. Here, instead of using the terminal to receive user input, the user will directly edit the text file, commit their changes, and then build and run our program to see the graph of the route they will take depending on their input. 
Then we made a second scanner to read in our userInput.txt file, and assign the correct string to String variables we use in our depthFirstTraversal method of the graph of ALL stations.
We had to trim our string variables from the user input to ensure they were uniform before using the variables in different methods.
We made a method (findStation) that searches our allStations ArrayList to find the corresponding Station with the same name as the start station that the user inputted. This way, we were able to pass this Station into the DFT method.
Dec 12 2023:
We computed the simpler statistics like number of nodes by finding the size of the ArrayList that contained all the nodes.  
For the average degree, we the used the information that in evey route that is displayed to the user, every node except for the first and last will have degree 2, and the first and last will have degree 1. We used this information to create and equation to find the average degree using the varying number of nodes in the graph.
For number of edges we used a similar logic, for every route displayed, every node will have two edges coming out of it, except for the first and final node. So we used the degree sum equation to find the number of edges by multiplying the total number of degrees, and dividing it by 2.
For the max degree we created an ArrayList of all the degrees of the nodes on the correct routes, and sorted the ArrayList. Then the max degree was the last element in the ArrayList, because ArrayLists of integers are sorted in ascending order.
As said above for our significant finding about node connectivity, we chose to create a DepthfirstTraversal method, in which our graph of all Stations was traversed in a depth first order, and then once the correct destination station was found, recursed back through the stations finding and saving the correct path.
We chose to do DFT because we wanted to display the correct route from one station to another, and some individual stations were a part of more than one route. This meant that without our recursive DFT, the graph would have added the first station it saw everytime and would not be able to get from stations on different routes, even though this is possible in real like because certain stations have multiple paths.
Using our DFT allowed us to traverse and distinguish between all the possible stations, finding, saving and displaying only those that would directly take the user from their start to their end destination.
We chose to display our graph using Nicks GraphDisplay class.

Phase 3: Output

December 11, 2023
We planned to output only the nodes that the user would need to visit to get from their starting station to their destination
While this worked when going through the graph linearly, we aren’t sure how to keep track of the nodes that were on the right track when there were multiple connections at one station when traversing the graph
Dec 12 2023:
We were able to figure out our issues of traversal by making a recursive DFT method that kept track of the correct path, and return null if no correct path was found.
Again, we displayed our graph visually, reading in our Arralist of stations in the correct route in a for loop, and adding a node and two edges for each Station corresponding to their data. This was the same process as reading the data for all the stations to a graph, but with only the stations in the correct route now, 
This graph is called choosenRoute
Our graph of ALL stations is called allRoutes




