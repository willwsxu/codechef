
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
        ChefTeams2 team = new ChefTeams2();
        team.addChef(2,3);
        team.addChef(1,7);
        team.addChef(5,5);
        team.addChef(3,1);
        team.addChef(8,15);        
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
    public static void main(String[] args)
    {
        autoTest();
    }
}
