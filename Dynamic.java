import java.util.List;

/**
 * This is the Dynamic strategy to solving our coin problem. Also known as a bottom-up solution
 */
class Dynamic extends Problem
{
    
    int[] numberOfCoins; // the number of coins we use to solve our sub problem target
    int[] coinUsed;// the coin we added to a subproblem to get our next solution

    /**
     * constructor for our dynamic strategy
     * @param denominations the type of coins that we have
     * @param target the change we will make
     */
    public Dynamic(List<Integer> denominations, int target)
    {   
        super(denominations, target);
        numberOfCoins = new int[target + 1]; // the number of coins we use for each sub problem
        coinUsed = new int[target + 1]; // the coins we used last to get this solution
        initializeSolutionArrays();
        initializeNumberOfEachCoin();
        initializeTotalCoins();
    }

    /**
     * this method encapsulates the assignment of totalCoins
     */
    public void initializeTotalCoins()
    {
        for(int num: numberOfEachCoin)
        {
            totalCoins += num;
        }

        this.totalCoins = totalCoins;
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
        numberOfEachCoin = new int[denominations.size()];
        int decrementor = target;
        while(decrementor > 0 && coinUsed[decrementor] > 0)
        {
            numberOfEachCoin[denominations.indexOf(coinUsed[decrementor])]++;
            decrementor -= coinUsed[decrementor];
        }
        this.numberOfEachCoin = numberOfEachCoin;
    }

    /**
     * toString method to override Problem toString
     * @return the number of each coins that we used along with at dynamic tag to indicate the strategy we used
     */
    @Override
    public String toString()
    {
        return "Dynamic - " + super.toString();
    }
}