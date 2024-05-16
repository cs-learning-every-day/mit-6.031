from threading import Thread

class DrinksFridge:
    '''
    A mutable type representing a refrigerator containing drinks, using
    message passing to communicate with clients.
    '''
    
    # Abstraction function:
    #   AF(drinksInFridge, incoming, outgoing) = a refrigerator containing
    #                                            `drinksInFridge` drinks that takes
    #                                            requests from `incoming` and sends
    #                                            replies to `outgoing`
    # Rep invariant:
    #   drinksInFridge >= 0
    
    def __init__(self, requests, replies):
        '''
        Make a DrinksFridge that will listen for requests and generate replies.
        requests: Queue to receive requests from
        replies: Queue to send replies to
        '''
        self.drinksInFridge = 0
        self.incoming = requests
        self.outgoing = replies
        self.checkRep()
    
    def checkRep(self):
        assert self.drinksInFridge >= 0
        assert self.incoming
        assert self.outgoing
    
    def start(self):
        '''
        Start handling drink requests.
        '''
        def runFridge():
            while True:
                n = self.incoming.get()
                result = self.handleDrinkRequest(n)
                self.outgoing.put(result)
        Thread(target=runFridge).start()
    
    def handleDrinkRequest(self, n):
        '''
        Take (or add) drinks from the fridge.
        n: if >= 0, removes up to n drinks from the fridge;
           if < 0, adds -n drinks to the fridge.
        Returns: FridgeResult reporting how many drinks were actually added or removed,
                 and how many drinks are left in the fridge.
        '''
        change = min(n, self.drinksInFridge)
        self.drinksInFridge -= change
        self.checkRep()
        return FridgeResult(change, self.drinksInFridge)

class FridgeResult:
    '''
    A threadsafe immutable message describing the result of taking or adding
    drinks to a DrinksFridge.
    '''
    
    # Rep invariant: TODO
    
    def __init__(self, drinksTakenOrAdded, drinksLeftInFridge):
        '''
        Make a new result message.
        drinksTakenOrAdded: (precondition? TODO)
        drinksLeftInFridge: (precondition? TODO)
        '''
        self.drinksTakenOrAdded = drinksTakenOrAdded
        self.drinksLeftInFridge = drinksLeftInFridge
    
    def __str__(self):
        return ('you took ' if self.drinksTakenOrAdded >= 0 else 'you put in ') + \
               f'{abs(self.drinksTakenOrAdded)} drinks, fridge has {self.drinksLeftInFridge} left'
