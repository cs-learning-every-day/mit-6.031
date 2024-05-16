from queue import Queue
import os
from drinksfridge import *

#
# Create and use a drinks fridge.
#

requests = Queue()
replies = Queue()

# start an empty fridge
fridge = DrinksFridge(requests, replies)
fridge.start()

# deliver some drinks to the fridge
requests.put(-42)

# maybe do something concurrently...

# read the reply
print(replies.get())

print('done')
os._exit(0) # abruptly ends the program, including DrinksFridge
