package SSAFY.N3282;

/**
 * 정석적인 0/1 냅색
 * 
 * ---
 * N=100, K=1K
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, MAX_ITEM, K, MAX_KG;
    static int[][] dp;

    static {
        N = MAX_ITEM = 100; // 1based
        K = MAX_KG = 1000; // 1based
        dp = new int[MAX_ITEM + 1][MAX_KG + 1]; // [n][v] = c; int
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N3282/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            int maxValue = getValue();
            result.append(maxValue).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 냅색 문제 진행
     * 
     * @return
     * @throws IOException
     */
    static int getValue() throws IOException {
        int maxValue = 0;

        for (int item = 1; item <= MAX_ITEM; item++) {
            st = new StringTokenizer(br.readLine().trim());

            int itemKg = parseInt(st.nextToken());
            int itemValue = parseInt(st.nextToken());
            for (int kg = 1; kg <= MAX_KG; kg++) {
                int nextValue;
                if (kg < itemKg) {
                    nextValue = dp[item - 1][kg];
                } else {
                    nextValue = Math.max(
                        dp[item - 1][kg], // 안넣고 이미 kg
                        dp[item - 1][kg - itemKg] + itemValue); //지금 넣고 보니 kg
                }
                if(dp[item][kg] < nextValue){
                    dp[item][kg] = nextValue;
                }
            }
        }

        return dp[MAX_ITEM][MAX_KG];
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = MAX_ITEM = parseInt(st.nextToken());
        K = MAX_KG = parseInt(st.nextToken());
        for (int item = 1; item <= MAX_ITEM; item++) {
            Arrays.fill(dp[item], 0, MAX_KG + 1, 0);
        }
    }
}