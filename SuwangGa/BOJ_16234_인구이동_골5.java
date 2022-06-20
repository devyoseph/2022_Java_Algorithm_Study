import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_16234_인구이동_골5 {
	static int N;
	static int L;
	static int R;
	static int [][]  map; //인구수 저장용
	static int[][][] open;//국경 열리는지 여부
	static boolean[][] check;//방문여부
	static int dx[] = {1,0,-1,0};
	static int dy[] = {0,1,0,-1};
	
	
	public static void main(String[] args) throws IOException {
		//입력받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		
		int res = 0; //국경선 열린 날짜수
		while(true) {
			//국경선 열림 체크 배열 초기화
			open = new int[N][N][4];
			//연합 형성여부 확인 연합 형성 안되면 break
			if(!checkOpen()) break;
			//방문 배열 초기화
			check = new boolean[N][N];
			//bfs로 연합 평균 인구수 구하기 
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(check[i][j]) continue; //방문 했으면 true
					if(open[i][j][0]==0&&open[i][j][1]==0&&open[i][j][2]==0&&open[i][j][3]==0) continue; //연합 포함 안된 국가 건너뜀
					int cnt = 0; //연합 이루는 국가수
					int sum = 0; //연합 총 인구수
					check[i][j] = true; //현재 위치 방문처리
					Queue<int[]> q = new LinkedList<int[]>(); //bfs를 위한 큐
					q.offer(new int [] {i,j}); //현재 위치 큐에 넣기
					while(!q.isEmpty()) {
						int[] cur = q.poll(); //현재위치 뽑기
						cnt++; //국가수+1
						sum += map[cur[0]][cur[1]]; //인구수 추가
						for(int k=0;k<4;k++) {
							if(open[cur[0]][cur[1]][k]!=1) continue; //이웃과 국경선 열었는지 체크
							//이웃 국가 좌표
							int nx = cur[1]+dx[k]; 
							int ny = cur[0]+dy[k];
							
							if(check[ny][nx]) continue; //방문한 국가이면 건너뜀
							q.offer(new int[] {ny,nx}); //방문하지 않았으면 큐에 넣기
							check[ny][nx] = true;//방문처리
						}
					}
					map[i][j] = sum/cnt; //현재 위치가 bfs 시작점이 되기때문에 현재위치에 평균 인구수 저장
				}
			}
			//bfs로 갱신
			for(int i=0; i<N;i++) {
				for(int j=0;j<N;j++) {
					if(!check[i][j]) continue; //방문하지 않았다면 연합 형성하지 않는 국가, 건너뜀
					int avg = map[i][j]; //bfs시작위치에 저장된 평균인구수 
					check[i][j] = false; //이동가능 여부 false
					Queue<int[]> q = new LinkedList<int[]>();
					q.offer(new int [] {i,j});
					while(!q.isEmpty()) {
						int [] cur = q.poll();
						map[cur[0]][cur[1]] = avg; //현재 위치 평균인구수로 갱신
						for(int k=0;k<4;k++) {
							if(open[cur[0]][cur[1]][k]!=1) continue; //국경선이 열려있지 않으면 건너뜀
							//다음 국가 좌표
							int nx = cur[1] +dx[k];
							int ny = cur[0] + dy[k];
							
							//방문 할수 없다면 건너뜀
							if(!check[ny][nx]) continue;
							q.offer(new int[] {ny,nx});
							check[ny][nx] =false;
						}
					}
				}
			}
			res++; //날짜++
		}
		System.out.println(res);
	}
	public static boolean checkOpen() {
		boolean isOpen = false; //연합 형성 여부
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				for(int k=0;k<4;k++) {
					//이웃 좌표
					int nx = j+dx[k];
					int ny = i+dy[k];
					
					if(nx<0||nx>=N||ny<0||ny>=N) continue; //범위 체크
					int temp = Math.abs(map[i][j]-map[ny][nx]);	//인구 차이
					if(temp<L||temp>R) continue; //인구 차이가 L,R 밖이면 건너뜀
					open[i][j][k] = 1;
					isOpen = true; //위 과정 수행시 연합형성 가능
				}
			}
		}	
		return isOpen; //연합 형성 여부 반환
	}
}
