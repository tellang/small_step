package DP.N18427;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static int N, M, H, DIV = 10_007;
	static Map<Integer, Integer> dp = new HashMap<>(), buff;
	static List<Integer>[] blocks;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		H = parseInt(st.nextToken());
		blocks = new ArrayList[N + 1];
		dp.put(0, 1);
		for (int n = 0; n < N; n++) {
			blocks[n] = new ArrayList<>();
			st = new StringTokenizer(br.readLine());
			while (st.hasMoreTokens()) {
				blocks[n].add(parseInt(st.nextToken()));
			}
			buff = new HashMap<>();
			for (int i : blocks[n]) {
				for (int k : dp.keySet()) {
					int newNum = k + i;
					buff.put(newNum, (buff.getOrDefault(newNum, 0) + dp.get(k)) % DIV);
				}
			}
			for (int i : buff.keySet()) {
				dp.put(i, (dp.getOrDefault(i, 0) + buff.get(i)) % DIV);
			}
		}
		System.out.println(dp.getOrDefault(H, 0) % DIV);
	}
}
