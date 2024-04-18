package Back_Tracking.N14889;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, TOTAL, MIN = MAX_VALUE,
        A_TEAM = -1, B_TEAM = 1;
    static int[][] S;
    static int[] team;
    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine());
        S = new int[N][N];
        for (int y = 0; y < N; y++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int x = 0; x < N; x++) {
                int status = parseInt(st.nextToken());
                S[y][x] = status;
                TOTAL += status;
            }
        }
        team = new int[N];
        Arrays.fill(team, A_TEAM);
        teamDivide(0, 0);
        System.out.println(MIN/2);
    }

    private static void teamDivide(int B_TEAM_COUNT, int index) {
        if ((B_TEAM_COUNT * 2) == N){
            int localMin = 0, aSum = 0, bSum = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i==j)
                        continue;
                    if (team[i] == A_TEAM && team[j] == A_TEAM)
                        aSum+=(S[i][j] + S[j][i]);
                    if (team[i] == B_TEAM && team[j] == B_TEAM)
                        bSum+=(S[i][j] + S[j][i]);
                }
            }
            localMin = abs(aSum-bSum);
            if (localMin < MIN)
                MIN = localMin;
            return;
        }

        for (int n = index; n < N; n++) {
            team[n] = B_TEAM;
            teamDivide(B_TEAM_COUNT+1, n+1);
            team[n] = A_TEAM;
        }
    }
}
