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
                accumulator.append(numberOfCoin[i] + "x" + denominations.get(i) + ( i == denominations.size() - 1? "" :", "));
        }


        return accumulator.toString();
    }
}

/**
 * this class contains the Greedy strategy to solve our coin problem
 */
class Greedy
{
    private List<Integer> denominations; // the type of coins we have
    private int[] numberOfCoin;// the cooresponding number of coins by denomination
    private int target; // the change we are trying to make
    private int totalCoins; // how many coins we use to get our solution

    /**
     * constructor for our Greedy strategy
     * @param denominations the list of the type of coins we will use
     * @param target the value we are trying to reach with the coins
     */
    public Greedy(List<Integer> denominations, int target)
    {
        this.denominations = denominations; // initialize our variables
        this.target = target;
        initializeSolution();
    }

    /**
     * method to encapsulate the initalization of the numberOfCoin array and the totalCoins variable
     */
    private void initializeSolution()
    {
        numberOfCoin = new int[denominations.size()];
        totalCoins = 0;

        boolean solved = false;
        int pointer = 0;

        while(!solved) // construct the greedy solution
        {
            if (target - denominations.get(pointer) >= 0)
            {
                numberOfCoin[pointer]++;
                target -= denominations.get(pointer);
                totalCoins++;
            }
            else
            {
                pointer++;
            }
            solved = target == 0;
        }

        this.numberOfCoin = numberOfCoin;
        this.totalCoins = totalCoins;
    }

    /**
     * simple getter for the total number of coins we use in our solution
     * @return the total number of coins we use in our solution
     */
    public int getTotalCoins()
    {
        return this.totalCoins;
    }

    /**
     * deturmines if the greedy approach is the best to solve this problem
     * @return true if the greedy appoach is optimal and false otherwise
     */
    public boolean isGreedy()
    {
        boolean isGreedy = true;

        for(int i = 0; i < denominations.size() - 2 && isGreedy; i++)
        {   
            List<Integer> test1Denominations = denominations.subList(i , denominations.size());
            List<Integer> test2Denominations = denominations.subList(i + 1, denominations.size());
            int testTarget = denominations.get(i) + denominations.get(i + 1) - 1;
            Greedy test1 = new Greedy(test1Denominations, testTarget);
            Greedy test2 = new Greedy(test2Denominations, testTarget);

            if(test1.getTotalCoins() > test2.getTotalCoins())
                isGreedy = false;
        }
        return isGreedy;
    }

    /**
     * toString method to override java.lang.Object and present us with relevent information about the greedy solution
     * @return the greedy tag with the number of each coin we used in our solution
     */
    @Override
    public String toString()
    {
        return ChangeDriver.createString("Greedy", denominations, numberOfCoin);
    }
}
/**
 * This is the Dynamic strategy to solving our coin problem. Also known as a bottom-up solution
 */
class Dynamic
{
    List<Integer> denominations; // the type of coins we have
    int[] numberOfCoins; // the number of coins we use to solve our sub problem target
    int[] coinUsed;// the coin we added to a subproblem to get our next solution
    int target;// the end goal target
    int[] numberOfEachCoin;

    /**
     * constructor for our dynamic strategy
     * @param denominations the type of coins that we have
     * @param target the change we will make
     */
    public Dynamic(List<Integer> denominations, int target)
    {   
        this.denominations = denominations;
        this.target = target;
        numberOfCoins = new int[target + 1]; // the number of coins we use for each sub problem
        coinUsed = new int[target + 1]; // the coins we used last to get this solution
        initializeSolutionArrays();
        initializeNumberOfEachCoin();
    }

    /**
     * method to get the total number of coins we used in our dynamic solution
     * @return the total number of coins we used in our dynamic solution
     */
    public int getTotalCoins()
    {
        return numberOfCoins[numberOfCoins.length - 1];
    }

    /**
     * this method encapsulates the initialization of numberOfCoins and coinUsed arrays
     */
    private void initializeSolutionArrays()
    {
        numberOfCoins[0] = 0; // initialize our worse case solutions
        coinUsed[0] = 1;
        for (int i = 1; i < numberOfCoins.length; i++)
        {
            numberOfCoins[i] = target;
            coinUsed[i] = 1;
        }
        
        for(int i = denominations.size() - 2; i >= 0; i--) // look at each neighboring pair of the types of coins we can use
        {
            for(int j = 1; j < numberOfCoins.length; j++) // go through each sub problem with these two coins
            {
                /*  the following logic uses the principle of transitive closure to make sure we have the minimum coins possible for our solution
                 *  the variables used are as follows and represent:
                 *  numberOfCoins[j - 1] + 1                            is our previous best solution with adding the lowest denomination
                 *  numberOfCoins[j - denominations.get(i)] + 1         is our solution using the highest coin of our two coins
                 *  numberOfCoins[j - denominations.get(i + 1)] + 1     is our solution using lower coin of the two
                 * 
                 *  j >= denominations.get(i) simply prevents us from indexing outside of the array via short circuit logic
                 */
                if(j >= denominations.get(i) && numberOfCoins[j - 1] + 1 > numberOfCoins[j - denominations.get(i + 1)] + 1 && numberOfCoins[j - denominations.get(i)] + 1 > numberOfCoins[j - denominations.get(i + 1)] + 1)
                {
                    numberOfCoins[j] = numberOfCoins[j - denominations.get(i + 1)] + 1;
                    coinUsed[j] = denominations.get(i + 1);
                }
                else if(j >= denominations.get(i) && numberOfCoins[j - 1] + 1 > numberOfCoins[j - denominations.get(i)] + 1 && numberOfCoins[j - denominations.get(i + 1)] + 1 > numberOfCoins[j - denominations.get(i)] + 1)
                {
                    numberOfCoins[j] = numberOfCoins[j - denominations.get(i)] + 1;
                    coinUsed[j] = denominations.get(i);
                }
                else if(j >= denominations.get(i) &&  numberOfCoins[j - denominations.get(i + 1)] + 1 > numberOfCoins[j - 1] + 1 && numberOfCoins[j - denominations.get(i + 1)] + 1 > numberOfCoins[j - 1] + 1)
                {
                    numberOfCoins[j] = numberOfCoins[j - 1] + 1;
                    coinUsed[j] = coinUsed[j - 1];
                }  
                //implicit else: if all the following statements are false we change nothing    
            }    
        }
        this.numberOfCoins = numberOfCoins;
        this.coinUsed = coinUsed;
    }

    /**
     * this method initializes our numberOfEachCoin array in our constructor
     */
    private void initializeNumberOfEachCoin()
    {
        int[] numberOfEachCoin = new int[denominations.size()];
        int decrementor = target;
        while(decrementor > 0 && coinUsed[decrementor] > 0)
        {
            numberOfEachCoin[denominations.indexOf(coinUsed[decrementor])]++;
            decrementor -= coinUsed[decrementor];
        }
        this.numberOfEachCoin = numberOfEachCoin;
    }

    /**
     * toString method to override java.lang.Object and present us with relevent information about our dynamic solution
     * @return the number of each coins that we used along with at dynamic tag to indicate the strategy we used
     */
    @Override
    public String toString()
    {
        return ChangeDriver.createString("Dynamic", denominations, numberOfEachCoin);
    }
}