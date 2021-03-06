# Wei-Chien Hsu hsuweic

This is my assignment-4 submission!

:hammer:

***

This is my assignemnt-3 submission!

### Wrute 4 unit tests for 4 different functions

- Write unit tests for `four functions` (not card implementations, and not cardEffect) in dominion.c. 
- Check these tests in as `unittest1.c,unittest2.c, unittest3.c, and unittest4.c.` 
- At least two of these functions should be more than 5 lines of code. 

### Write 4 unit tests for card implementation function
- Write unit tests for four Dominion cards implemented in dominion.c.
- Write these tests so that they work whether a card is implemented inside cardEffect or in its own function. 
- These tests should be checked in as `cardtest1.c, cardtest2.c, cardtest3.c, and cardtest4.c. `
- It is mandatory to test **smithy** and **adventurer** card.

### Write down the results

- Execute your unit tests and describe any bugs you find in a section named `Bugs`. (10 points)
- Use gcov to measure code coverage for all of these tests. 
- Report your findings by discussing your tests' coverages (statement, branch, boundary, etc.),
- Describe their implications for the tests in a section called `Unit Testing`. 
- Discuss your unit testing efforts in a section called `Unit Testing Efforts`. (10 points)

**I want you to look at the dominion code coverage and find out what parts of your code are not covered so that in future you can improve your test suite. (30 points).**

### Update the Makefile

- Add a rule in `Makefile` (named unittestresults) that will generate and execute all of these tests.
- Append complete `testing results (including % coverage)` into a file called `unittestresults.out`.
- The rule should be named `unittestresults` and should depend on `all your test code` as well as `the dominion code`. 
- The .out files contain the output of your running tests and coverage information.
- Basically .out file should act as a proof that your tests run correctly and you collected coverage information correctly. (10 points) 

```makefile
CFLAGS = -Wall -fpic -coverage -lm -std=c99

default: tests.out

tests.out: unittest1.c unittest2.c unittest3.c unittest4.c unittest5.c bst.c compare.c
	
	echo "Result for running BST tests:" > tests.out
	echo "unittest1.c:" >> tests.out
	gcc -o unittest1 compare.c bst.c unittest1.c $(CFLAGS)
	./unittest1 >> tests.out
	gcov bst.c >> tests.out

   echo "unittest2.c:" >> tests.out
	gcc -o unittest2 compare.c bst.c unittest2.c $(CFLAGS)
	./unittest2 >> tests.out
	gcov bst.c >> tests.out
	
   clean:
	rm tests.out

   cleanall: 
	rm tests.out unittest1 unittest2 unittest3 unittest4 unittest5 *.gcda *.gcno *.gcov

```

```txt
Result for running BST tests:
unittest1.c:
containsBSTree(): PASS when test containing 50 as root
containsBSTree(): PASS when test containing 13 as left of root
containsBSTree(): PASS when test containing 110 as right of root
containsBSTree(): PASS when test containing 10 as left of left of root
containsBSTree(): PASS when test containing 111, which is not in the tree
File 'bst.c'
Lines executed:38.37% of 86
bst.c:creating 'bst.c.gcov'

unittest2.c:
_leftMost(): PASS left most of root
_leftMost(): PASS left most of left of root
_leftMost(): PASS left most of left of left of root
_leftMost(): PASS left most of right of root
File 'bst.c'
Lines executed:41.86% of 86
bst.c:creating 'bst.c.gcov'
```

***

This is my assignment-2 submission!

:construction:

***


***

# Dominon Base Architecture

- [Header files](#header-files)
- [Source files](#soucre-files)

## Header files

***

### dominion_helpers.h

It **imports** the dominion.h header file and contains the following function prototypes: 

- drawCard()
- updateCoins()
- discardCard()
- gainCard()
- getCost()
- cardEffect()

***

### dominion.h

It contains most of the function prototypes exceptfor those in dominion_helper.h.

#### CARD enum:
- CURSE = 0,
- ESTATE = 1
- DUCHY = 2
- PROVINCE = 3
- COPPER = 4
- SILVER = 5
- GOLD = 6
- ADVENTURER = 7 `If no/only 1 treasure found, stop when full deck seen`
- COUNCIL_ROOM = 8
- FEAST = 9 `choice1 is supply # of card gained`
- GARDENS = 10
- MINE = 11 `choice1 is hand# of money to trash, choice2 is supply# of money to put in hand`
- REMODEL = 12 `choice1 is hand# of card to REMODEL, choice2 is supply#`
- SMITHY = 13
- VILLAGE = 14
- BARON = 15`choice1: boolean for discard of ESTATE`
   `Discard is always of first (lowest index) ESTATE`
- GREAT_HALL = 16
- MINION = 17 `choice1:  1 = +2 coin, 2 = redraw`
- STEWARD = 18 `choice1: 1 = +2 card, 2 = +2 coin, 3 = trash 2 (choice2,3)`
- TRIBUTE = 19

- AMBASSADOR = 20 `choice1 = hand#, choice2 = number to return to supply`
- CUTPURSE = 21
- EMBARGO = 22 `choice1 = supply#`
- OUTPOST = 23
- SALVAGER = 24 `choice1 = hand# to trash`
- SEA_HAG = 25
- TREASURE_MAP = 26

#### gameState struct

- int numPlayers; //number of players
- int supplyCount[TREASURE_MAP+1];  //this is the amount of a specific type of card given a specific number.
- int EMBARGOTokens[TREASURE_MAP+1];
- int outpostPlayed;
- int outpostTurn;
- int whoseTurn;
- int phase;
- int numActions; /* Starts at 1 each turn */
- int coins; /* Use as you see fit! */
- int numBuys; /* Starts at 1 each turn */
- int hand[MAX_PLAYERS][MAX_HAND];
- int handCount[MAX_PLAYERS];
- int deck[MAX_PLAYERS][MAX_DECK];
- int deckCount[MAX_PLAYERS];
- int discard[MAX_PLAYERS][MAX_DECK];
- int discardCount[MAX_PLAYERS];
- int playedCards[MAX_DECK];
- int playedCardCount;

#### Functions in the dominion

- All functions return -1 on failure, and **DO NOT CHANGE GAME STATE**; unless specified for other return, return 0 on success.

- struct gameState* newGame();
- int* kingdomCards(int k1, int k2, int k3, int k4, int k5, int k6, int k7, int k8, int k9, int k10);
- int initializeGame(int numPlayers, int kingdomCards[10], int randomSeed, struct gameState *state); `Responsible for initializing all supplies, and shuffling deck and drawing starting hands for all players.  Check that 10 cards selected are in fact (different) kingdom cards, and that numPlayers is valid.`

- `Cards not in game should initialize supply position to -1`

- int shuffle(int player, struct gameState *state); `Assumes all cards are now in deck array (or hand/played):  discard is empty`
- int playCard(int handPos, int choice1, int choice2, int choice3, struct gameState *state); `Play card with index handPos from current player's hand`

- int buyCard(int supplyPos, struct gameState *state); `Buy card with supply index supplyPos`

- int numHandCards(struct gameState *state); `How many cards current player has in hand`

- int handCard(int handNum, struct gameState *state); `enum value of indexed card in player's hand`

- int supplyCount(int card, struct gameState *state); `How many of given card are left in supply`

- int fullDeckCount(int player, int card, struct gameState *state); `Here deck = hand + discard + deck`

- int whoseTurn(struct gameState *state);

- int endTurn(struct gameState *state); `Must do phase C and advance to next player; do not advance whose turn if game is over`

- int isGameOver(struct gameState *state);

- int scoreFor(int player, struct gameState *state); `Negative here does not mean invalid; scores may be negative, -9999 means invalid input`

- int getWinners(int players[MAX_PLAYERS], struct gameState *state); `Set array position of each player who won (remember ties!) to 1, others to 0`


***



### interface.h

It **imports** dominion.h and contains several macros and allthe function prototypes from interface.c.z

- int `addCardToHand`(int player, int card, struct gameState *game); 
- int `countHandCoins`(int player, struct gameState *game);
- void `executeBotTurn`(int player, int *turnNum, struct gameState *game);
- void `phaseNumToName`(int phase, char *name); 
- void `cardNumToName`(int card, char *name);
- int `getCardCost`(int card);
- void `printHelp`(void);
- void `printHand`(int player, struct gameState *game);
- void `printDeck`(int player, struct gameState *game);
- void `printDiscard`(int player, struct gameState *game);
- void `printPlayed`(int player, struct gameState *game);
- void `printState`(struct gameState *game);
- void `printSupply`(struct gameState *game);
- void `printGameState`(struct gameState *game);
- void `printScores`(struct gameState *game);
- void s`electKingdomCards`(int randomSeed, int kingdomCards[NUM_K_CARDS]);

***

### rngs.h


- double Random(void);
- void   PlantSeeds(long x);
- void   GetSeed(long *x);
- void   PutSeed(long x);
- void   SelectStream(int index);
- void   TestRandom(void);

***

## Soucre Files

### dominion.c­ 
It defines all the functions to build a dominion game object and its contents. (e.g., deck ofcards, card effect, score, action, etc.)

### interface.c­ 
It is the user interface file.  It defines several functions to print player, card, deck, action, andscore in console.

### playdom.c­ 
It has a main() function and defines how two players play the dominion game with the Smithyand Adventure action card.

### player.c­ 
It has 2 main() functions. The main() function executes the dominion game and givesinstructions players during the game.  The main2() function is used to initialize a game.

### rngs.c­ 
This is an ASCII C library for multi­stream random number generation. It contains severalfunctions to create and generate a random number and set its state.

### rt.c­ 
It has a main() function.  
It tests the state of the random number generator from rngs.c

### testBuyerCard.c­ 
It defines a checkDrawCard function.  It performs random tests on the buyCard function withthe checkDrawCard function.

### testDrawCard.c­ 
It defines a checkDrawCard function.  It performs random tests on the drawCard function withthe checkDrawCard function.

### badTestDrawCard.c
It defines a checkDrawCard function.  It performs random tests on the drawCard function withthe checkDrawCard function.  

It seems to be the oldest testDawCard file and has beenreplaced by testDrawCard.c.

### betterTestDrawCard.c­ 
It defines a checkDrawCard function.  It performs random tests on the drawCard function withthe checkDrawCard function.  It seems to be an improved version of the oldest testDawCardfile and has been replaced by testDrawCard.c.

### testInit.c­ 
It has a main() function.  It tests the initializeGame function

### testShuffle.c­ It has a main() function.  It tests the shuffle() function.

***

This is my assignment-1 submission!

:tada: