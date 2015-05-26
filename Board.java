import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
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


public class Board extends JFrame implements ActionListener{
	private static final long serialVersionUID = -7193663594390472688L;
	private static final int TILELENGTH = 35;
	private static final int STARTXBORDER1 = 30;
	private static final int STARTXBORDER2 = STARTXBORDER1+(11*TILELENGTH);
	private static final int STARTYBORDER = 33-24;
	private String status = "init";
	public Board(){
		super("Battleships");
		
		JMenu file = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New game");
		JMenuItem instructions = new JMenuItem("Instructions");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuBar bar = new JMenuBar();
		GamePanel panel = new GamePanel();
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
		setSize(800, 400);
		
	        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);    
		
		
	}
	
	
	/*
	protected void drawExtra (Graphics g)
	{
		
		//Draw the deck
		if (carddeck.getSize() != 0) //If the deck has at least one card in it
		{
			drawCard(g, 10, 10,reverseimage);
		}
		//Draw the deck cards that are showing
		if(drawncards.peek() != null){
		
			Card card = drawncards.peek();
			drawdrawn(g,card.mainimage);
			
		}
		
		//Draw the piles
		gap = 0;
		drawPile(g,pile1, pile1sec,0);
		drawPile(g,pile2, pile2sec,1);
		drawPile(g,pile3, pile3sec,2);
		drawPile(g,pile4, pile4sec,3);
		drawPile(g,pile5, pile5sec,4);
		drawPile(g,pile6, pile6sec,5);
		drawPile(g,pile7, pile7sec,6);
		
		//Draw the Aces

		drawStack(g,stack1,0);
		drawStack(g,stack2,1);
		drawStack(g,stack3,2);
		drawStack(g,stack4,3);
		
		//Draw a dragged stack
		if (dragging)
		{
			LinkedStack<Card> cards = new LinkedStack<Card>();
			if(stacknumtostack(fromstack).peek() != null){
			
				stacknumtostack(fromstack).peek().reversed = true;

			}
			
			for (int card = cardsdrag.getSize() - 1; card >= 0 ; card--)
			{

				cards.push(cardsdrag.pop());	
		
			}							

			for (int card = cards.getSize() - 1; card >= 0 ; card--)
			{
			
				cardsdrag.push(cards.pop());
				drawCard(g, startingX + currentX - 30, startingY + (currentY + (gapFaceUp * card)) - 30, cardsdrag.peek().mainimage);	
				
			}	
			
		}			
		
	}
	
	private void drawPile(Graphics g, LinkedStack<Card> pile, LinkedStack<Card> pilesec, int column){
	
		Card[] cards = new Card[19];
		int pilesize = pile.getSize();
		int size = pile.getSize() + pilesec.getSize();
		gap = 0;
		int row;
		
		if(size == 0){
		
			return;
		
		}
		
		for (row = 0; row < size; row++){
			
			if(pilesec.peek() != null){
				pilesec.peek().reversed = false;
				cards[row] = pilesec.pop();
				
			}
			else{
				
				cards[row] = pile.pop();
				
			}
			
		}
		
		if(dragging && pile.equals(stacknumtostack(fromstack)) && dragStartCard != pile.getSize() + pilesec.getSize()){
		
		
			for(row = size - 1; row >= 0; row--){

				
				if(row  == size - pilesize && !cards[row].reversed){
				
					drawCard(g, ((column * 90) + 10), (120 + gap), cards[row].mainimage);
					gap += gapFaceUp;
				
				}

				else if (cards[row].reversed || (row == 0)){
				
					drawCard(g, ((column * 90) + 10), (120 + gap), cards[row].reverseimage);
					gap += gapFaceDown;
				
				}			

				
			}				
		
		
		}
		else{
		
			for(row = size - 1; row >= 0; row--){

				
				if(row  == size - pilesize || !cards[row].reversed){
				
					drawCard(g, ((column * 90) + 10), (120 + gap), cards[row].mainimage);
					gap += gapFaceUp;
				
				}

				else if (cards[row].reversed || (row == 0 && pilechange)){
				
					drawCard(g, ((column * 90) + 10), (120 + gap), cards[row].reverseimage);
					gap += gapFaceDown;
				
				}			

				
			}
			
		}
		
		for(row = size - 1; row >= 0; row--){
		
			if(cards[row].reversed){
			
				pile.push(cards[row]);
			
			}
			else if(pile.getSize() < pilesize){
			
				pile.push(cards[row]);
			}

			else{
			
				pilesec.push(cards[row]);
			
			}
		


			
		
		}
		
			
	}
	
	private void drawStack(Graphics g, LinkedStack<Card> stack, int column){

		int size = stack.getSize();
		
		if(stack.getSize() == 0){
		
			return;
		
		}
		Card card = stack.peek();
		if(card != null){
		drawCard(g, ((column * 90) + 280), 10, card.mainimage);
		}
		
	}
	
	private void drawCard (Graphics g, int startX, int startY, Image imgCard)
	{
		
		g.drawImage(imgCard, startX, startY, null);
		g.setColor(new Color(149,146,140)); //Grey
		g.drawRect(startX, startY, 71, 96); //Draw a border around the card
		
	}	
	private void paintSlot (Graphics g, int startX, int startY)
	{
		
		g.setColor(new Color(149,146,140)); //Grey
		g.fillRect(startX, startY, 71, 96); //Makes a slot holder
		g.setColor(new Color(0,0,0)); //black
		g.drawRect(startX, startY, 71, 96); //Gives the holder a border
		
	}
	
	private void drawdrawn(Graphics g, Image image){
	
		g.drawImage(image, 100,10,null);
		g.setColor(new Color(149,146,140));
		g.drawRect(100,10,71,96);
	
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}


	private class GamePanel extends JPanel implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2080838606441713101L;
		public GamePanel(){
			MouseReader mouse = new MouseReader();
			addMouseListener(mouse);
			addMouseMotionListener(mouse); 
		}
		
		//Draw functions
		public void paint(Graphics g){
			super.paintComponents(g);
			if(!status.equals("end")){
				drawBackgroundImage(g);
				drawGridLines(g);
				//drawBoats();
			}
			else{
				//drawResult();
			}
		}
		
		private void drawBackgroundImage(Graphics g){
			String filename = "";
			try {
				filename = "Images"+File.separator +"bgimage.png";
				g.drawImage(ImageIO.read( new File(filename)),0,0,800,400,null);
			} catch (IOException e) {
				System.out.println(filename);
			}
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


