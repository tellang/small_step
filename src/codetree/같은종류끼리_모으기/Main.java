package codetree.같은종류끼리_모으기;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, A_COUNT, B_COUNT, LEFT, RIGHT, LEFT_COUNT, RIGHT_COUNT, A_MID, B_MID;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        char[] arr = br.readLine().toCharArray();
        LEFT = arr[0];
        RIGHT = arr[arr.length-1];
        int i = 1;
        int present = LEFT;
        boolean isChanged = false;
        for (; i < arr.length && !isChanged; i++) {

            if (arr[i] != present)
                break;
            present = arr[i];
        }
        if (LEFT=='a')
            A_COUNT += i;
        else
            B_COUNT += i;
        LEFT_COUNT += i;

        int j = arr.length-2;
        present = RIGHT;
        for (; j >= 0; j--) {

            if (arr[j] != present)
                break;
            present = arr[j];
        }
        if (RIGHT=='a')
            A_COUNT += (arr.length - j - 1);
        else
            B_COUNT += (arr.length - j - 1);
        RIGHT_COUNT += (arr.length - j - 1);

        for (; i <= j; i++) {
            if(arr[i] == 'a')
                A_MID++;
            else B_MID++;
        }

        if (LEFT != RIGHT){
            System.out.println(min(A_MID, B_MID));
        }else {
            if (A_MID < B_MID) {
                if (LEFT == 'a')
                    System.out.println(A_MID+min(LEFT_COUNT, RIGHT_COUNT));
                else
                    System.out.println(A_MID);
            }
            else {
                if (LEFT == 'b')
                    System.out.println(B_MID+min(LEFT_COUNT, RIGHT_COUNT));
                else
                    System.out.println(B_MID);
            }
        }
    }
}
