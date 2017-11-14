
package acsl.c1;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agram {
    
    class Card
    {
        private String name;
        private int    value;
        private char   suit;
        Card(String n)
        {
            name=n;
            value=mapValue(n.charAt(0));
            suit = n.charAt(1);
        }
        int mapValue(char v) {
            switch (v) {
                case 'A':       return 1;
                case 'T':       return 10;
                case 'J':       return 11;
                case 'Q':       return 12;
                case 'K':       return 13;
                default:        return v-'0';
            }
        }
        int getValue() {
            return value;
        }
        char getSuit() {
            return suit;
        }
        String getName(){
            return name;
        }
    }
    
    Map<Character, List<Card>> dealer_suits=new HashMap<>();
    Agram(String cards[])
    {
        Card lead=new Card(cards[0]);               // lead card of opponenet
        List<Card> all_cards = new ArrayList<>();   // store all 5 cards from dealer
        for (int i=1; i<cards.length; i++) {
            Card c=new Card(cards[i]);              // object of dealer card
            all_cards.add(c);                       // add card to list
            List<Card> suit = dealer_suits.get(c.getSuit()); // find suit in map
            if (suit==null) {
                suit = new ArrayList<>();          // create suit list if this is first time
                dealer_suits.put(c.getSuit(), suit);// add suit list to map
            }
            suit.add(c); // add card to suit
        }
        List<Card> suit = dealer_suits.get(lead.getSuit());  // look for matching suit in dealer cards
        if (suit!=null) {  // find macthing suit
            Collections.sort(suit, (c1,c2)->c1.getValue()-c2.getValue());  // sort suit list by value
            for (int i=0; i<suit.size(); i++) {
                if (suit.get(i).getValue()>lead.getValue()) {
                    out.println(suit.get(i).getName());  // find the card with calue > lead
                    out.println("test case 1");
                    return;
                }
            }
            out.println("test case 2");
            out.println(suit.get(0).getName());  // did not find any card value > lead, pck the smallest
        } else {
            Collections.sort(all_cards, (c1,c2)->c1.getSuit()-c2.getSuit());  // sort by suit first, in case tie, pick suit in this order c, d, h, s
            Collections.sort(all_cards, (c1,c2)->c1.getValue()-c2.getValue());// then sort by vlue
            out.println(all_cards.get(0).getName());
            out.println("test case 3 and 4");
        }
    }
    public static void test()
    {
        // test case 1
        new Agram(new String[]{"5D", "2D", "6H", "9D", "TD", "6H"});  // 9D
        new Agram(new String[]{"TC", "AC", "KC", "QH", "JS", "TD"});  // KC
        // test case 2
        new Agram(new String[]{"3D", "4H", "5C", "6S", "2D", "7H"});  // 2D
    }
    public static void main(String[] args)
    {  
        test();
    }
}
