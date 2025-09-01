package SSAFY.N16930;

import java.io.*;
import java.util.*;

/**
 * value 맵을 이용한 가지치기
 *   
 * if(visited[ny][nx] < nt) break;
 * if(visited[ny][nx] == nt) continue;
 * 
 * 이게 핵심
 * 
 */
public class Main {

	static BufferedReader br;
	static StringTokenizer st;
	
	static int N, Y = 1001, M, X = 1001, K, MOVE_COUNT = 1000;
	static Node START, END;
	static Queue<Node> q;
	
	static char[][] map; //1based
	static int[][] visited; //1based
	static char WALL = '#';
	static int[] mv = {-1, 0, 1, 0, -1};
	
	static {
		br = new BufferedReader(new InputStreamReader(System.in));
		q = new ArrayDeque<>();
		visited = new int[Y][X]; //1based
		map = new char[Y][X]; //1based
	}
	public static void main(String[] args) throws IOException {
		
		init();
		int time = bfs();
		System.out.println(time);
	}
	
	
	static void init() throws IOException {
		st = new StringTokenizer(br.readLine().trim());
		N = Y = Integer.parseInt(st.nextToken());
		M = X = Integer.parseInt(st.nextToken());
		K = MOVE_COUNT = Integer.parseInt(st.nextToken());
		
		q.clear();
		for (int y = 1; y <= Y; y++) { //1based
			char[] row = br.readLine().trim().toCharArray();
			for (int x = 1; x <= X; x++) {
				map[y][x] = row[x-1];
			}
			Arrays.fill(visited[y], Integer.MAX_VALUE);
		}
		
		st = new StringTokenizer(br.readLine().trim());
		int y = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		
		visited[y][x] = 0;
		START = new Node(y, x, 0);
		q.offer(START);
		
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		
		END = new Node(y, x, 0);
		
	}
	
	static int bfs() {
		while(!q.isEmpty()) {
			Node poll = q.poll();
			if(poll.equals(END)) {
				return poll.time;
			}
			
			for (int mIdx = 0; mIdx < 4; mIdx++) {
				
				int nt = poll.time + 1;
				for (int mc = 1; mc <= MOVE_COUNT; mc++) {
					int ny = poll.y + mv[mIdx] * mc;
					int nx = poll.x + mv[mIdx + 1] * mc;
					
					if(!valid(ny, nx)) break;
					if(map[ny][nx] == WALL) break;
					if(visited[ny][nx] < nt) break;
                    if(visited[ny][nx] == nt) continue;


					q.offer(new Node(ny, nx, nt));
					visited[ny][nx] = nt;
					if(END.y == ny && END.x == nx)
						return nt;
					
				}
				
			}
		}
		
		return -1;
	}
	
	static boolean valid(int y, int x) {//1based
		return y > 0 && y <= Y && x > 0 && x <= X;
	}
	
	
	static class Node {
		int y, x, time;

		public Node(int y, int x, int time) {
			super();
			this.y = y;
			this.x = x;
			this.time = time;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		
	}
}

