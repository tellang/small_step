package SWEA.N1959;
/*
10*20*20 = 4K
완전탐색
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N, M, T, MAX, LOCAL_MAX;
	static int[] A, B, S, L;
	static StringBuilder result = new StringBuilder();

	public static void main(String args[]) throws Exception {
		T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			result.append('#').append(test_case).append(' ');
			MAX = 0;
			LOCAL_MAX = 0;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			A = new int[N];
			B = new int[M];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++) {
				B[i] = Integer.parseInt(st.nextToken());
			}
			if (N > M) {
				L = A;
				S = B;
			} else {
				L = B;
				S = A;
			}
			for (int l = 0; l <= L.length - S.length; l++) {
				for (int s = 0; s < S.length; s++) {
					LOCAL_MAX += (S[s] * L[s + l]);
				}
				MAX = Math.max(LOCAL_MAX, MAX);
				LOCAL_MAX = 0;
			}
			result.append(MAX).append('\n');
		}
		System.out.println(result);
	}
}