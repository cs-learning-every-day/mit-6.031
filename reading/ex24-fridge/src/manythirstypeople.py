from queue import Queue
import os
from drinksfridge import *

QUEUE_SIZE = 100
N = 250

requests = Queue(maxsize=QUEUE_SIZE)
replies = Queue(maxsize=QUEUE_SIZE)

fridge = DrinksFridge(requests, replies)
fridge.start()

# put enough drinks in the fridge to start
requests.put(-N)
print(replies.get())

# send the requests
for x in range(N):
    requests.put(1)
    print(f'person #{x} is looking for a drink')
# collect the replies
for x in range(N):
    print(f'person #{x}: {replies.get()}')

print('done')
os._exit(0) # abruptly ends the program, generally a bad idea
