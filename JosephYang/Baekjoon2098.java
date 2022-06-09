import java.util.*;
import java.io.*;

public class Baekjoon2098 {
    static int map[][], N;
    static int[][] dp;
    static int fullVisit;
    static int INF = 9999999;

    static int TSP(int now, int visit) {

        // 모두 방문
        if (visit == fullVisit) {
            if (map[now][0] == 0) { // 도착지에서 처음으로 돌아가는 경로가 없는 경우
                return INF;
            } else {
                return map[now][0];
            }
        }

        if (dp[now][visit] != INF)
            return dp[now][visit];

        for (int i = 0; i < N; i++) {

            int next = visit | (1 << i);

            if (map[now][i] == 0 || ((visit & (1 << i)) != 0))
                continue;

            dp[now][visit] = Math.min(dp[now][visit], TSP(i, next) + map[now][i]);
        }

        return dp[now][visit];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        fullVisit = (1 << N) - 1;
        dp = new int[N][fullVisit];
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(TSP(0, 1));
    }
}