#include "dominion.h"
#include "dominion_helpers.h"
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include "rngs.h"
#include <math.h>
#include <stdlib.h>
#include <time.h>

#define SILENT 1

int assertEquals(int a, int b, char* msg)
{   
    if (a==b) {
        if (!SILENT) {
            printf("PASS: %s\n", msg);
        }
        return 1;
    }
    else {
        printf("FAIL: %s\n", msg);
        return 0;
    }
}



void printComp(struct gameState* g1, struct gameState* g2)
{
    printf("gameState Before\tgameState After\n");

    printf("Num players: %d\t%d\n", g1->numPlayers, g2->numPlayers);
    for (int i=0; i<treasure_map+1; i++) {
        printf("Supply count %d: %d\t%d\n", i, g1->supplyCount[i], g2->supplyCount[i]);
    }
    for (int i=0; i<treasure_map+1; i++) {
        printf("Embargo tokens %d: %d\t%d\n", i, g1->embargoTokens[i], g2->embargoTokens[i]);
    }
    printf("Outpost played: %d\t%d\n", g1->outpostPlayed, g2->outpostPlayed);
    printf("Outpost turn: %d\t%d\n", g1->outpostTurn, g2->outpostTurn);
    printf("WhoseTurn: %d\t%d\n", g1->whoseTurn, g2->whoseTurn);
    printf("Phase: %d\t%d\n", g1->phase, g2->phase);
    printf("NumActions: %d\t%d\n", g1->numActions, g2->numActions);
    printf("Coins: %d\t%d\n", g1->coins, g2->coins);
    printf("NumBuys: %d\t%d\n", g1->numBuys, g2->numBuys);
    for (int i=0; i<MAX_PLAYERS; i++) {
        for (int j=0; j<MAX_HAND; j++) {
            printf("Hand - player %d - position %d: %d\t%d\n", i, j, g1->hand[i][j], g2->hand[i][j]);
        }
    }
    for (int i=0; i<MAX_PLAYERS; i++) {
        printf("HandCount - player %d: %d\t%d\n", i, g1->handCount[i], g2->handCount[i]);
    }
    for (int i=0; i<MAX_PLAYERS; i++) {
        for (int j=0; j<MAX_DECK; j++) {
            printf("Deck - player %d - position %d: %d\t%d\n", i, j, g1->deck[i][j], g2->deck[i][j]);
        }
    }
    for (int i=0; i<MAX_PLAYERS; i++) {
        printf("DeckCount - player %d: %d\t%d\n", i, g1->deckCount[i], g2->deckCount[i]);
    }
    for (int i=0; i<MAX_PLAYERS; i++) {
        for (int j=0; j<MAX_DECK; j++) {
            printf("Discard - player %d - position %d: %d\t%d\n", i, j, g1->discard[i][j], g2->discard[i][j]);
        }
    }
    for (int i=0; i<MAX_PLAYERS; i++) {
        printf("DiscardCount - player %d: %d\t%d\n", i, g1->discardCount[i], g2->discardCount[i]);
    }
    for (int i=0; i<MAX_DECK; i++) {
        printf("PlayedCard - position %d: %d\t%d\n", i, g1->playedCards[i], g2->playedCards[i]);
    }
    printf("PlayedCardCount: %d\t%d\n", g1->playedCardCount, g2->playedCardCount);
}

int main()
{
    srand(time(0));

    int passCount = 0;
    int testCount = 1000;

    for (int i=0; i<testCount; i++) {

        struct gameState g = generateGameState();

        int player = rand() % g.numPlayers;
        int handPos = rand() % g.handCount[player];

        // Setup g with pre-function values
        g.hand[player][handPos] = adventurer;
        g.whoseTurn = player;

        // Save old values
        int oldHandCount = g.handCount[player];
        int oldPlayedCardCount = g.playedCardCount;
        struct gameState before = g;

        // Execute function on g and test results
        int result = cardEffect(adventurer, 0, 0, 0, &g, handPos, NULL);

        printf("TESTING adventurer ITERATION %d:\n----------\n", i+1);

        int pass = 1;


        if (!assertEquals(g.playedCardCount, oldPlayedCardCount + 1, "adventurer: Played card pile contains 1 more card.")) {
            pass = 0;
        }
        if (!assertEquals(g.handCount[player], oldHandCount + 1, "adventurer: Player hand contains 1 more card.")) {
            pass = 0;
        }
        if (g.hand[player][handPos] == copper) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains copper.\n", handPos);
        }
        else if (g.hand[player][handPos] == silver) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains silver.\n", handPos);
        }
        else if (g.hand[player][handPos] == gold) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains gold.\n", handPos);
        }
        else {
            printf("FAIL: adventurer: Player hand position %d doesn't contain treasure.\n", handPos);
            pass = 0;
        }
        if (g.hand[player][oldHandCount] == copper) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains copper.\n", oldHandCount);
        }
        else if (g.hand[player][oldHandCount] == silver) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains silver.\n", oldHandCount);
        }
        else if (g.hand[player][oldHandCount] == gold) {
            if (!SILENT)
                printf("PASS: adventurer: Player hand position %d contains gold.\n", oldHandCount);
        }
        else {
            printf("FAIL: adventurer: Player hand position %d doesn't contain treasure.\n", oldHandCount);
            pass = 0;
        }

        if (pass) {
            printf("ALL TESTS PASSED\n");
            passCount++;
        }
        else {
            // If test fails, print all data in gameState before and after function is called
            printComp(&before, &g);
        }

        printf("----------\n");
    }

    printf("Tests passed: %d\n", passCount);
    printf("Total tests: %d\n", testCount);

    return 0;
}