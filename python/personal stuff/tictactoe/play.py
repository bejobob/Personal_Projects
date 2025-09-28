from check import board

def block_danger(type, which, bots_shape):
    if type == 'row':
        print("row")
        for i in range(3):
            if board[which][i] == ' ':
                board[which][i] = bots_shape
                break
    elif type == 'column':
        print("column")
        for i in range(3):
            if board[i][which] == ' ':
                board[i][which] = bots_shape
                break
    else:
        print("diagonal")
        if which == 1:
            i = 0
            for j in range(3):
                if board[i][j] == ' ':
                    board[i][j] = bots_shape
                    break
                i += 1
        else:
            i = 0
            for j in range(2, -1, -1): # this part checks the positive diagonal
                if board[i][j] == ' ':
                    board[i][j] = bots_shape
                    break
                i += 1