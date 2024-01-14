# DSA-project

This is a Data Stoructue and Algorithms project I did in 2nd year at college.

We were given the following input files (obtained by using TransLink open API https://developer.translink.ca/ enabling access to data about Vancouver public transport system)

• stops.txt – list of all bus stops in the system, cca 8,000 entries  
• transfers.txt – list of possible transfers and transfer times between stops, cca 5,000 entries  
• stop_times.txt – daily schedule containing the trip times of all routes on all stops, cca 1,7 million entries  

There are 4 functionalities as follows that have to be implemented.
## 1st Functionality

This functionality will find shortest paths between 2 bus stops (as input by the user), returning the list of stops en route as well as the associated “cost”.
Stops are listed in stops.txt and connections (edges) between them come from stop_times.txt and transfers.txt files. All lines in transfers.txt are edges (directed), while in stop_times.txt an edge should be added only between 2 consecutive stops with the same trip_id.

eg first 3 entries in stop_times.txt are

9017927, 5:25:00, 5:25:00,646,1,,0,0,  
9017927, 5:25:50, 5:25:50,378,2,,0,0,0.3300  
9017927, 5:26:28, 5:26:28,379,3,,0,0,0.5780

This should add a directed edge from 646 to 378, and a directed edge from 378 to 379 (as they’re on the same trip id 9017927).

Cost associated with edges should be as follows: 1 if it comes from stop_times.txt, 2 if it comes from transfers.txt with transfer type 0 (which is immediate transfer possible), and for transfer type 2 the cost is the minimum transfer time divided by 100.

### Demo

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/1stFunc.gif" />
</p>

This functionality prompts the user to enter stop ids of the source stop and destination stop.
It will find shortest paths between these 2 bus stops and visualise the list of stops en route as well as the associated “cost”.
In a demo above, the user enter 12345 as a stop id of the source stop and 2 as that of the destination stop and the system returns the list of stops in the shortest paths with associated costs. Total cost is 38.8.
Also the user can go back to the main page if "back" is typed.

## 2nd Functionality

Searching for a bus stop by full name or by the first few characters in the name, using a ternary search tree (TST), returning the full stop information for each stop matching the search criteria (which can be zero, one or more stops)

In order for this to provide meaningful search functionality please move keywords flagstop, wb, nb, sb, eb from start of the names to the end of the names of the stops when reading the file into a TST (eg “WB HASTINGS ST FS HOLDOM AVE” becomes “HASTINGS ST FS HOLDOM AVE WB”)

### Demo

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/2ndFunc.gif" />
</p>

This functionality prompts the user to enter bus stop name then displays the information of the bus stop the user entered. A special function is that it can display information of all bus stops that start with a few characters the user entered.
In a demo above, the user entered only cross but the system displays the details of all bus stops that start with cross.

## 3rd functionality

It will search for all trips with a given arrival time, returning full details of all trips matching the criteria (zero, one or more), sorted by trip id

Arrival time should be provided by the user as hh:mm:ss. When reading in stop_times.txt file you will need to remove all invalid times, e.g., there are times in the file that start 27/28 hours, so are clearly invalid. Maximum time allowed is 23:59:59.

### Demo

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/3rdFunc.gif" />
</p>

As a demo above user can put time in a range 00:00:00 ~ 23:59:59 and if there is a trip that matches the time the system returns full details of all trips e.g. user put 12:00:08 and it returns 24 trips with full details.

## 4th Functionality

Provide front interface enabling selection between the above features or an option to exit the programme, and enabling required user input. It does not matter if this is command-line or graphical, as long as functionality/error checking is provided.

It is required to provide error checking and show appropriate messages in the case of erroneous inputs – eg bus stop doesn’t exist, wrong format for time for bus stop (eg letters instead of numbers), no route possible etc.

### Front interface

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/interface.png" />
</p>

A picture above is an interface of main menu. As it is displayed 0 for exit, 1 for 1st functionality... and so on.

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/mainError.gif" />
</p>

And a demo shoes that if user types strings it will ask them to put an interger, and even if an integer is input but not 0 ~ 3, the user will be prompted to enter proper input.

### 1st functionality error handling

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/1stFuncError.gif" />
</p>

The demo illustrates that it does not accept strings and the bus stop id that does not exist, then keeps asking the user to input the ids.

### 2nd functionality error handling

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/2ndFuncError.gif" />
</p>

Above just shows that regardless of a number or a string, if there is no bus stop whose name starts with user inputs, it will not display any results and then user will be asked to type again. 

### 3rd functionality error handling

<p align="center">
  <img src="https://github.com/doimiod/DSA-project/blob/main/demo/3rdFuncError.gif" />
</p>

A demo above shows that the system does not allow incorrect format and correct format but larger than 23:59:59 e.g. 24:00:00.

## Running the Code
```bash
 git clone https://github.com/doimiod/DSA-project
 cd DSA-project/src
 javac Main.java && java Main
```
