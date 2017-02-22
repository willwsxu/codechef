
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WXU
 */
class ChefTeams {
    Map<Integer,Integer> chefs = new TreeMap<>();
    List<Integer> diffs = new ArrayList<>();
    int i=0;
    
    int accumulate(int[] values, int start, int end)
    {
        int total=0;
        for (int i=start; i<end; i++)
            total += values[i];
        return total;
    }
    void addChef(int age, int rating)
    {
        chefs.put(age, rating);
        int ratings[]=new int[chefs.size()];
        i=0;
        chefs.forEach((k,v)->ratings[i++]=v);
        int mid= chefs.size()/2+chefs.size()%2;
        int rating1=accumulate(ratings, 0, mid);
        int rating2=accumulate(ratings, mid, chefs.size());
        int dif = rating1>rating2?rating1-rating2:rating2-rating1;
        //diffs.add(dif);
        out.println(dif);
    }
    
    public static void main(String[] args)
    {
        ChefTeams team = new ChefTeams();
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        for (int i=0; i<N; i++)
        {
            int age = scan.nextInt();
            int rating = scan.nextInt();
            team.addChef(age, rating);
        }
        /*
        team.addChef(2,3);
        team.addChef(1,7);
        team.addChef(5,5);
        team.addChef(3,1);
        team.addChef(8,15);
*/
        // output 3 4 5 4 9
    }
}
