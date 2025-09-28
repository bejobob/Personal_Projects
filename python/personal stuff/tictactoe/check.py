"""
Tic Tac Toe checking function for Tic Tac Toe bot by Benjamin Kealey

Benjamin Kealey

The function that checks the board for wins or two-in-a-rows

v.1.0(02/10/24) It is fully functional.
"""



danger = ['row', 'number'] # this list contains the information needed to block a win
board = [[' ', ' ', ' '], # the board
         [' ', ' ', ' '], 
         [' ', ' ', ' ']]

def check_for_win_or_potential(bots_shape): # this function checks the board for wins or two-in-a-rows
    # this first part checks the horizontal rows for wins
    win = False
    danger[0], danger[1] = ' ', ' '

    for row in range(3):
        score = 0
        
        for square in range(3):
            
            if board[row][square] == bots_shape:
                score += 1
            elif board[row][square] != ' ':
                score -= 1
        if score == 3 or score == -3:
            print(f"Win on row {row+1}")
            win = True
            break
        elif score == -2 and danger[0] == ' ':
            danger[0], danger[1] = 'row', row
            break
        else:
            break

    # this second part checks the vertical columns for wins, but only triggers if the first part did not find a win
    if win == False:
        for column in range(3):
            score = 0

            for square in range(3):

                if board[square][column] == bots_shape:
                    score += 1
                elif board[square][column] != ' ': 
                    score -= 1

            if score == 3 or score == -3:
                print(f"Win in column {column+1}")
                win = True
                #exit()
                break

            elif score == -2 and danger[0] == ' ':
                danger[0], danger[1] = 'column', column
                break

    # this third part checks the diagonals for wins, but only triggers if neither of the first two parts found a win
    if win == False:
        score = 0
        i = 0
        for j in range(3): # this part checks the negative diagonal
            if board[i][j] == bots_shape:
                score += 1
            elif board[i][j] != ' ':
                score -= 1
            i += 1
            
        if score == 3 or score == -3:
            print("Win on the negative diagonal!")
            win = True
            #exit()

        elif score == -2 and danger[1] == ' ':

            danger[0], danger[1] = 'diagonal', 1            

    if win == False:
        score = 0
        i = 0
        for j in range(2, -1, -1): # this part checks the positive diagonal
            if board[i][j] == bots_shape:
                score += 1
            elif board[i][j] != ' ':
                score -= 1
            i += 1

        if score == 3 or score == -3:
            print("Win on the positive diagonal!")
            win = True
            #exit()

        elif score == -2 and danger[0] == ' ':

            danger[0], danger[1] = 'diagonal', 2

    return win, danger[0], danger[1]