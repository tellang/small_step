package SSAFY.N5644;

/**
 * BCField 10x10 - 1basedIdx 배터리 최대 8개
 *
 * APs[] 로 충전량 저장 - 1basedIdx BCField[][] 에 비트마스킹 (apMask)으로 APsIdx 저장
 *
 * 기본적으로 man1과 man2가 움직인 BCField[][]의 비트마스킹된 APsIdx(apMask)를 이용해 충전량을 계산 겹치는 필드가
 * 있는경우 - 두 사람이 선택할 수 있는 AP의 모든 조합을 고려하여 최대 충전량을 계산
 *
 * --- 트릭
 * man1Mv man2Mv 는 원래 0based지만 0번째에 정지 상태를 추가해서 정지가 추가된 1based
 * - move 후 charge 인데 charge 시작 charge 종료를 맞춰주기 위함
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, M, A, totalResultSum, Y = 10, X = 10;
    static int[][] BCField; // 해당하는 APs 의 idx를 마스킹으로 표시하는 2차원 배열 1based
    static int[] APs, man1Mv, man2Mv; // AP 충전량 1based, man1 이동 정보 0based 0 번째에 정지 추가, man2 이동 정보 0based0 번째에 정지 추가
    static int[] dy = {0, -1, 0, 1, 0}, 
                 dx = {0, 0, 1, 0, -1}; // 이동 방향 0based
    static Node man1, man2; // man1, man2의 현재 위치 1based

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5644/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            st = new StringTokenizer(br.readLine().trim());
            BCField = new int[Y + 1][X + 1];
            totalResultSum = 0;
            man1 = new Node(1, 1);
            man2 = new Node(Y, X);
            M = parseInt(st.nextToken());
            A = parseInt(st.nextToken());
            man1Mv = new int[M + 1];
            man2Mv = new int[M + 1];

            StringTokenizer man1MvData = new StringTokenizer(br.readLine().trim());
            StringTokenizer man2MvData = new StringTokenizer(br.readLine().trim());

            for (int m = 1; m <= M; m++) {
                man1Mv[m] = parseInt(man1MvData.nextToken());
                man2Mv[m] = parseInt(man2MvData.nextToken());
            }

            APs = new int[A + 1];
            for (int APsIdx = 1; APsIdx <= A; APsIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                int x = parseInt(st.nextToken());
                int y = parseInt(st.nextToken());
                int c = parseInt(st.nextToken());
                int p = parseInt(st.nextToken());
                APs[APsIdx] = p;
                markAPField(y, x, c, APsIdx);
            }

            for (int m = 0; m <= M; m++) {
                move(m);
                charge();
            }

            result.append(totalResultSum).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 해당 시간의 최대 충전량을 계산하여 totalResultSum에 더함
     */
    static void charge() {
        int m1Mask = BCField[man1.y][man1.x];
        int m2Mask = BCField[man2.y][man2.x];
        int maxChargePerTime = 0;

        if (m1Mask != 0 && m2Mask != 0) {
            // [CASE 1] 둘 다 충전 가능한 AP가 있을 때 (가장 복잡한 경우)
            for (int m1APsIdx = 1; m1APsIdx <= A; m1APsIdx++) {
                for (int m2APsIdx = 1; m2APsIdx <= A; m2APsIdx++) {
                    if (((m1Mask & (1 << m1APsIdx)) != 0) && ((m2Mask & (1 << m2APsIdx)) != 0)) {
                        int localSum = (m1APsIdx == m2APsIdx) ? APs[m1APsIdx] : APs[m1APsIdx] + APs[m2APsIdx];
                        maxChargePerTime = Math.max(maxChargePerTime, localSum);
                    }
                }
            }
        } else if ((m1Mask != 0) || (m2Mask != 0)) {
            for (int apsIdx = 1; apsIdx <= A; apsIdx++) {
                if (((m1Mask & (1 << apsIdx)) != 0)) {
                    maxChargePerTime = Math.max(maxChargePerTime, APs[apsIdx]);
                } else if (((m2Mask & (1 << apsIdx)) != 0)) {
                    maxChargePerTime = Math.max(maxChargePerTime, APs[apsIdx]);
                }
            }
        }

        totalResultSum += maxChargePerTime;
    }

    /**
     * 사용자의 위치를 이동시키는 메서드
     *
     * @param idx 이동 정보 인덱스
     */
    static void move(int idx) {
        move(man1, man1Mv, idx);
        move(man2, man2Mv, idx);
    }

    /**
     * 특정 사용자의 위치를 이동
     *
     * @param man 이동할 사용자
     * @param manMv 이동 정보 배열
     * @param idx 이동 정보 인덱스
     */
    static void move(Node man, int[] manMv, int idx) {
        int dOffset = manMv[idx];
        int ny = man.y + dy[dOffset];
        int nx = man.x + dx[dOffset];

        man.y = ny;
        man.x = nx;
    }

    /**
     * AP의 충전 범위를 BCField에 마킹
     *
     * @param y AP의 y 좌표
     * @param x AP의 x 좌표
     * @param c 충전 범위
     * @param APsIdx AP 인덱스
     */
    static void markAPField(int y, int x, int c, int APsIdx) {
        int mask = 1 << APsIdx;

        for (int ny = 1; ny <= Y; ny++) {
            for (int nx = 1; nx <= X; nx++) {
                if (Math.abs(y - ny) + Math.abs(x - nx) <= c) {
                    BCField[ny][nx] |= mask;
                }
            }
        }
    }

    /**
     * 사용자의 좌표를 저장하는 내부 클래스
     */
    static class Node {

        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
