from math import sqrt

def total_cost(parent, nx, ny):
    cost_to_current = sqrt((nx - parent.x)**2 + (ny - parent.y)**2) + parent.cost_spent
    return cost_to_current