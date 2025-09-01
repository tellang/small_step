package SSAFY.N20040;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * mst
 * 
 * 0based N
 * M 개 간선
 * 순차 설치
 * N 에서 join 참이면 --
 * @author SSAFY
 *
 */
public class Main {

	static BufferedReader br;
	static StringTokenizer st;
	
	static int N, NODE_MAX, M, EDGE_MAX, count;
	static Union union;
	
	
	static {
		br = new BufferedReader(new InputStreamReader(System.in));
		
	}
	public static void main(String[] args) throws IOException {
		
		init();
		boolean result = make();
		
		System.out.println(result ? 0 : count);
	}
	
	static boolean make() throws IOException {
		for (int ed = 0; ed < EDGE_MAX; ed++) {
			st = new StringTokenizer(br.readLine().trim());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			count++;
			if(!union.join(u, v)) return false;
			
		}
		return true;
		
	}
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		N = NODE_MAX = Integer.parseInt(st.nextToken());
		M = EDGE_MAX = Integer.parseInt(st.nextToken());
		count = 0;
		union = new Union(N);
	}
	
	static class Union {
		int[] parent, rank;
		
		public Union(int N) {
			parent = new int[N];
			rank = new int[N];
			
			for (int self = 0; self < N; self++) {
				parent[self] = self;
			}
		}
		
		int findRoot(int child) {
			if (child == parent[child]) {
				return child;
			}
			return parent[child] = findRoot(parent[child]);
		}
		
		boolean join(int u, int v) {
			int uRoot = findRoot(u);
			int vRoot = findRoot(v);
			
			if (uRoot == vRoot) return false;
			
			if(rank[uRoot] == rank[vRoot])
				rank[uRoot]++;
			
			if (rank[uRoot] > rank[vRoot]) {
				parent[vRoot] = uRoot;
			}else {
				parent[uRoot] = vRoot;
			}
			
			return true;
		}
	}
	
}

