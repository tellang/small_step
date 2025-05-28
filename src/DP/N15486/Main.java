package DP.N15486;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, MAX = 0;
	static int[] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		dp = new int[N + 2];
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			int wageDay = n + t - 1;
			dp[n] = Math.max(dp[n], dp[n - 1]);
			if (wageDay > N)
				continue;

			dp[wageDay] = Math.max(p + dp[n - 1], dp[wageDay]);
			MAX = Math.max(MAX, dp[wageDay]);
		}
		System.out.println(MAX);
	}
}
