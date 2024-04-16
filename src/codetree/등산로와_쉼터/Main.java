package codetree.등산로와_쉼터;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

            if (fromHeight > toHeight) {
                hasRoutes[toIndex][fromIndex] = true;
            }
            else if (fromHeight < toHeight) {
                hasRoutes[fromIndex][toIndex] = true;
            }
        }

        for (Node node : nodes) {
            for (int i = 1; i <= N; i++) {
                if (hasRoutes[node.index][i]){
                    resultVal[node.index] = max(resultVal[node.index], resultVal[i]);
                }
            }
            resultVal[node.index]++;
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
