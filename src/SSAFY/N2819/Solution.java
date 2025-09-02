package SSAFY.N2819;

/**
 * 
 * 2^4 x 2^14 = 2^18 = 1048576/4
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, Y = 4, X = 4;

    static Set<String> set = new HashSet<>();
    static char[][] map;
    static int[] mv = {-1, 0, 1, 0, -1};

    static {
        map = new char[4][4];
    }

    public static void main(String[] args) throws IOException {
        //System.setIn(new FileInputStream("./src/SSAFY/N2819/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            backtarcking(0);
            result.append(set.size()).append('\n');
        }
        System.out.println(result);
    }

    static void backtarcking(int count){
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                backtarcking(count, new char[7], y, x);
            }
        }
    }

    static void backtarcking(int count, char[] arr, int y, int x) {
        
        if(count == 7){
            set.add(Arrays.toString(arr));
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            int ny = y + mv[dir];
            int nx = x + mv[dir + 1];

            if(!isValid(ny, nx)) continue;
            arr[count] = map[ny][nx];
            
            backtarcking(count + 1, arr.clone(), ny, nx);
        }            
    }

    static boolean isValid(int y, int x){
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    static void init() throws IOException {
        
        set.clear();
        for (int y = 0; y < 4; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < 4; x++) {
                map[y][x] = st.nextToken().charAt(0);
            }
        }
    }
}