package MST.N2887;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N;
	static List<Node> map = new ArrayList<>();
	static List<Star> stars = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());

		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int x = parseInt(st.nextToken());
			int y = parseInt(st.nextToken());
			int z = parseInt(st.nextToken());
			stars.add(new Star(x, y, z, n));
		}
		stars.sort((o1, o2) -> o2.x - o1.x);
		Star aStar = stars.get(0);
		Star bStar;
		for (int n = 1; n < N; n++) {
			bStar = stars.get(n);
			map.add(new Node(aStar.i, bStar.i, Math.abs(aStar.x - bStar.x)));
			aStar = bStar;
		}

		stars.sort((o1, o2) -> o2.y - o1.y);
		aStar = stars.get(0);
		for (int n = 1; n < N; n++) {
			bStar = stars.get(n);
			map.add(new Node(aStar.i, bStar.i, Math.abs(aStar.y - bStar.y)));
			aStar = bStar;
		}

		stars.sort((o1, o2) -> o2.z - o1.z);
		aStar = stars.get(0);
		for (int n = 1; n < N; n++) {
			bStar = stars.get(n);
			map.add(new Node(aStar.i, bStar.i, Math.abs(aStar.z - bStar.z)));
			aStar = bStar;
		}

		Union union = new Union(N);

		Collections.sort(map);
		int result = 0;
		for (Node node : map) {
			if (union.union(node.a, node.b))
				result += node.v;
		}
		System.out.println(result);
	}
}

class Star {
	int x, y, z, i;

	public Star(int x, int y, int z, int i) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.i = i;
	}
}

class Node implements Comparable<Node> {
	int a, b, v;

	public Node(int a, int b, int v) {
		this.a = a;
		this.b = b;
		this.v = v;
	}

	@Override
	public int compareTo(Node o) {
		return this.v - o.v;
	}
}

class Union {
	static int[] parent, rank;

	public Union(int N) {
		parent = new int[N + 1];
		rank = new int[N + 1];
		for (int i = 1; i < N + 1; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	int findRoot(int i) {
		if (parent[i] != i) {
			parent[i] = findRoot(parent[i]);
		}
		return parent[i];
	}

	boolean union(int x, int y) {
		int rootX = findRoot(x);
		int rootY = findRoot(y);
		if (rootX == rootY) {
			return false;
		}
		if (rank[rootX] < rank[rootY]) {
			parent[rootX] = rootY;
		} else if (rank[rootX] > rank[rootY]) {
			parent[rootY] = rootX;
		} else {
			parent[rootY] = rootX;
			rank[rootX]++;
		}
		return true;
	}
}
