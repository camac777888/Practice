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
	private JLabel ShowBombNumber =new JLabel("���u�ƶq�G"+BombNumber);
	private int GameTimeSwitch = 1 ; 
	private int GameTimeWalking= 0 ;
	private JButton MidButton[][]=new JButton[FrameWidth][FrameHight]; 
	private int MiddleButton[][] = new int [MapRow][MapCol];
	private boolean MiddleButtonIsPress[][] = new boolean [MapRow][MapCol];
	private int MiddleButtonAroundBomb[][] = new int [MapRow][MapCol];
	private int direct [][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};//�P��K��
	
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
		getContentPane().setFont(new Font("�L�n������", Font.PLAIN, 12));
		
		JPanel TopPanel=new JPanel();
		TopPanel.add(ShowBombNumber);
		final JLabel CountingTime=new JLabel("�p�ɡG0");
		TimerTask clock=new TimerTask() {  //�ΦW���O
			public void run() {
				if(GameTimeSwitch == 1) 
					CountingTime.setText("�p�ɡG"+ (GameTimeWalking++));
			} } ;
		Timer tim=new Timer();
		tim.scheduleAtFixedRate(clock,0,1000);
		TopPanel.add(CountingTime);
		add(TopPanel,BorderLayout.NORTH);
		
		JPanel UnderPanel=new JPanel();
		JButton Restart=new JButton("���s�}�l");
		JButton Surrender=new JButton("�뭰");
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
			int tx = (int)(Math.random()*10) ; //X~Y���d��
			int ty = (int)(Math.random()*10) ; //Math.random() * (Y-X+1) + X
			System.out.println(tx+" "+ty);
			if (MiddleButton[tx][ty] == 0) MiddleButton[tx][ty] = 1;	
			count ++;
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
	
	private boolean InRange(int x ,int y) {
		return (x>0 && x<MapRow && y>0 && y<MapCol);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		String command[]=((JButton)e.getSource()).getActionCommand().split(" ");
		 if (command[0]=="R") {
			 ReStartGame();
		 } else if (command[0]=="S") {
			 GameOver();
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
