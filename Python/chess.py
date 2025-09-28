coord_board = [["a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"], ["a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"], ["a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"], ["a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"], ["a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"], ["a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"], ["a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"], ["a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"]]

letter_to_number = {"a": 0, "b": 1, "c": 2, "d": 3, "e": 4, "f": 5, "g": 6, "h": 7}

piece_board = [["♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"], ["♙", "♙", "♙", "♙", "♙", "♙", "♙", "♙"],["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"],["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"],["♟", "♟", "♟", "♟", "♟", "♟", "♟", "♟"],["♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"]]

square_board = [["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"],["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"],["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"],["■", "□", "■", "□", "■", "□", "■", "□"],["□", "■", "□", "■", "□", "■", "□", "■"]]

piece_type = {"♖": "rook", "♘": "knight", "♗": "bishop", "♕": "queen", "♔": "king", "♙": "pawn", "♟": "pawn", "♜": "rook", "♞": "knight", "♝": "bishop", "♛": "queen", "♚": "king"}

turn = True # relative to white's turn

"""def check_pawn(start_column, start_row, end_column, end_row):
    if start_column == end_column and end_row - start_row == 1:
        print()
    elif start_column == end_column and abs(end_row - start_row == 2):
        if start_row == 1 or start_row == 6:
            print()
    else:
        if abs(start_column - end_column) == 1 and piece_board[end_row][end_column] not in square_board:
            return True""" # I need to check for the colour so that to player doesn't try to move the pawn backwards

def check_rook():

def check_knight():

def check_bishop():

def check_queen():

def check_king():



while True:

    move_from = ""
    move_to = ""
    start_row = ""
    end_row = ""
    start_column = ""
    end_column = ""

    if turn:
        for row in piece_board:
            print(row)
    else:
        for row in piece_board[::-1]:
            print(row)

    while not any(move_from in row for row in coord_board):
        move_from = input("Starting square coordinate: ")

    while not any(move_to in row for row in coord_board):
        move_to = input("Ending square coordinate: ")

    for row in coord_board:
        if move_from in row:
            start_column = letter_to_number.get(move_from[0])
            start_row = 8 - int(move_from[1])
            
            for row in coord_board:
                if move_to in row:
                    end_column = letter_to_number.get(move_to[0])
                    end_row = 8 - int(move_to[1])
    
    piece_board[end_row][end_column], piece_board[start_row][start_column] = piece_board[start_row][start_column], square_board[start_row][start_column]

    if turn:
        turn = False
    else:
        turn = True