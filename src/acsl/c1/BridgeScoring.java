
package acsl.c1;

import static java.lang.System.out;
import java.util.Scanner;

public class BridgeScoring {
    class Team
    {
        int overline_points;
        int underline_points;
        Team  other;
        
        // score rule, trickWon under line or above line
        int score(int trickWon, char suit, boolean under) {
            switch (suit) {
                case 'H':
                case 'S':
                    return trickWon*30;
                case 'C':
                case 'D':
                    return trickWon*20;
                case 'T':
                    int s = trickWon*30;  // simplify rule to use 30 as base points per trick
                    return under?s+10:s;  // add 10 poinrs if underline
            }
            return 0;  // never should happen
        }
        
        void game(int bid, int tricks, char suit) {
            if (tricks>=bid+6) {  // bidding team won
                underline_points += score(bid, suit, true);
                if (tricks>bid+6)  // if there are trick over the line
                    overline_points += score(tricks-bid-6, suit, false);
                //out.println("won "+underline_points+" bid "+bid+suit+" trick above " +(tricks-bid-6));
            } else {  // bidding team lose game, award other team over the line points
                other.update_penalty(bid+6-tricks, underline_points>0);
            }                
        }
        //The penalty differs depending upon whether the bidding team has won a game in that match or not
        // Not vulnerable means the team has not won a game in that match
        void update_penalty(int p, boolean vulnerable) {
            overline_points += p*(vulnerable?100:50); // Vulnerable, 100 points per undertrick
            //out.println("penalty "+overline_points+" vulnerable="+vulnerable); //
        }
        void setOpponent(Team p)
        {
            other=p;
        }
        boolean won() {
            return underline_points>=100;
        }
        void matchReset()
        {
            underline_points=0;
            other.underline_points=0;
        }
    }
    
    Team players[]=new Team[2];
    BridgeScoring()
    {
        players[0]=new Team();  // create two players at start
        players[1]=new Team();
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
    }
    void play(String game_info[])
    {
        int p=Integer.parseInt(game_info[0]);   // player 1 or 2
        int bid=Integer.parseInt(game_info[1]); // # of bids
        int tricks=Integer.parseInt(game_info[2]);// total tricks won by bidding team
        players[p-1].game(bid, tricks, game_info[3].charAt(0));  // set score, cumulative
        out.println(players[0].underline_points+","+players[0].overline_points+","+players[1].underline_points+","+players[1].overline_points);
        if (players[p-1].won())   // reset underline points of both teams after a match
            players[p-1].matchReset();
    }
    public static void test()
    {
        BridgeScoring bridge=new BridgeScoring();
        bridge.play(new String[]{"1", "2", "8","C"});
        bridge.play(new String[]{"1", "1", "9","H"});
        bridge.play(new String[]{"2", "4", "10","H"});
        bridge.play(new String[]{"2", "2", "11","T"});
        bridge.play(new String[]{"2", "3", "6","S"});
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {
        BridgeScoring bridge=new BridgeScoring();
        for (int i=0; i<5; i++) {
            bridge.play(sc.nextLine().split("\\s*[,\n]\\s*"));
        }
    }
}
