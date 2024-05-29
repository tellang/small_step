package DFS.N17471;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, RED_COUNT = 0, BLUE_COUNT, MIN = MAX_VALUE, TOTAL_POPULATION = 0;
    static boolean RED = true, BLUE = false;
    static boolean[] isRed, isVisited;
    static int[] population;
    static ArrayList<Integer>[] nodes;
    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine()); //1 line
        population = new int[N+1];
        nodes = new ArrayList[N+1];
        isRed = new boolean[N+1];
        isVisited = new boolean[N+1];
        st = new StringTokenizer(br.readLine()); //2 line
        for (int n = 1; n <= N; n++) {
            population[n] = parseInt(st.nextToken());
            TOTAL_POPULATION += population[n];
            nodes[n] = new ArrayList<>();
        }
        for (int n = 1; n <= N; n++) {
            st = new StringTokenizer(br.readLine()); //3 ~ n+3 line
            int num = parseInt(st.nextToken());
            for (int i = 0; i < num; i++) {
                int nodeNum = parseInt(st.nextToken());
                nodes[n].add(nodeNum);
                nodes[nodeNum].add(n);
            }
        }


        BLUE_COUNT = N;
        backtracking(1);
        if (MIN == MAX_VALUE)
            MIN = -1;
        System.out.println(MIN);
    }
    private static void backtracking(int idx) {
        if (idx > N) {
            if (RED_COUNT != N && isDivided()) {
                int redPop = getRedPop();
                int bluePop = TOTAL_POPULATION - redPop;
                MIN = min(MIN, abs(bluePop - redPop));
            }
            return;
        }
        if (!isVisited[idx]) {
            isVisited[idx] = true;

            isRed[idx] = true;
            RED_COUNT++;
            BLUE_COUNT--;
            backtracking(idx+1);
            isRed[idx] = false;
            RED_COUNT--;
            BLUE_COUNT++;
            backtracking(idx+1);
            isVisited[idx] = false;
        }
    }
    private static boolean isDivided() {
        boolean isRedHasLine = false;
        boolean isBlueHasLine = false;
        for (int n = 1; n <= N; n++) {
            if (isRed[n]) {
                isRedHasLine = getRedNum(n) == RED_COUNT;
            }else
                isBlueHasLine = getBlueNum(n) == BLUE_COUNT;
        }
        return isRedHasLine && isBlueHasLine;
    }
    private static int getRedNum(int start) {
        int redCount = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1];
        stack.push(start);
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            if (visited[pop])
                continue;
            visited[pop] = true;
            redCount++;
            for (int node : nodes[pop]) {
                if (isRed[node])
                    stack.push(node);
            }
        }
        return redCount;
    }
    private static int getBlueNum(int start) {
        int blueCount = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1];
        stack.push(start);
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            if (visited[pop])
                continue;
            visited[pop] = true;
            blueCount++;
            for (int node : nodes[pop]) {
                if (!isRed[node])
                    stack.push(node);
            }
        }
        return blueCount;
    }
    private static int getRedPop() {
        int redPop = 0;
        for (int n = 1; n <= N; n++) {
            if (isRed[n])
                redPop += population[n];
        }
        return redPop;
    }
}
