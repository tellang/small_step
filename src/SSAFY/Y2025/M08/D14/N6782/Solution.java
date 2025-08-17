package SSAFY.Y2025.M08.D14.N6782;
/**
 * 어짜피 1M 이니까
 * 미리 다 만들어 놓으면
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

	static BufferedReader br;
	static StringBuilder result = new StringBuilder();

	static int T, ROOT_MAX = 1_000_000;
	static long N, count;
	// 제곱수들을 미리 계산하여 저장하는 배열
	static long[] getPow;

	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("src/SSAFY.Y2025.M08.D14.N6782/sample_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));

		T = Integer.parseInt(br.readLine().trim());
		getPow = new long[ROOT_MAX + 1];
		for (int root = 2; root <= ROOT_MAX; root++) {
			getPow[root] = (long)root * root;
		}
		for (int tc = 1; tc <= T; tc++) {
			result.append('#').append(tc).append(' ');
			N = Long.parseLong(br.readLine().trim());
			count = 0;
			while (N != 2) {
				int nextPowIdx = Arrays.binarySearch(getPow, 0, getPow.length, N);
				if (nextPowIdx < 0) {
					nextPowIdx = (~nextPowIdx);
					count += getPow[nextPowIdx] - N;
					N = getPow[nextPowIdx];
				} else {
					count++;
					N = nextPowIdx;
				}

			}
			result.append(count).append('\n');
		}
		System.out.println(result);
	}
}
