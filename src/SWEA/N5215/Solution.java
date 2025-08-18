package SWEA.N5215;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

/*
 * 조합으로 푸는 버전
 * 근데 이제 1개 고르고 2개 고르고 ... N개 고르고
 * 1. 점수와 칼로리를 합산
 * 2. 제한 칼로리 초과 확인
 *  1-1. 미초과시 갱신시도
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, FOOD_COUNT, L, LIMIT, MAX_POINT;
    static Food[] foods, selectedFood;
    static Food zeroFood = new Food(0, 0);

    public static void main(String[] args) throws IOException {
        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            inputTestCase();
            for (int combCount = 0; combCount < FOOD_COUNT; combCount++) {
                selectedFood = new Food[combCount + 1];
                combination(0, combCount);
            }

            result.append(MAX_POINT).append('\n');
        }
        System.out.println(result);
    }

    private static void inputTestCase() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = parseInt(st.nextToken());
        L = parseInt(st.nextToken());
        FOOD_COUNT = N;
        LIMIT = L;
        foods = new Food[FOOD_COUNT];
        MAX_POINT = 0;

        for (int foodIdx = 0; foodIdx < FOOD_COUNT; foodIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            int point = parseInt(st.nextToken());
            int cal = parseInt(st.nextToken());
            foods[foodIdx] = new Food(point, cal);
        }
    }

    private static void combination(int originalFoodIdx, int selectedFoodIdx) {
        if (selectedFoodIdx == selectedFood.length) {
            int localSumCal = 0, localSumPoint = 0;

            for (Food food : selectedFood) {
                localSumCal += food.cal;
                localSumPoint += food.point;
            }
            if (localSumCal <= LIMIT) {
                MAX_POINT = Math.max(MAX_POINT, localSumPoint);
            }
            return;
        }

        if (originalFoodIdx == FOOD_COUNT) {
            return;
        }

        selectedFood[selectedFoodIdx] = foods[originalFoodIdx];
        combination(originalFoodIdx + 1, selectedFoodIdx + 1);

        selectedFood[selectedFoodIdx] = zeroFood;
        combination(originalFoodIdx + 1, selectedFoodIdx);
    }

}

class Food {

    int point, cal;

    public Food(int point, int cal) {
        this.point = point;
        this.cal = cal;
    }
}
