package SSAFY.N5215;

/**
 * dp(n_k, l_j) : A[N_igredients][L_Kcal] = 점수
 * dp(n_k, l_j) = max( dp(n_k - 1, l_j), dp(n_k - 1, l_j - l_cur) + p_cur )

 * T < 1k
 * --- 
 * if (k < cur.kcal) { <- 상태 전이 꼭 하자
        dp[ingre][k] = dp[ingre - 1][k];
        continue;
    }
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, INGREDIENT_MAX, L, KCAL_LIMIT;

    static int[][] dp; // 1based
    static Ingredient[] ingredients; // 1based

    static {
        N = INGREDIENT_MAX = 20;
        L = KCAL_LIMIT = 10000;
        ingredients = new Ingredient[INGREDIENT_MAX + 1];
        dp = new int[INGREDIENT_MAX + 1][KCAL_LIMIT + 1];
    }

    static class Ingredient {
        int point, kcal;

        public Ingredient(int point, int kcal) {
            this.point = point;
            this.kcal = kcal;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5215/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            int maxPoint = knapsackLikeDP();
            // LocalUtill.PrintHelper.Map.Number.print(dp, INGREDIENT_MAX + 1, KCAL_LIMIT +
            // 1);
            result.append(maxPoint).append('\n');
        }
        System.out.println(result);
    }

    static int knapsackLikeDP() {
        int maxPoint = 0;
        for (int ingre = 1; ingre <= INGREDIENT_MAX; ingre++) {
            Ingredient cur = ingredients[ingre];
            for (int k = 0; k <= KCAL_LIMIT; k++) {
                if (k < cur.kcal) {
                    dp[ingre][k] = dp[ingre - 1][k];
                    continue;
                }
                dp[ingre][k] = Math.max(
                        dp[ingre - 1][k], // 이번거 안먹었지만 이미 k 칼로리
                        dp[ingre - 1][k - cur.kcal] + cur.point // 이번거를 먹고 k 칼로리
                );
                maxPoint = Math.max(dp[ingre][k], maxPoint);
            }
        }

        return maxPoint;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = INGREDIENT_MAX = parseInt(st.nextToken());
        L = KCAL_LIMIT = parseInt(st.nextToken());

        for (int igre = 1; igre <= INGREDIENT_MAX; igre++) {
            st = new StringTokenizer(br.readLine().trim());
            int point = parseInt(st.nextToken());
            int kcal = parseInt(st.nextToken());
            ingredients[igre] = new Ingredient(point, kcal);

            Arrays.fill(dp[igre], 0, KCAL_LIMIT + 1, 0);
        }

        /**
         * 가중치 순으로 정렬
         */
        Arrays.sort(ingredients, 1, INGREDIENT_MAX + 1, Comparator.comparingDouble(i -> i.kcal / i.point));
    }
}