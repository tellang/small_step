package SSAFY.N17845;
/**
 * 일반 냅색, 중복 X 역순
 * int subject[T_time] = I_benefit
 * subject[T_time] = max(subject[T_time - t] + i, subject[T_time])
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, MAX_TIME, K, MAX_SUB; 
    static int[] subjects;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N17845/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        getSubjects();
        System.out.println(subjects[MAX_TIME]);
    }

    static void getSubjects() throws IOException {
        for (int sub = 0; sub < MAX_SUB; sub++) {
            st = new StringTokenizer(br.readLine().trim());
            int benefit = parseInt(st.nextToken());
            int time = parseInt(st.nextToken());

            for (int t = MAX_TIME; t >= time; t--) {
                subjects[t] = Math.max(subjects[t - time] + benefit, subjects[t]);
            }
        }
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        MAX_TIME = N = parseInt(st.nextToken());
        MAX_SUB = K = parseInt(st.nextToken());
        
        subjects = new int[MAX_TIME + 1];
    }
}