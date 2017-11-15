
package acsl.c1;

public class BridgeScoring {
    class Player
    {
        int overline_points;
        int underline_points;
        boolean vulnerable;
        int penalty;
        Player  other;
        
        int score(int bid, char suit, boolean under) {
            switch (suit) {
                case 'H':
                case 'S':
                    return bid*30;
                case 'C':
                case 'D':
                    return bid*40;
                case 'T':
                    int s = bid*30;
                    return under?s+10:1;
            }
            return 0;  // never should happen
        }
        void game(int bid, int tricks, char suit) {
            if (tricks>=bid+6) {
                underline_points += score(bid, suit, true);
                if (tricks>bid+6)
                    overline_points += score(tricks-bid-6, suit, false);
            } else {
                other.update_penalty(bid+6-tricks);
            }                
        }
        void update_penalty(int p) {
            penalty += p;
        }
    }
}
