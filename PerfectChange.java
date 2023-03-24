import java.util.List;
import java.util.ArrayList;

public class PerfectChange
{
    class Greedy
    {
        private List<Integer> denominations;
        private int target;

        public Greedy(List<Integer> denominations, int target)
        {
            this.denominations = denominations;
            this.target = target;
        }

        public boolean isGreedy()
        {
            denominations
        }

        public String toString()
        {

        }

    }

    class Dynamic
    {

    }

    public static void main(String[] args)
    {
        List<Integer> denominations = new ArrayList<>();
        int target;

        for(int i = 0; i < args.length; i++)
        {
            if(args[i] != 1)
            {
                denominations.add(Integer.parseInt(args[i]))
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