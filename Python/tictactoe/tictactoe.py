"""
Tic Tac Toe bot

Benjamin Kealey

A robot that will play tictactoe against you, and hopefully never lose.

v.0.0(02/16/24)
v.0.1(02/17/24) I finished the 'check_for_win' function
v.1.0(03/10/24) The bot is functional, and can detect wins. It still needs to look for its own potential wins and pursue them.
v.1.1(09/25/25) Improved the part where the square gets updated with the user's move. Got rid of an if-elif-else statement and replaced it with one line of logic
v.1.2(09/25/25) Moved the board variable to tictactoe.py to avoid circular imports and the player can't play in filled squares any more.
TODO:
    - Fix win detection??
    - Generally improve stuff
    - Why does the program remember the board after exiting?
"""

from random import randint
from check import check_for_win_or_potential, danger
from play import block_danger

board = [[' ', ' ', ' '], # the board
         [' ', ' ', ' '], 
         [' ', ' ', ' ']]

win = False

player_shape = (input("Do you want to play X or O?: ")).upper()

if player_shape == "O": # assigning the shapes
    robots_shape = "X"
else:
    robots_shape = "O"

while win == False:
    selected_square = 0 #we have to pick a number that isn't on the list

    while (9 < selected_square or selected_square < 1) or (board[(selected_square-1)//3][selected_square%3-1] != ' '):
        try:
            selected_square = int(input("Please select a number from 1 to 9: "))
            assert board[(selected_square-1)//3][selected_square%3-1] == ' ', "That square is already taken!" #TODO: something is not working here. Loop breaking on its own?
        except (TypeError, AssertionError) as a:
            print(a)
        break
    
    board[(selected_square-1)//3][selected_square%3-1] = player_shape
    win, danger[0], danger[1] = check_for_win_or_potential(board, robots_shape)
    
    if win:
        print("Someone Won!")
    elif danger[0] != ' ':
        block_danger(board, danger[0], danger[1], robots_shape)
        check_for_win_or_potential(board, robots_shape)
        if win:
            print("Someone Won!")
    else:
        print()

        a, b = -1, -1

        for i in range(9):
            if board[a][b] != ' ' or a == -1:
                a, b = randint(0, 2), randint(0, 2)
        board[a][b] = robots_shape
        check_for_win_or_potential(board, robots_shape)
        if win:
            print("Someone Won!")
    for i in range(3):
        print (board[i])
        if win:
            exit()