package Back_Tracking.N2239;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
백트래킹 N = 9
재귀형식
입력시 0 위치 저장 input[]
세로, 가로, 박스 검사

재귀함수 (입력인덱스)
for 1~9
세로, 가로, 박스 적절성 검사
input[n]
재귀함수(n++

 */
public class Main {
	static int[][] map = new int[9][9];
	static int N, MAX;
	static List<Node> inputs = new ArrayList<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static char[] buff;

	public static void main(String[] args) throws IOException {
		for (int y = 0; y < 9; y++) {
			buff = br.readLine().toCharArray();
			for (int x = 0; x < 9; x++) {
				map[y][x] = buff[x] - '0';
				if (map[y][x] == 0) {
					inputs.add(new Node(y, x));
					N++;
				}
			}
		}
		dfs(0);
	}

	public static void dfs(int idx) {
		MAX = Math.max(idx, MAX);
		if (idx == inputs.size()) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					sb.append(map[i][j]);
				}
				sb.append("\n");
			}
			System.out.println(sb);
			System.exit(0);
		}
		for (int i = 1; i <= 9; i++) {
			Node input = inputs.get(idx);
			if (isValid(input, i)) {
				map[input.y][input.x] = i;
				dfs(idx + 1);
				map[input.y][input.x] = 0;
			}
		}
	}

	public static boolean validHorizon(int y, int x, int num) {
		for (int i = 0; i < 9; i++) {
			if (map[y][i] == num)
				return false;
		}
		return true;
	}

	public static boolean validVertical(int y, int x, int num) {
		for (int i = 0; i < 9; i++) {
			if (map[i][x] == num)
				return false;
		}
		return true;
	}

	public static boolean validSquare(int y, int x, int num) {
		int ny = (y / 3) * 3;
		int nx = (x / 3) * 3;
		for (int yOff = 0; yOff < 3; yOff++) {
			for (int xOff = 0; xOff < 3; xOff++) {
				if (map[ny + yOff][nx + xOff] == num)
					return false;
			}
		}
		return true;
	}

	public static boolean isValid(int y, int x, int num) {
		return validHorizon(y, x, num) && validVertical(y, x, num) && validSquare(y, x, num);
	}

	public static boolean isValid(Node node, int num) {
		return isValid(node.y, node.x, num);
	}
}

class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}
