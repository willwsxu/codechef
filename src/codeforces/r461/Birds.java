package codeforces.r461;

/*
 * n tree with a nest, each nest has Ci birds. To summon one bird he needs to stay under and incur costi
 *  However his mana capacity will increase by B for each bird summoned
 * Initial he stands under first tree, has W mana, which is also his capacity.
 * He only go forward, each time he restores X points of mana when moving to next tree, but not exceeding his capacity
 */

// not finished
public class Birds {
    
    int mana;  //0 ≤ W, B, X ≤ 10^9
    int capacity;
    int trees;  // 1 ≤ n ≤ 10^3
    int capacityInc;    // B
    int restoreMana; // X
    int dp[][];
    int c[];        // birds in next
    int cost[];     // summon cost
    Birds(int n, int W, int B, int X)
    {
        trees=n;
        mana=W;
        capacity=W;
        capacityInc = B;
        restoreMana=X;
        dp = new int[n][10001];
        dp[0][0]=W;
    }
    void bottomUp()
    {
        for (int i=0; i<trees; i++) {
            for (int j=0; j<=c[i]; j++) {
                
            }
        }
    }
}
