import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class BoardUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = -7193663594390472688L;
	private static final int TILELENGTH = 35;
	private static final int STARTXBORDER1 = 30;
	private static final int STARTXBORDER2 = STARTXBORDER1+(11*TILELENGTH);
	private static final int STARTYBORDER = 33-24;
	Board board;
	int[][] playerBoard;
	int[][] opponentBoard;
	

	public BoardUI(){
		super("Battleships");
		
		JMenu file = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New game");
		JMenuItem instructions = new JMenuItem("Instructions");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuBar bar = new JMenuBar();
		GamePanel panel = new GamePanel();
		board = new Board();
		playerBoard = board.myBoard;
		opponentBoard = board.opponentBoard;
		
		file.setMnemonic(KeyEvent.VK_F);
		newgame.setMnemonic(KeyEvent.VK_N);

		instructions.addActionListener(this);
		instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		//file.addActionListener(new JMenuActionListener(this));
		
		file.add(newgame);
		file.add(instructions);
		file.addSeparator();
		file.add(exit);
		
        bar.add(file);
        setJMenuBar(bar);
        setDefaultLookAndFeelDecorated(true);		
        
		Container con = this.getContentPane();
		con.setLayout(new FlowLayout());
		setContentPane(panel);
		setSize(800, 420);
		
	        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);    
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	private class GamePanel extends JPanel implements Serializable {

		private static final long serialVersionUID = 2080838606441713101L;
		public GamePanel(){
			MouseReader mouse = new MouseReader();
			addMouseListener(mouse);
			addMouseMotionListener(mouse); 
		}
		
		//Draw functions
		public void paint(Graphics g){
			super.paintComponents(g);
			if(!board.gameStatus.equals("end")){
				try{
					drawBackgroundImage(g);
					drawGridLines(g);
					//drawBoats();
					drawBoardStatus(g);
				}catch(IOException e){
					
				}
			}
			else{
				//drawResult();
			}
		}
		
		private void drawBoardStatus(Graphics g) throws IOException{
			for(int i = 0; i <= 9; i++){
				for(int j = 0; j <= 9; j++){
					if(playerBoard[i][j]==Board.TILEDEAD)
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tiledead.png")), getPlayerTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else if(playerBoard[i][j]==Board.BOMBED)
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tilebombed.png")), getPlayerTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					if(opponentBoard[i][j]==Board.TILEDEAD)
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tiledead.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else if(opponentBoard[i][j]==Board.BOMBED)
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tilebombed.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
				}
			}
		}
		
		private int getTileYPosition(int i) {			
			return STARTYBORDER + i*TILELENGTH;
		}

		private int getPlayerTileXPosition(int i) {
			return STARTXBORDER1 + i*TILELENGTH;
		}

		private int getOpponentTileXPosition(int i) {
			return STARTXBORDER2 + i*TILELENGTH;
		}
		
		private void drawBackgroundImage(Graphics g) throws IOException{
			String filename = "";
			filename = "Images"+File.separator +"bgimage.png";
			g.drawImage(ImageIO.read( new File(filename)),0,0,800,400,null);
		}
		private void drawGridLines(Graphics g){
			
			g.fillRect(STARTXBORDER1-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
			g.fillRect(STARTXBORDER2-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
			g.clearRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
			g.clearRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
			g.drawRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
			g.drawRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
			
			for(int i = 1; i <= 9; i++){
				g.drawLine(STARTXBORDER1+(i*TILELENGTH),STARTYBORDER,STARTXBORDER1+(i*TILELENGTH),STARTYBORDER+10*TILELENGTH);
				g.drawLine(STARTXBORDER2+(i*TILELENGTH),STARTYBORDER,STARTXBORDER2+(i*TILELENGTH),STARTYBORDER+10*TILELENGTH);
				g.drawLine(STARTXBORDER1,STARTYBORDER+i*TILELENGTH,STARTXBORDER1+10*TILELENGTH,STARTYBORDER+i*TILELENGTH);
				g.drawLine(STARTXBORDER2,STARTYBORDER+i*TILELENGTH,STARTXBORDER2+10*TILELENGTH,STARTYBORDER+i*TILELENGTH);
			}
		}
		private class MouseReader extends MouseAdapter implements MouseMotionListener{
			public void mouseClicked(MouseEvent e){
				repaint();
				System.out.println("x: " + e.getX() + " y: " + e.getY());
			}
		}
		
	}
}


