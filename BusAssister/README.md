Application Requirements

[![](http://img.youtube.com/vi/OZAyJ5kyMos/0.jpg)](http://www.youtube.com/watch?v=OZAyJ5kyMos "")


Application is developed to help bus operators at various fronts. One being keeping track of passengers’ pickup and effectively reporting to head office. Apart from that it also helps conductor to perform his job effectively thus increasing customer experience. 
Application to have following functionalities

1.	Onboard Service
2.	Offboard Service
3.	Travel Reports

#### 1. Onboard Service

Onboard service as the name represents, it helps bus conductor to onboard passengers in all respective pick points and effectively 
stay on top of multiple things that happen during travel. Following features will explain the functionality in detail

a.	Identify all passengers for a route/service, their pickup points & destination  
b.	Use GPS coordinates of the bus, recognize passengers for current or next pick up point  
c.	Help conductor to onboard passengers w.r.t pickup points  
d.	As the vehicle travels various points, based on GPS of pickup points, next set of passengers to be coming up on the screene.
	Any passengers who hasn’t onboarded at a pickup point will be immediately reported to head office and head office 
	to contact passenger and help them to onboard at next boarding points  
e.	Since the connection is live, any reservations and cancellations would immediate reflect in application  

#### 2.	Offboard Service
a.	Again, based on passengers drop point & GPS co-ordinates, help conductor in identifying passengers and offboard them safely. 

#### 3.	Reports
a.	At the end of travel or anywhere during the travel, 
 ability to report passengers onboarded vs offboard and passengers who couldn’t onboard for several
 reasons  
b.	All the data will be synched to the server  

#### Advantages

1.	Use technology to keep track of passengers and effectively report information to head office with zero delay 
2.	Paperless service – A unique flagship in travel industry and help society
3.	Accurate and effective way to plan travel 
4.	Bus can start from anywhere unlike only from head office to collect passenger’s information
5.	Effective way to keep track of various buses in various routes for large numbers of passengers for head office and travel points in between

### Development Guidelines
  #### Onboard Service
  
A mobile application developed on android platform to serve the purpose and to follow below application flow guidelines for development
1.	Mobile Application to connect bus operator application using API or a URL based login
2.	Download passenger details for a route (for eg. Hyderabad to Vizag), their pick-up points and drop locations in a local database  
  a.	Passenger Name  
  b.	Phone Number  
  c.	Seat No  
  d.	Pick up point (GPS Co-ordinates)  
  e.	Drop point (GPS Co-ordinates)  
  f.	Boarding point order number  
3.	Once data is populated in Mobile app, conductor to choose service (Onboard / Offboard)
4.	Conductor to choose starting point of the journey or pick up locations
5.	Applications to pick up only passengers based out of that location using GPS coordinates/location from data base
6.	All those passengers to be fitted in a screen with Green as a background colour and displaying following features  
  a.	Name  
  b.	Seat No  
  c.	Pick up location  
  d.	Call icon (to help conductor to make a call directly from the app instead of opening dial pad from outside)  
7.	When a passenger is onboarded, swipe to the right, this will mark as passenger as onboarded in the database
8.	As the bus moves on and if a passenger is not onboarded at a designated pick up point, information in #6 to be displayed with a Red back ground, indicating this needs attention
9.	Passenger information from #8 to be reported to head office call centre immediately, where an executive will get InTouch with passenger and take next actions.
10.	Head office can have following functionality. On contacting passenger head office call centre executive can  
  a.	Help passenger to onboard in upcoming pickup points. They can update pick up point of the passenger and immediately this would reflect in application. In this case, passenger would reflect in next pick up points, and data in #9 now would turn to Orange indicating a change in location of pickup point  
  b.	Cancel ticket based on passengers change of travel plan and update. This would immediate reflect in mobile application and back ground for data in #9 turns to Blue indicating a ticket is cancelled and any new passenger in-route can be onboarded 
11.	When all passengers are onboarded, application to suggest completing pickup service 
12.	Once done, data from mobile app would be synched with head office reporting the on-board service status and all information
 
#### Off board service 
1.	Application should auto start based on approaching first drop off points or driver could start
    the service
2.	Details in #6 of onboard service without call facility to be display based on
    drop points and GPS location of the vehicle
3.	Once offboarded, swiping right for each passenger will update accordingly 
4.	When offboarding is completed for all passenger, by completing off board service, 
    data would be reported to head office. 

#### Reports
At any point during the journey, following reports should display data
1.	Onboard service report  
  a.	No. of passengers Booked  
  b.	No. of passengers onboarded    
  c.	No. of passengers didn’t onboard    
  d.	No. of passengers routed to different location to onboard  
2.	Off board service report  
  a.	No. of passengers onboarded  
  b.	No. of passengers off boarded  
