from math import sqrt

def total_cost(parent, nx, ny):
    cost_to_current = sqrt(abs(nx - parent.x)**2 + abs(ny - parent.y)**2) + parent.value  
    return cost_to_current