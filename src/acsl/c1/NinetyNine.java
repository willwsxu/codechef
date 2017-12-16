package acsl.c1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Annie
 */
public class NinetyNine {
    
    int total = 0;
    class Player{
        int hand[] = new int[5];
        int pile[] = new int [5];
        Player(String n[], String y[]){            
            for (int i = 0; i<n.length; i++){
                hand[i]=mapValue(n[i]);
            }            
            for (int i = 0; i<y.length; i++){
                pile[i]=mapValue(y[i]);
            }            
        }
        
        int mapValue(String v) {  // convert card value to int
            switch (v) {
                case "A":       return 14;
                case "T":       return 10;
                case "J":       return 11;
                case "Q":       return 12;
                case "K":       return 13;
                default:        return Integer.parseInt(v);  
            }        
        }        
        
        int getCard(){
            Arrays.sort(hand);
            return hand[2];
        }  
        
        void drawNewCard(int m){
            hand[2] = pile[m];
        }
    }     
    
    public void calcTotalPoints(int card){
        int total1 = calcPoints(card);
        out.println("card "+card+" total="+total1);
        crossBorder(total1);
    }        
        
    public int calcPoints(int card){
        int total1 = total;
        if (card == 9){
            return total1;
        }
        else if (card == 10){
            total1 -= 10;
        }
        else if (card == 7){
            if (card+total1 >99){
                total1 += 1;
            }
            else{
                total1 +=7;
            }
        }
        else{
            total1 += card;
        }    
        return total1;
    }

    public void crossBorder(int total1){                //total1 is new total, total is old total
        if ((total <= 33 && total1 >33) || (total <= 55 && total1 >55) || (total <= 77 && total1>77)){
            out.println("cross total="+total+" new total="+total1);
            total1 += 5;
        }
        if ((total >= 34 && total1 <34) || (total >= 56 && total1 <56) || (total >= 78 && total1 <78)){
            out.println("cross back total="+total+" new total="+total1);
            total1 += 5;
        }      
        total = total1;
    }
    
    NinetyNine(String deal[], String input[]){
        String p1d[] = new String[5];
        String p2d[] = new String[5];
        String p1pile[] = new String[5];
        String p2pile[] = new String[5];
        for (int i = 0; i<5; i++){
            p1d[i] = deal[i];            
        }
        for (int i = 0; i<5; i++){
            p2d[i] = deal[i+5];            
        }   
        for (int i = 0; i<5; i++){
            p1pile[i] = input[2*i+1];
        }
        for (int i = 0; i<5; i++){            
            p2pile[i] = input[2*i+2];  
        }
        Player player1 = new Player(p1d, p1pile);
        Player player2 = new Player(p2d, p2pile);
        total = Integer.parseInt(input[0]);    
        for (int j = 0; j<6; j++){
            int card1 = player1.getCard();
            calcTotalPoints(card1);
            if (total > 99){
                System.out.println(total + ", Player #2");
                out.println("loop "+j);
                break;
            }            
            player1.drawNewCard(j);
            int card2 = player2.getCard();
            calcTotalPoints(card2);
            if (total > 99){
                System.out.println(total + ", Player #1");
                out.println("loop "+j);
                break;
            }
            player2.drawNewCard(j);
        }
    }    
    
    public static void test(){
        new NinetyNine(new String[]{"8", "9", "Q", "6", "7", "K", "A", "5", "9", "8"}, new String[]{"79", "A", "9", "7", "T", "A", "9", "T", "A", "6", "4"});
    }
    
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {          
     //   test();
        String s=sc.nextLine();
        String[] h = s.split("\\s*[,\n]\\s*");
        int T=5;  // 5 lines of input
        while(T-->0) {
            String input[]=new String[11];
            for (int i=0; i<input.length; i++)
                input[i]=sc.next();
            new NinetyNine(h, input);
        }        
    }
}
