package DFS.N1707;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean isBinary;
    static int K, V, E, RED = -1, BLUE = 1, NULL = 0;
    static List<Integer>[] vertexes;
    static int[] colors;
    static boolean[] isVisited;
    static ArrayDeque<Node> stk;
    static StringBuilder result = new StringBuilder();
    public static void main(String[] args) throws IOException {
        K = parseInt(br.readLine());
        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            V = parseInt(st.nextToken());
            E = parseInt(st.nextToken());
            vertexes = new ArrayList[V+1];
            colors = new int[V+1];
            for (int v = 1; v <= V; v++) {
                vertexes[v] = new ArrayList<>();
            }
            for (int e = 0; e < E; e++) {
                st = new StringTokenizer(br.readLine());
                int a = parseInt(st.nextToken());
                int b = parseInt(st.nextToken());
                vertexes[a].add(b);
                vertexes[b].add(a);
            }
            isBinary = true;
            isVisited = new boolean[V+1];
            for (int v = 1; v <= V; v++) {
                stk = new ArrayDeque<>();
                stk.push(new Node(v, RED));
                dfs();
                if (!isBinary)
                    break;
            }
            if (isBinary)
                result.append("YES");
            else
                result.append("NO");
            result.append('\n');
        }
        System.out.println(result);
    }

    private static void dfs() {
        while (!stk.isEmpty()) {
            Node pop = stk.pop();
            if (!isVisited[pop.idx]){
                int nextColor = pop.color * (-1);
                isVisited[pop.idx] = true;
                colors[pop.idx] = pop.color;
                for (int v : vertexes[pop.idx]) {
                    if (colors[v] == pop.color) {
                        isBinary = false;
                        break;
                    }
                    stk.push(new Node(v, nextColor));
                }
            }

        }
    }
}
class Node {
    int idx, color;

    public Node(int idx, int color) {
        this.idx = idx;
        this.color = color;
    }
}
