package DP.N15681;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
트리 제작후 역탐색
 */
public class Main {
	static int N, R, Q;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Integer>[] arr;
	static int[] leafCount;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		R = parseInt(st.nextToken());
		Q = parseInt(st.nextToken());
		arr = new List[N + 1];
		leafCount = new int[N + 1];
		visited = new boolean[N + 1];
		for (int n = 1; n <= N; n++) {
			arr[n] = new ArrayList<>();
		}
		for (int n = 1; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int u = parseInt(st.nextToken());
			int v = parseInt(st.nextToken());
			arr[u].add(v);
			arr[v].add(u);
		}
		dfs(R);
		StringBuilder sb = new StringBuilder();
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			int node = parseInt(st.nextToken());
			sb.append(leafCount[node]).append("\n");
		}
		System.out.println(sb);
	}

	static int dfs(int n) {
		visited[n] = true;
		int count = 1;
		if (arr[n].size() != 1 || n == R) {
			for (int i : arr[n]) {

				if (!visited[i]) {
					count += dfs(i);
				}
			}
		}
		leafCount[n] = count;
		return count;
	}
}