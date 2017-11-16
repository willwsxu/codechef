
package acsl.c1;

import static java.lang.System.out;
import java.util.Scanner;

public class BridgeScoring {
    class Player
    {
        int overline_points;
        int underline_points;
        Player  other;
        
        int score(int bid, char suit, boolean under) {
            switch (suit) {
                case 'H':
                case 'S':
                    return bid*30;
                case 'C':
                case 'D':
                    return bid*20;
                case 'T':
                    int s = bid*30;
                    return under?s+10:s;
            }
            return 0;  // never should happen
        }
        void game(int bid, int tricks, char suit) {
            if (tricks>=bid+6) {
                underline_points += score(bid, suit, true);
                if (tricks>bid+6)
                    overline_points += score(tricks-bid-6, suit, false);
                //out.println("won "+underline_points+" bid "+bid+suit+" trick above " +(tricks-bid-6));
            } else {
                other.update_penalty(bid+6-tricks, underline_points>0);
            }                
        }
        //The penalty differs depending upon whether the bidding team has won a game in that match or not
        // Not vulnerable means the team has not won a game in that match
        void update_penalty(int p, boolean vulnerable) {
            overline_points += p*(vulnerable?100:50); // Vulnerable, 100 points per undertrick
            //out.println("penalty "+overline_points+" vulnerable="+vulnerable); //
        }
        void setOpponent(Player p)
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
    
    Player players[]=new Player[2];
    BridgeScoring()
    {
        players[0]=new Player();
        players[1]=new Player();
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
    }
    void play(String game_info[])
    {
        int p=Integer.parseInt(game_info[0]);
        int bid=Integer.parseInt(game_info[1]);
        int tricks=Integer.parseInt(game_info[2]);
        players[p-1].game(bid, tricks, game_info[3].charAt(0));
        out.println(players[0].underline_points+","+players[0].overline_points+","+players[1].underline_points+","+players[1].overline_points);
        if (players[p-1].won())
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
        test();
    }
}
