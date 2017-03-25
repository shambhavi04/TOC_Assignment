/*Implementing string matching using Finite Automata as given in Cormen book pg. no. 995. In the first step, 
take pattern as an input, construct transition table using the algorithm. In second step, take a string as an 
input from user and see whether the pattern exists or not using the transition table created in first step. */

package toc;

/**
 *
 * @author Barkha Agrawal
 */
import java.util.Scanner;
 
public class TOC
{
    public static final int NCHARS = 256;
   
    
    //prints all occurrences of pattern in string 
    public static void pattern(char[] pat, char[] string)
    {
        int len1 = pat.length;  //length of pattern
        int len2 = string.length;   //length of string
        int[][] table = new int[len1 + 1][NCHARS];
        construct(pat, len1, table);  //construction of transition table
      
        int i, state = 0;
        for (i = 0; i < len2; i++)
        {
            state = table[state][string[i]];
            if (state == len1)
            {
                System.out.print(pat);
                System.out.println(" String Found at Position " + (i - len1 + 1));
            }
        }
    }
    
    //construct transition table using the algorithm
     public static void construct(char[] pat, int len1, int[][] table)
    {
        int state, x;
        for (state = 0; state <= len1; ++state)
            for (x = 0; x < NCHARS; ++x)
                table[state][x] = new_state(pat, len1, state, x);
    }

    
    //function to get the next state of transition table
     public static int new_state(char[] pat, int len1, int state, int x)
    {
        
        if (state < len1 && x == pat[state])    //checking if pattern matches with string
            return state + 1;                   //increment the state
        int next_state, i;                 //next_state stores the result
       
        for (next_state = state; next_state > 0; next_state--)
        {
            if (pat[next_state - 1] == x)
            {
                for (i = 0; i < next_state - 1; i++)
                {
                    if (pat[i] != pat[state - next_state + 1 + i])
                        break;
                }
                if (i == next_state - 1)
                    return next_state;  
            }
        }
        return 0;
    }
 
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the String: ");
        String main = input.nextLine();
        System.out.println("Enter the pattern you are searching for: ");
        String pattern = input.nextLine();
        pattern(pattern.toCharArray(), main.toCharArray());
        input.close();
    }
}
