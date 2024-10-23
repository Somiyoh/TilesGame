# CS 351 Project 01- Matching Tiles

Matching Tiles Game is a JavaFX-based puzzle game where players click on tiles to find matches. 
When two matching tiles are clicked in succession, they disappear, aiming to clear the board.

## Usage
The entry point is the Main.java class in the cs351project1 package.
To run the game, you can use this command: java -jar .jarFileName on the terminal.

## Features

Interactive GUI: Click on two matching tiles to make them disappear.
Scoring System: Keep track of your current chain of matches and your maximum chain throughout the game.
End Game Detection: The game automatically detects when no more moves are possible.


## Known Issues

Border Persistence: Occasionally, the highlight border may persist after a match is made.
Game Over Detection: In some rare cases, the game may not correctly detect the game over state.
