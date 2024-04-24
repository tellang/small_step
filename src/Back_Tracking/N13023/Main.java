package Back_Tracking.N13023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static List<Integer>[] member;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.    readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        member = new ArrayList[N];
        for (int n = 0; n < N; n++) {
            member[n] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            member[a].add(b);
            member[b].add(a);
        }
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            recur(i, 1);
        }

        System.out.println(0);
    }
    private static void recur(int num, int count) {
        if (count == 5) {
            System.out.println(1);
            System.exit(0);
        }
        visited[num] = true;

        for (int m : member[num]) {
            if (!visited[m]) {
                recur(m, count + 1);
            }
        }
        visited[num] = false;
    }
}

