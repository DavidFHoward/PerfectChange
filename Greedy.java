import java.util.List;

/**
 * this class contains the Greedy strategy to solve our coin problem
 */
class Greedy extends Problem
{
    /**
     * constructor for our Greedy strategy
     * @param denominations the list of the type of coins we will use
     * @param target the value we are trying to reach with the coins
     */
    public Greedy(List<Integer> denominations, int target)
    {
        super(denominations, target);
        initializeSolution();
    }

    /**
     * method to encapsulate the initalization of the numberOfEachCoin array and the totalCoins variable
     */
    private void initializeSolution()
    {
        numberOfEachCoin = new int[denominations.size()];
        totalCoins = 0;

        boolean solved = false;
        int pointer = 0;

        while(!solved) // construct the greedy solution
        {
            if (target - denominations.get(pointer) >= 0)
            {
                numberOfEachCoin[pointer]++;
                target -= denominations.get(pointer);
                totalCoins++;
            }
            else
            {
                pointer++;
            }
            solved = target == 0;
        }

        this.numberOfEachCoin = numberOfEachCoin;
        this.totalCoins = totalCoins;
    }

    /**
     * toString method to override Problem toString
     * @return the greedy tag with the number of each coin we used in our solution
     */
    @Override
    public String toString()
    {
        return "Greedy - " + super.toString();
    }
}