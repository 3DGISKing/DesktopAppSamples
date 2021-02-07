# JavaFx MVC Demo

## Aim: 
This assignment focuses on building a graphical user interface to the logic. 
 
## Suggested Work Plan:  
First note that the internal structures of the kiosk model have been adjusted a little to make GUI building a little easier, so use the code skeleton given as a starting point, rather than trying to adapt your own assignment. If you do this, you can complete the assignment without modifying this part of the code at all, leaving only the GUI components to complete. 
This assignment is also less linear from the perspective of which bit of the code to work on - there are several groups of classes and FXML objects that usefully developed "in parallel". There is, however, a sensible meta-level order in which to approach the assignment: 

1. Build the three main menus (kiosk, catalogue and admin): these share significant structure - you really just need to change where the buttons point to and their sizes.   
2. Build empty or partial windows for everything else: so, when you click a button in the main menu, something appears, even if it's a rudimentary or empty window.   
3. Add the close buttons.   
4. Add the components that don’t require lists or tables. You should be able to complete the add customer and movie windows entirely at this point.   
5. Once you have a good idea about how to handle lists (Week 10), complete the customer record and favorites menu. You can also partially complete many of the other windows (remove customer and movies, show movies by year and genre, rent a movie, return a movie and top-up an account – Note: don’t worry about disabling and enabling the buttons at this point).   
6. About this time, you should also know how to select an item in a list or a table, this will allow you to complete the remove customer and movies windows, the show by genre and year and the rent and return, except for enabling and disabling buttons.   

## Implementation: 
There are a few windows to implement, by approaching the assignment in terms of GUI functionality across windows, you actually only have a limited number of tasks. 
It is also worth noting that several of the windows are very closely related, thus it is useful to think of them in certain groups, rather than individually (note that the order here is not suggesting difficulty): 

A. The main menu, the catalogue menu and the admin menu.  
B. The add movies and add customers windows.  
C. Remove movies and remove customers' windows.  
D. The customer record and customer favourites windows. (These can also be thought of as a sub- group of group G, and related to group F.)   
E. The show catalogue and available movies windows.  
F. The show movies by year and genre windows.  
G. The rent, return and top-up account windows.  


[![](http://img.youtube.com/vi/CitK_SdgAX4/0.jpg)](http://www.youtube.com/watch?v=CitK_SdgAX4 "")