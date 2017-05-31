
import codechef.IntPair;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Put Chefs into 2 teams by age, team should be balanced at all times, young team will get the odd extra
 * Fast Technique, PriorityQueue, min heap old team and max Heap for young team
 * Second Fast, TreeSet, sorted by age
 */

// CTEAMS EASY
class ChefTeams3 {
    TreeSet<IntPair> youngTeam = new TreeSet<>((p1,p2)->p1.int1()-p2.int1());
    TreeSet<IntPair> oldTeam   = new TreeSet<>((p1,p2)->p1.int1()-p2.int1());
    int rating1=0;
    int rating2=0;
    
    int addChef(int age, int rating)
    {
        if(youngTeam.size()<=oldTeam.size()) {
                if ( oldTeam.isEmpty() || age <oldTeam.first().int1()) { // insert to first half
                    youngTeam.add(new IntPair(age, rating));
                    rating1 += rating;
                } else {
                    oldTeam.add(new IntPair(age, rating));
                    IntPair p = oldTeam.pollFirst();
                    youngTeam.add(p);
                    rating1 += p.int2();
                    rating2 -= p.int2();
                    rating2 += rating;
                    //out.println("even add. shift left "+shift);
                }
            } else {
                if ( age > youngTeam.last().int1()) { // insert to second half
                    oldTeam.add(new IntPair(age, rating));
                    rating2 += rating;
                } else {
                    youngTeam.add(new IntPair(age, rating));
                    IntPair p = youngTeam.pollLast();
                    oldTeam.add(p);
                    rating1 += rating;
                    rating1 -= p.int2();
                    rating2 += p.int2();
                    //out.println("odd add. shift right "+shift);
                }
            }
        //out.println(rating1+":"+rating2);
        return rating1>rating2?rating1-rating2:rating2-rating1;
    }
}

class ChefTeam4
{
    PriorityQueue<IntPair> yng=new PriorityQueue<>(50000, (p1,p2)->p2.int1()-p1.int1()); // max heap
    PriorityQueue<IntPair> old=new PriorityQueue<>(50000, (p1,p2)->p1.int1()-p2.int1()); // min heap
    int total[]=new int[2];
    
    int addChef(int age, int rating)
    {
        if (yng.size()<=old.size()) {
            IntPair p=old.peek();
            if (p!=null && p.int1()<age) {
                p=old.poll();
                old.add(new IntPair(age, rating));
                yng.add(p);
                total[1] += rating;
                total[1] -= p.int2();
                total[0] += p.int2();
            } else {
                yng.add(new IntPair(age, rating));    
                total[0] += rating;            
            }
        } else {  // add to old group
            IntPair p=yng.peek();
            if (p.int1()>age) {
                p=yng.poll();
                yng.add(new IntPair(age, rating));
                old.add(p);  
                total[0] += rating;   
                total[0] -= p.int2();
                total[1] += p.int2();         
            } else {
                old.add(new IntPair(age, rating));   
                total[1] += rating;             
            }
        }
        int diff=total[0]-total[1];
        return diff>0?diff:-diff;
    }
}

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
    void addChef(int age, int rating) // brute force
    {
        chefs.put(age, rating);
        int ratings[]=new int[chefs.size()];
        i=0;
        chefs.forEach((k,v)->ratings[i++]=v);
        int mid= chefs.size()/2+chefs.size()%2;
        int rating1=accumulate(ratings, 0, mid);
        int rating2=accumulate(ratings, mid, chefs.size());
        int dif = rating1>rating2?rating1-rating2:rating2-rating1;
        out.println(dif);
    }
    
    // output 3 4 5 4 9
    static void manualTest()
    {
        ChefTeams3 team = new ChefTeams3();
        out.println(team.addChef(2,3));
        out.println(team.addChef(1,7));
        out.println(team.addChef(5,5));
        out.println(team.addChef(3,1));
        out.println(team.addChef(8,15));  
        
        ChefTeam4 team4 = new ChefTeam4();
        out.println(team4.addChef(2,3));
        out.println(team4.addChef(1,7));
        out.println(team4.addChef(5,5));
        out.println(team4.addChef(3,1));
        out.println(team4.addChef(8,15));        
    }
    static void autoTest()
    {        
        ChefTeams3 team = new ChefTeams3();
        StringBuilder sb=new StringBuilder();
        int N = sc.nextInt();          //1 <= N <= 10^5
        for (int i=0; i<N; i++)
        {
            int age = sc.nextInt();    // 1 <= Ai <= 10^9
            int rating = sc.nextInt(); // 1 <= Ri <= 1000
            sb.append(team.addChef(age, rating));
            sb.append("\n");
        }
        out.print(sb.toString());
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        manualTest();
    }
}
