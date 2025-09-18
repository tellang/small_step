package SSAFY.N1938;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();

	static final int LIMIT_N = 50, ROW = 0, COL = 1;
	static final int[] mv = {1, 0, -1, 0, 1};
	static final char TREE = '1', BASE = 'B', END = 'E';

	static int N, Y, X;
	static char[][] map;
	static boolean[][][] visited; // [y][x][dir]
	static Node base, end;
	static List<Node> baseList, endList;
	static Queue<Node> q;

	static {
		base = new Node(0, 0, 0, 0);
		end = new Node(0, 0, 0, 0);
		baseList = new ArrayList<>();
		endList = new ArrayList<>();
		q = new ArrayDeque<>();
	}

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		init();
		int minTime = getMinTime();
		System.out.println(minTime);
	}

	/**
	 * 3차원 bfs
	 * 통나무의 중심좌표를 굴린다
	 * @return
	 */
	static int getMinTime() {
		while (!q.isEmpty()) {
			Node cur = q.poll();
			int ndir = cur.dir ^ 1;
			int nt = cur.time + 1;
			/*
			 * 굴리기
			 */
			for (int d = 0; d < 4; d++) {
				int ny = cur.y + mv[d];
				int nx = cur.x + mv[d + 1];


				if (!canGo(ny, nx, cur.dir))
					continue;
				if (visited[ny][nx][cur.dir])
					continue;

				if (isEnd(ny, nx, cur.dir))
					return nt;

				visited[ny][nx][cur.dir] = true;
				q.offer(new Node(ny, nx, cur.dir, nt));
			}

			/*
			 * 돌리기
			 */
			if(!canTurn(cur.y, cur.x)) continue;
			if(visited[cur.y][cur.x][ndir]) continue;
			visited[cur.y][cur.x][ndir] = true;
			q.offer(new Node(cur.y, cur.x, ndir, nt));
		}
		return 0;
	}

	static boolean canTurn(int y, int x) {
		for (int ny = y - 1; ny <= y + 1; ny++) {
			for (int nx = x - 1; nx <= x + 1; nx++) {
				if(!isValid(ny, nx)) return false;
				if (map[ny][nx] == TREE) return false;
			}
		}
		return true;
	}

	static boolean isEnd(int y, int x, int dir) {
		return y == end.y && x == end.x && dir == end.dir;
	}

	/**
	 * 실제로 갈 수 있는지
	 * @param y
	 * @param x
	 * @param dir
	 * @return
	 */
	static boolean canGo(int y, int x, int dir) {
		if (dir == ROW) {
			for (int nx = x - 1; nx <= x + 1; nx++) {
				if (!isValid(y, nx))
					return false;
				if (map[y][nx] == TREE)
					return false;
			}
		} else {
			for (int ny = y - 1; ny <= y + 1; ny++) {
				if (!isValid(ny, x))
					return false;
				if (map[ny][x] == TREE)
					return false;
			}
		}

		return true;
	}

	/**
	 * 물리적 좌표 유효성
	 * @param y
	 * @param x
	 * @return
	 */
	static boolean isValid(int y, int x) {
		return y >= 0 && y < Y && x >= 0 && x < X;
	}

	static void init() throws IOException {
		N = Y = X = parseInt(br.readLine().trim());
		map = new char[Y][X];
		visited = new boolean[Y][X][2];

		for (int y = 0; y < Y; y++) {
			map[y] = br.readLine().trim().toCharArray();
			for (int x = 0; x < X; x++) {
				if (map[y][x] == BASE)
					baseList.add(new Node(y, x, -1, 0));
				else if (map[y][x] == END)
					endList.add(new Node(y, x, -1, 0));
			}
		}

		makeLog();
		q.offer(base);
		visited[base.y][base.x][base.dir] = true;
	}

	static void makeLog() {
		makeLog(baseList, base);
		makeLog(endList, end);
	}

	/**
	 * 통나무 중심 찾기
	 * @param logs
	 * @param mid
	 */
	static void makeLog(List<Node> logs, Node mid) {
		logs.sort((l0, l1) -> l0.y == l1.y ? Integer.compare(l0.x, l1.x) : Integer.compare(l0.y, l1.y));
		Node center = logs.get(1);
		mid.y = center.y;
		mid.x = center.x;
		mid.dir = (logs.get(0).y == logs.get(1).y) ? ROW : COL;
		// return mid;
	}

	static class Node {
		int y, x, dir, time;

		public Node(int y, int x, int dir, int time) {
			this.y = y;
			this.x = x;
			this.dir = dir;
			this.time = time;
		}
	}
}