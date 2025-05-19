package DP.N1516;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Integer>[] graph;
	static List<Pair> pairs = new ArrayList<>();
	static List<Integer> times = new ArrayList<>();
	static List<Integer> baseTimes = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		graph = new List[N + 1];
		for (int n = 1; n <= N; n++) {
			graph[n] = new ArrayList<>();
		}
		times.add(10000000);
		baseTimes.add(10000000);
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			int time = parseInt(st.nextToken());
			times.add(time);
			baseTimes.add(time);
			int before = parseInt(st.nextToken());
			int count = 0;
			while (before != -1) {
				graph[before].add(n);
				before = parseInt(st.nextToken());
				count++;
			}
			pairs.add(new Pair(n, count));
		}
		pairs.sort(Comparator.comparingInt(o -> o.bc));

		int count = 0;
		while (count < N) {
			int i = 0;
			List<Integer> buildTango = new ArrayList<>();
			int[] timeOffset = new int[N + 1];
			while (i < N && pairs.get(i).bc <= 0){
				buildTango.add(pairs.get(i).n);
				pairs.get(i).bc = 100000;
				i++;
			}
			pairs.sort(Comparator.comparingInt(o -> o.n));
			for (int num : buildTango) {
				int time = times.get(num);
				for (int nextBuild : graph[num]) {
					timeOffset[nextBuild] = Math.max(timeOffset[nextBuild], time);
					pairs.get(nextBuild - 1).bc--;
				}
			}
			for (int j = 1; j <= N; j++) {
				if (timeOffset[j] == 0) continue;
				times.set(j, Math.max(times.get(j), timeOffset[j]+ baseTimes.get(j)));
			}
			count += i;
			pairs.sort(Comparator.comparingInt(o -> o.bc));

		}
		StringBuilder sb = new StringBuilder();
		for (int n = 1; n <= N; n++) {
			sb.append(times.get(n)).append("\n");
		}
		System.out.println(sb);
	}
}
class Pair {
	int n, bc;

	public Pair(int n, int bc) {
		this.n = n;
		this.bc = bc;
	}
}

