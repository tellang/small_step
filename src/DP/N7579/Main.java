package DP.N7579;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, COST_MIN = 100 * 1000, COST_MAX = 0;
	static long[] memory; //c
	static int[] MEM, COST;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		MEM = new int[N + 1];
		COST = new int[N + 1];

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			MEM[i] = parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			COST[i] = parseInt(st.nextToken());
			COST_MAX += COST[i];
		}
		memory = new long[COST_MAX + 1];
		for (int i = 1; i <= N; i++) { //100
			int cost = COST[i];
			int mem = MEM[i];
			for (int c = COST_MAX; c >= cost; c--) {// 중복 사용 배제 역순
				memory[c] = Math.max(memory[c], (long)(memory[c - cost] + mem));
				if (memory[c] >= M)
					COST_MIN = Math.min(c, COST_MIN);
			}
		}
		System.out.println(COST_MIN);
	}
}
