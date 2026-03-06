import keyboard
import pyperclip
import pyautogui
from time import sleep

"""
if op == 1, return uppercase
if op == 2, return lowercase
if op == 3, return sentence case
if op == 4, return original case without formatting
If Return Original Case Without Formatting  
"""
def epicPaster(op, copy):
    if copy:
        print(pyperclip.paste())
        pyautogui.hotkey('ctrl', 'c')
        sleep(0.5)
    #print("this works")
    text = pyperclip.paste()
    if not text:
        return None
    if op == 1:
        pyperclip.copy(text.upper())
        keyboard.send('ctrl+v')
        #print("pasted")
        sleep(0.1)
    elif op == 2:
        pyperclip.copy(text.lower())
        keyboard.send('ctrl+v')
        #print("pasted")
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
            if char in ".!?\n":
                capitalize_next = True
        pyperclip.copy(new_text)
        keyboard.send('ctrl+v')
        #print("pasted")
        sleep(0.1)
    elif op == 4:
        pyperclip.copy(text)
        keyboard.send('ctrl+v')
        #print("pasted")
        sleep(0.1)
    elif op == 5:
        new_text = ""
        words = text.split(" ")
        for word in words:
            if words.index(word) == 0:
                new_text += word.capitalize() + " "
                continue
            if word.__len__() > 3:
                new_text += word.capitalize() + " "
            else:
                new_text += word.lower() + " "
        if new_text.endswith(" "):
            new_text += " "
        pyperclip.copy(new_text)
        keyboard.send('ctrl+v')




keyboard.add_hotkey('ctrl+shift+1', epicPaster, args = (1, False))
keyboard.add_hotkey('ctrl+shift+2', epicPaster, args = (2, False))
keyboard.add_hotkey('ctrl+shift+3', epicPaster, args = (3, False))
keyboard.add_hotkey('ctrl+shift+4', epicPaster, args = (4, False))

keyboard.add_hotkey('ctrl+shift+5', epicPaster, args = (5, False))
"""keyboard.add_hotkey('ctrl+shift+6', epicPaster, args = (2, True))
keyboard.add_hotkey('ctrl+shift+7', epicPaster, args = (3, True))
keyboard.add_hotkey('ctrl+shift+8', epicPaster, args = (4, True))"""

keyboard.wait()