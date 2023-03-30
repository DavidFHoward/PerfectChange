/* Name: Olivia Howard
 * Class: CSCI 3005 Algorithms
 * Instructor: Dr. Smith
 * Date due: March 31, 2023, 5:00 PM
 */

import java.util.List;
import java.util.ArrayList;

/**
 * This class contains a program to present the optimal combination of coins for a target value
 */
public class ChangeDriver
{
    /**
     * the main of the program, that drives the creation of our problem and the solution (Greedy or Dynamic) we employ.
     * @param args the integers delimited by a space of our denomination of coins and the change we would like to make
     */
    public static void main(String[] args)
    {
        List<Integer> denominations = new ArrayList<>(); // this list contains the type of coins we have
        int target = 0;// this will contain what value we are targeting with these coins

        for(int i = 0; i < args.length; i++) // initialize variables to values pertaining to our problem
        {
            if(Integer.parseInt(args[i]) != 1)
            {
                denominations.add(Integer.parseInt(args[i]));
            }
            else
            {
                denominations.add(Integer.parseInt(args[i]));
                target = Integer.parseInt(args[i + 1]);
                break;
            }
        }
        Greedy problem = new Greedy(denominations, target); // construct the problem with a greedy approach

        if(problem.isGreedy()) // if this problem is best suited for this strategy
        {
            Greedy solution = problem; // then we have our solution
            System.out.println(solution);
        }
        else // otherwise construct the Dynamic Strategy
        {
            Dynamic dynamicSolution = new Dynamic(denominations, target);
            Greedy greedySolution = problem;

            if(greedySolution.getTotalCoins() <= dynamicSolution.getTotalCoins()) // compare both solutions to find the better
                System.out.println(greedySolution);
            else 
                System.out.println(dynamicSolution);
        }
    }
    /**
     * static method used in both the Dynamic Class and the Greedy Class to create the output string
     * @param strategy the string name of the strategy we are tagging the solution with
     * @param denominations the types of coins we can use in a list
     * @param numberOfCoin the number of each type of coin in an array of integers
     * @return the formated string using these inputs
     */
    public static String createString(String strategy, List<Integer> denominations, int[] numberOfCoin)
    {
        StringBuilder accumulator = new StringBuilder();
        accumulator.append(strategy + " - ");

        for(int i = 0; i < denominations.size(); i++)
        {
            if (numberOfCoin[i] != 0)
                accumulator.append(numberOfCoin[i] + "x" + denominations.get(i) + ", ");
        }
        
        accumulator.delete(accumulator.length() - 2, accumulator.length());

        return accumulator.toString();
    }
}