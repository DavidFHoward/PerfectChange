import java.util.List;
import java.util.ArrayList;

public class PerfectChange
{
    public static void main(String[] args)
    {
        List<Integer> denominations = new ArrayList<>();
        int target = 0;

        for(int i = 0; i < args.length; i++)
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

        Greedy problem = new Greedy(denominations, target);

        if(problem.isGreedy())
        {
            Greedy solution = problem;
            System.out.println(solution);
        }
        else
        {
            Dynamic solution = new Dynamic(denominations, target);
            System.out.println(solution);
        }
    }
}

class Greedy
{
    private List<Integer> denominations;
    private int[] numberOfCoin;
    private int target;
    private int totalCoins;

    public Greedy(List<Integer> denominations, int target)
    {
        this.denominations = denominations;
        numberOfCoin = new int[denominations.size()];
        this.target = target;
        totalCoins = 0;
        boolean solved = false;
        int pointer = 0;

        while(!solved)
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

    }

    public int getTotalCoins()
    {
        return this.totalCoins;
    }

    public boolean isGreedy()
    {
        boolean isGreedy = true;

        for(int i = 0; i < denominations.size() - 1 && isGreedy; i++)
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

    public String toString()
    {
        StringBuilder accumulator = new StringBuilder();
        accumulator.append("Greedy - ");

        for(int i = 0; i < denominations.size(); i++)
        {
            if (numberOfCoin[i] != 0)
                accumulator.append(numberOfCoin[i] + "x" + denominations.get(i) + ", ");
        }
        accumulator.delete(accumulator.length() - 2, accumulator.length());


        return accumulator.toString();
    }

}
class Dynamic
{
    public Dynamic(List<Integer> denominations, int target)
    {

    }
}