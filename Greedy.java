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