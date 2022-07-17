import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

//22 + 11  = 33
class Board{
	int red_next; //다음 번호
	int blue_next = -1;
	int score;
	
	Board(int red_next, int score){
		this.red_next = red_next;
		this.score = score;
	}
}
public class Main {
	static int MAX = 0;
	static int[] dice = new int[10];
	static Board[] board = new Board[33];
	static void dfs(int depth, int sum, int[] horse) {
		if(depth==10) {
			MAX = Math.max(MAX, sum);
			return;
		}
		if(depth==0) {
			dfs(depth+1, move(horse, 0, 0, dice[depth]), horse);
		}else {
			for(int i=0; i<4; i++) {
				if(canMove(horse, horse[i], dice[depth])) {
					int now = horse[i];
					dfs(depth+1, sum+move(horse, i, now, dice[depth]), horse);
					horse[i] = now;
				}
			}
		}
	}
	private static int move(int[] horse, int horse_num, int now, int NUMBER) {
		int number = NUMBER;
		if(board[now].blue_next != -1) {
			now = board[now].blue_next;
			number --;
		}
		for(int i=0; i<number; i++) {
			if(now>=33) break;
			//System.out.println(now);
			now = board[now].red_next;
		}
		horse[horse_num] = now;
		return board[now].score;
	}
	static boolean canMove(int[] horse, int NOW, int NUMBER) {
		int now = NOW;
		int number = NUMBER;
		
		if(now>=32) return false;
		
		if(board[now].blue_next != -1) {
			now = board[now].blue_next;
			number --;
		}
		
		for(int i=0; i<number; i++) {
			now = board[now].red_next;
			if(now>=32) return true;
		}
		
		
		for(int h : horse) {
			if(h==now) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] horse = new int[4]; //0이 출발
		
		//게임판 만들기: 현재판에 다음판에 대한 정보/현재판의 점수 존재
		for(int i=0; i<20; i++) {
			board[i] = new Board(i+1, (i)*2);
		}
		board[20] = new Board(32, 40); //0~20, 33 사용 
		board[5].blue_next = 21;
		
		board[21] = new Board(22, 13);
		board[22] = new Board(23, 16);
		
		board[23] = new Board(24, 19); //24는 중앙점
		
		board[10].blue_next = 25;
		board[25] = new Board(26, 22);
		board[26] = new Board(24, 24);
		
		board[15].blue_next = 27;
		board[27] = new Board(28, 28);
		board[28] = new Board(29, 27);
		board[29] = new Board(24, 26);
		
		board[24] = new Board(30, 25);
		board[30] = new Board(31, 30);
		board[31] = new Board(20, 35);
		
		board[32] = new Board(32, 0);// 오류 방지를 위한 코드
		
		for(int i=0; i<10; i++) {
			dice[i] = Integer.parseInt(st.nextToken());
		}
		
		dfs(0, 0, horse);
		System.out.println(MAX);
	}

}
