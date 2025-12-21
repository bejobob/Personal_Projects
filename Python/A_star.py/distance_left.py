from math import sqrt

def distance_left(x, y, xf, yf):
    dx = abs(x - xf)
    dy = abs(y - yf)
    return min(dx, dy) * sqrt(2) + max(dx, dy) - min(dx, dy)