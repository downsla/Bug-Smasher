# Bug-Smasher
CPSC 4326 Android Programming - Final Project
07/06/2021

Create an Android application using Android 4.4 (KitKat).  Your application is a 2D video game similar to [Ant Smasher](https://play.google.com/store/apps/details?id=com.bestcoolfungames.antsmasher&amp;hl=en).

Instructions  
Your program will have the following features:
1) A Title Screen showing the name of the application and 2 options:  
	Play Game  
	High Score
2) Clicking High Score should activate a Preferences Screen with one option – the display of the best high score obtained so far. Clicking the back button from this screen should return to the Title Screen.
3) Clicking Play Game from the Title Screen should go to the Game Screen.
4) On the Game Screen, implement the following functionality:
	- Clicking the back button at any time will return to the Title Screen.
	- Upon entering the Game Screen, the app will audibly say “Get Ready”.  After 3 seconds the game will begin.  Music will begin to play and should continue playing as long as the user is playing the game on the Game Screen.
	- The player has 3 “lives”.  These are displayed as small icons in the upper part of the screen (on the score bar).
	- The player score will appear in the upper left part of the screen (on the score bar)
	- A food bar will appear in the lower bottom portion of the screen.
	- Bugs will randomly move from the top of the screen to the bottom of the screen.  For each bug that reaches the bottom of the screen, one “life” will be lost.
	- The user can click on bugs to kill them.  Use the DOWN event for these user clicks.
	- Bugs will take 1 click to kill and are worth 1 point. There should be a sound effect when killing a bug.  For this sound you should have at least 3 sounds and randomly choose one to play (3 different squishy sounds).  
	- The score should be constantly updated.
	- Bugs should move down the screen at different speeds (chosen randomly) since some bugs will be faster than others.
	- The “kill zone” of a bug should be as large as the bug sprite on screen.
	- The legs of your bugs should appear to move.
	- At the end of the game, if the score is higher than the current high score stored in preferences then store the new high score in preferences.  Also, display a message to the user indicating a new high score has been reached. This can be done several ways: 
	  - a graphical image with the words “New High Score”
	  - a popup Android dialog 
	  - a Toast  
    - In addition, play a special sound effect for this event.
	    - For clicks on the screen that do not touch bugs, play a short sound effect that gives the user feedback for the click.
	    - There must be situations in which multiple bugs can appear on screen at the same time.  Any program that only displays a single bug on the screen at any one time will be grade with a zero grade.
