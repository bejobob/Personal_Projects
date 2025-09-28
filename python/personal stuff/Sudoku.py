"""
Sudoku Solver
by Benjamin Kealey

v.1.0: the code works
v.1.1: removed an unused variable
v.1.2: removed an if statement that only existed so that I could put a breakpoint there

A program that solves Sudoku puzzles by using a backtracking algorithm.
"""

board_to_change = [
        [0, 0, 0,  7, 0, 0,  4, 0, 0],
        [0, 8, 0,  5, 0, 0,  9, 0, 0],
        [0, 7, 5,  3, 0, 0,  0, 1, 0],

        [0, 1, 0,  0, 2, 0,  6, 0, 0],
        [7, 0, 4,  0, 0, 0,  0, 0, 0],
        [6, 0, 0,  0, 8, 0,  0, 4, 0],

        [0, 0, 0,  0, 0, 0,  0, 0, 0],
        [0, 6, 0,  8, 0, 0,  0, 0, 3],
        [0, 5, 0,  0, 9, 0,  1, 0, 0]
    ]

perm_board = [
        [0, 0, 0,  7, 0, 0,  4, 0, 0],
        [0, 8, 0,  5, 0, 0,  9, 0, 0],
        [0, 7, 5,  3, 0, 0,  0, 1, 0],

        [0, 1, 0,  0, 2, 0,  6, 0, 0],
        [7, 0, 4,  0, 0, 0,  0, 0, 0],
        [6, 0, 0,  0, 8, 0,  0, 4, 0],

        [0, 0, 0,  0, 0, 0,  0, 0, 0],
        [0, 6, 0,  8, 0, 0,  0, 0, 3],
        [0, 5, 0,  0, 9, 0,  1, 0, 0]
    ]

r = -1
c = 0
original_number = 0
square = []

def identify_square(a, b): # variable to identify which 3x3 square we're in
    if r < 3 and r > -1:
        square = [board_to_change[i][j] for i in range(3) for j in range(a, b)]

    elif r > 2 and r < 6:
        square = [board_to_change[i][j] for i in range(3, 6) for j in range(a, b)]

    else:
        square = [board_to_change[i][j] for i in range(6, 9) for j in range(a, b)]
    
    return square
    
while r < 9:
    r += 1
    c = 0

    while c < 9:
        if r == 9:
            break
        if perm_board[r][c] == 0:

            row = board_to_change[r] # identify the row
            column = [board_to_change[i][c] for i in range(9)] # identify the column
            
            if c < 3 and c > -1: # identify which square we're in
                square = identify_square(0, 3)
            elif c > 2 and c < 6:
                square = identify_square(3, 6)
            else:
                square = identify_square(6, 9)

            for digit in range(1, 10): # try all digits from 1 to 9
                if digit not in row and digit not in column and digit not in square and digit > original_number: # we make sure the digit fits the cell according to the rules of sudoku
                    board_to_change[r][c] = digit # the cell is updated with the new digit
                    c += 1 # we move to the next cell
                    original_number = 0 # we reset the original_ umber variable to avoid getting stuck in an infinite loop
                    break

                if digit == 9:
                    # there is no solution to the square
                    # backtrack to the last edited cell
                    while True:
                        
                        if c != 0: # to make sure we don't have a range error
                            c -= 1 # back up one square

                        elif c == 0 and r > 0: # if we're at the end of a row and we aren't at the top row,
                            r -= 1 # go to the previous row
                            c = 8 # and go to the first column

                        if perm_board[r][c] == 0: # if the original cell was empty, then we can go back to solving
                            original_number = board_to_change[r][c] # remember the original "solution" to the cell
                            board_to_change[r][c] = 0 # reset the value of the cell
                            break 
        else:
            c += 1
                
for m in range(9): # this just prints the final board
    print(board_to_change[m])