package SWEA.N5215.subset;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();
	static int T, N, FOOD_COUNT, L, LIMIT, MAX_POINT;
	static Food[] foods;

	public static void main(String[] args) throws IOException {
		T = parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			result.append('#').append(tc).append(' ');

			inputTestCase();

			// 모든 재료에 대해 선택/미선택을 결정하는 재귀 호출 시작
			combination(0, 0, 0);

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

	/**
	 * 재귀를 통해 각 재료를 선택하거나 선택하지 않는 경우를 모두 탐색합니다.
	 *
	 * @param foodIdx 현재 고려하고 있는 재료의 인덱스
	 * @param currentCal 현재까지 선택한 재료들의 총 칼로리
	 * @param currentPoint 현재까지 선택한 재료들의 총 점수
	 */
	private static void combination(int foodIdx, int currentCal, int currentPoint) {
		// 현재 칼로리가 제한을 초과하면 탐색 중단
		if (currentCal > LIMIT) {
			return;
		}

		// 모든 재료를 다 고려했을 경우
		if (foodIdx == FOOD_COUNT) {
			MAX_POINT = Math.max(MAX_POINT, currentPoint);
			return;
		}

		// 현재 재료를 선택하는 경우
		combination(foodIdx + 1, currentCal + foods[foodIdx].cal, currentPoint + foods[foodIdx].point);

		// 현재 재료를 선택하지 않는 경우
		combination(foodIdx + 1, currentCal, currentPoint);
	}
}

class Food {
	int point, cal;

	public Food(int point, int cal) {
		this.point = point;
		this.cal = cal;
	}
}