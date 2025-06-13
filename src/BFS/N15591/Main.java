package BFS.N15591;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
가중치가 있는 간선
노드와 노드 사이의 가중치의 최솟값 중 특정 값 이하의 모든 가중치 구하기
노드 수 5K 질문 수 5K
5K x 15Klog5 < 1G 노가다 가능

유일 루트
 */
public class Main {
	static int N, Q;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static ArrayList<Node>[] map;
	static Queue<Integer> queue;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		map = new ArrayList[N + 1];
		for (int n = 0; n <= N; n++) {
			map[n] = new ArrayList<>();
		}
		for (int n = 0; n < N - 1; n++) {
			st = new StringTokenizer(br.readLine());
			int pi = Integer.parseInt(st.nextToken());
			int qi = Integer.parseInt(st.nextToken());
			int ri = Integer.parseInt(st.nextToken());
			if (pi == qi)
				continue;
			map[pi].add(new Node(qi, ri));
			map[qi].add(new Node(pi, ri));
		}

		for (int q = 0; q < Q; q++) {
			queue = new ArrayDeque<>();
			visited = new boolean[N + 1];
			int count = -1;

			st = new StringTokenizer(br.readLine());
			int ki = Integer.parseInt(st.nextToken());
			int vi = Integer.parseInt(st.nextToken());

			queue.offer(vi);

			while (!queue.isEmpty()) {
				int poll = queue.poll();

				count++;
				visited[poll] = true;
				for (Node node : map[poll]) {
					int next = node.i;
					if (!visited[next] &&
						node.v >= ki) {
						queue.offer(next);
					}

				}
			}
			sb.append(count).append("\n");

		}
		System.out.println(sb);
	}
}

class Node {
	int i, v;

	Node(int i, int v) {
		this.i = i;
		this.v = v;
	}
}