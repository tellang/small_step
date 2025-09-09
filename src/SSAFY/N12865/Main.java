package SSAFY.N12865;
/**
 * 정석 냅색
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int N, MAX_ITEM, K, MAX_WEIGHT;
    static int[][] knapsack;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N12865/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        init();
        knapsack();
        System.out.println(knapsack[MAX_ITEM][MAX_WEIGHT]); // 어짜피 MAX_WEIGHT 까지 포문 도니까
    }

    static void knapsack() throws IOException {
        for (int n = 1; n <= MAX_ITEM; n++) {
            st = new StringTokenizer(br.readLine());
            int weight = parseInt(st.nextToken());
            int val = parseInt(st.nextToken());
            for (int wi = 0; wi <= MAX_WEIGHT; wi++) {
                if (wi < weight)
                    knapsack[n][wi] = knapsack[n - 1][wi];
                else
                    knapsack[n][wi] = Math.max(
                            knapsack[n - 1][wi - weight] + val,
                            knapsack[n - 1][wi]
                            );
            }
        }
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        MAX_ITEM = N = parseInt(st.nextToken());
        MAX_WEIGHT = K = parseInt(st.nextToken());
        knapsack = new int[MAX_ITEM + 1][MAX_WEIGHT + 1];
    }

}