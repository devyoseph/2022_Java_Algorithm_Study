import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.*;

public class Baekjoon1238 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        int MAX = 0;

        int[][] arr = new int[N + 1][N + 1];

        for (int i = 0; i < M; i++) { // 2차원 배열에 단방향 그래프 기록
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            arr[a][b] = t;
        }

        // X마을에서 임의의 마을 n까지 최소 경로 구하기
        int[] go = new int[N + 1]; // 오는 경로 중 최소 경로 기록
        Arrays.fill(go, Integer.MAX_VALUE);
        go[X] = 0; // 시작 부분 비우기
        boolean[] visit = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {

            int MIN = Integer.MAX_VALUE;
            int idx = 0;

            for (int j = 1; j <= N; j++) {
                if (!visit[j] & go[j] < MIN) {
                    MIN = go[j];
                    idx = j;
                }
            }

            visit[idx] = true;

            for (int j = 1; j <= N; j++) {
                if (!visit[j] && arr[j][idx] != 0 &&
                        go[j] > go[idx] + arr[j][idx]) {
                    go[j] = go[idx] + arr[j][idx];
                }
            }
        }
        // System.out.println(Arrays.toString(go));

        // X마을에서 임의의 마을 n까지 최소 경로 구하기
        int[] back = new int[N + 1]; // 오는 경로 중 최소 경로 기록
        Arrays.fill(back, Integer.MAX_VALUE);
        back[X] = 0; // 시작 부분 비우기
        visit = new boolean[N + 1]; // 방문체크배열 초기화

        for (int i = 1; i <= N; i++) {

            int MIN = Integer.MAX_VALUE;
            int idx = 0;

            for (int j = 1; j <= N; j++) {
                if (!visit[j] & back[j] < MIN) {
                    MIN = back[j];
                    idx = j;
                }
            }
            visit[idx] = true;

            for (int j = 1; j <= N; j++) {
                if (!visit[j] && arr[idx][j] != 0 &&
                        back[j] > back[idx] + arr[idx][j]) {
                    back[j] = back[idx] + arr[idx][j];
                }
            }
        }
        // System.out.println(Arrays.toString(back));

        for (int i = 0; i <= N; i++) {
            if (go[i] != Integer.MAX_VALUE && back[i] != Integer.MAX_VALUE && MAX < go[i] + back[i]) {
                MAX = go[i] + back[i];
            }
        }

        System.out.println(MAX);
    }

}