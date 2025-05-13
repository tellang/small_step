package Implementation.N14500;
import java.io.*;
import java.util.*;
/*
맵 크기 250K
테트로미노 내가 만들어서 놓기
250K x 4 의 dfs = 1M
 */
public class Main {
	static int MAX = 0, N, M, VISIT_VAL = 0;
	static int[][] map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] mv = {-1, 0, 1, 0, -1};
	static int[] ㅘㅝ = new int[4];
	static int[][] visited;
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for(int n = 0; n < N; n++){
			st = new StringTokenizer(br.readLine());
			for(int m = 0; m < M; m++){
				map[n][m] = Integer.parseInt(st.nextToken());
			}
		}
		visited = new int[N][M];
		for(int n = 0; n < N; n++){
			for(int m = 0; m < M; m++){
				VISIT_VAL++;
				visited[n][m] = VISIT_VAL;
				dfs(new Pair(n, m, map[n][m], 1));
				int localㅘㅝSum = map[n][m];
				int localㅘㅝMin = Integer.MAX_VALUE;

				for(int i = 0; i < 4; i++){
					int nn = n + mv[i];
					int nm = m + mv[i+1];
					if(isValid(nn, nm)){
						ㅘㅝ[i] = map[nn][nm];
					} else
						ㅘㅝ[i] = 0;

					localㅘㅝMin = Math.min(localㅘㅝMin, ㅘㅝ[i]);
					localㅘㅝSum += ㅘㅝ[i];
				}
				localㅘㅝSum -= localㅘㅝMin;
				MAX = Math.max(MAX, localㅘㅝSum);
			}
		}
		System.out.println(MAX);
	}

	private static void dfs(Pair p) {
		if (p.d == 4){
			MAX = Math.max(MAX, p.v);
			return;
		}

		for(int i = 0; i < 4; i++){
			int nn = p.n + mv[i];
			int nm = p.m + mv[i+1];
			if(isValid(nn, nm)){
				if (visited[nn][nm] != VISIT_VAL) {
					visited[nn][nm] = VISIT_VAL;
					dfs(new Pair(nn, nm, p.v + map[nn][nm], p.d + 1));
					visited[nn][nm] = 0;
				}
			}

		}
	}

	static boolean isValid(int n, int m){
		return n >= 0 && n < N && m >= 0 && m < M;
	}

}
class Pair {
	int n, m, v, d;
	public Pair(int n, int m, int v, int d) {
		this.n = n;
		this.m = m;
		this.v = v;
		this.d = d;
	}
}
