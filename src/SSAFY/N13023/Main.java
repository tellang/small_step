package SSAFY.N13023;   

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M; // 사람의 수 N, 친구 관계 수 M
    static ArrayList<Integer>[] member; // 친구 관계를 저장하는 인접 리스트
    static boolean[] visited; // 방문 여부
    
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        member = new ArrayList[N];
        for (int n = 0; n < N; n++) {
            member[n] = new ArrayList<Integer>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            member[a].add(b);
            member[b].add(a);
        }
        
        visited = new boolean[N];

        for (int n = 0; n < N; n++) {
            recur(n, 1);
        }

        System.out.println(0); // 5명 이상의 친구 관계가 없을 경우
    }
    
    /**
     * 깊이가 5인 DFS 경로를 찾는 재귀 메서드
     * @param num 현재 방문 중인 사람의 번호
     * @param count 현재까지의 DFS 깊이
     */
    private static void recur(int num, int count) {
        if (count == 5) {
            System.out.println(1);
            System.exit(0); // 조건 만족 시 즉시 종료
        }
        
        visited[num] = true;

        for (int m : member[num]) {
            if (!visited[m]) {
                recur(m, count + 1);
            }
        }
        visited[num] = false; // 백트래킹
    }
}
