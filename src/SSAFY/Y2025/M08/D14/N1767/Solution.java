package SSAFY.Y2025.M08.D14.N1767;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * 핵심 로직
 * 백트래킹을 이용한 완전탐색
 *
 * - 가장자리가 아닌 코어만 탐색
 * - 가지치기 남은 코어를 모두 연결해도 기존 최대 연결 개수를 넘지 못하면 탐색 중단
 *
 * - 각 코어는 상/하/좌/우/연결안함(5가지)의 선택지를 가짐
 * - map에 전선 직접 표시 및 복구
 *
 * 정답 갱신
 * 1. 연결된 코어 개수가 많을수록 좋음
 * 2. 코어 개수가 같다면, 전선 길이가 짧을수록 좋음
 */
public class Solution {

	static final int EMPTY = 0, CORE = 1, LINE = 2;
	static final int U = 0, R = 1, D = 2, L = 3;
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static int T, N, maxCoreCnt, minWireLength;
	static int[][] map;
	static List<Node> cores = new ArrayList<>();
	static int[] mv = {-1, 0, 1, 0, -1}; // U(y:-1,x:0), R(y:0,x:1), D(y:1,x:0), L(y:0,x:-1)

	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("input"));
		br = new BufferedReader(new InputStreamReader(System.in));

		T = parseInt(br.readLine().trim());
		for (int tc = 1; tc <= T; tc++) {
			sb.append('#').append(tc).append(' ');

			N = parseInt(br.readLine().trim());
			map = new int[N][N];
			cores.clear();
			maxCoreCnt = 0;
			minWireLength = MAX_VALUE;

			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine());
				for (int x = 0; x < N; x++) {
					map[y][x] = parseInt(st.nextToken());
					if (!isBorder(y, x) && map[y][x] == CORE) {
						cores.add(new Node(y, x));
					}
				}
			}

			backTracking(0, 0, 0);

			sb.append(minWireLength).append('\n');
		}
		System.out.print(sb);
	}

	/**
	 * 코어 연결
	 */
	static void backTracking(int idx, int coreCnt, int len) {
		if (coreCnt + (cores.size() - idx) < maxCoreCnt) {
			return;
		}

		if (idx == cores.size()) {
			updateResult(coreCnt, len);
			return;
		}

		Node localNode = cores.get(idx);
		int y = localNode.y;
		int x = localNode.x;

		for (int d = U; d <= L; d++) {
			if (canConnect(y, x, d)) {
				int wireLen = setWire(y, x, d, LINE);
				backTracking(idx + 1, coreCnt + 1, len + wireLen);
				setWire(y, x, d, EMPTY);
			}
		}
		backTracking(idx + 1, coreCnt, len);
	}

	/**
	 * 최종 결과 갱신
	 */
	static void updateResult(int coreCnt, int len) {
		if (coreCnt > maxCoreCnt) {
			maxCoreCnt = coreCnt;
			minWireLength = len;
		} else if (coreCnt == maxCoreCnt) {
			minWireLength = Math.min(minWireLength, len);
		}
	}

	/**
	 * 전선 연결 가능 여부 확인
	 */
	static boolean canConnect(int y, int x, int dir) {
		int ny = y, nx = x;
		while (true) {
			ny += mv[dir];     // dy 대신 mv[dir]
			nx += mv[dir + 1]; // dx 대신 mv[dir+1]
			if (!isValid(ny, nx))
				break;
			if (map[ny][nx] != EMPTY)
				return false;
		}
		return true;
	}

	/**
	 * 전선 설치 또는 제거
	 */
	static int setWire(int y, int x, int dir, int val) {
		int len = 0;
		int ny = y, nx = x;
		while (true) {
			ny += mv[dir];     // dy 대신 mv[dir]
			nx += mv[dir + 1]; // dx 대신 mv[dir+1]
			if (!isValid(ny, nx))
				break;
			map[ny][nx] = val;
			len++;
		}
		return len;
	}

	/**
	 * 좌표 유효성 검사
	 */
	static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < N;
	}

	/**
	 * 가장자리 여부 판별
	 */
	static boolean isBorder(int y, int x) {
		return y == 0 || y == N - 1 || x == 0 || x == N - 1;
	}

	/**
	 * 코어 좌표
	 */
	static class Node {
		int y, x;

		Node(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}