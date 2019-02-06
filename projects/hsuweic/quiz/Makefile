CFLAGS= -Wall -fpic -coverage -lm -std=c99
all:
	gcc -o testme -g testme.c $(CFLAGS)
	echo "Result for testme()" >> testmeresults.out
	./testme >> testmeresults.out
	gcov testme.c >> testmeresults.out

clean:
	rm -f testme testmeresults.out *.gcov *.gcda *.gcno *.so *.out