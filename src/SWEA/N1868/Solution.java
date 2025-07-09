package SWEA.N1868;
/*
 * �� ������ ���� ����
 * 
 * mineValue �� 0�� ��찡 �ִ� ��츸 
 *  - 9���� ���� mineValue �� 0�� �Ⱥ��϶����� TIME--l
 *  
 * 
 * �ƴѰ�� �ش���ġ ��;
 * 
 */
import java.util.*;
import java.io.*;


class Solution
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int T, N, TIME;
    static int[][] valueMap;
    static char[][] map;
    static StringBuilder sb = new StringBuilder();
    static int[] dy = {-1, -1, -1, 0, 1, 1, 1, 0},
   				  dx = {-1, 0, 1, 1, 1, 0, -1, -1};
    static ArrayDeque<Node> q = new ArrayDeque<>();
    static List<Node> eList = new ArrayList<>();
    static boolean[][] visited;
	public static void main(String args[]) throws Exception
	{

		T = Integer.parseInt(br.readLine());

		for(int t = 1; t <= T; t++)
		{
            sb.append("#").append(t).append(" ");
			q.clear();
			N = Integer.parseInt(br.readLine());
			eList.clear();
            valueMap = new int[N][N];
            map = new char[N][N];
            visited = new boolean[N][N];
			
            for(int y = 0; y < N; y++){
            	map[y] = br.readLine().toCharArray();
            }
            TIME = N*N;
            fillValue();
            
            for (Node n : eList) {
            	if(visited[n.y][n.x]) continue;
                
                bfs(new Node(n.y, n.x));
			} 
            sb.append(TIME).append("\n");
		}
        System.out.println(sb);
	}
    static void bfs(Node s){
         visited[s.y][s.x] = true;
         q.offer(s);
    	while(!q.isEmpty()){
            Node poll = q.poll();

            for(int i = 0; i < 8; i++){
                int ny = poll.y + dy[i];
                int nx = poll.x + dx[i];
                if(valid(ny, nx) && !visited[ny][nx]){
                    visited[ny][nx] = true;
                    if (valueMap[ny][nx] == 0){
                        q.offer(new Node(ny, nx));   
                    }
                    TIME--;
                }
            }
        }
    }
    static void fillValue(){
    	for(int y = 0; y < N ; y++){
            for (int x = 0; x <N; x++){
                int mineValue = 0;
                if (map[y][x] == '*') {
                 	valueMap[y][x] = 9;
                    visited[y][x] = true;
                    TIME--;
                    continue;
                }
                for(int i = 0; i < 8; i++){
                    int ny = y + dy[i];
                    int nx = x + dx[i];
                    if(valid(ny, nx) && map[ny][nx] == '*'){
						mineValue++;
                    }
                }
                if (mineValue == 0) {
					eList.add(new Node(y, x));
				}
                valueMap[y][x] = mineValue;
            }
        }
    }
    
    static boolean valid(int y, int x){
        return y >=0 && y < N && x >=0 && x < N;
    }
}
class Node {
 	int y, x;
    public Node (int y, int x){
     	this.y = y;
        this.x = x;
    }
}