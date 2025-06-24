package MST.N17472;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
땅 별로 int로 구별 좀 하고
땅의 edge 들을 구별하고
땅 별로 이을 수 있는 다리를 모두 구하고
	각 땅의 edged에서 직선으로 그어서 땅 찾기
해당 다리를 기준으로 또 prim
map 100
 */
public class Main {

	final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, GROUND = 1, NON_START_IDX = -2, NON = 0, GROUND_START_IDX = -1;
	static int min, gIdx = 0;
	static int[][] map;
	static int[] move = {-1, 0, 1, 0, -1};
	static List<Map<Integer, Integer>> bridges = new ArrayList<>();
	static List<ArrayDeque<Node>> edges = new ArrayList<>();
	static ArrayDeque<Node> groundList = new ArrayDeque<>();
	static ArrayDeque<Node> q = new ArrayDeque<>();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		map = new int[N][M];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			for (int m = 0; m < M; m++) {
				int i = parseInt(st.nextToken());
				if (i == NON) {
					map[n][m] = NON_START_IDX;
				} else if (i == GROUND) {
					map[n][m] = GROUND_START_IDX;
					groundList.offer(new Node(n, m));
				}
			}
		}
		NON = NON_START_IDX;
		GROUND = GROUND_START_IDX;
		while (!groundList.isEmpty()) {
			Node poll = groundList.poll();
			if (poll.getVal() != GROUND)
				continue;

			bridges.add(new HashMap<>());
			edges.add(new ArrayDeque<>());
			q.offer(poll);
			map[poll.y][poll.x] = gIdx;
			while (!q.isEmpty()) {
				Node pn = q.poll();

				for (int i = 0; i < 4; i++) {
					int ny = pn.y + move[i];
					int nx = pn.x + move[i + 1];
					if (isValid(ny, nx)) {
						if (map[ny][nx] == GROUND) {
							map[ny][nx] = gIdx;
							q.offer(new Node(ny, nx));
						} else if (map[ny][nx] == NON) {
							edges.get(gIdx).add(new Node(pn.y, pn.x));
						}
					}
				}
			}
			gIdx++;
		}
            /*
            다리 짓기, 같은 섬 이라면 가장 짧은 거리로, a->b b->a 의 최소값도 구해야하나?
             */
		for (int gn = 0; gn < edges.size(); gn++) {
			ArrayDeque<Node> edgeQ = edges.get(gn);
			while (!edgeQ.isEmpty()) {
				Node pe = edgeQ.poll();

				for (int i = 0; i < 4; i++) {
					int t = 1;
					int ny = pe.y + move[i] * t;
					int nx = pe.x + move[i + 1] * t;
					while (isValid(ny, nx) && map[ny][nx] == NON) {
						t++;
						ny = pe.y + move[i] * t;
						nx = pe.x + move[i + 1] * t;
					}
					if (!isValid(ny, nx)) {
						continue;
					}
					if (map[ny][nx] != gn) {
						if (t > 2) {
							int shortestBridge = bridges.get(gn)
								.getOrDefault(map[ny][nx], Integer.MAX_VALUE);
							bridges.get(gn).put(map[ny][nx], Math.min(shortestBridge, t - 1));
						}
					}

				}
			}
		}
            /*
            다리 이어주기 프림
             */
		PriorityQueue<Bridge> pq = new PriorityQueue<>((Comparator.comparingInt(o -> o.v)));
		boolean[] visited = new boolean[gIdx];
		min = 0;
		pq.offer(new Bridge(0, 0));
		while (!pq.isEmpty()) {
			Bridge b = pq.poll();
			if (visited[b.n])
				continue;
			visited[b.n] = true;
			min += b.v;
			Map<Integer, Integer> gList = bridges.get(b.n);
			for (int i : gList.keySet()) {
				if (gList.get(i) != 0 && !visited[i]) {
					pq.offer(new Bridge(i, gList.get(i)));
				}
			}
		}

		for (boolean b : visited) {
			if (!b) {
				min = -1;
				break;
			}
		}
		System.out.println(min);
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < M;
	}

	static class Node {

		int y, x;

		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}

		public int getVal() {
			return map[this.y][this.x];
		}

	}

	static class Bridge {
		int n, v;

		public Bridge(int n, int v) {
			this.n = n;
			this.v = v;
		}
	}
}

