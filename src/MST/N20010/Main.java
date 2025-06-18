package MST.N20010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N, K;
	static long MAX, MIN;
	static long[][] mtx;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static ArrayDeque<Pair> stk;
	static boolean[] visited;
	static List<Node> nodes = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		mtx = new long[N][N];
		for (int k = 0; k < K; k++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			nodes.add(new Node(a, b, v));
		}
		nodes.sort(Comparator.comparingLong(o -> o.v));
		Union union = new Union(N);
		int idx = 0;
		for (Node node : nodes) {
			if (union.union(node)) {
				idx++;
				MIN += node.v;
				mtx[node.a][node.b] = node.v;
				mtx[node.b][node.a] = node.v;
			}
			if (idx == N - 1)
				break;
		}
		int start = 0;
		int end = 0;
		for (int i = 0; i < 2; i++) {
			stk = new ArrayDeque<>();
			visited = new boolean[N];
			stk.push(new Pair(start, 0));
			while (!stk.isEmpty()) {
				Pair pop = stk.pop();
				if (MAX < pop.v) {
					end = pop.idx;
					MAX = pop.v;
				}

				visited[pop.idx] = true;
				for (int n = 0; n < N; n++) {
					if (!visited[n]) {
						long v = mtx[pop.idx][n];
						if (v != 0) {
							stk.push(new Pair(n, pop.v + v));
						}
					}
				}
			}
			start = end;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(MIN).append("\n").append(MAX);
		System.out.println(sb);
	}
}

class Union {
	int[] rank, parent;

	public Union(int N) {
		this.rank = new int[N];
		this.parent = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	public int findParent(int n) {
		if (parent[n] == n)
			return n;
		return parent[n] = findParent(parent[n]);
	}

	public boolean union(Node n) {
		return union(n.a, n.b);
	}

	public boolean union(int x, int y) {
		int xParent = findParent(x);
		int yParent = findParent(y);
		if (xParent == yParent)
			return false;
		if (rank[xParent] < rank[yParent]) {
			parent[xParent] = yParent;
		} else if (rank[xParent] > rank[yParent]) {
			parent[yParent] = xParent;
		} else {
			parent[xParent] = yParent;
			rank[yParent]++;
		}
		return true;
	}
}

class Pair {
	int idx;
	long v;

	public Pair(int idx, long v) {
		this.idx = idx;
		this.v = v;
	}
}

class Node {
	int a, b;
	long v;

	public Node(int a, int b, long v) {
		this.a = a;
		this.b = b;
		this.v = v;
	}
}
