package SWEA.N5215;

import java.io.*;
import java.util.StringTokenizer;

/**
 * SWEA 5215 햄버거 다이어트
 * 1. 테스트 케이스 개수를 입력받는다.
 * 2. 각 테스트 케이스마다,
 * 2-1. 재료의 개수와 제한 칼로리를 입력받는다.
 * 2-2. 재료의 개수만큼, 각 재료의 점수와 칼로리를 입력받는다.
 * 
 * 3. 조합을 구현한다.
 * 3-1-1. 점수와 칼로리를 합산한다.
 * 3-1-2. 제한 칼로리를 넘어가는지 확인하고,
 * 3-1-2-1. 칼로리를 넘어가지 않으면, 점수가 더 높으면 갱신.
 * 4. 가장 높은 점수를 출력한다.
 */

public class Solution {

    static StringTokenizer st;
    static BufferedReader br;
    static StringBuilder sb;

    static int foodCount, limitCalories;
    static int[][] foodInfoArray;
    static int[][] selectedFoodArray;

    static final int SCORE = 0, CALORIE = 1;
    static int bestScore = 0;

    public static void inputTestCase() throws IOException {
        // 2-1. 재료의 개수와 제한 칼로리를 입력받는다.
        st = new StringTokenizer(br.readLine().trim());
        foodCount = Integer.parseInt(st.nextToken());
        limitCalories = Integer.parseInt(st.nextToken());

        // 2-2. 재료의 개수만큼, 각 재료의 점수와 칼로리를 입력받는다.
        foodInfoArray = new int[foodCount][2];
        bestScore = 0;
        for (int foodIndex = 0; foodIndex < foodCount; foodIndex++) {
            st = new StringTokenizer(br.readLine());
            foodInfoArray[foodIndex][SCORE] = Integer.parseInt(st.nextToken());
            foodInfoArray[foodIndex][CALORIE] = Integer.parseInt(st.nextToken());
        }
    }

    public static void combination(int elementIndex, int selectIndex) {
        // 3-1-1. 점수와 칼로리를 합산한다.
        if (selectIndex == selectedFoodArray.length) {
            int tmpSumScore = 0;
            int tmpSumCalorie = 0;

            for (int index = 0; index < selectedFoodArray.length; index++) {
                tmpSumCalorie += selectedFoodArray[index][CALORIE];
                tmpSumScore += selectedFoodArray[index][SCORE];
            }

            if (tmpSumCalorie <= limitCalories) {
                // 3-1-2. 제한 칼로리를 넘어가는지 확인하고,
                if(tmpSumScore > bestScore) {
                    // 3-1-2-1. 칼로리를 넘어가지 않으면, 점수가 더 높으면 갱신.
                    bestScore = tmpSumScore;
                }
            }
            return;
        }
        if (elementIndex == foodCount) {
            return;
        }

        selectedFoodArray[selectIndex][SCORE] = foodInfoArray[elementIndex][SCORE];
        selectedFoodArray[selectIndex][CALORIE] = foodInfoArray[elementIndex][CALORIE];

        combination(elementIndex + 1, selectIndex + 1);

        selectedFoodArray[selectIndex][SCORE] = -1;
        selectedFoodArray[selectIndex][CALORIE] = -1;

        combination(elementIndex + 1, selectIndex);
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        // 1. 테스트 케이스 개수를 입력받는다.
        int testCase = Integer.parseInt(br.readLine().trim());

        for (int t = 1; t <= testCase; t++) {
            inputTestCase();
            // 3. 조합을 구현한다.

            for (int combCount = 1; combCount <= foodCount; combCount++) {
                selectedFoodArray = new int[combCount][2];
                combination(0, 0);
            }
            // 4. 가장 높은 점수를 출력한다.

            sb.append("#").append(t).append(" ").append(bestScore).append("\n");
        }
        System.out.println(sb);
    }
}
