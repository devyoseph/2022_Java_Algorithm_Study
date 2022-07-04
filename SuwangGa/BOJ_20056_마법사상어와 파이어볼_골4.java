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

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int dx[] = {0,1,1,1,0,-1,-1,-1};
		int dy[] = {-1,-1,0,1,1,1,0,-1};
		int map[][][][] = new int [N][N][2][5]; 
		//0 = m 질량, 1 = s 속력, 2 = d 방향, 3 = (0 = 짝수만, 1=홀수만, 2 = 홀수짝수섞임),4=파이어볼 갯수
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			map[r][c][0][0] = Integer.parseInt(st.nextToken());
			map[r][c][0][1] = Integer.parseInt(st.nextToken());
			map[r][c][0][2] = Integer.parseInt(st.nextToken());
			map[r][c][0][4] = 1;
		}
		
		for(int k=0;k<K;k++) {
			//파이어볼 이동
			for(int i=0; i<N;i++) {
				for(int j=0; j<N;j++) {
					if(map[i][j][0][0]==0) continue;
					if(map[i][j][0][4]==1) { //1개짜리
			
						int nextX = j + (dx[map[i][j][0][2]]==0?0:map[i][j][0][1]*dx[map[i][j][0][2]]); //dx가 0이 아니면 dx * 속도
						int nextY = i + (dy[map[i][j][0][2]]==0?0:map[i][j][0][1]*dy[map[i][j][0][2]]); //dy가 0이 아니면 dy * 속도
						
						//배열 범위 넘어갔을때 처리
						if(nextX<0) nextX = N- (Math.abs(nextX)%N);
						if(nextX>N-1) nextX = nextX%N;
						if(nextY<0) nextY = N- (Math.abs(nextY)%N);
						if(nextY>N-1) nextY =nextY%N;
						
						//현재 위치에 있는 파이어볼의 방향이 짝수, 홀수, 섞임 판단
						
						//이동하려는 위치에 파이어볼이 없으면
						if(map[nextY][nextX][1][4]==0) {
							map[nextY][nextX][1][3] = map[i][j][0][2] % 2; //나머지 저장
						}
						//이동하려는 위치에 파이어볼이 있으면
						else { 
							if(map[nextY][nextX][1][3]!=map[i][j][0][2]%2) map[nextY][nextX][1][3] = 2; //미리 저장된 나머지와 현재 움직이는 파이어볼의 나머지가 다르면 2저장
						}
						
						
						map[nextY][nextX][1][0] += map[i][j][0][0]; //질량 합치기
						map[nextY][nextX][1][1] += map[i][j][0][1]; //속도 합치기
						map[nextY][nextX][1][2] = map[i][j][0][2]; //방향 유지, 여러개는 4등분되니까 생각x					
						map[nextY][nextX][1][4]++;	//파이어볼 갯수+1
						
						//이전 위치 파이어볼 초기화
						for(int l=0;l<5;l++) {
							map[i][j][0][l] = 0;
						}
					}
					else {//여러개 합쳐진부분
						map[i][j][0][0] = map[i][j][0][0] / 5; //질량 나누기
						map[i][j][0][1] = map[i][j][0][1] / map[i][j][0][4]; //속도 나누기
						if(map[i][j][0][0] == 0) { //질량 0이면 소멸
							for(int l=0;l<5;l++) {
								map[i][j][0][l] = 0;
							}
							continue;
						}
						
						//합쳐진 파이어볼 4방향으로 보내기
						for(int l = map[i][j][0][3]==2?1:0; l<8; l+=2) { //홀수 짝수 섞여 있으면 l이 1부터 시작 -> 1,3,5,7방향이동 , 그렇지 않으면 0부터 시작 -> 0,2 4 6 방향이동
							
							//다음 좌표 구하기
							int nextX = j + (dx[l]==0?0:map[i][j][0][1]*dx[l]); //다음 좌표 구하기
							int nextY = i + (dy[l]==0?0:map[i][j][0][1]*dy[l]);
							if(nextX<0) nextX = N- (Math.abs(nextX)%N);
							if(nextX>N-1) nextX = nextX%N;
							if(nextY<0) nextY = N- (Math.abs(nextY)%N);
							if(nextY>N-1) nextY =nextY%N;
							
							//다음 위치 홀수 짝수 저장
							if(map[nextY][nextX][1][4]==0) {
								map[nextY][nextX][1][3] = l % 2;
							}
							else {
								if(map[nextY][nextX][1][3]!=l%2) map[nextY][nextX][1][3] = 2;
							}
							
							map[nextY][nextX][1][0] += map[i][j][0][0]; //질량 합치기
							map[nextY][nextX][1][1] += map[i][j][0][1]; //속도 합치기
							map[nextY][nextX][1][2] = l;				
							map[nextY][nextX][1][4]++;
						}
						for(int l=0;l<5;l++) {
							map[i][j][0][l] = 0;
						}
					}				
				}
			}
			//이동후 배열, 이동전 배열에 저장하기
			for(int i=0; i<N;i++) {
				for(int j=0; j<N;j++) {
					for(int l=0;l<5;l++) {
						map[i][j][0][l] = map[i][j][1][l];
						map[i][j][1][l] = 0;
					}					
				}
			}
		}
		int res = 0;
		for(int i=0; i<N;i++) {
			for(int j=0; j<N;j++) {
				res += (map[i][j][0][4]==1?map[i][j][0][0]:(map[i][j][0][0]/5)*4); // 파이어볼 갯수가 1개가 아니면 합쳐진 질량 저장, 하지만 파이어볼은 4개로 나뉜상태여야함
				//그래서 (질량 /5)*4 로 현재 위치에 존재하는 4개의 파이어볼 질량의 합 구해서 결과에 더함 
			}
		}
		System.out.println(res);

	}
		
		
	

}
