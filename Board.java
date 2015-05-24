import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class Board extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7193663594390472688L;
	
	public Board(){
		super("Battleships");
		
		JMenu file = new JMenu("File");
		JMenuItem newgame = new JMenuItem("New game");
		JMenuItem instructions = new JMenuItem("Instructions");
		JMenuItem exit = new JMenuItem("Exit");
		
		file.setMnemonic(KeyEvent.VK_F);
		newgame.setMnemonic(KeyEvent.VK_N);

		instructions.addActionListener(this);
		instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		
		
		file.add(newgame);
		file.add(instructions);
		file.addSeparator();
		file.add(exit);
		
	}
	
	
	
	//Draw functions
	public void paint(Graphics g){
		super.repaint();
		if(!win){
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(bgimage,0,0,643,624,null);

			paintSlot(g, 10, 10); //The deck slot holder
			
			for (int column = 0; column < 7; column++) //Draw the stack holders
			{
				paintSlot(g, ((column * 90) + 10), 120);
			}
			
			for (int column = 0; column < 4; column++) //Draw the ace holders
			{
				paintSlot(g, ((column * 90) + 280), 10);
			}
			
			
			drawExtra(g);

		}
		
		else{
		
			g.drawImage(winImage,0,0,634,624,null);
		
		
		}
	}

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
	
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
