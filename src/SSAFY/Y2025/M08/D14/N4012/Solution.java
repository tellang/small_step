package SSAFY.Y2025.M08.D14.N4012;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 넣고 빼는 백트래킹
 * N = 16
 * 2^16 x 2^8 = 2^24 = 1048576 x 4 = 4M
 */
public class Solution {

	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();

	static int T, N, INGRE_MAX_COUNT, minIngreSum;
	static int[][] valMap;

	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));

		T = parseInt(br.readLine().trim());
		for (int tc = 1; tc <= T; tc++) {
			result.append('#').append(tc).append(' ');

			N = parseInt(br.readLine().trim());
			INGRE_MAX_COUNT = N >> 1;
			valMap = new int[N][N];
			minIngreSum = MAX_VALUE;

			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int x = 0; x < N; x++) {
					valMap[y][x] = parseInt(st.nextToken());
				}
			}

			combination(0, 0, 0);
			result.append(minIngreSum).append('\n');
		}
		System.out.print(result);
	}

	/**
	 * N/2개의 재료 조합 생성
	 */
	static void combination(int startIdx, int selectedCount, int ingreMask) {
		if (selectedCount == INGRE_MAX_COUNT) {
			int ingreDiff = getIngreDiff(ingreMask);
			minIngreSum = Math.min(minIngreSum, ingreDiff);
			return;
		}
		if (startIdx == N) {
			return;
		}
		combination(startIdx + 1, selectedCount + 1, ingreMask | (1 << startIdx));
		combination(startIdx + 1, selectedCount, ingreMask);
	}

	/**
	 * 시너지 차이 계산
	 */
	static int getIngreDiff(int ingreMask) {
		int ingreSumT = 0, ingreSumF = 0;

		for (int mainIdx = 0; mainIdx < N; mainIdx++) {
			for (int subIdx = 0; subIdx < N; subIdx++) {
				boolean isMainInT = (ingreMask & (1 << mainIdx)) != 0;
				boolean isSubInT = (ingreMask & (1 << subIdx)) != 0;

				if (isMainInT && isSubInT) {
					ingreSumT += valMap[mainIdx][subIdx];
				} else if (!isMainInT && !isSubInT) {
					ingreSumF += valMap[mainIdx][subIdx];
				}
			}
		}
		return Math.abs(ingreSumT - ingreSumF);
	}
}