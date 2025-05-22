package Implementation.N11559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

///
/// \(12 x 6)^2
/// 수는 문제 없다
///
/// 중력, 폭발 반복
/// class Node
/// 먼저 아래서부터 인덱스를 올리며 받는다
/// 전수조사를 하며 터트린다
/// - flag true 조건 while문
/// 	- flag false, 방문 처리 초기화
/// 	- 전수 조사 후 매번 bfs로 검사 searchQ<Node>
/// 		- 한 싸이클내에 또 다른 deleteQ 에 저장
/// 		- 크기가 4개 이상이면 해당위치 지우기
/// 			- 지워진적 있으면 flag true
/// 		- flag true 일 경우 연쇄 1 증가
///
/// ---
/// node
public class Main {
	static int COUNT;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static char[][] map = new char[12][6], buffer;
	static Queue<Node> searchQ = new ArrayDeque<>(), deleteQ = new ArrayDeque<>();
	static boolean[][] visited;
	static boolean isDeleted = true;
	static int[] mv = {-1, 0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		for (int y = 0; y < 12; y++) {
			char[] arr = br.readLine().toCharArray();
			for (int x = 0; x < 6; x++) {
				if (arr[x] != '.')
					map[y][x] = arr[x];
			}
		}
		down();

		while (isDeleted) {
			isDeleted = false;
			visited = new boolean[12][6];
			for (int y = 0; y < 12; y++) {
				for (int x = 0; x < 6; x++) {
					if (0 != map[y][x] && !visited[y][x]) {
						visited[y][x] = true;
						char tango = map[y][x];
						deleteQ.clear();
						searchQ.offer(new Node(y, x));
						deleteQ.offer(new Node(y, x));
						while (!searchQ.isEmpty()) {
							Node poll = searchQ.poll();
							for (int i = 0; i < 4; i++) {
								int ny = poll.y + mv[i];
								int nx = poll.x + mv[i + 1];
								if (isValid(ny, nx) && map[ny][nx] == tango) {
									searchQ.offer(new Node(ny, nx));
									deleteQ.offer(new Node(ny, nx));
									visited[ny][nx] = true;
								}
							}
						}
						if (deleteQ.size() >= 4) {
							isDeleted = true;
							while (!deleteQ.isEmpty()) {
								Node poll = deleteQ.poll();
								map[poll.y][poll.x] = 0;
							}
						}
					}
				}
			}
			if (isDeleted) {
				COUNT++;
			} else {
				System.out.println(COUNT);
				return;
			}
			down();
		}
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && y < 12 && x >= 0 && x < 6 && !visited[y][x] && map[y][x] != 0;
	}

	static void down() {
		buffer = new char[12][6];
		for (int x = 0; x < 6; x++) {
			int bY = 11;
			for (int y = 11; y >= 0; y--) {
				if (map[y][x] != 0) {
					buffer[bY--][x] = map[y][x];
				}
			}
		}
		map = buffer;
	}
}

class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}