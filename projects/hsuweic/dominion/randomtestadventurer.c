/* Testing Adventurer
  Reveal cards from your deck until you reveal 2 Treasure
  cards. Put those Treasure cards into your hand and discard
  the other revealed card.
 * 1. After playing Adventurer, there should have 2 Treasure(GOLD/SILVER/COPPER) in hand.
 * 2. After playing Adventurer, there should not have any Treasure in the discard
 * 3. After playing Adventurer, there should have 1 more card be used and 1 more care on hand.
 */

#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "rngs.h"
#include "dominion_cards_helpers.h"
#include "random_test_helper.h"
#include <time.h>
#include <math.h>
#define TREASURE_CARD_NUM 3


void testCardAdventurer()
{
    int testTime = 10;
    int handHold[MAX_HAND];
    bool test_result = true;
    int test_counter = 0;
    int test_passed_counter = 0;
    int* treasure_cards = malloc(3 * sizeof(SILVER)); 
    treasure_cards[0] = COPPER;
    treasure_cards[1] = ESTATE;

    
    for(int i = 0; i < testTime; i++)
    {

        struct gameState game = gameStateRandomlyGenerate();
        
        int currentPlayer = rand() % game.numPlayers;
        int currentHandPos = rand() % game.handCount[currentPlayer];

        game.hand[currentPlayer][currentHandPos] = ADVENTURER;
        game.whoseTurn = currentPlayer;

        /* Cache the current Values */
        int currentHandCount = game.handCount[currentPlayer];
        int currentPlayedCardCount = game.playedCardCount;
        
        /* Randomly assign TREASURE CARDS */
        int estateNumber = rand() % TREASURE_CARD_NUM;

        for(int j = estateNumber; j < 6; j++) {
            game.deck[currentPlayer][i] = COPPER;
        }

        /* The main tested function */
        playAdventurer(&game, handHold, currentPlayer);
        
        /* Test for the Hand count and played card  */
        testEqual("Played Card Should Contains One more card.", currentPlayedCardCount + 1, game.playedCardCount, &test_result, &test_counter, &test_passed_counter);
        testEqual("Hand Card Should Contains One more card.", currentHandCount + 1, game.handCount[currentPlayer], &test_result, &test_counter, &test_passed_counter);
        
        int currentTreasure = game.hand[currentPlayer][currentHandCount];

        testEqual("At least contains one type of treasure cards in hand.", false ,currentTreasure != COPPER && currentTreasure != SILVER && currentTreasure != GOLD, &test_result, &test_counter, &test_passed_counter);
        testEqual("There should have 2 TREASURES in HAND", 2, game.handCount[currentPlayer] - currentHandCount, &test_result, &test_counter, &test_passed_counter);
    }

    testResult(test_result, test_counter, test_passed_counter);
}



int main() 
{
  testCardAdventurer();
  return 0;
}