package DP.N1915;

import static java.lang.Math.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, MAX;
	static int[][] dp;
	static char[][] mtx;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		dp = new int[N][M];
		mtx = new char[N][M];
		for (int n = 0; n < N; n++) {
			mtx[n] = br.readLine().toCharArray();
		}
		for (int n = 0; n < N; n++) {
			if (mtx[n][0] == '1') {
				dp[n][0] = 1;
				MAX = 1;
			}
		}
		for (int m = 0; m < M; m++) {
			if (mtx[0][m] == '1') {
				dp[0][m] = 1;
				MAX = 1;
			}
		}
		for (int n = 1; n < N; n++) {
			for (int m = 1; m < M; m++) {
				if (mtx[n][m] == '1') {
					dp[n][m] = min(min(dp[n - 1][m], dp[n][m - 1]), dp[n - 1][m - 1]) + 1;
					MAX = max(MAX, dp[n][m]);
				}
			}
		}
		System.out.println(MAX * MAX);
	}
}
