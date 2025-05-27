package BFS.N16236;
/*
가까운거 먹기
우선순위 큐 활용
	1. 가장 빠른 순
	2. y 오름차순
	3. x 오름차순
움직일때 STEP
먹으면 타겟 레벨 INF
EAT++

Q. 같은 랭크의 물고기가 여럿있는데
그중 가장 우선순위의 높은 물고기가 도달 할 수 없는 물고기였다면?

contain 작동 안함
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int N, NULL = 0, EAT = 0, STEP = 0, TOTAL_FISH = 0, LEVEL = 2, ATE = -20 * 20 * 100,
		EXP = 0;
	static int[][] map;
	static boolean[][] visited;
	static int[]
		xMv = {0, -1, 1, 0},
		yMv = {-1, 0, 0, 1};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static PriorityQueue<VNode> q;
	static Node baby;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine());
			for (int x = 0; x < N; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
				if (map[y][x] != NULL) {
					if (map[y][x] == 9) {
						baby = new Node(y, x);
						map[y][x] = 0;
					}
					TOTAL_FISH++;
				}
			}
		}

		while (TOTAL_FISH > EAT) {

			q = new PriorityQueue<>(((o1, o2) -> {
				if (o1.v == o2.v) {
					if (o1.y == o2.y) {
						return o1.x - o2.x;
					}
					return o1.y - o2.y;
				}
				return o1.v - o2.v;
			}
			));
			q.add(new VNode(baby, STEP));
			visited = new boolean[N][N];
			boolean doseEaten = false;
			VNode cur = null;
			while (!q.isEmpty()) {
				cur = q.poll();
				if (visited[cur.y][cur.x])
					continue;
				visited[cur.y][cur.x] = true;
				if (eatFish(cur)) {
					STEP = cur.v;
					doseEaten = true;
					break;
				}

				for (int i = 0; i < 4; i++) {
					int ny = cur.y + yMv[i];
					int nx = cur.x + xMv[i];
					Node next = new Node(ny, nx);

					if (isValid(next)) {
						if (!visited[ny][nx] && LEVEL >= map[ny][nx]) {
							q.offer(new VNode(next, cur.v + 1));
						}
					}

				}
			}
			if (doseEaten)
				baby = cur;
			else {
				System.out.println(STEP);
				return;
			}
		}

		System.out.println(STEP);
	}

	private static boolean isValid(Node cur) {
		return cur.y >= 0 && cur.x >= 0 && cur.y < N && cur.x < N;
	}

	private static <N extends Node> boolean eatFish(N cur) {
		if (canEat(cur)) {
			map[cur.y][cur.x] = ATE;
			EAT++;
			EXP++;
			levelUp();
			return true;
		}
		return false;
	}

	private static <N extends Node> boolean canEat(N cur) {
		return map[cur.y][cur.x] > 0 && map[cur.y][cur.x] < LEVEL;
	}

	private static void levelUp() {
		if (EXP == LEVEL) {
			LEVEL++;
			EXP = 0;
		}
	}
}

class Node {
	int y;
	int x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

class VNode extends Node {
	int v;

	public VNode(Node n, int v) {
		super(n.y, n.x);
		this.v = v;
	}
}