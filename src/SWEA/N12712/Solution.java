package SWEA.N12712;
/*
 * 10x15x15x15 < 8K
 */
import java.io.*;
import java.util.StringTokenizer;

public class Solution
{
	static int N, T, M, MAX, OFFSET = 20;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[][] map;
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	public static void main(String args[]) throws Exception
	{
		T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			sb.append("#").append(t).append(' ');
			MAX = 0;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N+OFFSET*2][N+OFFSET*2];
			
			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine());
				for (int x = 0; x < N; x++) {
					map[y+OFFSET][x+OFFSET] = Integer.parseInt(st.nextToken());
				
				}
			}
			
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					MAX = Math.max(MAX, getCross(y+OFFSET, x+OFFSET));
					MAX = Math.max(MAX, getX(y+OFFSET, x+OFFSET));
				}
			}
			sb.append(MAX).append('\n');
	
			
		}
		
		System.out.println(sb);
	}
	static int getCross(int y, int x) {
		int result = 0;
		for (int m = 1; m < M; m++) {
			result += map[y+m][x];
			result += map[y][x+m];
			result += map[y-m][x];
			result += map[y][x-m];
		}
		result += map[y][x];
		return result;
	}
	static int getX(int y, int x) {
		int result = 0;
		for (int m = 1; m < M; m++) {
			result += map[y+m][x+m];
			result += map[y-m][x+m];
			result += map[y-m][x-m];
			result += map[y+m][x-m];
		}
		result += map[y][x];
		return result;
	}
}