import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class BoardUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = -7193663594390472688L;
	protected static final int TILELENGTH = 35;
	protected static final int STARTXBORDER1 = 30;
	protected static final int STARTXBORDER2 = STARTXBORDER1+(11*TILELENGTH);
	protected static final int STARTYBORDER = 33-24;
	
	//dragging variables
	private int refreshCounter = 0;
	private static final int refreshRate = 3;
	int startingX, startingY, currentX, currentY;
	Ship lastShip;
	int lastX, lastY;
		
	private boolean dragging = false, clicked = true;
	protected Board board;
	protected String[][] playerBoard;
	protected String[][] opponentBoard;
	

	public BoardUI(Board b){
		super("Battleships");
		
		JMenu file = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New game");
		JMenuItem instructions = new JMenuItem("Instructions");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuBar bar = new JMenuBar();
		GamePanel panel = new GamePanel();
		board = b;
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
		//con.setLayout(new FlowLayout());
		panel.setLayout(null);
		
		
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	board.isSetUpDone = true;
            	board.setupDone();
            	done.setEnabled(false);
            	//repaint();
            }
        });   
		panel.add(done);

		Insets insets = panel.getInsets();
		Dimension size = done.getPreferredSize();
		done.setBounds(310 + insets.left, 365 + insets.top,
		             size.width, size.height);
		
		setContentPane(panel);
		setSize(800, 480);
		
	        
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
			//paintComponents(g);
			if(!board.gameStatus.equals("end")){
				try{
					drawBackgroundImage(g);
					g.fillRect(STARTXBORDER1-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
					g.fillRect(STARTXBORDER2-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
					g.clearRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
					g.clearRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
					g.drawRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
					g.drawRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
					drawShips(g);
					drawGridLines(g);
					drawBoardStatus(g);
				}catch(IOException e){
					
				}
			}
			else{
				//drawResult();
			}
		}
		
		private void drawShips(Graphics g) {
			Ship currShip = null;
			int xPos = 0, yPos = 0;
			g.setColor(Color.BLUE);
			for(int i = 0; i <= 7; i++){
				currShip = board.myShips.get(i);
				xPos = currShip.XCoor;
				yPos = currShip.YCoor ;
				drawBorderedRect(xPos, yPos, currShip.xSize, currShip.ySize, g);
				
				//System.out.println("yPos: " + yPos + " xPos: " + xPos + " ySize: " + ySize + " xSize: " + xSize);
				xPos = 0;
				yPos = 0;
			}
				
		}

		private void drawBoardStatus(Graphics g) throws IOException{
			playerBoard = board.myBoard;
			for(int i = 0; i <= playerBoard.length-1; i++){
				for(int j = 0; j <= playerBoard[i].length-1; j++){
					if(playerBoard[i][j].equals(Board.TILEDEAD))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tiledead.png")), getPlayerTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else if(playerBoard[i][j].equals(Board.BOMBED))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tilebombed.png")), getPlayerTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else{}
					if(opponentBoard[i][j].equals(Board.TILEDEAD))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tiledead.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else if(opponentBoard[i][j].equals(Board.BOMBED))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tilebombed.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else{}
				}
			}
		}
		
		private int getTileYPosition(int i) {			
			return STARTYBORDER + i*TILELENGTH+1;
		}

		private int getPlayerTileXPosition(int i) {
			return STARTXBORDER1 + i*TILELENGTH+1;
		}

		private int getOpponentTileXPosition(int i) {
			return STARTXBORDER2 + i*TILELENGTH;
		}
		
		private void drawBackgroundImage(Graphics g) throws IOException{
			String filename = "";
			filename = "Images"+File.separator +"bgimage.png";
			g.drawImage(ImageIO.read( new File(filename)),0,0,800,400,null);
		}
		
		private void drawBorderedRect(int x, int y, int tilenumx, int tilenumy, Graphics g){
			g.fillRect(x-1, y-1, tilenumx*TILELENGTH+1, tilenumy*TILELENGTH+1);
			g.clearRect(x, y, tilenumx*TILELENGTH-1, tilenumy*TILELENGTH-1);
			g.drawRect(x, y, tilenumx*TILELENGTH, tilenumy*TILELENGTH);
		}
		private void drawGridLines(Graphics g){
			
			g.setColor(Color.BLACK);
			for(int i = 1; i <= 9; i++){
				g.drawLine(STARTXBORDER1+(i*TILELENGTH),STARTYBORDER,STARTXBORDER1+(i*TILELENGTH),STARTYBORDER+10*TILELENGTH);
				g.drawLine(STARTXBORDER2+(i*TILELENGTH),STARTYBORDER,STARTXBORDER2+(i*TILELENGTH),STARTYBORDER+10*TILELENGTH);
				g.drawLine(STARTXBORDER1,STARTYBORDER+i*TILELENGTH,STARTXBORDER1+10*TILELENGTH,STARTYBORDER+i*TILELENGTH);
				g.drawLine(STARTXBORDER2,STARTYBORDER+i*TILELENGTH,STARTXBORDER2+10*TILELENGTH,STARTYBORDER+i*TILELENGTH);
			}
		}
		private class MouseReader extends MouseAdapter implements MouseMotionListener{
			Ship currShip = null;
			int x = 0, y = 0, counter = 0;
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2&&!board.isSetUpDone) {
					for(int i = 0; i <= 7; i++){
						if(withinCoordinates(currShip = board.myShips.get(i), e.getX(), e.getY())){
							System.out.println(clicked + " here");
							counter++;
							break;
						}
					}
					if(counter>1){
						currShip.isHorizontal = !currShip.isHorizontal;
						currShip.updateSizes();
					}
				}else if(e.getX() >= STARTXBORDER2 && e.getY() >= STARTYBORDER&&board.isSetUpDone&&board.isMyTurn){
					int x = (e.getX() - STARTXBORDER2)/TILELENGTH;
					int y = (e.getY() - STARTYBORDER)/TILELENGTH;
					board.evaluateMyTurn(x, y);
					System.out.println("x: " + x + " y: " + y);
				}
				clicked = true;
				//System.out.println("x: "+ e.getX() + " y: " +  e.getY() );
				repaint();
			}
			public void mousePressed(MouseEvent e){
				
				
				for(int i = 0; i <= 7; i++){
					if(withinCoordinates(currShip = board.myShips.get(i), e.getX(), e.getY())&&!board.isSetUpDone){
						System.out.println(clicked + " i ");
						startingX = e.getX();
						startingY = e.getY();
						lastX = currShip.XCoor;
						lastY = currShip.YCoor;
						lastShip = currShip;
						lastShip.updateSizes();
						break;
					}
				}
				dragging = true;
				clicked = true;
			}
			public void mouseDragged(MouseEvent e){
				if(!board.isSetUpDone){//YOU CAN DRAG YAY
					if(!dragging){
						for(int i = 0; i <= 7; i++){
							if(withinCoordinates(currShip = board.myShips.get(i), e.getX(), e.getY())){
								//System.out.println(i + " i ");
								startingX = e.getX();
								startingY = e.getY();
								break;
							}
						}
						dragging = true;
					}
					if (dragging){
						if (refreshCounter >= refreshRate){
							//System.out.println("here");
							currShip.XCoor = currShip.XCoor + e.getX() - startingX;
							currShip.YCoor = currShip.YCoor + e.getY() - startingY;
							startingX = e.getX();
							startingY = e.getY();
							refreshCounter = 0;
							repaint();
						}
						else{
							refreshCounter++;
						}
					}
				}
				clicked = false;
			}
			public void mouseReleased(MouseEvent e){
				if(!clicked&&!board.isSetUpDone){
					int xIndex = 0, yIndex = 0, counter = 0, i;
					int width = TILELENGTH*lastShip.xSize;
					int height = TILELENGTH*lastShip.ySize;
					Rectangle curr;
					Rectangle drag = new Rectangle(lastShip.XCoor, lastShip.YCoor, width, height);
					for(i = 0; i <= 7; i++){
						currShip = board.myShips.get(i);
						width = TILELENGTH*currShip.xSize;
						height = TILELENGTH*currShip.ySize;
						curr = new Rectangle(currShip.XCoor, currShip.YCoor, width, height);
						if(drag.intersects(curr)){
							System.out.println(i + " i ");
							startingX = e.getX();
							startingY = e.getY();
							counter++;
						}
					}
					if(counter>1){
						System.out.println(counter + " tis truuu ");
						lastShip.XCoor = lastX;
						lastShip.YCoor = lastY;
						dragging = false;
					}else{
						System.out.println(counter + " tis trruuu ");
						xIndex = (e.getX()-STARTXBORDER1)/TILELENGTH;
						yIndex = (e.getY()-STARTYBORDER)/TILELENGTH;
						lastShip.XCoor = getPlayerTileXPosition(xIndex);
						lastShip.YCoor = getTileYPosition(yIndex);
						dragging = false;
					}
					dragging = false;
				}
				repaint();		
			}
		}
		public boolean withinCoordinates(Ship ship, int x, int y) {
			//System.out.println("x: " + e.getX() + " y: " + e.getY() + " YCoor: " + ship.YCoor + " YEnd: " + (ship.YCoor+BoardUI.TILELENGTH*ship.xSize) + " XCoor")
			return (x>= ship.XCoor && x <= (ship.XCoor+BoardUI.TILELENGTH*ship.xSize) && y >= ship.YCoor && y <=ship.YCoor+BoardUI.TILELENGTH*ship.ySize);
		}
		
	}
}


