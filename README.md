# Assignment2
This is our implementation of Deadwood.
In order to run you must compile then run the Deadwood.java file in command line with no arguments.
Once in the Deadwood directory in your terminal you must type:
    "javac Deadwood.java"
After everything gets compiled you must type:
    "java Deadwood.java"

You will be prompted for a player count to play the game, from there it will pick a random player to start and you will be prompted to choose an option, these options are case-sensitive and must look exactly as printed to the screen.
There are also additional commands you are able to run at any time after you pick the player count.
These include:
    "active?"
        active? will print data about the player whose turn it currently is. This data includes their player number, funds, current room, and if they are working it also shows the scene's name and the line.
    "info"
        info will print out every player with their number as well as what room they are currently in. The player whose turn it is will be highlighted with "(Active)"
    "end game"
        end game will end the game at any point and display the top scoring player(s) then exit the program.