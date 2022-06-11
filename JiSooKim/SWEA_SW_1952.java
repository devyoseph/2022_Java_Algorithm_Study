package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author us13579
 * @since 220611
 * @title 수영장
 */

public class SWEA_SW_1952 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int[] price;
    static int[] month;
    static int min;
    public static void main(String[] args) throws IOException {
        int tc = Integer.parseInt(br.readLine());
        for(int t=1;t<=tc;t++){
            sb.append("#").append(t).append(" ");
            price = new int[4];
            month = new int[12];

            // 가격 입력
            st = new StringTokenizer(br.readLine()," ");
            for(int i=0; i<4; i++){
                price[i] = Integer.parseInt(st.nextToken());
            }

            // 수영장 이용 계획
            st = new StringTokenizer(br.readLine()," ");
            for(int i=0; i<12; i++){
                month[i] = Integer.parseInt(st.nextToken());
            }

            min = price[3];
            dfs(0,0);
            sb.append(min).append("\n");
        }
        System.out.println(sb);
    }
    static void dfs(int cnt, int sum){
        if(cnt>=12){
            min = Math.min(min,sum);
            return;
        }

        // 이용계획이 없다면
        if(month[cnt] == 0){
            dfs(cnt+1, sum);
        }
        else {
            // 일
            dfs(cnt + 1, sum + (month[cnt] * price[0]));
            // 1개월
            dfs(cnt + 1, sum + price[1]);
            // 3개월
            dfs(cnt + 3, sum + price[2]);
        }
    }
}
