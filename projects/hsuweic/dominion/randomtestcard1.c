/* Testing Smithy
 * 1. Should gain 3 cards from deck to player's hand
 * 2. After playing Smity, make sure total cards on hand will + 3 - 1 = +2
 * 3. After playing Smity, make sure total cards on deck will - 3 + 1 = -2
 * 4. After playing Smity, the top value of playedCard should be SMITHY
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

void testCardVillage()
{
    int testTime = 10;
    bool test_result = true;
    int test_counter = 0;
    int test_passed_counter = 0;
    
    for(int i = 0; i < testTime; i++)
    {

        struct gameState game = gameStateRandomlyGenerate();
        
        int currentPlayer = rand() % game.numPlayers;
        int currentAction = game.numActions;
        int villagePosition = 0;

        /* Cache the current Values */
        int currentHandCount = game.handCount[currentPlayer];

        gainCard(VILLAGE, &game, 0, currentPlayer);

        /* get Village position */
        for(int i = 0; i < game.handCount[currentPlayer]; i++) 
        {
          if(game.hand[currentPlayer][i] == VILLAGE)
          {
            villagePosition = i;
            break;
          }
        }
        
        game.whoseTurn = currentPlayer;

        /* The main tested function */
        playVILLAGE(&game, currentPlayer, villagePosition);
        
        /* Test for the Hand count and played card  */
        testEqual("Check VILLAGE has been played.", VILLAGE, game.playedCards[0], &test_result, &test_counter, &test_passed_counter);
        testEqual("Current player should get 1 more cards", 1, game.handCount[currentPlayer] - currentHandCount, &test_result, &test_counter, &test_passed_counter);
        testEqual("There should have 2 more actions", 2, game.numActions - currentAction, &test_result, &test_counter, &test_passed_counter);
    }

    testResult(test_result, test_counter, test_passed_counter);
}



int main() 
{
  testCardVillage();
  return 0;
}