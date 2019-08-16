package BoobGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class bombGame extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	private int FrameWidth = 600, FrameHight = 600;
	private int MapRow = 10 , MapCol = 10 ;
	private int BombNumber = 10 ; 
	private JLabel ShowBombNumber =new JLabel("炸彈數量："+BombNumber);
	private int GameTimeSwitch = 1 ; 
	private int GameTimeWalking= 0 ;
	private JButton MidButton[][]=new JButton[FrameWidth][FrameHight]; 
	private int MiddleButton[][] = new int [MapRow][MapCol];
	private boolean MiddleButtonIsPress[][] = new boolean [MapRow][MapCol];
	private int MiddleButtonAroundBomb[][] = new int [MapRow][MapCol];
	private int direct [][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};//周圍八格
	private boolean flag[][] = new boolean [MapRow][MapCol];
	public static void main(String args[]){
		new bombGame();
	}

	bombGame(){
		initialize();
		SetRandomBomb();
		GetAroundBombNumber();
		
		
		
		setVisible(true);
		
	}
	
	private void initialize() {
		setSize(FrameWidth, FrameHight); 
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setTitle("Minesweeper"); 
		getContentPane().setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		
		JPanel TopPanel=new JPanel();
		TopPanel.add(ShowBombNumber);
		final JLabel CountingTime=new JLabel("計時：0");
		TimerTask clock=new TimerTask() {  //匿名類別
			public void run() {
				if(GameTimeSwitch == 1) 
					CountingTime.setText("計時："+ (GameTimeWalking++));
			} } ;
		Timer tim=new Timer();
		tim.scheduleAtFixedRate(clock,0,1000);
		TopPanel.add(CountingTime);
		add(TopPanel,BorderLayout.NORTH);
		
		JPanel UnderPanel=new JPanel();
		JButton Restart=new JButton("重新開始");
		JButton Surrender=new JButton("投降");
		Restart.setActionCommand("R"); 
		Surrender.setActionCommand("S");
		Restart.addMouseListener(this); 
		Surrender.addMouseListener(this);
		UnderPanel.add(Restart);
		UnderPanel.add(Surrender);
		add(UnderPanel,BorderLayout.SOUTH);
		
		JPanel MiddlePanel = new JPanel();
		MiddlePanel.setLayout(new GridLayout(MapRow,MapCol));
		for(int i=0;i<MapRow;i++){
			for(int j=0;j<MapCol;j++){
				MidButton[i][j]=new JButton();
				MidButton[i][j].setBackground(Color.black);  
				MidButton[i][j].setActionCommand(i+" "+j);
				MidButton[i][j].addMouseListener(this); 
				MiddlePanel.add(MidButton[i][j]);
			}
		}
		add(MiddlePanel,BorderLayout.CENTER);
	}
	
	private void SetRandomBomb() {
		int count = 0;
		while (count!=BombNumber) {
			int tx = (int)(Math.random()*10) ; //X~Y的範圍
			int ty = (int)(Math.random()*10) ; //Math.random() * (Y-X+1) + X
			System.out.println(tx+" "+ty);
			if (MiddleButton[tx][ty] == 0) MiddleButton[tx][ty] = 1;	
			count ++;
		}
	}

	private void GetAroundBombNumber() {
		for(int i=0;i<MapRow;i++){
			for(int j=0;j<MapCol;j++){
					if (MiddleButton[i][j]==1) {
						MiddleButtonAroundBomb[i][j] = -1;
					}
					else {
				for(int k=0;k<8;k++){
					int tx = direct[k][0]+i;
					int ty = direct[k][1]+j;
					if (InRange(tx,ty)&&MiddleButton[tx][ty] == 1) {
						MiddleButtonAroundBomb[i][j]++;}
				}
				}
			}
	}
	}
	
	
	private void ReStartGame() {
		GameTimeSwitch = 1;
		GameTimeWalking= 0;
		for(int i=0;i<MapRow;i++){
			for(int j=0;j<MapCol;j++){
				MidButton[i][j].setBackground(Color.black);
				MidButton[i][j].setText("");
				MiddleButton[i][j]= 0;
				MiddleButtonIsPress[i][j] = false;
				MiddleButtonAroundBomb[i][j] = 0;
			}
		}
		BombNumber=10;
		 ShowBombNumber.setText("目前炸彈數："+BombNumber); 
		SetRandomBomb();
		GetAroundBombNumber();
	}
	
	private void GameOver() {
		GameTimeSwitch=0;
		
		for(int i=0;i<MapRow;i++){
			for(int j=0;j<MapCol;j++){
				if (MiddleButton[i][j]==1) {
					MidButton[i][j].setText("*");
					MidButton[i][j].setBackground(Color.RED);
				}
			}	
			}
		 JOptionPane.showMessageDialog(null, "GameOver"); 
		 ReStartGame();
	}
	
	private void Spread(int x ,int y) {
		 int ArrayX[] = new int [MapRow*MapCol]; 
		 int ArrayY[] = new int [MapRow*MapCol]; 
		 int a=0,b=0;
		 ArrayX[a] = x;
		 ArrayY[a] = y;
		 a++;
		 
		 while (a>b) {
			int tex	= ArrayX[b]  ;
			int tey = ArrayY[b]  ;
			if (MiddleButtonAroundBomb[tex][tey] == 0)
						for(int k=0;k<8;k++){
							int tx = direct[k][0]+tex;
							int ty = direct[k][1]+tey;
							if (InRange(tx,ty)&& !MiddleButtonIsPress[tx][ty]) {
								MiddleButtonIsPress[tx][ty] = true;
								ArrayX[a] =tx;
								ArrayY[a] =ty;
								a++;
								}
							}
							b++;
								}
			 for (int d=0;d<a;d++) {
				 MidButton[ArrayX[d]][ArrayY[d]].setBackground(Color.white);
				 if (MiddleButtonAroundBomb[ArrayX[d]][ArrayY[d]] > 0) {
					 MidButton[ArrayX[d]][ArrayY[d]].setText(Integer.toString(MiddleButtonAroundBomb[ArrayX[d]][ArrayY[d]]));
				 }
			 }
			 
		 }
	private void Returnflag(int x, int y ) {
		 BombNumber++;
		 ShowBombNumber.setText("目前炸彈數："+BombNumber);
		 flag[x][y]=false;
	}
	private void JudgeGame() {
		if (BombNumber==0) {
			 int count = 0;
			 for(int i=0;i<MapRow;i++){
					for(int j=0;j<MapCol;j++){
						if (MiddleButton[i][j]==1&&flag[i][j]) {
							count++;
						}
					}	
					}
			 if  (count==10) {
				 GameTimeSwitch=0;
				 JOptionPane.showMessageDialog(null, "Winner"); 
				 ReStartGame();
			 }
					
			 
		 }
		
	}
	
	
	private boolean InRange(int x ,int y) {
		return (x>=0 && x<MapRow && y>=0 && y<MapCol);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		String command[]=((JButton)e.getSource()).getActionCommand().split(" ");
		 if (command[0]=="R") {
			 ReStartGame();
		 } else if (command[0]=="S") {
			 GameOver();
		 }
		 else {
		 int x=Integer.parseInt(command[0]);
		 int y=Integer.parseInt(command[1]);
		 
		 if(e.getButton()==MouseEvent.BUTTON1){ //左鍵
			 if (MiddleButton[x][y]==1&& !MiddleButtonIsPress[x][y]) { //炸彈
				 GameOver();
			 } else if (MiddleButtonAroundBomb[x][y]==0&& !MiddleButtonIsPress[x][y]) { //周圍無炸彈
				 Spread(x,y) ;
				 if (flag[x][y]) {
					 Returnflag(x,y);
				 }
			 } else if (MiddleButtonAroundBomb[x][y]>0&& !MiddleButtonIsPress[x][y]) { //周圍有炸彈
				 MidButton[x][y].setBackground(Color.white);
				 MidButton[x][y].setText(Integer.toString(MiddleButtonAroundBomb[x][y]));
				 MiddleButtonIsPress[x][y] = true;
				 if (flag[x][y]) {
					 Returnflag(x,y);
				 }
			 }
			 JudgeGame();
		 } else if (e.getButton()==MouseEvent.BUTTON3){ //右鍵
			 if (!MiddleButtonIsPress[x][y]) {
				 if (!flag[x][y]) {
					 if(BombNumber>0) {
					 MidButton[x][y].setBackground(Color.GREEN);
					 BombNumber--;
					 ShowBombNumber.setText("目前炸彈數："+BombNumber); 
					 flag[x][y]=true;}
					 	else {
					 	JOptionPane.showMessageDialog(null, "你只有10個拆炸包"); }
					 JudgeGame();
				 }else if (flag[x][y]) {
					 MidButton[x][y].setBackground(Color.black);
					 Returnflag(x,y);
					 
				 }
			 }
		 }
			 
		 }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
