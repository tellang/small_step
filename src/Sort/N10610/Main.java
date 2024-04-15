package Sort.N10610;

import static java.util.Collections.reverseOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        char[] arr = br.readLine().toCharArray();
        List<Character> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (var c : arr) {
            list.add(c);
            sum += (c - '0');
        }
        if ((sum % 3) != 0)
            sb.append("-1");
        else {
            list.sort(reverseOrder());
            if (list.get(list.size()-1) == '0'){
                for (Character c : list) {
                    sb.append(c);
                }
            }
            else
                sb.append("-1");
        }
        System.out.println(sb);
    }
}
