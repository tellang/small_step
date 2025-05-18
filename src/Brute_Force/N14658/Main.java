package Brute_Force.N14658;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, L, K, MAX = 1;
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        ArrayList<Node> stars = new ArrayList<>();
        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            stars.add(new Node(y, x));
        }
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < K; j++) {
                if (i==j)
                    continue;
                int lx, ly, rx, ry, localMax = 0;
                lx = Math.min(stars.get(i).x, stars.get(j).x);
                ly = Math.min(stars.get(i).y, stars.get(j).y);
                rx = lx + L;
                ry = ly + L;
                for (int k = 0; k < K; k++) {
                    int nx = stars.get(k).x;
                    int ny = stars.get(k).y;
                    if (nx >= lx && ny >= ly && nx <= rx && ny <= ry)
                        localMax++;
                }
                MAX = Math.max(MAX, localMax);
            }
        }
        System.out.println(K-MAX);
    }
}
class Node {
    int y, x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public String toString() {
        return "Node{" +
            "y=" + y +
            ", x=" + x +
            '}';
    }
}
