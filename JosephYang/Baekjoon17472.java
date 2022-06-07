import java.util.*;
import java.io.*;

public class Baekjoon17472 {
    // KRUSKAL + union find
    static int[] dx = { 0, 0, 1, -1 };
    static int[] dy = { -1, 1, 0, 0 };
    static int map[][], N, M;
    static int[] parent;

    static void dfs(int number, int r, int c, boolean[][] visit) {
        visit[r][c] = true; // 방문체크
        map[r][c] = number;

        for (int k = 0; k < 4; k++) {
            int row = r + dx[k];
            int col = c + dy[k];

            if (row >= N || row < 0 || col >= M || col < 0 ||
                    map[row][col] == 0 || visit[row][col]) {
                continue;
            }

            dfs(number, row, col, visit);
        }
    }

    static int find(int now) {
        if (now == parent[now]) {
            return now;
        }
        parent[now] = find(parent[now]);
        return parent[now];
    }

    static boolean union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot == bRoot)
            return false; // 이미 같은 집합이라면 합치지 않음
        parent[bRoot] = aRoot;
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        boolean[][] visit = new boolean[N][M]; // 방문체크
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int num = 0; // 섬의 개수
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] != 0 && !visit[i][j]) {
                    num++;
                    dfs(num, i, j, visit);
                }
            }
        }

        parent = new int[num + 1]; // 유니온 파인드를 위한 초기화
        for (int i = 1; i <= num; i++) {
            parent[i] = i; // 자기 자신이 부모
        }

        // for(int[] a: map) {
        // System.out.println(Arrays.toString(a));
        // }

        ArrayList<int[]> S = new ArrayList<int[]>();

        // 가로부터 체크
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M - 1; j++) { // 0을 스킵 후 1부터 검사
                if (map[i][j] == 0 && map[i][j - 1] != 0) { // 왼쪽 스타트 포인트 잡기
                    int start = map[i][j - 1];
                    int size = 1;
                    while (j + 1 < M - 1 && map[i][j + 1] == 0) { // 한칸 뒤를 미리 확인 후 가능하면
                        j++; // 확장
                        size++;
                    }
                    if (size >= 2 && map[i][j + 1] != 0) {
                        S.add(new int[] { size, start, map[i][j + 1] });
                        j++;
                    }
                }
            }
        }

        // 세로 체크
        for (int j = 0; j < M; j++) { // j를 1로 그대로 둔 실수 발견
            for (int i = 1; i < N - 1; i++) { // 0을 스킵 후 1부터 검사
                if (map[i][j] == 0 && map[i - 1][j] != 0) { // 왼쪽 스타트 포인트 잡기
                    int start = map[i - 1][j];
                    int size = 1;
                    while (i + 1 < N - 1 && map[i + 1][j] == 0) { // 한칸 뒤를 미리 확인 후 가능하면
                        i++; // 확장
                        size++;
                    }
                    if (size >= 2 && map[i + 1][j] != 0) {
                        S.add(new int[] { size, start, map[i + 1][j] });
                        i++;
                    }
                }
            }
        }

        Collections.sort(S, (o1, o2) -> o1[0] - o2[0]);

        int possible = 1; // 이동 가능한 최소 섬의 개수는 1이다.
        int MAX = 0;
        for (int[] a : S) {
            // System.out.println(Arrays.toString(a));
            int start = a[1];
            int last = a[2];

            if (union(start, last)) {
                possible++;
                MAX += a[0];
            }
        }
        // System.out.println(possible +" "+ num+" "+MAX);
        if (possible != num || S.size() == 0) {
            System.out.println(-1);
        } else {
            System.out.println(MAX);
        }
    }
}
