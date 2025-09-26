"""
Tic Tac Toe bot

Benjamin Kealey

A robot that will play tictactoe against you, and hopefully never lose.

v.0.0(02/16/24)
v.0.1(02/17/24) I finished the 'check_for_win' function
v.1.0(03/10/24) The bot is functional, and can detect wins. It still needs to look for its own potential wins and pursue them.

TODO:
    - Fix win detection??
    - Generally improve stuff
"""

from random import randint
from check import check_for_win_or_potential, board, danger
from play import block_danger

coordinates = {'A': [0, 0], 'B': [0, 1], 'C': [0, 2], 'D': [1, 0], 'E': [1, 1], 'F': [1, 2], 'G': [2, 0], 'H': [2, 1], 'I': [2, 2]}

win = False

player_shape = (input("Do you want to play X or O?: ")).upper()

if player_shape == "O": # assigning the shapes
    robots_shape = "X"
else:
    robots_shape = "O"

while win == False:
    selected_square = 0

    while selected_square > 9 or selected_square < 1:
        try:
            selected_square = int(input("Select a square number! (1-9): "))
        except ValueError:
            print("Please select a number from 1 to 9!")
        break
    
    board[(selected_square-1)//3][selected_square%3-1] = player_shape
    win, danger[0], danger[1] = check_for_win_or_potential(robots_shape)
    
    if win:
        print("Someone Won!")
    elif danger[0] != ' ':
        block_danger(danger[0], danger[1], robots_shape)
        check_for_win_or_potential(robots_shape)
        if win:
            print("Someone Won!")
    else:
        print()

        a, b = -1, -1

        for i in range(9):
            if board[a][b] != ' ' or a == -1:
                a, b = randint(0, 2), randint(0, 2)
        board[a][b] = robots_shape
        check_for_win_or_potential(robots_shape)
        if win:
            print("Someone Won!")
    for i in range(3):
        print (board[i])
        if win:
            exit()