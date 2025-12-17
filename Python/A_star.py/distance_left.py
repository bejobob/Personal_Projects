from math import sqrt

def distance_left(x, y, xf, yf):
    dx = abs(x - xf)
    dy = abs(y - yf)
    return max(dx, dy) + (sqrt(2) - 1) * min(dx, dy)