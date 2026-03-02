import keyboard
import pyperclip
from time import sleep

"""
if op == 1, return uppercase
if op == 2, return lowercase
if op == 3, return sentence case
if op == 4, return plain text
"""

def epicPaster(op, copy):
    if copy:
        keyboard.send('ctrl+c')
    #print("this works")
    text = pyperclip.paste()
    if not text:
        return None
    if op == 1:
        pyperclip.copy(text.upper())
        keyboard.send('ctrl+v')
        sleep(0.1)
    elif op == 2:
        pyperclip.copy(text.lower())
        keyboard.send('ctrl+v')
        sleep(0.1)
    elif op == 3:
        new_text = ""
        capitalize_next = True
        for char in text:
            if capitalize_next and char.isalpha():
                new_text += char.upper()
                capitalize_next = False
            else:
                new_text += char.lower()
            if char in ".!?":
                capitalize_next = True
        pyperclip.copy(new_text)
        keyboard.send('ctrl+v')
        sleep(0.1)
    elif op == 4:
        pyperclip.copy(text)
        keyboard.send('ctrl+v')
        sleep(0.1)



keyboard.add_hotkey('ctrl+shift+1', epicPaster, args = (1, False))
keyboard.add_hotkey('ctrl+shift+2', epicPaster, args = (2, False))
keyboard.add_hotkey('ctrl+shift+3', epicPaster, args = (3, False))
keyboard.add_hotkey('ctrl+shift+4', epicPaster, args = (4, False))

keyboard.add_hotkey('ctrl+1', epicPaster, args = (1, True))
keyboard.add_hotkey('ctrl+2', epicPaster, args = (2, True))
keyboard.add_hotkey('ctrl+3', epicPaster, args = (3, True))
keyboard.add_hotkey('ctrl+4', epicPaster, args = (4, True))

keyboard.wait()