package Dijkstra.N5719;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
다익스트라로 자르기 전에 일정 경로가 겹치는 최단경로도 선별해야한다
 */
public class Main {

	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, S, D, U, V, P, MAX = 100_000_000, MIN = MAX;
	static StringBuilder results = new StringBuilder();
	static HashMap<Integer, Integer>[] map;
	static StringTokenizer st;
	static int[] dijkstra;
	static HashSet<Integer>[] beforeList;
	static boolean[] isVisited;
	static PriorityQueue<Integer> pq;
	static ArrayDeque<Integer> q;

	public static void main(String[] args) throws IOException {
		while (true) {
			st = new StringTokenizer(br.readLine());
			N = parseInt(st.nextToken());
			M = parseInt(st.nextToken());
			if (N == 0 && M == 0) {
				break;
			}
			map = new HashMap[N];
			beforeList = new HashSet[N];
			for (int n = 0; n < N; n++) {
				map[n] = new HashMap<>();
				beforeList[n] = new HashSet<>();
			}

			st = new StringTokenizer(br.readLine());
			S = parseInt(st.nextToken());
			D = parseInt(st.nextToken());

			for (int m = 0; m < M; m++) {
				st = new StringTokenizer(br.readLine());
				U = parseInt(st.nextToken());
				V = parseInt(st.nextToken());
				P = parseInt(st.nextToken());
				map[U].put(V, P);
			}

			initialize();
			dijkstra();

			if (dijkstra[D] == MAX) {
				MIN = -1;
			} else {
				MIN = dijkstra[D];

				deleteRoute();

				initialize();
				dijkstra();

				if (dijkstra[D] == MAX) {
					MIN = -1;
				} else {
					MIN = dijkstra[D];
				}

			}

			results.append(MIN).append('\n');
		}
		System.out.println(results.toString());
	}

	private static void deleteRoute() {
		q = new ArrayDeque<>();
		isVisited = new boolean[N];
		q.offer(D);
		isVisited[D] = true;
		while (!q.isEmpty()) {
			int before = q.poll();

			for (int i : beforeList[before]) {
				if (i == -1)
					continue;
				if (!isVisited[i]) {
					q.offer(i);
					isVisited[i] = true;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			if (!isVisited[i])
				continue;
			for (int b : beforeList[i]) {
				if (b == -1)
					continue;
				map[b].remove(i);
			}
		}

	}

	private static void initialize() {
		dijkstra = new int[N];
		isVisited = new boolean[N];
		pq = new PriorityQueue<>((Comparator.comparingInt(o -> dijkstra[o])));
		Arrays.fill(dijkstra, MAX);
		beforeList[S].add(-1);
		dijkstra[S] = 0;
	}

	private static void dijkstra() {
		pq.offer(S);

		while (!pq.isEmpty()) {
			Integer node = pq.poll();
			if (isVisited[node])
				continue;

			if (node == D)
				break;

			isVisited[node] = true;

			for (int nextNode : map[node].keySet()) { //dijkstra
				if (!isVisited[nextNode]) {
					int nv = Math.min(map[node].get(nextNode) + dijkstra[node], MAX);
					if (dijkstra[nextNode] > nv) {
						dijkstra[nextNode] = nv;
						beforeList[nextNode].clear();
						beforeList[nextNode].add(node);
						pq.offer(nextNode);
					} else if (dijkstra[nextNode] == nv) {
						beforeList[nextNode].add(node);
					}
				}
			}
		}
	}
}
