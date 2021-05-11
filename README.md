This is a 'snakes and ladders' program made in java and run by console. It was made using Intellij Idea on windows
and to run it you'll need java 8, then you can run the main.class file, or, you can also use Intellij and import the project there.

As you know, the game consists of a board of size nxm, with a determined number of snakes and ladders. If a player 'rolls' the dice and moves into a snake, he'll descend
into the cell where the snakes ends, and in the opposite way, if he lands on a ladder, he'll climb up to the cell where the ladder ends.
The first player to reach the last cell (nxm) wins.

The documentation; both the functional requirements and the class diagram are here: [Documentation](docs/fc.pdf)

The user is first welcomed by a menu, where he's asked for an input: 

![Alt text](docs/1.png?raw=true "first menu")

If he selects the first option, he'll be asked for a new input with the settings for the board.

![Alt text](docs/2.png?raw=true "game settings")

Then, the board will be created and displayed, both with its enumerated cells, and with the players, ladders and snakes.

![Alt text](docs/3.png?raw=true "intro")

At this point, if the player writes "simul" and hits enter, the game will continue on by itself making a move every 2 seconds.
Also, if he presses "enum" the enumerated board will be displayed again.

If the user just hits enter, the game will continue, moving the actual player.

![Alt text](docs/4.png?raw=true "moving the players")

The game will continue this way until someone wins, then the winner will be asked for a name to save his score and the user will be returned to the main menu.

![Alt text](docs/5.png?raw=true "saving score")

If he selecs the second option, all the previous winners will be displayed in a list, with the piece and score they won the game.

![Alt text](docs/6.png?raw=true "ranking score")

And, if the user selects the last option, the program will close.

![Alt text](docs/7.png?raw=true "ranking score")
