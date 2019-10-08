## MoodEvent Model Class ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)
**US 02.03.01** Story Point: 1 risk Level: low 
- As a participant, I want to specify the social situation for a mood event to be one of: alone, with one other person, with two to several people, or with a crowd. 

**US 02.01.01** Story Point: 1 risk Level: low 
- As a participant, I want to express the reason why for a mood event using a brief textual explanation (no more than 20 characters or 3 words). 

**US 02.02.01** Story Point: 1 risk Level: low 
- As a participant, I want to express the reason why for a mood event using a photograph. 

**US 06.01.01** Story Point: 1 risk Level: low 
- As a participant, I want to optionally attach my current location to a mood event. 

**US 01.01.01** Story Point: 1 risk Level: low 
- As a participant, I want to add a mood event to my mood history, each event with the current date and time, a required emotional state, optional reason, and optional social situation. 


### In order to implement the above user stories: 
- [ ] **Requires a MoodEvent Model class**; The required instance variables of the class are as follows: 
- date (Date) not null, 
- emotionalState (String) not null, 
- reasonInText (String <= 20 Characters) nullable, 
- reasonInPhoto (String) nullable, 
- socialSituation (String : "alone", "with one other person", "with two to several ppl", "with a crowd") nullable, 
- locationOfMoodEvent (Geolocation, retrieve from GPS) nullable.

- [ ] Requires encapulation to ensure data integrity; getters and setters of instance variables should be implemented.

## Database Utility Class ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)

**Database Implementation : Story Point: 5 risk Level: high** 
- [ ] requires a Mood History Database  with tables as follows: 
- a table that records All MoodEvents associated with a participant, primary key : username
- a table that stores participant authentication information, primary key : username 
- a table that stores all participants relationship, primary key : username 
- a table that stores all pending Friend Requests of a participants. 
- [ ] a static class with name "Database" should be implemented in the project with class methods as follows: 
- authenticateParticipant
- retrieveAllMoodEventOfAParticipant
- retrieveFriendRequestOfAParticipant
- retrieveAllParticipantsOpenToFollow
- retrieveGeolocationOfMoodEventsOfAParticipant
- more to come ...


## Participant Login/ Signup Screen ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)
**US 03.01.01** Story Point: 3 risk Level: high
As a user, I want a profile with a unique username. 
- [ ] requires an **Android Activity and its corresponding layout xml** which serves two purposes:  as an login screen; as a new participant signup screen.
#### Layout Design 
- [ ] the layout should contains the following Widgets:
- An Image View displaying Logo (Project Name)
- An Edit Text view for Username 
- An Edit Text view for password
- a button with Text "Sign Up" and a button with Text "Log In"
- After pressing Login Button and detected that participant entered wrong password, text should be displayed under password Edit Text in red that saying "Invalid password, please try again".
- After pressing Login Button and detected that participant entered username that doesn't exist, text should be displayed under username Edit Text in red that saying "Invalid username, please try again".



## Add/View/Edit MoodEvent Screen ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)

**US 01.02.01** Story Point: 1 risk Level: low 
- As a participant, I want consistent emoticons and colors to depict and distinguish the emotional states in any view.
 - [ ] requires an Enum that binds emotionalState to a certain color in hash.  

**US 01.03.01** Story Point: 1 risk Level: low 
- As a participant, I want to view a given mood event and all its available details. 

**US 01.04.01** Story Point: 1 risk Level: low 
- As a participant, I want to edit the details of a given mood event of mine. 


### In order to implement the above two user stories: 
- [ ] Requires an **Android Activity and its corresponding layout xml**.
- The activity and the layout serves two purposes: to Allow participant Add a new MoodEvent and Edit an existing MoodEvent. 
#### Layout Design
- [ ] The layout file should contain views as follows: 
- Edit Text for date and its prompt 
- Edit Text for emotionalState and its prompt 
- Edit Text for reasonInText and its prompt 
- an embedded camera view to allow participant takes photo to explain the reason of the mood event.
- an dropdown list for socialSituation; Values of the dropdown are : "alone", "with one other person", "with two to several ppl", "with a crowd".
- A CheckBox, when checked, retrieve geolocation where the MoodEvent takes place from GPS.
- A Button, when participant is adding a new MoodEvent, has the text "Add"; When participant is editing an existing mood event, has the text "Update".
- [ ] Requires implementations that assists the participant in proper data entry. For example, use appropriate user interface controls to enforce particular data types and avoid illegal values.
- [ ] After pressing the "Add/ Update" button, the participant should be directed back to MoodEvent History Screen. 

## MoodEvent History Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 01.05.01** Story Point: 1 risk Level: low  
As a participant, I want to delete a given mood event of mine.

**US 05.03.01** Story Point: 2 risk Level: medium 
As a participant, I want to view as a list the most recent mood events (**the most recent 1 mood event**) of the other participants I am granted to follow, sorted by date and time, in reverse chronological order (most recent coming first).

**US 04.01.01** Story Point: 2 risk Level: medium 
- As a participant, I want to view as a list my mood history, sorted by date and time, in reverse chronological order (most recent coming first).

**US 04.02.01** Story Point: 1 risk Level: low 
- As a participant, I want to filter my mood history list to show only mood events with a particular emotional state.

### In order to implement the above user stories:
- [ ] Requires an **Android Activity and its corresponding layout xml** which serves two purposes: Display Mood History of the Participant; Display Mood History of the Participants that the current participant follow. 
#### Layout Design
- [ ] The layout should be in a tabbed layout with the following two tab:
- [ ] **First tab** should contain a Recycler view which is used to display MoodEvent history of the current participant. 
- [ ] A search bar above the Recycler View so that when participant enters, only the MoodEvent with emotionalState value same as the participant's inputs are left on the screen. 
- [ ] A Floating Action button below the Reycler View which serves as a "Add MoodEvent" Button, when pressed, directs participant to Add/View/Edit MoodEvent Screen. 
- [ ] Requires a Recycler View list item xml which displays minimum information(date, time, emotionalState.) of a MoodEvent, An edit button, when pressed redirects the current participant to  Add/View/Edit MoodEvent Screen. Also a delete button, when pressed deletes the MoodEvent from the current participant's MoodEvent history
- [ ] **Second tab** should contain a Recycler View which is used to display MoodEvent history of the Friends of the current participant in read-only mode. 
- [ ] Requires a Recycler View list item xml which displays minimum information(date, time, emotionalState.) of a MoodEvent.


## Add Friends & View Pending Friend Requests Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 05.01.01** Story Point: 2 risk Level: medium 
As a participant, I want to ask another participant to follow their most recent mood event. 

**US 05.02.01** Story Point: 2 risk Level: medium 
As a participant, I want to grant another participant permission to follow my most recent mood event. 

### In order to implement the above user stories:
- [ ] Requires an **Android Activity and its corresponding layout xml** that serves two purposes: display a list of pending friend requests; display a list of participants open to follow. 
#### Layout Design 
- [ ] the layout xml should contain a tabbed layout which contains three tabs: view existing friends tab, approving friend requests tab and view list of participants to follow tab. Should allow participants to switch among the three. 
- [ ] Recycler View should be implemented in each Tab. 
- [ ] Recycler View list item layout xml should be implemented for each Recycler View.
- [ ] Specifically, for the Recycler View that displays list of participants to follow, the list item should contain the participant's username and a button when pressed, sends a friend request to the participant.
- [ ] for the Recycler View that displays existing friends of a participant, the list item should contain the participant's username.
- [ ] for the Recycler View that displays pending friends request, the list item should contain the participant's username and a button, when pressed, grant access of most recent MoodEvent to the participant. 



## Map View Of MoodEvent History Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 06.02.01** Story Point: 3 risk Level: high 

As a participant, I want to see a map of the mood events (showing their emotional states) from my mood history list (that have locations). 

**US 06.03.01** Story Point: 3 risk Level: high 

As a participant, I want to see a map of the mood events (showing their emotional states and the username) from my mood following list (that have locations). 

### In order to implement the above user stories: 
- [ ] Requires an **Android Activity and its corresponding layout xml** that serves two purposes: 
display participant's own mood event on map; display the mood events from the participant's mood following list

#### Layout Design 
- [ ] the layout xml should be in Tabbed Layout with two tabs. First tab should contain a Map View that displays participant's own MoodEvent on map by showing emotional state of every MoodEvent. 
- [ ] A second tab should contain a Map View that displays MoodEvents from the following list by showing username and emotional state. 
- [ ] a button at the bottom left corner which when pressed, directs participant to Home Screen.



## Home Screen: Story Point: 1 risk Level: low ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

- [ ] Requires an **Activity and its corresponding layout xml** which serves as a Home screen (portal) to all other functionalities.
#### Layout Design
- [ ] the layout should be in constraint layout which contains several buttons as follows:
- should contain a Button that when clicked, go To Add Friends & View Pending Friend Requests Screen
- should contain a Button that when clicked, Go To Map View Of MoodEvent History Screen
- should contain a Button that when clicked, Go To MoodEvent History Screen
- should contain a Button that when clicked, Log out the current Participant and return to the Participant Login/ Signup Screen 
