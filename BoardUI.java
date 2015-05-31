import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
	protected Rectangle userRect;
	private Ship boat1;
	private boolean doNothing=false;


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
		exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	System.exit(0);
            }
        });   
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		//file.addActionListener(new JMenuActionListener(this));
		
		//file.add(newgame);
		//file.add(instructions);
		//file.addSeparator();
		//file.add(exit);
		
        //bar.add(file);
        setJMenuBar(bar);
        setDefaultLookAndFeelDecorated(true);		
        
		Container con = this.getContentPane();
		//con.setLayout(new FlowLayout());
		panel.setLayout(null);
		
		
		final JButton done = new JButton("Done");
		done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	board.isSetUpDone = true;
            	board.setupDone();
            	done.setEnabled(false);
            	repaint();
            }
        });   
		panel.add(done);

		Insets insets = panel.getInsets();
		Dimension size = done.getPreferredSize();
		done.setBounds(310 + insets.left, 365 + insets.top,
		             size.width, size.height);
		
		setContentPane(panel);
		setSize(800, 450);
		
	        
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
			try{
				drawBackgroundImage(g);
				g.fillRect(STARTXBORDER1-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
				g.fillRect(STARTXBORDER2-1, STARTYBORDER-1, 10*TILELENGTH+1, 10*TILELENGTH+1);
				g.clearRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
				g.clearRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
				g.drawRect(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
				g.drawRect(STARTXBORDER2, STARTYBORDER, 10*TILELENGTH, 10*TILELENGTH);
				drawShips(g);

				userRect = new Rectangle(STARTXBORDER1, STARTYBORDER, 10*TILELENGTH-1, 10*TILELENGTH-1);
				drawGridLines(g);
				drawBoardStatus(g);
				if(board.gameStatus.equals("win")||board.gameStatus.equals("lose"))
					drawResult(g);
					
				//System.out.println("ENNNND" + board.gameStatus);
			}catch(IOException e){
				
			}
		
			//System.out.println("ENNNND" +  board.gameStatus);
			super.paintComponents(g);
			//paintComponents(g);
		}
		
		private void drawResult(Graphics g){
			//if(.equals("win"))
			try {
				g.drawImage(ImageIO.read(new File("Images" + File.separator + board.gameStatus + ".png")), 400-144, 200-72, 288,144, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void drawShips(Graphics g) {
			Ship currShip = null, oppShip = null;
			int xPos = 0, yPos = 0;
			g.setColor(Color.BLUE);
			for(int i = 0; i <= 7; i++){
				currShip = board.myShips.get(i);
				oppShip = board.opponentShips.get(i);
				xPos = currShip.XCoor;
				yPos = currShip.YCoor ;
				if(currShip.sunk==0)
					g.setColor(Color.RED);
				drawBorderedRect(xPos, yPos, currShip.xSize, currShip.ySize, g);
				g.setColor(Color.RED);
				oppShip.updateSizes();
				if(oppShip.sunk==0){
					drawBorderedRect(oppShip.getOppXCoordinates(), oppShip.getYCoordinates(), oppShip.xSize, oppShip.ySize, g);
					
				}
				xPos = 0;
				yPos = 0;	
				g.setColor(Color.BLUE);
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
					if(opponentBoard[i][j].equals(Board.TILEDEAD))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tiledead.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
					else if(opponentBoard[i][j].equals(Board.BOMBED))
						g.drawImage(ImageIO.read(new File("Images" + File.separator + "tilebombed.png")), getOpponentTileXPosition(i), getTileYPosition(j), TILELENGTH,TILELENGTH, null);
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
			g.fillRect(x, y-1, tilenumx*TILELENGTH, tilenumy*TILELENGTH+1);
			//g.clearRect(x, y, tilenumx*TILELENGTH-1, tilenumy*TILELENGTH-1);
			//g.drawRect(x, y, tilenumx*TILELENGTH, tilenumy*TILELENGTH);
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
				doNothing=false;
				if(!doNothing){
					if (e.getClickCount() == 2&&!board.isSetUpDone) {
						for(int i = 0; i <= 7; i++){
							if(withinCoordinates(currShip = board.myShips.get(i), e.getX(), e.getY())){
								//System.out.println(clicked + " here");
								counter++;
								break;
							}
						}
						if(counter>1){
							int width, height, counter2=0;
							Ship lastShip = board.myShips.get(0);
							currShip.isHorizontal = !currShip.isHorizontal;
							currShip.updateSizes();
							lastShip = currShip;
							Rectangle curr = new Rectangle();
							Rectangle drag = new Rectangle(currShip.XCoor, currShip.YCoor, TILELENGTH*currShip.xSize, TILELENGTH*currShip.ySize);
							for(int i = 0; i <= 7; i++){
								currShip = board.myShips.get(i);
								width = TILELENGTH*currShip.xSize;
								height = TILELENGTH*currShip.ySize;
								curr = new Rectangle(currShip.XCoor, currShip.YCoor, width, height);
								if(drag.intersects(curr)){
									
									startingX = e.getX();
									startingY = e.getY();
									counter2++;
								}
							}
							if(counter2>1||lastShip.XCoor<(STARTXBORDER1)||lastShip.getEndXCoor()>(STARTXBORDER1+TILELENGTH*10)||lastShip.YCoor<STARTYBORDER||lastShip.YCoor>(STARTYBORDER+10*TILELENGTH)){
								lastShip.isHorizontal = !currShip.isHorizontal;
								lastShip.updateSizes();
							}
						}
					}else if(board.isSetUpDone&&e.getX() >= STARTXBORDER2 && e.getY() >= STARTYBORDER&&board.isSetUpDone&&board.isMyTurn){
						int x = (e.getX() - STARTXBORDER2)/TILELENGTH;
						int y = (e.getY() - STARTYBORDER)/TILELENGTH;
						board.evaluateMyTurn(x, y);
						//System.out.println("x: " + x + " y: " + y);
					}
					clicked = true;
					//System.out.println("x: "+ e.getX() + " y: " +  e.getY() );
					repaint();
				}else
					doNothing = false;
				System.out.println(" 1 ");
			}
			public void mousePressed(MouseEvent e){
				boat1 = board.myShips.get(0);
				int i, counter = 0;
				for(i = 0; i <= 7; i++){
					if(withinCoordinates(currShip = board.myShips.get(i), e.getX(), e.getY())&&!board.isSetUpDone){
						//System.out.println(clicked + " i ");
						startingX = e.getX();
						startingY = e.getY();
						lastX = currShip.XCoor;
						lastY = currShip.YCoor;
						lastShip = currShip;
						lastShip.updateSizes();
						counter++;
						break;
					}
				}
				if(i>7)
					dragging = true;
				else
					dragging = false;
				clicked = true;
				if(counter!=1||e.getX()>STARTXBORDER1+10*TILELENGTH||e.getY()>STARTYBORDER+10*TILELENGTH||e.getX()<STARTXBORDER1||e.getY()<STARTYBORDER)
					doNothing = true;
				else 
					doNothing = false;
			}
			public void mouseDragged(MouseEvent e){
				if(!doNothing&&e.getX()<=STARTXBORDER1 + 10*TILELENGTH&&e.getX()>=STARTXBORDER1&&e.getY()>=STARTYBORDER&&e.getY()<=STARTYBORDER+10*TILELENGTH){
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
						//	System.out.println("5");
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
							if(currShip.XCoor<(STARTXBORDER1)||currShip.getEndXCoor()>(STARTXBORDER1+TILELENGTH*10)||currShip.YCoor<STARTYBORDER||currShip.YCoor>(STARTYBORDER+10*TILELENGTH)){
								//System.out.println(counter + " tis truuu ");
								currShip.XPosition = board.myShips.get(0).XPosition;
								currShip.YPosition = board.myShips.get(0).YPosition;
							//	System.out.println("3");
							}
							//System.out.println("6");
						}
					}
					clicked = false;
				}
				System.out.println(" 3 ");
			}
			public void mouseReleased(MouseEvent e){
				if(!doNothing){
					if(!clicked&&!board.isSetUpDone&&dragging&&lastShip!=null){
						int xIndex = 0, yIndex = 0, counter = 0, i;
						//lastShip.updateSizes();
						int width = TILELENGTH*lastShip.xSize;
						int height = TILELENGTH*lastShip.ySize;
						Rectangle curr = new Rectangle();
						Rectangle drag = new Rectangle(lastShip.XCoor, lastShip.YCoor, width, height);
						//System.out.println("1");	
						for(i = 0; i <= 7; i++){
							currShip = board.myShips.get(i);
							currShip.updateSizes();
							width = TILELENGTH*currShip.xSize;
							height = TILELENGTH*currShip.ySize;
							curr = new Rectangle(currShip.XCoor, currShip.YCoor, width, height);
							if(drag.intersects(curr)){
								//System.out.println(i + " i ");
								startingX = e.getX();
								startingY = e.getY();
								counter++;
							}
						}
						//System.out.println("2");		
						xIndex = (e.getX()-STARTXBORDER1)/TILELENGTH;
						yIndex = (e.getY()-STARTYBORDER)/TILELENGTH;
						if(xIndex+lastShip.xSize>9)
							xIndex=9;
						if(yIndex+lastShip.ySize>9)
							yIndex=9;
						
						//System.out.println((lastShip.XCoor) + " < " + ((STARTXBORDER1)) + "eto na poooooo");
						if(counter>1||lastShip.XCoor<(STARTXBORDER1)||lastShip.getEndXCoor()>(STARTXBORDER1+TILELENGTH*10)||lastShip.YCoor<STARTYBORDER||lastShip.YCoor>(STARTYBORDER+10*TILELENGTH)){
							//System.out.println(counter + " tis truuu ");
							lastShip.XCoor = lastX;
							lastShip.YCoor = lastY;
							dragging = false;
					//		System.out.println("3");
						}else{
					//		System.out.println(4);
							//System.out.println(counter + " tis trruuu ");
							int XCoor = lastShip.XCoor;
							int YCoor = lastShip.YCoor;
							lastShip.XCoor = getPlayerTileXPosition(xIndex);
							lastShip.YCoor = getTileYPosition(yIndex);
							lastShip.XPosition = xIndex;
							lastShip.YPosition = yIndex;
	
							if(lastShip.getEndXCoor()>(STARTXBORDER1 + 10*TILELENGTH)){
								lastShip.XCoor = (STARTXBORDER1 + (10-lastShip.xSize)*TILELENGTH);
								lastShip.XPosition = 10-lastShip.xSize;
							}
							if(lastShip.getEndYCoor()>(STARTYBORDER+10*TILELENGTH)){
								lastShip.YCoor = (STARTYBORDER + (10-lastShip.ySize)*TILELENGTH);
								lastShip.YPosition = 10-lastShip.ySize;
							}
							
							dragging = false;
						}
						dragging = false;
					}
					
					repaint();
				}else
					doNothing = false;
				System.out.println(" 4 ");
			}
		}
		public boolean withinCoordinates(Ship ship, int x, int y) {
			//System.out.println("x: " + e.getX() + " y: " + e.getY() + " YCoor: " + ship.YCoor + " YEnd: " + (ship.YCoor+BoardUI.TILELENGTH*ship.xSize) + " XCoor")
			return (x>= ship.XCoor && x <= (ship.XCoor+BoardUI.TILELENGTH*ship.xSize) && y >= ship.YCoor && y <=ship.YCoor+BoardUI.TILELENGTH*ship.ySize);
		}
		
	}

	public void update(){
		repaint();	
	}

	public void closeWindow(){
		this.dispose();
	}
}


