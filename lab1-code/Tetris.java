package lab1;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Tetris extends JPanel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4472101028384774275L;
	private static final int CELL_SIZE=26;
	 public static final int PLAYING = 0;
	 public static final int PAUSE = 1;
	 public static final int GAMEOVER = 2;
	 String[] show_state = {"P[pause]","C[continue]","S[replay]"};
	 private int game_state;
	    public static BufferedImage T;
	    public static BufferedImage I;
	    public static BufferedImage O;
	    public static BufferedImage J;
	    public static BufferedImage L;
	    public static BufferedImage S;
	    public static BufferedImage Z;
	    public static BufferedImage background;
	    public static BufferedImage gameover;
	    static {
	    	try {
	    		T = ImageIO.read(Tetris.class.getResource("T.png"));
	    		I = ImageIO.read(Tetris.class.getResource("I.png"));
	    		O = ImageIO.read(Tetris.class.getResource("O.png"));
	    		J = ImageIO.read(Tetris.class.getResource("J.png"));
	    		L = ImageIO.read(Tetris.class.getResource("L.png"));
	    		S = ImageIO.read(Tetris.class.getResource("S.png"));
	    		Z = ImageIO.read(Tetris.class.getResource("Z.png"));
	    		
	    		background = ImageIO.read(Tetris.class.getResource("background.png"));
	    		gameover = ImageIO.read(Tetris.class.getResource("gameover.png"));
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    }
}

               private Tetromino currentOne = Tetromino.randomOne();
               private Tetromino nextOne = Tetromino.randomOne();
               private Cell[][] 
            		   wall= new Cell[20][10];
               

               public static void main(String[]args) {
            	   
            	   JFrame frame = new JFrame("俄罗斯方块");
            	   Tetris panel = new Tetris();
            	   frame.add(panel);
            	   
            	   frame.setVisible(true);
            	   frame.setSize(620, 730);
            	   frame.setLocationRelativeTo(null);
                   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                   panel.start();
               }
                   
                   public void paint(Graphics g) 
                   {
                	  
                	   g.drawImage(background, 0, 0, null );
                	   g.translate(73, 115);
                	   paintWall(g);
                	   paintCurrentOne(g);
                	   paintNextOne(g);
                	   painScore(g);
                	   painScore(g);
                	   paintState(g);
                   }
               
               
                 
                   public void paintWall(Graphics a) {
            	 for (int i =0;i<20;i++) {
            		 for(int j=0;j<10;j++) {
            			 int x = j*CELL_SIZE;
            			 int y = i*CELL_SIZE;
            			 Cell cell=wall[i][j];
            			 
            			 
            			 if(cell==null)
            			 {
            				 a.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            			 }
            			 else {
            				 a.drawImage(cell.getImage(), x,y,null);
            			 }
            		 }
            	 }
             
             }
             
                  public void paintCurrentOne(Graphics g) {
            	 Cell[] cells = currentOne.cells;
            	 for(Cell c:cells)
            	 {
            		 int x = c.getCol()*CELL_SIZE;
            		 int y = c.getRow()*CELL_SIZE;
            		 g.drawImage(c.getImage(), x,y,null);
            	 }
             }
             
             public void paintNextOne(Graphics g) {
            	 Cell[] cells = nextOne.cells;
            	 for(Cell c:cells) {
            		 int row = c.getRow();
            		 int col = c.getCol();
            		 int x = col*CELL_SIZE+255;
            		 int y = row*CELL_SIZE+25;
            		 g.drawImage(c.getImage(), x, y, null);
            	 }
             }
             int [] scores_pool = {0,1,2,5,10};
             private int totalScore = 0;
             private int totalLine = 0;
             
             public void painScore(Graphics g) {
            	 g.setFont(new Font("黑体",Font.PLAIN,25));
            	 g.drawString("SCORES:"+totalScore, 320, 200);
            	 g.drawString("LINES:"+totalLine,320,250);
             }
             public void paintState(Graphics g) {		
            	 if(game_state == GAMEOVER) 
            	 {			g.drawImage(gameover, 0, 0, null);		
            	 g.drawString(show_state[GAMEOVER], 320, 300);		
            	 }		else if (game_state == PLAYING)
            	 {			g.drawString(show_state[PLAYING], 320, 300);		}	
            	 else if (game_state == PAUSE) 
            	 {			g.drawString(show_state[PAUSE], 320, 300);	
            	 }		
             }
             
             public boolean outOfBounds() {
            	 Cell[] cells = currentOne.cells;
            	 for(Cell c:cells) {
            		 int col = c.getCol();
            		 int row = c.getRow();
            		 if(col<0||col>9||row>19||row<0)
            			 return true;
            	 }
            	 return false;
             }
             public boolean coincide() {
            	 Cell[] cells = currentOne.cells;
            	 for(Cell c:cells) {
            		 int row = c.getRow();
            		 int col = c.getCol();
            		 if(wall[row][col]!=null)
            			 return true;
            	 }
            	 return false;
             }
             public void moveRightAction() {
            	 currentOne.moveRight();
            	 if(outOfBounds()||coincide())
            		 currentOne.moveleft();
             }
             public void moveLeftAction() {
            	 currentOne.moveleft();
            	 if(outOfBounds()||coincide())
            		 currentOne.moveRight();
             }
             public void rotateRightAction() {
            	 currentOne.rotateRight();
            	 if(outOfBounds()||coincide()){
            		 currentOne.rotateLeft();
            	 }
             }
             public boolean isGameOver() {
            	 Cell[] cells = nextOne.cells;
            	 for(Cell c:cells) {
            		 int row = c.getRow();
            		 int col = c.getCol();
            		 if(wall[row][col]!=null) {
            			 return true;
            		 }
            	 }
            	 return false;
             }
             public boolean isFullLine(int row) {
            	 Cell[] line = wall[row];
            	 for(Cell r:line) {
            		 if(r==null) {
            			 return false;
            		 }
            	 }
            	 return true;
             }
             public void destroyLine() {
            	 int lines = 0;
            	 Cell[] cells = currentOne.cells;
            	 for(Cell c:cells){
            		int row = c.getRow();
            		while(row<20) {
            	    if(isFullLine(row)) {
            	    	lines++;
            	    	wall[row] = new Cell[10];
            	    	for(int i=row;i>0;i--) {
            	    		System.arraycopy(wall[i-1], 0, wall[i],0, 10);	
            	    	}
            	    	wall[0] = new Cell[10];
            	    }
            	    row++;
            		 }
            	 }
            	 
            	 totalScore+=scores_pool[lines];
            	 totalLine+=lines;
             }
            public boolean canDrop() {
            	 Cell[] cells = currentOne.cells;
            	 for(Cell c:cells) {
            		 int row = c.getRow();
            		 int col = c.getCol();
            		 if (row==19) {
            			 return false; 
            		 }else if(wall[row+1][col]!=null) {
            			 return false;
            		 }
            	 }
            	 return true;
             }
            public void landToWall() {
            	Cell[] cells = currentOne.cells;
            	for(Cell cell:cells) {
            		int row = cell.getRow();
            		int col = cell.getCol();
            		wall[row][col] = cell;
            	}
            	
            }
            
            	 
             public void softDropAction() {
            	 
            	 if(canDrop()) {
            		 currentOne.softDrop();
            	 }
            	 else {
            		 landToWall();
            		 destroyLine();
            		 if(!isGameOver()) {
            			 currentOne = nextOne;

         				nextOne = Tetromino.randomOne();
            		 }
            		 else {
            			 game_state = GAMEOVER;
            		 }
            	 }
             }
             public void handDropAction() {
            	 for(;;) {
            		 if(canDrop()){
            			 currentOne.softDrop();
            		 }
            		 else break;
            		 
            	 }landToWall();
            	 destroyLine();
            	 if(!isGameOver()) {
            		 currentOne = nextOne;
            		 nextOne = Tetromino.randomOne();
            	 }
            	 else {
            		 game_state = GAMEOVER;

         		}
            	 }
            	 

public void start() {

		game_state = PLAYING;
		//开启键盘监听事件

		KeyListener l = new KeyAdapter() {
			@Override

			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_P) {
					if(game_state == PLAYING)
						game_state=PAUSE;
				}

				if(code == KeyEvent.VK_C) {
					if(game_state == PAUSE)
						game_state=PLAYING;
				}

				if(code == KeyEvent.VK_S) {
					game_state=PLAYING;
					wall = new Cell[20][10];

					currentOne = Tetromino.randomOne();

					nextOne = Tetromino.randomOne();

					totalScore = 0;

					totalLine = 0;

				}

				switch (code) {

				case KeyEvent.VK_DOWN:

					softDropAction();

					break;

				case KeyEvent.VK_LEFT:

					moveLeftAction();

					break;

				case KeyEvent.VK_RIGHT:

					moveRightAction();

					break;

				case KeyEvent.VK_UP:

					rotateRightAction();

					break;

				case KeyEvent.VK_SPACE:
					handDropAction();
					break;

				
				default:

					break;

				}
				repaint();

			}	

		};
		this.addKeyListener(l);
		this.requestFocus();

		while(true) {

			if(game_state == PLAYING) {

			try {

				Thread.sleep(400);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(canDrop()) {
				currentOne.softDrop();
			}

			else {
				landToWall();

				destroyLine();
				if(!isGameOver()) {

					currentOne = nextOne;

					nextOne = Tetromino.randomOne();

				}

				else {

					game_state = GAMEOVER;

				}

			}
			}

			repaint();
		}
	}

             }

 
               









