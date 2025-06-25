package BFS.N2174;
/*
1. 로봇은 여러개다
    A. 로봇은 하나씩 움직인다
        a. 명령은 최대 100개
    B. 로봇끼리 충돌하거나 맵 벗어나면 오류메시지 출력후 종료
    C. 문제가 없으면 OK 출력
    D. 로봇은 1부터 시작한다
 */

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int X, Y, N, M;
	static Robot[] robots;
	static int[]
		dy = {0, 1, 0, -1},
		dx = {1, 0, -1, 0}; // E, S, W, N;
	static int[][] isRobots;

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());
		X = parseInt(st.nextToken());
		Y = parseInt(st.nextToken());

		isRobots = new int[Y][X];
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		var sb = new StringBuilder();

		robots = new Robot[N + 1];
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			var x = parseInt(st.nextToken());
			var y = parseInt(st.nextToken());
			y = Y - y;
			x = x - 1;
			char wise = st.nextToken().charAt(0);
			robots[n] = new Robot(y, x, getWiseIdx(wise));
			isRobots[y][x] = n;
		}
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			var botNum = parseInt(st.nextToken());
			var action = st.nextToken().charAt(0);
			var count = parseInt(st.nextToken());
			var bot = robots[botNum];
			int ny = bot.y;
			int nx = bot.x;
			if (action == 'F') {
				for (int c = 0; c < count; c++) {
					ny += dy[bot.wiseIndex];
					nx += dx[bot.wiseIndex];
					if (isValid(ny, nx)) {
						if (isRobots[ny][nx] > 0) {
							sb.append("Robot ").append(botNum).append(" crashes into robot ").append(isRobots[ny][nx]);
							System.out.println(sb);
							return;
						}
					} else {
						sb.append("Robot ").append(botNum).append(" crashes into the wall");
						System.out.println(sb);
						return;
					}
				}
				isRobots[ny][nx] = isRobots[bot.y][bot.x];
				isRobots[bot.y][bot.x] = 0;
				bot.y = ny;
				bot.x = nx;
			} else if (action == 'L') {
				bot.wiseIndex = (bot.wiseIndex - count % 4 + 4) % 4;
			} else if (action == 'R') {
				bot.wiseIndex = (bot.wiseIndex + count) % 4;
			}
		}
		System.out.println("OK");
	}

	private static int getWiseIdx(char wise) {
		if (wise == 'E') {
			return 0;
		} else if (wise == 'S') {
			return 1;
		} else if (wise == 'W') {
			return 2;
		} else if (wise == 'N') {
			return 3;
		} else {
			throw new IllegalArgumentException("Unrecognized wise character: " + wise);
		}
	}

	private static boolean isValid(int y, int x) {
		return y >= 0 && y < Y && x >= 0 && x < X;
	}

}

class Robot {
	int y, x, wiseIndex;

	public Robot(int y, int x, int wiseIndex) {
		this.y = y;
		this.x = x;
		this.wiseIndex = wiseIndex;
	}

	@Override
	public String toString() {
		return "Robot{" +
			"y=" + y +
			", x=" + x +
			", wise=" + wiseIndex +
			'}';
	}
}
