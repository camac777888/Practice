package BoobGame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class bombGame extends JFrame implements MouseListener {

	private int FrameWidth = 600, FrameHight = 600;
	private int MapRow = 10 , MapCol = 10 ;
	private int BombNumber = 10 ; 
	private JLabel ShowBombNumber =new JLabel("炸彈數量："+BombNumber);
	private int GameTimeSwitch = 1 ; 
	private int GameTimeWalking= 0 ;
	
	
	
	public static void main(String args[]){

		new bombGame();
		

		}

	bombGame(){
		initialize();
		
		
		
		
		
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
			}
		} ;
		new Timer().scheduleAtFixedRate(clock,0,1000);
		TopPanel.add(CountingTime);
		add(TopPanel,BorderLayout.NORTH);
		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
