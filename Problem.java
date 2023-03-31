import java.util.List;

abstract class Problem implements Comparable<Problem>
{
    List<Integer> denominations; // the type of coins we have
    int[] numberOfEachCoin; // the number of each coin by denomination
    int target;// the end goal target
    int totalCoins;

    /**
     * constructor for our problem
     * @param denominations the types of coins we can use
     * @param target the value in coins we want to get to with the fewest coins possible
     */

    public Problem(List<Integer> denominations, int target) 
    {
        this.denominations = denominations;
        this.target = target;
    }

    /** 
     * method to get totalCoins
     * @return the total number of coin it takes for the solution
    */
    public int getTotalCoins()
    {
        return totalCoins;
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

    @Override
    public int compareTo(Problem solution)
    {
        return this.getTotalCoins() - solution.getTotalCoins();
    }

    @Override
    public String toString()
    {
        StringBuilder accumulator = new StringBuilder();

        for(int i = 0; i < denominations.size(); i++)
        {
            if (numberOfEachCoin[i] != 0)
                accumulator.append(numberOfEachCoin[i] + "x" + denominations.get(i) + ", ");
        }
        
        accumulator.delete(accumulator.length() - 2, accumulator.length());

        return accumulator.toString();
    }
}
