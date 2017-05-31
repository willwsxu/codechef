
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * Put Chef into 2 teams by age, team should be balanced at all times, young team will get the odd extra
 */

class ChefTeams2 {
    int rating1=0;
    int rating2=0;
    List<Integer> ageList = new LinkedList<>();
    List<Integer> ratingList = new LinkedList<>();   
    void addChef(int age, int rating)
    {
        int loc = Collections.binarySearch(ageList, age);
        if (loc>=0) {
            out.println("same age exist "+age);
            return;
        }
        //out.println(loc);
        loc = (-1)*loc-1;  // convert back from -loc-1=x
        ageList.add(loc, age);
        ratingList.add(loc, rating);
        int size = ageList.size();
        if (size%2==1) {  //odd number
            if (loc <= size/2 )  // new one is at first half
            {
                rating1 += rating;
            } else
            {
                int shift = ratingList.get(size/2); // middle moved from second to first group
                //out.println("odd middle "+shift);
                rating1 += shift;    
                rating2 += rating;  
                rating2 -= shift;                 
            }
        } else {  // even number
            if (loc < size/2 )  // new one is at first half
            {
                int shift = ratingList.get(size/2); // middle moved from second to second group 
                rating1 += rating;  
                rating1 -= shift;   
                rating2 += shift;    
                //out.println("even middle "+shift);
            } else {  
                rating2 += rating;                  
            }
        }
        //out.println(rating1+":"+rating2);
        int dif = rating1>rating2?rating1-rating2:rating2-rating1;
        out.println(dif);
    } 
}


class ChefTeams3 {
    class IntPair{
        int     key;    // age
        int     value;  // rating
        IntPair(int k, int v)
        {
            key=k;
            value=v;
        }
        int getKey() {
            return key;
        }
    }
    List<IntPair> youngTeam = new LinkedList<>();
    List<IntPair> oldTeam = new LinkedList<>();
    int rating1=0;
    int rating2=0;
    
    void insert(List<IntPair> team, int age, int rating)
    {
        IntPair pair = new IntPair(age, rating);
        Comparator<IntPair> cmp1=Comparator.comparing(IntPair::getKey);
        int loc = Collections.binarySearch(team, pair, cmp1);
        team.add((-1)*loc-1, pair);        
    }
    void addChef(int age, int rating)
    {
        if(youngTeam.isEmpty()) {
            youngTeam.add(new IntPair(age, rating));
            rating1 = rating;
        } else {
            int size1 = youngTeam.size();
            if (size1 == oldTeam.size()) {
                if ( age <oldTeam.get(0).key) { // insert to first half
                    insert(youngTeam, age, rating);
                    rating1 += rating;
                } else {
                    insert(oldTeam, age, rating);
                    int shift = oldTeam.get(0).value;
                    rating1 += shift;
                    rating2 -= shift;
                    rating2 += rating;
                    youngTeam.add(size1, oldTeam.get(0));
                    oldTeam.remove(0);
                    //out.println("even add. shift left "+shift);
                }
            } else {
                if ( age > youngTeam.get(size1-1).key) { // insert to second half
                    insert(oldTeam, age, rating);
                    rating2 += rating;
                } else {
                    insert(youngTeam, age, rating);
                    rating1 += rating;
                    int shift = youngTeam.get(size1).value;
                    rating1 -= shift;
                    rating2 += shift;
                    oldTeam.add(0, youngTeam.get(size1));
                    youngTeam.remove(size1);
                    //out.println("odd add. shift right "+shift);
                }
            }
        }
        //out.println(rating1+":"+rating2);
        int dif = rating1>rating2?rating1-rating2:rating2-rating1;
        out.println(dif);
    }
}
// copied from subArray
class IntPair  // pair of int
{
    int first;
    int second;
    IntPair(int f, int s)
    {
        first=f;
        second=s;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof IntPair) {
            IntPair other =(IntPair)s;
            return first==other.first && second==other.second;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(first*second);
    }
    @Override
    public String toString()
    {
        return first+":"+second;
    }
}

class ChefTeam4
{
    PriorityQueue<IntPair> yng=new PriorityQueue<>(50000, (p1,p2)->p2.first-p1.first); // max heap
    PriorityQueue<IntPair> old=new PriorityQueue<>(50000, (p1,p2)->p1.first-p2.first); // min heap
    int total[]=new int[2];
    
    int addChef(int age, int rating)
    {
        if (yng.size()<=old.size()) {
            IntPair p=old.peek();
            if (p!=null && p.first<age) {
                p=old.poll();
                old.add(new IntPair(age, rating));
                yng.add(p);
                total[1] += rating;
                total[1] -= p.second;
                total[0] += p.second;
            } else {
                yng.add(new IntPair(age, rating));    
                total[0] += rating;            
            }
        } else {  // add to old group
            IntPair p=yng.peek();
            if (p.first>age) {
                p=yng.poll();
                yng.add(new IntPair(age, rating));
                old.add(p);  
                total[0] += rating;   
                total[0] -= p.second;
                total[1] += p.second;         
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
    
    // output 3 4 5 4 9
    static void manualTest()
    {
        ChefTeams3 team = new ChefTeams3();
        team.addChef(2,3);
        team.addChef(1,7);
        team.addChef(5,5);
        team.addChef(3,1);
        team.addChef(8,15);  
        
        ChefTeam4 team4 = new ChefTeam4();
        out.println(team4.addChef(2,3));
        out.println(team4.addChef(1,7));
        out.println(team4.addChef(5,5));
        out.println(team4.addChef(3,1));
        out.println(team4.addChef(8,15));        
    }
    static void autoTest()
    {        
        ChefTeams2 team = new ChefTeams2();
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        for (int i=0; i<N; i++)
        {
            int age = scan.nextInt();
            int rating = scan.nextInt();
            team.addChef(age, rating);
        }
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        manualTest();
    }
}
