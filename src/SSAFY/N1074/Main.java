package SSAFY.N1074;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

/**
 * Z-order traversal 문제를 해결하는 솔루션 클래스
 * 재귀적인 분할 정복 방식을 사용하여 지정된 좌표 (r, c)의 방문 순서를 계산
 */
public class Main {
    
    static long index = 0; // 방문 순서를 저장하는 변수
    static int N, r, c; // 맵의 크기, 행 좌표, 열 좌표
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = (int) Math.pow(2,parseInt(st.nextToken()));
        r = parseInt(st.nextToken());
        c = parseInt(st.nextToken());
        System.out.println(funcZ(N));
    }

    /**
     * 초기 재귀 호출을 위한 메서드
     * @param n 현재 사각형의 한 변 길이
     * @return 지정된 좌표의 방문 순서
     */
    static long funcZ(int n){
        return funcZ(0, 0, n);
    }

    /**
     * 재귀적으로 Z-order를 탐색하는 메서드
     * @param x 현재 사각형의 시작 X 좌표
     * @param y 현재 사각형의 시작 Y 좌표
     * @param n 현재 사각형의 한 변 길이
     * @return 지정된 좌표의 방문 순서
     */
    static long funcZ(int x, int y, int n){
        if (n == 1){
            if (c > x){
                if (r > y){
                    return index+= 3L;
                }else {
                    return index+=1L;
                }
            }else {
                if (r > y){
                    return index+= 2L;
                }else {
                    return index;
                }
            }
        }else {
            int offset = n / 2;
            int size = (int) Math.pow(offset, 2);
            if (c >= x+offset){
                if (r >= y+offset){
                    index+=size* 3L;
                    return funcZ(x+offset, y+offset, offset);
                }else {
                    index+=size;
                    return funcZ(x+offset, y, offset);
                }
            }else {
                if (r >= y+offset){
                    index+=size* 2L;
                    return funcZ(x, y+offset, offset);
                }else {
                    return funcZ(x, y, offset);
                }
            }
        }
    }
}
