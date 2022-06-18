import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Baekjoon3190 {
	static int N, K, L, nowT = 0, nowD = 0; //크기, 사과 개수, 방향전환, 현재 시간, 현재 방향
	static int[][] map;
	static int[] tail, head;
	
	static ArrayList<int[]> snake = new ArrayList<>(); // 뱀 정보 반환
	
	static int[] dx = {0, 1, 0, -1}; //우 하 좌 상
	static int[] dy = {1, 0, -1, 0};
	
	static int res = 0; //결과값 반환
	static boolean found = false; //찾았음을 나타내는 불린 변수
	
	static void move() { //머리의 위치
		res++;
//		for(int[] a: map) {
//			System.out.println(Arrays.toString(a));
//		}
		//System.out.println("방향: "+nowD);
		int[] head = snake.get(snake.size()-1);
		int nextR = head[0] + dx[nowD];
		int nextC = head[1] + dy[nowD];
		//System.out.println("head : "+nextR +" "+nextC);
		if(nextR < 0 || nextR >= N || nextC < 0 || nextC >= N
				|| map[nextR][nextC] == -1) { //경계값을 벗어나거나 몸이라면
			found = true;
			return;
		}
		
		snake.add(new int[] {nextR, nextC}); //머리 추가 
		
		if(map[nextR][nextC]==1) { //사과가 있다면 꼬리제거 X 
//			K--; //사과 개수 감소
//			if(K==0) {
//				found = true;
//				return;
//			}
			map[nextR][nextC] = -1; //머리표시
		
		}else { //사과가 없다면
			
			map[nextR][nextC] = -1; //머리 표시 
			
			int[] tail = snake.get(0); //꼬리 정보
			//System.out.println("tail : "+Arrays.toString(tail));
			map[tail[0]][tail[1]] = 0; //꼬리 지우기
			snake.remove(0);
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		
		snake.add(new int[] {0,0});
		
		map = new int[N][N];
		map[0][0] = -1;
		
		for(int k=0; k<K; k++) { //사과 기록
			int a = sc.nextInt();
			int b = sc.nextInt();
			map[a-1][b-1] = 1;
		}
		
		L = sc.nextInt();
		
		for(int l=0; l<L; l++) {
			int t = sc.nextInt();
			char d = sc.next().charAt(0);
			
			for(int m=0; m<t-nowT; m++) {
				move();
				if(found) {
					break;
				}
			}
			nowT = t;
			
			if(found) { //필수? 
				break;
			}
			
			if(d == 'L') {
				if(--nowD<0) { //감소와 동시에 확인
					nowD = 3;
				}
			}else if(d =='D') {
				if(++nowD>3) {
					nowD = 0;
				}
			}
		}
		
		for(int m=0; m<10000-nowT; m++) {
			if(found) {
				break;
			}
			move();
		}
		System.out.println(res);
	}

}
