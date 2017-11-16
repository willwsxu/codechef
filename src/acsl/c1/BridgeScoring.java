
package acsl.c1;

import static java.lang.System.out;
import java.util.Scanner;

// BridgeScoring class creates 2 team objects, provide play method to process input
// Team class stores state of team so points can be accumulated.
// Team also contain opponent  to calculate penalty.
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
        
        // check if bidding team won the game
        // if so, caculate underline points, and overline points if ther is over trick
        // if lost, update opponent overline points, with proper vulnerable
        void game(int bid, int tricks, char suit) {
            if (tricks>=bid+6) {  // bidding team won
                underline_points += score(bid, suit, true);
                if (tricks>bid+6)  // if there are trick over the line
                    overline_points += score(tricks-bid-6, suit, false);
                //out.println("won "+underline_points+" bid "+bid+suit+" trick above " +(tricks-bid-6));
            } else {  // bidding team lose game, award other team over the line points
                other.update_penalty(bid+6-tricks, underline_points>0);  // vunerable if bidding team ever won underline points
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
        String getPoints()
        {
            return underline_points+","+overline_points;
        }
    }
    
    Team teams[]=new Team[2];
    BridgeScoring()
    {
        teams[0]=new Team();  // create two teams at start
        teams[1]=new Team();
        teams[0].setOpponent(teams[1]);
        teams[1].setOpponent(teams[0]);
    }
    // parse input fields, 3 int, one char
    // calculate points
    // print current cumulative points of boh teams
    // reset if bidding won
    void play(String game_info[])
    {
        int p=Integer.parseInt(game_info[0]);   // team 1 or 2
        int bid=Integer.parseInt(game_info[1]); // # of bids
        int tricks=Integer.parseInt(game_info[2]);// total tricks won by bidding team
        teams[p-1].game(bid, tricks, game_info[3].charAt(0));  // set score, cumulative
        out.println(teams[0].getPoints()+","+teams[1].getPoints());
        if (teams[p-1].won())   // reset underline points of both teams after a match
            teams[p-1].matchReset();
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
