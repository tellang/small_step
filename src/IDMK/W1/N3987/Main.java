package IDMK.W1.N3987;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *
 * - 4방향에 대해 각각 시뮬레이션 실행
 * - 각 시뮬레이션은 독립된 visited 배열 사용
 *
 * 시뮬레이션 규칙
 * - 1. 현재 위치 유효성 및 블랙홀 확인
 * - 2. 현재 위치/방향의 기존 방문 여부 확인
 * - 3. 현재 위치/방향 방문 기록
 * - 4. 현재 위치가 행성인 경우 방향 전환
 * - 5. 현재 방향으로 1칸 이동
 *
 */
public class Main {
	static final int U = 0, R = 1, D = 2, L = 3;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();
	static int N, M, MAX_TIME;
	static int[][] visited;
	static char[][] map;
	static String VOYAGER = "Voyager";
	static char SPACE = '.', BLACK_HOLE = 'C',
		PLANET_A = '\\', PLANET_B = '/';
	static char MAX_TIME_D;
	static char[] dList = {'U', 'R', 'D', 'L'};
	static int[] dy = {-1, 0, 1, 0}; // U, R, D, L
	static int[] dx = {0, 1, 0, -1}; // U, R, D, L

	static Node presentNode;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());

		map = new char[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		st = new StringTokenizer(br.readLine());
		// 0 기반 좌표로 변경
		int startY = parseInt(st.nextToken()) - 1;
		int startX = parseInt(st.nextToken()) - 1;

		for (int d = U; d <= L; d++) {
			visited = new int[N][M];
			int localTime = 0;

			presentNode = new Node(startY, startX, d);

			while (true) {
				// 1. 현재 위치가 범위를 벗어났거나 블랙홀인지 확인
				if (!isValid(presentNode) || isBlackHole(presentNode)) {
					break;
				}

				// 2. 현재 위치/방향으로 방문한 적이 있는지 확인 (무한 루프)
				if (isVisited(presentNode)) {
					result.append(dList[d]).append('\n').append(VOYAGER);
					System.out.println(result.toString());
					System.exit(0);
					return;
				}

				// 3. 현재 위치/방향 방문 기록
				visiteCheck(presentNode);
				localTime++;

				// 4. 현재 위치의 지형에 따라 방향 전환
				redirected(presentNode);

				// 5. 현재 방향으로 1칸 이동
				presentNode.y += dy[presentNode.d];
				presentNode.x += dx[presentNode.d];
			}

			if (localTime > MAX_TIME) {
				MAX_TIME = localTime;
				MAX_TIME_D = dList[d];
			}
		}

		result.append(MAX_TIME_D).append('\n').append(MAX_TIME);
		System.out.println(result.toString());
	}

	static boolean isPlanet(Node node) {
		char position = map[node.y][node.x];
		return position == PLANET_A || position == PLANET_B;
	}

	static void visiteCheck(Node node) {
		visited[node.y][node.x] |= (1 << node.d);
	}

	static boolean isVisited(Node node) {
		return (visited[node.y][node.x] & (1 << node.d)) != 0;
	}

	static boolean isBlackHole(Node node) {
		return map[node.y][node.x] == BLACK_HOLE;
	}

	/**
	 * 행성 반사
	 */
	static void redirected(Node node) {
		if (!isPlanet(node))
			return;
		int redirected = redirected(map[node.y][node.x], node.d);
		if (redirected != -1) {
			node.d = redirected;
		}
	}

	static int redirected(char planet, int direction) {
		if (planet == PLANET_B) { // '/' 모양 행성
			if (direction == U)
				return R;
			if (direction == R)
				return U;
			if (direction == D)
				return L;
			if (direction == L)
				return D;
		} else if (planet == PLANET_A) { // '\' 모양 행성
			if (direction == U)
				return L;
			if (direction == L)
				return U;
			if (direction == D)
				return R;
			if (direction == R)
				return D;
		}
		return -1;
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && x >= 0 && y < N && x < M;
	}

	static boolean isValid(Node node) {
		return isValid(node.y, node.x);
	}

	static class Node {
		int y, x, d;

		public Node(int y, int x, int d) {
			this.y = y;
			this.x = x;
			this.d = d;
		}
	}
}