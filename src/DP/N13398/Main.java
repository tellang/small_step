package DP.N13398;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, NON = 0, DELETED = 1, MAX = -10_000;
	static int[][] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		dp = new int[N + 1][2];
		st = new StringTokenizer(br.readLine());

		int init = Integer.parseInt(st.nextToken());
		MAX = init;
		dp[1][NON] = init;
		dp[1][DELETED] = 0;
		for (int n = 2; n <= N; n++) {
			int number = Integer.parseInt(st.nextToken());
			dp[n][NON] = Math.max(dp[n - 1][NON] + number, number);
			dp[n][DELETED] = Math.max(dp[n - 1][NON], dp[n - 1][DELETED] + number);

			MAX = Math.max(dp[n][NON], Math.max(dp[n][DELETED], MAX));
		}

		System.out.println(MAX);
	}
}
