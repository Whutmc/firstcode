package lab1;
import java.util.Arrays;

public class Tetromino {

    protected Cell[] cells =new Cell[4];
    
    
    public void moveleft() {
    	for(Cell c:cells)
    		c.left();
    }
    
    public void moveRight() {
    	for(Cell c:cells)
    		c.right();
    }
    
    public void softDrop() {
    	for(Cell c:cells)
    		c.drop();
    }
    
    @Override
    public String toString() {
    	return "["+ Arrays.toString(cells) +"]";
    }
    public static Tetromino randomOne() {
    	Tetromino t = null;
    	int num=(int)(Math.random()*7);
    	switch(num) {
    	case 0:t=new T();break;
    	case 1:t=new O();break;
    	case 2:t=new I();break;
    	case 3:t=new J();break;
    	case 4:t=new S();break;
    	case 5:t=new Z();break;
    	case 6:t=new L();break;
    	}
    	return t;
    }
        protected State[] states;
        private int count = 10000;
        
        public void rotateRight() {
        	
        	count++;
        	State s = states[count%states.length];
        	Cell c = cells[0];
        	int row = c.getRow();
        	int col = c.getCol();
        	cells[1].setRow(row+s.row1);
        	cells[1].setCol(col+s.col1);
        	cells[2].setRow(row+s.row2);
        	cells[2].setCol(col+s.col2);
        	cells[3].setRow(row+s.row3);
        	cells[3].setCol(col+s.col3);
        }
        
        public void rotateLeft() {
        	count--;
        	State s = states[count%states.length];
        	Cell c = cells[0];
        	int row = c.getRow();
        	int col = c.getCol();
        	cells[1].setRow(row+s.row1);
        	cells[1].setCol(col+s.col1);
        	cells[2].setRow(row+s.row2);
        	cells[2].setCol(col+s.col2);
        	cells[3].setRow(row+s.row3);
        	cells[3].setCol(col+s.col3);
        }
}
