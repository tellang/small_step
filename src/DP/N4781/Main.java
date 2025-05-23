package DP.N4781;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, MAX;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] dp;

	public static void main(String[] args) throws IOException {
		StringBuilder result = new StringBuilder();
		while (true) {
			MAX = 0;
			st = new StringTokenizer(br.readLine());
			N = parseInt(st.nextToken());
			M = (int)((Double.parseDouble(st.nextToken()) * 100.0) + 0.5);
			if (N == 0)
				break;
			dp = new int[M + 1];
			dp[0] = 0;
			for (int n = 0; n < N; n++) { //5000
				st = new StringTokenizer(br.readLine());
				int cal = parseInt(st.nextToken());
				int cost = (int)((Double.parseDouble(st.nextToken()) * 100.0) + 0.5);
				for (int i = cost; i <= M; i++) { //10,000
					dp[i] = Math.max(dp[i], dp[i - cost] + cal);
					MAX = Math.max(dp[i], MAX);
				}
			}
			result.append(MAX).append("\n");
		}
		System.out.println(result);
	}
}
