package DP.N14699;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M;
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        boolean[][] hasRoutes = new boolean[N + 1][N + 1];
        int[] nodeHeights = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        int[] resultVal = new int[N+1];
        List<Node> nodes = new ArrayList<>();
        for (int n = 1; n <= N; n++) {
            int height = parseInt(st.nextToken());
            nodeHeights[n] = height;
            nodes.add(new Node(n, height));
        }
        nodes.sort(((o1, o2) -> o1.height < o2.height ? o1.height : -o2.height));

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int fromIndex = parseInt(st.nextToken());
            int fromHeight = nodeHeights[fromIndex];
            int toIndex = parseInt(st.nextToken());
            int toHeight = nodeHeights[toIndex];
            if (fromHeight < toHeight) {
                hasRoutes[toIndex][fromIndex] = true;
            }
            else if (fromHeight > toHeight) {
                hasRoutes[fromIndex][toIndex] = true;
            }

        }

        for (Node node : nodes) {
            if (resultVal[node.index] != 0)
                continue;
            Queue<VisitNode> q = new ArrayDeque<>();
            q.offer(new VisitNode(node.index, 1));
            while (!q.isEmpty()){
                VisitNode poll = q.poll();
                if (resultVal[poll.index] < poll.visited){
                    resultVal[poll.index] = poll.visited;

                    for (int i = 1; i <= N; i++) {
                        if (hasRoutes[poll.index][i]){
                            q.offer(new VisitNode(i, poll.visited+1));
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            result.append(resultVal[i]).append('\n');
        }

        System.out.println(result);
    }
}

class Node {
    public Node(int index, int height) {
        this.index = index;
        this.height = height;
    }

    int index;
    int height;
}
class VisitNode {
    int index;
    int visited;

    public VisitNode(int index, int visited) {
        this.index = index;
        this.visited = visited;
    }
}
