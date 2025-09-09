package SSAFY.N9084;

/**
 * int[] dp[price] = count
 * 중복이 가능하므로 오름차순
 * 
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, MAX_COIN, M, TANGO_PRICE;
    static int[] kindCount, coins;

    static {
        N = MAX_COIN = 20;
        M = TANGO_PRICE = 10000;
        kindCount = new int[TANGO_PRICE + 1];
        coins = new int[MAX_COIN];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N9084/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());
        for (int tc = 0; tc < T; tc++) {
            init();
            findKind();
            result.append(kindCount[TANGO_PRICE]).append('\n');
        }
        System.out.println(result);
    }

    static void findKind() {

        for (int cIdx = 0; cIdx < MAX_COIN; cIdx++) {
            int coin = coins[cIdx];
            kindCount[coin]++;
            for (int price = coin; price <= TANGO_PRICE; price++) {
                if (kindCount[price - coin] > 0) {
                    kindCount[price] += kindCount[price - coin];
                }
            }
        }
    }

    static void init() throws NumberFormatException, IOException {
        N = MAX_COIN = parseInt(br.readLine().trim());
        st = new StringTokenizer(br.readLine().trim());
        for (int cIdx = 0; cIdx < MAX_COIN; cIdx++) {
            coins[cIdx] = parseInt(st.nextToken());
        }
        M = TANGO_PRICE = parseInt(br.readLine().trim());
        Arrays.fill(kindCount, 0, TANGO_PRICE + 1, 0);
    }
}