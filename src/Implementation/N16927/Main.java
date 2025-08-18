package Implementation.N16927;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 모듈려 연산이 필요할듯
 * 300x300 < 0.1M
 * 이차원 배열 옮기자
 * - 어짜피 그렇게 무겁지않다
 * - 무거웠으면 인덱싱으로 출력만 바꿨을듯
 * 카피배열 쓰자
 * - 어짜피 메모리 넓다
 * - 좁았으면 인덱싱만했을듯
 * 시작 오프셋을 움직이자
 */
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, R, Y, X;
    static int[][] originMap, movedMap;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        Y = N;
        X = M;
        originMap = new int[Y][X];
        movedMap = new int[Y][X];

        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < X; x++) {
                originMap[y][x] = Integer.parseInt(st.nextToken());
            }
        }
    }
    public static int getMaxRollCount(int y, int x){
        return -1;
    }
    public static int getMovedIdx(int y, int x, int offSet){
        return -1;
    }
    public static boolean isValid(int y, int x){
        return y >= 0 && y < Y && x >= 0 && x < X;
    }
}
