package MST.N14621;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static int N, M, MIN = 0;
	static char[] gender;
	static List<Node> nodes = new ArrayList<>();
	static Map<Integer, Integer>[] graph;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		gender = new char[N + 1];
		graph = new Map[N + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			gender[i] = st.nextToken().charAt(0);
			graph[i] = new HashMap<>();
		}
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			if (gender[u] != gender[v] && u != v) {
				if (graph[u].containsKey(v)) {
					int minD = Math.min(graph[u].get(v), d);
					graph[u].put(v, minD);
				} else if (graph[v].containsKey(u)) {
					int minD = Math.min(graph[v].get(u), d);
					graph[v].put(u, minD);
				} else {
					graph[u].put(v, d);
				}
				// nodes.add(new Node(u, v, d));
			}
		}
		for (int n = 1; n <= N; n++) {
			for (int k : graph[n].keySet()) {
				nodes.add(new Node(n, k, graph[n].get(k)));
			}
		}
		nodes.sort(Comparator.comparingInt(node -> node.d));

		Union union = new Union(N);
		int count = 0;
		for (Node node : nodes) {
			if (union.append(node)) {
				MIN += node.d;
				count++;
			}
		}

		if (count != N - 1) {
			MIN = -1;
		}
		System.out.println(MIN);
	}
}

class Union {
	int[] parent, rank;

	public Union(int n) {
		parent = new int[n + 1];
		rank = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	public boolean append(Node node) {
		return append(node.u, node.v);
	}

	public int getParent(int i) {
		if (i == parent[i])
			return i;
		return parent[i] = getParent(parent[i]);
	}

	public boolean append(int u, int v) {
		int uP = getParent(u);
		int vP = getParent(v);
		if (uP == vP) {
			return false;
		}
		if (rank[uP] < rank[vP]) {
			parent[uP] = vP;
		} else if (rank[uP] > rank[vP]) {
			parent[vP] = uP;
		} else {
			parent[uP] = vP;
			rank[vP]++;
		}
		return true;
	}
}

class Node {
	int u, v, d;

	public Node(int u, int v, int d) {
		this.u = u;
		this.v = v;
		this.d = d;
	}
}
