package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SWEA_SW_2117 {
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int tc = Integer.parseInt(br.readLine());

		for (int TC = 1; TC <= tc; TC++) {
			sb.append("#").append(TC).append(" ");

			st = new StringTokenizer(br.readLine(), " ");

			// map의 크기
			N = Integer.parseInt(st.nextToken());

			// 집의 지불비용
			M = Integer.parseInt(st.nextToken());

			ArrayList<Integer> HX = new ArrayList<>();
			ArrayList<Integer> HY = new ArrayList<>();

			// 집 입력
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					int temp = Integer.parseInt(st.nextToken());
					// 집인 경우 좌표 저장
					if (temp == 1) {
						HX.add(i);
						HY.add(j);
					}
				}
			}

			// map안에 있는 집 개수
			int H = HX.size();
			// 최대 집수
			int max = 0;
			// 줄여가면서 구하기
			for (int k = (int) Math.sqrt(N * N) + 1; k > 0; k--) {
				int cost = k*k + (k-1)*(k-1);
				for(int X=0; X<N; X++) {
					for(int Y=0; Y<N; Y++) {
						// 포함되는 집수
						int cnt = 0;
						// 거리안에 속하면 집 개수 ++
						for(int i=0; i<H; i++) {
							if(Math.abs(HX.get(i)-X) + Math.abs(HY.get(i) - Y)<k) {
								cnt++;
							}
						}
						// 조건 충족 한다면 최대집 개수 갱신
						if(cnt*M >= cost) {
							max = Math.max(max, cnt);
						}
					}
				}
			}
			sb.append(max).append("\n");
		}
		System.out.println(sb);
	}
}
