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

void testCardSmithy()
{
    int testTime = 10;
    bool test_result = true;
    int test_counter = 0;
    int test_passed_counter = 0;
    int choice1 = 0;
    int choice2 = 0;
    int choice3 = 0;
    
    for(int i = 0; i < testTime; i++)
    {

        struct gameState game = gameStateRandomlyGenerate();
        
        int currentPlayer = rand() % game.numPlayers;
        int smithyPosition = 0;
        int currentHandCount = 0;
        int currentDeckCount = 0;

        /* Add Smith to hand */
        gainCard(SMITHY, &game, 2, currentPlayer);

        /* Get Smithy position */
        for(int i = 0; i < game.handCount[currentPlayer]; i++) 
        {
          if(game.hand[currentPlayer][i] == SMITHY)
          {
            smithyPosition = i;
            break;
          }
        }

        currentHandCount = game.handCount[currentPlayer];
        currentDeckCount = game.deckCount[currentPlayer];

        // playSmithy(&game, currentPlayer, smithyPosition);
        cardEffect(SMITHY, choice1, choice2, choice3, &game, 0, NULL);

        
        /* Test for the Hand count and played card  */
        testEqual("Number of cards in hand should + 3 - 1 after playing Smithy", 2, game.handCount[currentPlayer] - currentHandCount, &test_result, &test_counter, &test_passed_counter);
        testEqual("Number of cards in deck should - 3 + 1 after playing Smithy", 2, currentDeckCount - game.deckCount[currentPlayer], &test_result, &test_counter, &test_passed_counter);
        testEqual("Check SMITHY has been played.", SMITHY, game.playedCards[0], &test_result, &test_counter, &test_passed_counter);
        testEqual("Difference between hand and deck should increase by 4",
             currentHandCount - currentDeckCount + 4,  game.handCount[currentPlayer] - game.deckCount[currentPlayer], &test_result, &test_counter, &test_passed_counter);
    }
    testResult(test_result, test_counter, test_passed_counter);
}

int main() 
{
  testCardSmithy();
  return 0;
}