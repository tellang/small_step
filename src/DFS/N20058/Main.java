package DFS.N20058;
/*

 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, Q, NP, TOTAL_ICE = 0, MAX_COUNT = 0;
	static int[][] map, buffer;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Queue<Node> q = new ArrayDeque<>();
	static int[] mv = {-1, 0, 1, 0, -1};
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		NP = (int)Math.pow(2, N);
		map = new int[NP+2][NP+2];
		for (int y = 1; y <= NP; y++) {
			st = new StringTokenizer(br.readLine());
			for (int x = 1; x <= NP; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for (int q = 0; q < Q; q++) {
			int l = Integer.parseInt(st.nextToken());
			storm(l);
			fire();
		}
		StringBuilder result = new StringBuilder();
		visited = new boolean[NP+2][NP+2];
		int localMaxCount = 0;
		for (int y = 1; y <= NP; y++) {
			for (int x = 1; x <= NP; x++) {
				TOTAL_ICE += map[y][x];
				if (visited[y][x] || map[y][x] == 0)
					continue;
				localMaxCount = 1;
				q.clear();
				q.offer(new Node(y,x));
				visited[y][x] = true;
				while (!q.isEmpty()) {
					Node node = q.poll();

					for (int i = 0; i < 4; i++) {
						int ny = node.y + mv[i];
						int nx = node.x + mv[i + 1];

						if (map[ny][nx] > 0 && !visited[ny][nx]) {
							visited[ny][nx] = true;
							localMaxCount++;
							q.offer(new Node(ny,nx));
						}
					}
				}
				MAX_COUNT = Math.max(MAX_COUNT, localMaxCount);

			}
		}
		result.append(TOTAL_ICE).append("\n");
		result.append(MAX_COUNT);
		System.out.println(result);
	}


	private static void fire() {
	    ArrayDeque<Node> meltList = new ArrayDeque<>();

	    for (int y = 1; y <= NP; y++) {
	        for (int x = 1; x <= NP; x++) {
	            if (map[y][x] <= 0) continue;

	            int adjacentIce = 0;
	            for (int i = 0; i < 4; i++) {
	                int ny = y + mv[i];
	                int nx = x + mv[i + 1];

	                if (valid(ny, nx) && map[ny][nx] > 0) {
	                    adjacentIce++;
	                }
	            }

	            if (adjacentIce < 3) {
	                meltList.offer(new Node(y, x));
	            }
	        }
	    }

	    while (!meltList.isEmpty()) {
	        Node node = meltList.poll();
	        if (map[node.y][node.x] > 0) map[node.y][node.x]--;
	    }
	}


	public static boolean valid(int y, int x) {
		return y > 0 && y <= NP && x > 0 && x <= NP;
	}

	public static void storm(int l) {
	    int size = (int)Math.pow(2, l);
	    int[][] tmp = new int[NP + 2][NP + 2];

	    for (int y = 1; y <= NP; y += size) {
	        for (int x = 1; x <= NP; x += size) {
	            rotate(y, x, size, tmp);
	        }
	    }

	    for (int y = 1; y <= NP; y++) {
	        for (int x = 1; x <= NP; x++) {
	            map[y][x] = tmp[y][x];
	        }
	    }
	}
	public static void rotate(int sy, int sx, int size, int[][] tmp) {
	    for (int y = 0; y < size; y++) {
	        for (int x = 0; x < size; x++) {
	            tmp[sy + x][sx + size - 1 - y] = map[sy + y][sx + x];
	        }
	    }
	}
}
class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}
