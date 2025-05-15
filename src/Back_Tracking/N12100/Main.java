package Back_Tracking.N12100;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static int N, MAX, L = 0, U = 1, R = 2, D = 3;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] move = {0, -1, 0, 1, 0};
	static int[][] mtx;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		mtx = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				mtx[i][j] = Integer.parseInt(st.nextToken());
				MAX = Math.max(MAX, mtx[i][j]);
			}
		}
		for (int i = 0; i < 4; i++) {
			int[][] cloneMtx = new int[N][N];
			for (int n = 0; n < N; n++) {
				cloneMtx[n] = mtx[n].clone();
			}
			backtrack(0, i, cloneMtx);
		}
		System.out.println(MAX);
	}

	public static void backtrack(int turn, int moveIdx, int[][] mtx) {
		if (turn >= 5) {
			return;
		}
		HashSet<Node> sealedList = new HashSet<>();
		if (moveIdx == 0) {
			for (int x = 1; x < N; x++) {
				for (int y = 0; y < N; y++) {
					moveMtx(moveIdx, mtx, y, x, sealedList);
				}
			}
		} else if (moveIdx == 1) {
			for (int y = 1; y < N; y++) {
				for (int x = 0; x < N; x++) {
					moveMtx(moveIdx, mtx, y, x, sealedList);
				}
			}
		} else if (moveIdx == 2) {
			for (int x = N - 2; x >= 0; x--) {
				for (int y = 0; y < N; y++) {
					moveMtx(moveIdx, mtx, y, x, sealedList);
				}
			}
		} else if (moveIdx == 3) {
			for (int y = N - 2; y >= 0; y--) {
				for (int x = 0; x < N; x++) {
					moveMtx(moveIdx, mtx, y, x, sealedList);
				}
			}
		}
		for (int i = 0; i < 4; i++) {
			int[][] cloneMtx = new int[N][N];
			for (int n = 0; n < N; n++) {
				cloneMtx[n] = mtx[n].clone();
			}
			backtrack(turn + 1, i, cloneMtx);
		}
	}

	private static void printMtx(int[][] mtx, int turn) {
		System.out.println("turn = " + turn);
		for (int[] ints : mtx)
			System.out.println(Arrays.toString(ints));
		System.out.println();
	}

	private static void moveMtx(int moveIdx, int[][] mtx, int y, int x, HashSet<Node> sealedList) {
		int offset = 0;
		int pY = y;
		int pX = x;
		if (mtx[y][x] == 0)
			return;
		while (true){
			pY = y + offset * move[moveIdx];
			pX = x + offset * move[moveIdx + 1];
			offset++;
			int nextY = y + offset * move[moveIdx];
			int nextX = x + offset * move[moveIdx + 1];
			if (isValid(nextY, nextX)) {
				if (mtx[nextY][nextX] == 0){
					mtx[nextY][nextX] = mtx[pY][pX];
					Node pNode = new Node(pY, pX);
					if (sealedList.contains(pNode)){
						sealedList.add(new Node(nextY, nextX));
						sealedList.remove(pNode);
					}
					mtx[pY][pX] = 0;

				} else if (mtx[nextY][nextX] == mtx[pY][pX] &&
					!sealedList.contains(new Node(nextY, nextX)) && !sealedList.contains(new Node(pY, pX))) {
					mtx[nextY][nextX] *= 2;
					MAX = Math.max(MAX, mtx[nextY][nextX]);
					mtx[pY][pX] = 0;
					sealedList.add(new Node(nextY, nextX));
				} else {
					break;
				}
			} else
				break;
		}
	}

	public static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < N;
	}
}
class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Node node = (Node)o;
		return y == node.y && x == node.x;
	}

	@Override
	public int hashCode() {
		return Objects.hash(y, x);
	}
}
