package Implementation.N15685;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
드래곤 다이브를 리스트 누적 재귀형식으로 구현
방향에 따른 드로잉 메소드를 구현
방향은 0123 형식이니 시계방향 회전은 (방향 + 3)%4
전체 방문 표시 visited, 목표 전역 세대

(방향, 리스트) // 주어진 리스트의 끝점을 기준으로 리스트 회전변환
cos sin
-sin cos
공식 기준은 반시계
-ㅠ/2
 0 1  x  y, -x
-1 0  y

---

1. 원점에서 드래곤 커브 하고
	a. list를 이전세대를 그대로 clone
	b. 이전세대의 마지막 점을 시작지점 및 회전변환기준 (a, b)
		b.1 (-a, -b) 만큼 평행이동
		b.2 회전변환 (y, -x)
		b.3 (a, b) 만큼 평행이동
		b.4 리스트 추가
2. 일괄평행이동
	a. 방문처리
3. 네모 체크
 */
public class Main {
	static int N, Y, X, D, G, COUNT;
	static boolean[][] visited;
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Node>[][] dive = new ArrayList[4][11];

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		for (int d = 0; d < 4; d++) {
			for (int g = 0; g < 11; g++) {
				dive[d][g] = new ArrayList<>();

			}
			dive[d][0].add(new Node(0, 0));
			dive[d][0].add(new Node(dy[d], dx[d]));
		}
		visited = new boolean[101][101];

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine());
			X = Integer.parseInt(st.nextToken());
			Y = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			G = Integer.parseInt(st.nextToken());
			if (dive[D][G].isEmpty()) {
				int g = 0;
				for (g = 0; g <= G; g++) {
					if (!dive[D][g].isEmpty()) {
						continue;
					}
					for (Node node : dive[D][g - 1]) {
						dive[D][g].add(node);
					}
					int lastIdx = dive[D][g - 1].size() - 1;
					Node lastNode = dive[D][g - 1].get(lastIdx);

					for (int i = lastIdx - 1; i >= 0; i--) {

						Node tango = dive[D][g - 1].get(i);
						tango = parallelNegativeMove(tango, lastNode);
						tango = getTrans(tango);
						tango = parallelPositiveMove(tango, lastNode);
						dive[D][g].add(tango);
					}

				}
			}
			parallelPositiveMove(Y, X, dive[D][G]);
		}
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				if (visited[y][x] &&
					visited[y][x + 1] &&
					visited[y + 1][x] &&
					visited[y + 1][x + 1]) {
					COUNT++;
				}
			}
		}

		System.out.println(COUNT);
	}

	public static Node getTrans(Node node) {
		int ny = node.x;
		int nx = node.y * -1;

		return new Node(ny, nx);
	}

	public static Node parallelNegativeMove(Node tango, Node offset) {
		int ny = tango.y - offset.y;
		int nx = tango.x - offset.x;
		return new Node(ny, nx);
	}

	public static Node parallelPositiveMove(Node tango, Node offset) {
		int ny = tango.y + offset.y;
		int nx = tango.x + offset.x;
		return new Node(ny, nx);
	}

	public static void parallelPositiveMove(int y, int x, List<Node> nodes) {
		visited[y][x] = true;
		for (Node node : nodes) {
			int ny = node.y + y;
			int nx = node.x + x;
			visited[ny][nx] = true;
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
