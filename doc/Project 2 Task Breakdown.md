## ![#f03c15](https://placehold.it/15/f03c15/000000?text=+) A Menu Screen to show all avaliable Service
- GO To View And Accept Friend's Request Screen
- Go To Check and Add available Participants Screen
- Go To View Own Mood History Screen 
- Go To View Friend's Mood History Screen 
- Anything else? 

## ![#f03c15](https://placehold.it/15/f03c15/000000?text=+) Firebase 
- Mood History Database Table that contains: `Username (as primary key`), `Date`, `Time`, `Emotional State`, `Reason in text(nullable)`, `Reason in Photo(nullable)`, `Social Situation in text (nullable)`, `Geolocation (nullable)`
- **A User Login Screen (password authetication)**
- **A Database Class that exports Services** 


## ![#f03c15](https://placehold.it/15/f03c15/000000?text=+) Mood Object 
- Date 
- Time 
- Emotional State (mapping to emoticons and colors)
- Reason (optional) in text (<= 20 Chars)
- Reason (optional) in Photo 
- Social Situation (optional) (one of the following values: `alone`, `with one other person`, `with two to several people`, or `with a crowd`.)
Geolocation (optional)
- **Data Class** 

## ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) Participant
- Able to Add A Mood (to their own history)
- Able to View A **a given???**  Mood (all details)
- Able to Edit A Mood (of their own)
- Able to Delete A Mood (of their own)
- **One screen : Add/Edit/View A Mood** 

## ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) Mood History 
- Display A participant's Mood History **sorted by date and time, in reverse chronological order (most recent coming first)**
- Filter A participant's Mood History by **a particular emotional state**
- **One screen: A recycler view shows Mood History; allow mood deletion; click list item to view full details; allow filtering**
- **what to show minimum per list item ????**


## ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) Mood Following And Sharing 
- Allow Participant to follow others' Most Recent Mood Event (prerequisite: "friend request")
- Allow Participant to grant others' "friend request"
- Allow Participant to view Most Recent Mood Events of the participatns whom he/she follows. **in reverse chronological order (most recent coming first).**
- **screen: display a list of available participants to follow; check & accept friend request**
- **screen: display most recent mood events of the participants he/she follows**

## ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) Map
- Display a Map of Mood Event of their own (showing their emotional states on Map) If Has Location
- Display a Map of Mood event from my mood following list (showing their emotional states on Map)If Has Location
- **one screen: show own mood on map; show friend's mood on map**

User Stories 
US 01.01.01
As a participant, I want to add a mood event to my mood history, each event with the current date and time, a required emotional state, optional reason, and optional social situation.

US 01.02.01
As a participant, I want consistent emoticons and colors to depict and distinguish the emotional states in any view.

US 01.03.01
As a participant, **I want to view a given mood event and all its available details.** `Can it be someone else's Mood? `

US 01.04.01
As a participant, I want to edit the details of a given mood event of mine.

US 01.05.01
As a participant, I want to delete a given mood event of mine.

Other Mood Details

US 02.01.01
As a participant, I want to express the reason why for a mood event using a brief textual explanation (no more than 20 characters or 3 words).

US 02.02.01
As a participant, I want to express the reason why for a mood event using a photograph.

US 02.03.01
As a participant, I want to specify the social situation for a mood event to be one of: alone, with one other person, with two to several people, or with a crowd.

Profile

US 03.01.01
As a user, I want a profile with a unique username.

Mood History

US 04.01.01
As a participant, I want to view as a list my mood history, sorted by date and time, in reverse chronological order (most recent coming first).

US 04.02.01
As a participant, I want to filter my mood history list to show only mood events with a particular emotional state.

Mood Following and Sharing

US 05.01.01
As a participant, I want to ask another participant to follow their most recent mood event. `Please define most recent event. like latest 20 ???`

US 05.02.01
As a participant, I want to grant another participant permission to follow my most recent mood event.

US 05.03.01
As a participant, I want to view as a list the most recent mood events of the other participants(**display all together???**) I am granted to follow, sorted by date and time, in reverse chronological order (most recent coming first).

Geolocation and Maps

US 06.01.01
As a participant, I want to optionally attach my current location to a mood event.

US 06.02.01
As a participant, I want to see a map of the mood events (showing their emotional states) from my mood history list (that have locations).

US 06.03.01
As a participant, I want to see a map of the mood events (showing their emotional states and the username) from my mood following list (that have locations).

