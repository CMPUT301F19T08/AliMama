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
- reasonInPhoto (Bitmap) nullable, 
- socialSituation (String : "alone", "with one other person", "with two to several ppl", "with a crowd") nullable, 
- locationOfMoodEvent (Geolocation, retrieve from GPS) nullable.

- [ ] Requires encapulation to ensure data integrity; getters and setters of instance variables should be implemented.

## Database Utility Class ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)

**Database Implementation : Story Point: 5 risk Level: high** 
- [ ] requires a Mood History Database  with tables as follows: 
- a table that records All Mood Event associated with a participant, primary key : username
- a table that stores user authentication information, primary key : username 
- a table that stores all participants relationship, primary key : username 
- a table that stores all pending friend request of a participants. 
- [ ] a static class with name "Database" should be implemented in the project with class methods as follows: 
- authenticateParticipant
- retrieveAllMoodEventOfAParticipant
- retrieveFriendRequestOfAParticipant
- retrieveAllParticipantsOpenToFollow
- retrieveGeolocationOfMoodEventsOfAParticipant
- more to come ...


## Participant Login/ Registration Screen ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)
**US 03.01.01** Story Point: 3 risk Level: high
As a user, I want a profile with a unique username. 
- [ ] requires an **Android Activity and its corresponding layout xml** which serves two purposes:  as an login screen; as a new participant registration screen.
#### Layout Design 
- [ ] the layout should contains the following Widgets:
- An Image View displaying Logo (Project Name)
- An Edit Text view for Username 
- An Edit Text view for password
- a button with Text "Register" and a button with Text "Log In"
- After pressing Login Button and detected that participant entered wrong password, text should be displayed under password Edit Text in red that saying "Invalid password, please try again".



## Participant View/Edit/Add A MoodEvent Screen ![#f03c15](https://placehold.it/15/f03c15/000000?text=+)

**US 01.02.01** Story Point: 1 risk Level: low 
- As a participant, I want consistent emoticons and colors to depict and distinguish the emotional states in any view.
 - [ ] requires an Enum that binds emotionalState to a certain color in hash.  

**US 01.03.01** Story Point: 1 risk Level: low 
- As a participant, I want to view a given mood event and all its available details. 

**US 01.04.01** Story Point: 1 risk Level: low 
- As a participant, I want to edit the details of a given mood event of mine. 


### In order to implement the above two user stories: 
- [ ] Requires an **Android Activity and its corresponding layout xml**.
- The activity and the layout serves two purposes: to Allow participant Add a new Mood Event and Edit an existing Mood Event. 
#### Layout Design
- [ ] The layout file should contain views as follows: 
- Edit Text for date and its prompt 
- Edit Text for emotionalState and its prompt 
- Edit Text for reasonInText and its prompt 
- an embedded camera view to allow participant takes photo to explain the reason of the mood event. (reasonInPhoto)
- an dropdown list view for socialSituation; Values of the dropdown are : "alone", "with one other person", "with two to several ppl", "with a crowd".
- A CheckBox, when checked, retrieve geolocation where the mood event takes place from GPS.
- A Button, when participant is adding a new mood event, has the text "Add"; When participant is editing an existing mood event, has the text "Update".
- [ ] A Cancel Button, when pressed, directs participant back to View Mood History Screen. 
- [ ] Requires implementations that assists the participant in proper data entry. For example, use appropriate user interface controls to enforce particular data types and avoid illegal values.
- [ ] After pressing the "Add/ Update" button, the participant should be directed backinto View Mood History Screen. 

## Participant View Mood History Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 01.05.01** Story Point: 1 risk Level: low  
As a participant, I want to delete a given mood event of mine.

**US 05.03.01** Story Point: 2 risk Level: medium 
As a participant, I want to view as a list the most recent mood events of the other participants I am granted to follow, sorted by date and time, in reverse chronological order (most recent coming first).

**US 04.01.01** Story Point: 2 risk Level: medium 
- As a participant, I want to view as a list my mood history, sorted by date and time, in reverse chronological order (most recent coming first).

**US 04.02.01** Story Point: 1 risk Level: low 
- As a participant, I want to filter my mood history list to show only mood events with a particular emotional state.

### In order to implement the above user stories:
- [ ] Requires an **Android Activity and its corresponding layout xml** which serves two purposes: Display Mood History of the Participant; Display Mood History of the Participants that the current logged-in participants follow. 
#### Layout Design
- [ ] The layout should be in LinearLayout that contains an EditText, a Recycler View below the EditText and a Floating Action Button below the Recycler View.
- [ ] The Recycler view is used to display Mood history of the participant or the participants followed by the current logged-in participant. 
- [ ] The Edit Text acts as a search bar so that when participant enters, only the MoodEvent with emotionalState value same as the participant's inputs are left on the screen. 
- [ ] The Floating Action button serves as a "Add MoodEvent" Button, when pressed, directs participant to View/Edit/Add A MoodEvent Screen. 
- [ ] Requires a Recycler View list item xml which displays minimum information(date, time, emotionalState.) of a MoodEvent **(need to ask what information should at least be displayed on each list item).**
- [ ] a button in the Action Bar which when pressed, directs participant to Home Screen.


## Participant Approve Pending Friend Request/ Add More Participant to Follow Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 05.01.01** Story Point: 2 risk Level: medium 
As a participant, I want to ask another participant to follow their most recent mood event. 

**US 05.02.01** Story Point: 2 risk Level: medium 
As a participant, I want to grant another participant permission to follow my most recent mood event. 

### In order to implement the above user stories:
- [ ] Requires an **Android Activity and its corresponding layout xml** that serves two purposes: display a list of pending friend requests; display a list of participants open to follow. 
#### Layout Design 
- [ ] the layout xml should contain a tabbed layout which contains two tabs: approving friend requests tab and view list of participants to follow tab. Should allow participants to switch in between the two. 
- [ ] Recycler View should be implemented in each Tab. 
- [ ] Recycler View list item layout xml should be implemented for each Recycler View.
- [ ] Specifically, for the Recycler View that displays list of participants to follow, the list item should contain the participant's username and a button when pressed, sends a friend request to the participant.
- [ ] for the Recycler View that displays pending friends request, the list item should contain the participant's username and a button, when pressed, grant access of most recent mood event to the participant. 
- [ ] a button in the Action Bar which when pressed, directs participant to Home Screen. 


## Map View Of Mood History Screen ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

**US 06.02.01** Story Point: 3 risk Level: high 

As a participant, I want to see a map of the mood events (showing their emotional states) from my mood history list (that have locations). 

**US 06.03.01** Story Point: 3 risk Level: high 

As a participant, I want to see a map of the mood events (showing their emotional states and the username) from my mood following list (that have locations). 

### In order to implement the above user stories: 
- [ ] Requires an **Android Activity and its corresponding layout xml** that serves two purposes: 
display participant's own mood event on map; display the mood events from the participant's mood following list

#### Layout Design 
- [ ] the layout xml should contain a Map View that displays participant's own mood event on map by showing emotional state of every mood event. Also, the Map View should display mood events from the following list by showing username and emotional state. 
- [ ] a button in the Action Bar which when pressed, directs participant to Home Screen.



## Home Screen: Story Point: 1 risk Level: low ![#c5f015](https://placehold.it/15/c5f015/000000?text=+)

- [ ] Requires an **Activity and its corresponding layout xml** which serves as a Home screen (portal) to all other functionalities.
#### Layout Design
- [ ] the layout should contains several buttons as follows:
- should contain a Button that when clicked, go To View/Accept friends request & Add open participants screen
- should contain a Button that when clicked, Go To View Mood History Screen 
- should contain a Button that when clicked, Go To View Mood History on Map Screen
- should contain a Button that when clicked, Log out the current Participant and return to the Login & SignUp Screen 
