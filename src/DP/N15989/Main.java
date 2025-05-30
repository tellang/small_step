package DP.N15989;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int N, MAX;
	static int[] dp;
	static int[] pre;
	static int[] targets = {1, 2, 3};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		pre = new int[N];
		for (int n = 0; n < N; n++) {
			int num = Integer.parseInt(br.readLine());
			pre[n] = num;
			MAX = Math.max(pre[n], MAX);
		}
		dp = new int[MAX + 1];
		dp[0] = 1;
		for (int t : targets) {
			for (int i = t; i <= MAX; i++) {
				dp[i] += dp[i - t];
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i : pre) {
			sb.append(dp[i]).append("\n");
		}
		System.out.println(sb);
	}
}
