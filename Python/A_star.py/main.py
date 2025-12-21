"""
A* Pathfinding algorithm
by Benjamin Kealey

description: it's in the title, buddy. It's an A* pathfinding algorithm

v 1.0 (december 16th, 2025): it finaly works! Or maybe? I think I might need to swap all the x-coords with the y-coords and vice-versa
v 1.1 (december 16th, 2025): I flipped the coordinates and yes! it actually works
v 1.2 (december 16th, 2025): When the program finishes finding a path it draws the maze and highlights the path for a visual of the solution
v 1.3 (december 16th, 2025): now it shows you the open nodes in the maze as it solves it. Shows you how it works. Cool stuff.
v 1.4 (december 20th, 2025): began implementation of a hash table to store coordinate-to-node relations to fix my incredibly high runtime. It's actually insane how long it takes
"""
"""
TODO: fix x-y flip. This is caused by how 2d lists work. Just make it consistent and clear throughout the entire program
         finish setting up the hash table. It will be a list of dicts. Each dict will have an index found using the hash value of the coordinates
         then we can use the coordinates themselves to identify the node in question.
"""
from total_distance import total_cost
from distance_left import distance_left
import matplotlib.pyplot as plt

open_nodes = []
closed_nodes = []
open_coords = set()
closed_coords = set()
nodes_in_path = []
xf = None
yf = None

coord_to_node = []

def hash_value(coords):
    return int(str(coords[0]) + str(coords[1]))%512

def load_map(path): # this function takes a file path and turns a .map into a usable 2d list
    with open(path) as f:
        lines = f.read().splitlines()

    i = lines.index("map") + 1
    grid = lines[i:]
    return grid

class Node(): # node structure
    def __init__(self, x, y, cost_spent, cost_left, parent=None):
        self.x = x # the x coord
        self.y = y # the y coord
        self.cost_spent = cost_spent
        self.cost_left = cost_left
        self.value = cost_spent + cost_left
        self.parent = parent # the node we came from to get here

class Entry():
    def __init__(self, coords, node, nxt=None):
        self.coords = coords
        self.node = node
        self.nxt = nxt

DIRS = [(-1, -1), # left and down
        (-1, 0), # left
        (-1, 1), # left and up
        (0, -1), # down
        (0, 1), # up
        (1, -1), # right and down
        (1, 0), # right
        (1, 1)] # right and up
######################## GETTING FILE PATH ################################################
while(True):
    try:
        path = input("Please enter the map path: ")
        grid = load_map(path)
        break
    except FileNotFoundError as e: # I'm only checking to see if the file exists, but not that the file format is correct
        print(e)
    break
###########################################################################################
######################## DRAWING STUFF ####################################################
plt.ion()

img = [[0 if c == '@' else 1 for c in row] for row in grid]
fig, ax = plt.subplots()
ax.imshow(img, cmap="gray")
ax.axis("off")

open_scatter = ax.plot([], [], 'bo', markersize=1)[0]    # open set
best_dot = ax.plot([], [], 'ro', markersize = 1)[0]
closed_scatter = ax.plot([], [], 'ro', markersize=1)[0]  # closed set
###########################################################################################
######################## GET STARTING AND TARGET POINTS ###################################
while(True):
    try:
        x = int(input(f"Enter starting x-coordinate between 0 and {len(grid[0])}: "))
        if (0 > x > len(grid[0])):
            print("x out of bounds")
            continue
        y = int(input(f"Enter starting y-coordinate between 0 and {len(grid)}: "))
        if (0 > y > len(grid)):
            print("y out of bounds")
            continue
        if (grid[x][y] == '@'):
            print("Sorry, xy coordinate is a wall and we can't start there")
            continue

        xf = int(input(f"Enter target x-coordinate between 0 and {len(grid[0])}: "))
        if (0 > xf > len(grid[0])):
            print("x out of bounds")
            continue
        yf = int(input(f"Enter targer y-coordinate between 0 and {len(grid)}: "))
        if (0 > yf > len(grid)):
            print("y out of bounds")
            continue
        if (grid[xf][yf] == '@'):
            print("Sorry, xy coordinate is a wall and we can't start there")
            continue
        break
    except TypeError as e:
        print(e)
###########################################################################################
######################## INITIALIZNG STARTING STUFFS ######################################
current_node = Node(x, y, 0, distance_left(x, y, xf, yf))
closed_nodes.append(current_node)
nodes_in_path.append(current_node)
coord_to_node[hash_value((x, y))] = Entry((x, y), current_node)
###########################################################################################

def node_value(x, y, parent): # this function identifies the value of a node given the coordinates and the parent
    cost_spent = total_cost(parent, x, y) # i use this function when creating a node, so I can't pass the node itself as the parameter
    cost_left = distance_left(x, y, xf, yf) # which is why I have to use x, y, and parent instead of just node
    return (cost_spent, cost_left)

######################## MAIN FUNCTION ####################################################
def astar(current_node, xf, yf):
    steps = 0
    current_node = current_node
    while (True):
        for dx, dy in DIRS:
            nx, ny = current_node.x + dx, current_node.y + dy
            new_node = Node(nx, ny, *node_value(nx, ny, current_node), current_node)
######################### MAKE SURE THE NEW NODE IS VALID #########################
            if (nx > len(grid)-1 or ny > len(grid[0])-1):
                continue
            elif (grid[nx][ny] == '@'):
                continue
            elif ((ny, nx) in closed_coords or (nx, ny) in open_coords):
                continue
            elif (dx != 0 and dy != 0):
                if grid[y][x + dx] == '@' or grid[y + dy][x] == '@':
                    continue
###########################################################################################
######################### CREATE NEW NODE #################################################
            open_nodes.append(new_node)
            open_coords.add((nx, ny))
            #print(new_node.value)
###########################################################################################
######################## IDENTIFY BEST OPEN NODE ##########################################
        best = open_nodes[0]
        for node in open_nodes:
            if (node.value < best.value):
                best = node
###########################################################################################
######################## REMOVE NODE FROM OPEN LISTS ######################################
        for node in open_nodes:
            if (node.x == best.x and node.y == best.y):
                open_nodes.remove(node)
                break
        open_coords.remove((best.x, best.y))
        closed_coords.add((best.y, best.x))
######################## DRAWING THE CURRENT STATE OF THINGS ##############################
        """
        open_xs = [n.y for n in open_nodes]
        open_ys = [n.x for n in open_nodes]
        best_xs = [best.y]
        best_ys = [best.x]
        closed_xs = [n.y for n in closed_nodes]
        closed_ys = [n.x for n in closed_nodes]

        open_scatter.set_data(open_xs, open_ys)
        best_dot.set_data(best_xs, best_ys)
        closed_scatter.set_data(closed_xs, closed_ys)
        steps += 1
        if steps % 500 == 0:
            plt.pause(0.05)
        """
###########################################################################################
######################## SET NEW CURRENT ##################################################
        current_node = best
###########################################################################################
######################## CHECK IF WE GOT TO THE END #######################################
        if (current_node.x == xf and current_node.y == yf):
            return current_node
###########################################################################################
        
######################## MAIN PROGRAM #####################################################
target = astar(current_node, xf, yf) # astar returns the final node, and we can use it to trace all the way back through the path using the parent element of the nodes
open_scatter.set_data([], []) # to clear the maze of the "in-progrss" drawing elements
closed_scatter.set_data([], []) # ditto
plt.pause(0.001)
coords = [] # the coords in the path will be stored as tuples in here
while (target.parent != None): # this makes sure we go all the way back to the first node
    coords.insert(0, (target.y, target.x)) # we add the coordinates to the list. For some reason I need to flip all the coords, not sure why
    target = target.parent # and we move on to the next node
coords.insert(0, (y, x)) # this adds the original node to the list
#print(coords)
ys, xs = zip(*coords) # unpacks the tuples so that we can draw the final path
ax.plot(ys, xs, 'c', linewidth=2)
plt.ioff() # and this stuff draws it
plt.show()