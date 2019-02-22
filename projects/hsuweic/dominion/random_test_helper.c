#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "rngs.h"
#include "dominion_cards_helpers.h"
#define MAX_PLAYER_NUMBER 4
#define MIN_PLAYER_NUMBER 2
#define MAX_COIN_NUMBER 8
#define MAX_BUYS_NUMBER 5
#define GIVEN_BINARY 2
#define MAX_ACTION_NUMBER 3
#define MAX_CARD_IN_HAND 16
#define MIN_CARD_IN_HAND 8
#define TOTAL_TREASURE_NUMBER 3
#define CARDS_NUMBER 11

void testEqual(char *message, int expected, int actual, bool *test_result) {
  int len = strlen(message);
  char outputMessage[len + 50];
  memset(&outputMessage, 0, sizeof(outputMessage));
  strcat(outputMessage, "Test: ");
  strcat(outputMessage, message);
  if(expected != actual)
  {
    strcat(outputMessage, " || FAILED ||");
    printf("%s expected = %d, actual = %d\n", outputMessage, expected, actual);
    *test_result = false;
  }
}

void testResult(bool test_result)
{
  if(test_result)
  {
    printf("All tests passed!\n");
  }
  else
  {
    printf("You got some bugs, please read the error message.\n");
  }
}

struct gameState gameStateRandomlyGenerate()
{
    struct gameState game;
    /* Set up players information */

    game.numPlayers = rand() % (MAX_PLAYER_NUMBER - 1) + (MIN_PLAYER_NUMBER); 

    for (int i = 0; i < MAX_PLAYERS; i++) 
    {
      for (int j = 0; j < MAX_HAND; j++) 
      {
          game.hand[i][j] = rand() % (TREASURE_MAP + 1); /* Assign random cards in hand */
      }
    }

    for (int i = 0; i < MAX_PLAYERS; i++) 
    {
        game.handCount[i] = rand() % MAX_CARD_IN_HAND + MIN_CARD_IN_HAND;
    }

    for (int i=0; i < MAX_PLAYERS; i++) 
    {
        for (int j = 0; j < MAX_DECK; j++) 
        {
            // Ten Tresures for first ten cards
            if (j < 10) 
            {
                game.deck[i][j] = rand() % TOTAL_TREASURE_NUMBER + 4; /* Start from the forth */
            }
            else 
            {
                game.deck[i][j] = rand() % (TREASURE_MAP + 1); /* Assign random card to rest of deck */
            }
        }
    }

    for (int i=0; i<MAX_PLAYERS; i++) 
    {
        game.deckCount[i] = rand() % CARDS_NUMBER + 5; 
    }

    for (int i=0; i<MAX_PLAYERS; i++) 
    {
        for (int j=0; j<MAX_DECK; j++) 
        {
            game.discard[i][j] = rand() % (TREASURE_MAP + 1); 
        }
    }

    for (int i=0; i<MAX_PLAYERS; i++) 
    {
        game.discardCount[i] = rand() % CARDS_NUMBER + 5; 
    }

    for (int i=0; i<MAX_DECK; i++) 
    {
        game.playedCards[i] = rand() % (TREASURE_MAP + 1); 
    }

    /* Players status */

    game.playedCardCount = rand() % CARDS_NUMBER; 

    game.outpostTurn = rand() % game.numPlayers;

    game.whoseTurn = rand() % game.numPlayers;


    /* Actual Cards in hand and game situation */
    
    game.coins = rand() % (MAX_COIN_NUMBER + 1);

    game.numBuys = rand() % (MAX_BUYS_NUMBER + 1);

    for (int i=0; i < TREASURE_MAP + 1; i++) 
    {
        game.supplyCount[i] = rand() % 100; 
    }

    for (int i=0; i < TREASURE_MAP + 1; i++) 
    {
        game.EMBARGOTokens[i] = rand() % GIVEN_BINARY;
    }

    game.phase = rand() % (MAX_ACTION_NUMBER + 1);

    game.numActions = rand() % (MAX_ACTION_NUMBER + 1);

    game.outpostPlayed = rand() % GIVEN_BINARY;

    return game;
}