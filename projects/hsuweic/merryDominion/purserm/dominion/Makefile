CFLAGS= -Wall -fpic -coverage -lm -std=c99

rngs.o: rngs.h rngs.c
	gcc -c rngs.c -g  $(CFLAGS)

dominion.o: dominion.h dominion.c rngs.o
	gcc -c dominion.c -g  $(CFLAGS)

playdom: dominion.o playdom.c
	gcc -o playdom playdom.c -g dominion.o rngs.o $(CFLAGS)
#To run playdom you need to entere: ./playdom <any integer number> like ./playdom 10*/
testDrawCard: testDrawCard.c dominion.o rngs.o
	gcc  -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

badTestDrawCard: badTestDrawCard.c dominion.o rngs.o
	gcc -o badTestDrawCard -g  badTestDrawCard.c dominion.o rngs.o $(CFLAGS)

testBuyCard: testDrawCard.c dominion.o rngs.o
	gcc -o testDrawCard -g  testDrawCard.c dominion.o rngs.o $(CFLAGS)

testAll: dominion.o testSuite.c
	gcc -o testSuite testSuite.c -g  dominion.o rngs.o $(CFLAGS)

interface.o: interface.h interface.c
	gcc -c interface.c -g  $(CFLAGS)

runtests: testDrawCard 
	./testDrawCard &> unittestresult.out
	gcov dominion.c >> unittestresult.out
	cat dominion.c.gcov >> unittestresult.out

test_helper.o: test_helper.h test_helper.c
	gcc -c test_helper.c -g  $(CFLAGS)

unittest1: unittest1.c dominion.o test_helper.o rngs.o
	gcc -o unittest1 unittest1.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
unittest2: unittest2.c dominion.o test_helper.o rngs.o
	gcc -o unittest2 unittest2.c -g dominion.o test_helper.o rngs.o $(CFLAGS)	
	
unittest3: unittest3.c dominion.o test_helper.o rngs.o
	gcc -o unittest3 unittest3.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
unittest4: unittest4.c dominion.o test_helper.o rngs.o
	gcc -o unittest4 unittest4.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
cardtest1: cardtest1.c dominion.o test_helper.o rngs.o
	gcc -o cardtest1 cardtest1.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
cardtest2: cardtest2.c dominion.o test_helper.o rngs.o
	gcc -o cardtest2 cardtest2.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
cardtest3: cardtest3.c dominion.o test_helper.o rngs.o
	gcc -o cardtest3 cardtest3.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
cardtest4: cardtest4.c dominion.o test_helper.o rngs.o
	gcc -o cardtest4 cardtest4.c -g dominion.o test_helper.o rngs.o $(CFLAGS)
	
cardtestresults: unittest1 unittest2 unittest3 unittest4 cardtest1 cardtest2 cardtest3 cardtest4
	@chmod +x cardtest_result_generator
	@./cardtest_result_generator
	echo "============ GENERAL ANALYSIS ============" >> unittestresults.out
	gcov dominion.c --function-summaries >> unittestresults.out


random_test_helper.o: random_test_helper.h random_test_helper.c
	gcc -c random_test_helper.c -g  $(CFLAGS)

randomtestcard1:randomtestcard1.c dominion.o random_test_helper.o rngs.o
	gcc -o randomtestcard1 randomtestcard1.c -g dominion.o random_test_helper.o rngs.o $(CFLAGS) 

randomtestcard2:randomtestcard2.c dominion.o random_test_helper.o rngs.o
	gcc -o randomtestcard2 randomtestcard2.c -g dominion.o random_test_helper.o rngs.o $(CFLAGS) 

randomtestadventurer:randomtestadventurer.c dominion.o random_test_helper.o rngs.o
	gcc -o randomtestadventurer randomtestadventurer.c -g dominion.o random_test_helper.o rngs.o $(CFLAGS) 

randomtestresults: randomtestcard1 randomtestcard2 randomtestadventurer
	@chmod +x randomtest_result_generator
	@./randomtest_result_generator
	echo "============ GENERAL ANALYSIS ============" >> randomtestresults.out
	gcov dominion.c --function-summaries >> randomtestresults.out

randomtestcard1.out: randomtestcard1
	@chmod +x randomtestcard1_result_generator
	@./randomtestcard1_result_generator
	
randomtestcard2.out: randomtestcard2
	@chmod +x randomtestcard2_result_generator
	@./randomtestcard2_result_generator
	
randomtestadventurer.out: randomtestadventurer
	@chmod +x randomtestadventurer_result_generator
	@./randomtestadventurer_result_generator


player: player.c interface.o
	gcc -o player player.c -g  dominion.o rngs.o interface.o $(CFLAGS)

all: playdom player 

clean:
	rm -f *.o playdom.exe playdom player player.exe test_helper random_test_helper randomtestadventurer randomtestcard1 randomtestcard2 unittest1 unittest2 unittest3 cardtest4 cardtest1 cardtest2 cardtest3 unittest4 *.gcov *.gcda *.gcno *.so *.out testDrawCard testDrawCard.exe
