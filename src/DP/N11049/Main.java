package DP.N11049;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Integer> p = new ArrayList<>();
	static int[][] dp; // s ~ k 까지의 최소

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		p.add(parseInt(st.nextToken()));
		p.add(parseInt(st.nextToken()));

		for (int n = 1; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken();
			p.add(parseInt(st.nextToken()));
		}
		dp = new int[N + 1][N + 1];
		for (int len = 2; len <= N; len++) {
			for (int s = 1; s <= N - len + 1; s++) {
				int e = s + len - 1;
				dp[s][e] = Integer.MAX_VALUE;
				for (int k = s; k < e; k++) {
					int As_Ak = dp[s][k];
					int Ak1_Ae = dp[k + 1][e];
					int ske = p.get(s - 1) * p.get(k) * p.get(e);
					dp[s][e] = Math.min(dp[s][e], As_Ak + Ak1_Ae + ske);
				}
			}
		}
		System.out.println(dp[1][N]);
	}
}
