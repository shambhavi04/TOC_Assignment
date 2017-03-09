#include<stdio.h>
#include<string.h>
#include<stdlib.h>

//function to get the next state of transition table
int new_state(char *pattern, int len1, int state, int j)
{	if (state < len1 && j == pattern[state])	//checking if pattern matches with string
  	return state+1;					//increment the stat
 	
  	int next_state, i; 		// next_state stores the result
 
  	for (next_state = state; next_state > 0; next_state--)
  	{	if(pattern[next_state-1] == j)
  		{	for(i = 0; i < next_state-1; i++)
  			{	if (pattern[i] != pattern[state-next_state+1+i])
  					break;
  			}
  			if (i == next_state-1)
  				return next_state;
  		}
  	}
 	return 0;
}
 
//construct transition table using the algorithm
void construct(char *pattern, int len1, int table[][500])
{	int state, j;
  	for (state = 0; state <= len1; state++)
  		for (j = 0; j < 500; j++)
  			table[state][j] = new_state(pattern, len1, state, j);
}
 
//prints all occurrences of pattern in txt 
void search(char *pattern, char *string)
{	int len1 = strlen(pattern);
  	int len2 = strlen(string);
 	int table[len1+1][500];
 	construct(pattern, len1, table); 	//construction of transition table
 
	int i, state=0;
  	for (i = 0; i < len2; i++)
  	{	state = table[state][string[i]];
  		if (state == len1)
			printf ("\nPattern found at position %d", i-len1+1);
  	}
  	printf("\n");
}
 
int main()
{	//take pattern as an input	
	char *string = (char *) "an apple an day an";   //entering the string
  	char *pattern = (char *) "an";            //entering the pattern
  	search(pattern, string);		   //calling search function
	return 0;
}
