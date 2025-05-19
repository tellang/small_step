package DP.N1106;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N, C, MIN = 1000*1000; // N<20, C<1000
	static List<Pair> pairs = new ArrayList<>();
	static int[] dp; //cost[gain]
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		C = parseInt(st.nextToken());
		N = parseInt(st.nextToken());
		dp = new int[C + 101];
		Arrays.fill(dp, MIN);
		dp[0] = 0;
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			pairs.add(new Pair(parseInt(st.nextToken()), parseInt(st.nextToken()))); // 100<, 100<
		}
		pairs.sort(((o1, o2) -> o2.gain / o2.cost - o1.gain / o1.cost));
		for (Pair p : pairs) { //20
			for (int i = 0; i <= C; i++) {
				int tangoGain = i + p.gain;
				dp[tangoGain] = Math.min(dp[tangoGain], p.cost + dp[i]);
				if (tangoGain >= C){
					MIN = Math.min(dp[tangoGain], MIN);
				}
			}

		}
		System.out.println(MIN);
	}
}
class Pair {
	int cost, gain;

	public Pair(int cost, int gain) {
		this.cost = cost;
		this.gain = gain;
	}
}
