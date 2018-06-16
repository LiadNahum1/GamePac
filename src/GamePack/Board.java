package GamePack;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;
import Food.Apple;
import Food.Energy;
import Food.Food;
import Food.PineApple;
import Food.RegDot;
import Food.StrawBerry;
import Ghosts.GreenGhost;
import Ghosts.PinkGhost;
import Ghosts.RedGhost;
import Ghosts.WhiteGhost;
import Ghosts.YellowGhost;
import Pacmen.AngryPacman;
import Pacmen.DefendedPacman;
import Pacmen.NicePacman;
import Pacmen.Pacman;
import Tiles.BoardTile;
import Tiles.GateTile;
import Tiles.RoadTile;
import Tiles.WallTile;

/*The class defines the board of the game - on it will be painted the maze and the figures - pacman, ghosts and fruits*/  
public class Board extends JPanel implements ActionListener, KeyListener { 
	private BoardTile [][] boardTiles;
	private String [][] boardTilesS; 
	private PacTimer timer;
	private Vector<String> [][] neighbors;
	private int level; 
	private Pacman pacman;
	private WhiteGhost whiteGhost;
	private GreenGhost greenGhost;
	private RedGhost redGhost;
	private YellowGhost yellowGhost;
	private PinkGhost pinkGhost;
	private boolean start; 
	private boolean isFruitsOn;
	private Vector<Food> fruitsLeft;
	private Vector<BoardTile> fruitsTiles; 
	private int numTicksOfGame;

	public Board(int level ,PacTimer timer) {
		initializeBoardPanel(level, timer);

	}
	public void initializeBoardPanel(int level, PacTimer timer) {
		this.level = level; 
		this.setBackground(Color.BLACK);
		this.start = false; //the game isn't start yet. waiting for press on space 
		this.isFruitsOn = false; 
		this.fruitsTiles = new Vector<>();
		this.timer =timer;
		timer.addLisenerArg(this);

		initializeBoardTilesS();
		initializeBoard();
		initializePacman();
		initializeNeighborsMat();
		InisializeGhosts();
		initializeFruits();

		this.numTicksOfGame = 1;
		this.setFocusable(true);
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(640,640));
		this.setVisible(true);
	}

	/*initializes Pacman according to the level and add it to action listener list of timer*/
	public void initializePacman() {
		if(level == 1) 
			this.pacman = new NicePacman(new Pair(14,16),this.boardTiles, this.boardTilesS, this.timer); 
		if(level == 2)
			this.pacman = new DefendedPacman(new Pair(14,16),this.boardTiles, this.boardTilesS, this.timer);
		if(level == 3)
			this.pacman = new AngryPacman(new Pair(14,16),this.boardTiles, this.boardTilesS, this.timer);
		this.timer.addLisenerArg(this.pacman);
	}
	/*return Pacman */
	public Pacman getPacman(){
		return this.pacman;
	}

	/*initializes ghosts and add them to action listener list of timer according to the level */
	public void InisializeGhosts() {
		this.greenGhost = new GreenGhost(this.boardTiles, new Pair(16,15), this.pacman, neighbors, this.timer);
		this.pinkGhost = new PinkGhost(this.boardTiles, new Pair(16,17),this.pacman, neighbors, this.timer);
		this.yellowGhost = new YellowGhost(this.boardTiles, new Pair(16,16),this.pacman, neighbors, this.timer);
		if(level == 1 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors, this.timer);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors, this.timer);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(pinkGhost);
			timer.addLisenerArg(whiteGhost);
		}
		if(level == 2 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors,this.timer);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors,this.timer);
			timer.addLisenerArg(whiteGhost);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(yellowGhost);
		}
		if(level == 3 ) {
			this.redGhost = new RedGhost(this.boardTiles, new Pair(16,14),this.pacman, neighbors,this.timer);
			this.whiteGhost = new WhiteGhost(this.boardTiles, new Pair(16,13),this.pacman, neighbors,this.timer);
			timer.addLisenerArg(greenGhost);
			timer.addLisenerArg(redGhost);
			timer.addLisenerArg(yellowGhost);
		}	
	}
	/*initializes fruits vector according to the level */
	public void initializeFruits() {
		this.fruitsLeft = new Vector<>();
		if(this.level == 1) {
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
		}
		else if(this.level == 2) {
			for(int i=0; i< 4; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 4; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
			this.fruitsLeft.add(new StrawBerry());
		}
		else {
			for(int i=0; i< 5; i = i+1) {
				this.fruitsLeft.add(new PineApple());
			}
			for(int i=0; i< 5; i = i+1) {
				this.fruitsLeft.add(new Apple());
			}
			for(int i=0; i< 2; i = i+1) {
				this.fruitsLeft.add(new StrawBerry());
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void initializeNeighborsMat() {
		neighbors=(Vector<String>[][]) new Vector[32][32];
		for(int i=0;i<32;i++) {
			for(int j=0;j<32;j++) {
				neighbors[i][j]=findneighbors(i,j);

			}
		}
	}
	private Vector<String> findneighbors(int x,int y) {
		Vector <String> possibleDirs = new Vector <>();//the frame of the board is all wals so there wont be problems of edged
		if(this.boardTilesS[x][y] == "w") {
			return null;
		}
		else {
			if(this.boardTilesS[x-1][y]!="w") //can move up
				possibleDirs.add("u");
			if(this.boardTilesS[x+1][y]!="w" & this.boardTilesS[x+1][y]!="g") //can move down
				possibleDirs.add("d");
			if(this.boardTilesS[x][y+1]!="w")//can move right
				possibleDirs.add("r");
			if(this.boardTilesS[x][y-1]!="w")//can move left
				possibleDirs.add("l");
			return possibleDirs;

		}

	}
	
	/*initialize BoardTile matrix according to BoardTileS matrix*/
	public void initializeBoard() {
		this.boardTiles = new BoardTile [32][32];  
		for(int i=0;i<32;i++){
			for(int j=0;j<32;j++){
				initializeBoardTile(i,j);
			}
		}
	}
	private void initializeBoardTile (int x,int y) {
		if(boardTilesS [x][y] == "w")
			boardTiles[x][y] =new WallTile(this.level, x, y);
		if(boardTilesS [x][y] == "d")
			boardTiles[x][y] =new RoadTile(x, y, true, new RegDot()); //regDot
		if(boardTilesS [x][y] == "0")
			boardTiles[x][y] =new RoadTile(x,y, false,null); //emptyTile
		if(boardTilesS[x][y] == "g")
			boardTiles [x][y] = new GateTile(x,y);
		if(boardTilesS[x][y] == "gh") // ghost on it
			boardTiles [x][y] = new RoadTile(x,y, true, null);
		if(boardTilesS[x][y] == "e") {
			boardTiles [x][y] = new RoadTile(x,y, true, new Energy());
		}
	}
	

	/*The function checks if Pacman ate all pills on screen. If he does return true, otherwise false*/
	public boolean checkIfWinLevel() {
		for(int i=0; i <this.boardTiles.length; i = i+1) {
			for(int j=0; j <this.boardTiles.length; j = j+1) {
				BoardTile b = boardTiles[i][j];
				if(!this.fruitsTiles.contains(b) & b.getFood()!=null) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	/*The function is responsible for:
	 * 1. starting the movement of the ghosts
	 * 2. drawing the fruits, dim them for 3 seconds and make them disappear after 5 seconds
	 * 3. check if Pacman eat something
	 * */
	public void actionPerformed(ActionEvent e) {
		this.requestFocusInWindow();
		int timerSpeed = this.timer.getSpeed();
		if(e.getSource().equals(this.timer.getGameTimer())) {
			if(this.numTicksOfGame <12)
				checkStart();
			this.numTicksOfGame =this.numTicksOfGame + 1;
		}
		//fruits
		if(this.numTicksOfGame > 10 * timerSpeed) {
			if(numTicksOfGame%timerSpeed == 0 & this.numTicksOfGame/timerSpeed  % 6== 0 ) {
				if(!this.fruitsLeft.isEmpty()) {
					drawFruits();
					this.isFruitsOn = true;
				}
				else
					this.isFruitsOn = false;
			}
			if(numTicksOfGame%timerSpeed == 0 & numTicksOfGame/timerSpeed % 6 > 2 & numTicksOfGame/timerSpeed% 6 <5) { // dim
				isFruitsOn = !isFruitsOn; 
			}
			if(numTicksOfGame%timerSpeed == 0 &numTicksOfGame/timerSpeed % 6 == 5) { //disappear
				this.isFruitsOn = false;
			}
		}
		checkIfPacEat();
		repaint();
	}

	private void checkStart() {
		if(level == 1 ) {
			if(this.numTicksOfGame == 7) 
				greenGhost.start();
			if(this.numTicksOfGame == 9) 
				pinkGhost.start();
			if(this.numTicksOfGame == 11)
				whiteGhost.start();
		}
		if(level == 2) {
			if(this.pacman.getScore() > 4000) {
				if(this.numTicksOfGame == 2) 
					greenGhost.start();
			}
			else 
			{
				if(this.numTicksOfGame == 4) 
					greenGhost.start();
			}
			if(this.numTicksOfGame == 6) 
				yellowGhost.start();
			if(this.numTicksOfGame == 9)
				whiteGhost.start();
		}
		if(level == 3) {
			if(this.pacman.getScore() > 10000) {
				greenGhost.start();
				redGhost.start();
				yellowGhost.start();		
			}
			else {
				if(this.numTicksOfGame == 4) 
					greenGhost.start();
				if(this.numTicksOfGame == 6) 
					yellowGhost.start();
				if(this.numTicksOfGame == 8)
					redGhost.start();		
			}
		}
	}

	/*The function is called after Pacman died but didn't lose all his lives
	 * The function returns all figures to their initial position*/
	public void revivePacman() {		
		this.pacman.initializePacman(new Pair(14,16));
		if(level == 1) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.pinkGhost.inisializeData(new Pair(16,17), new Pair(0,0), "l");
			this.whiteGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
		}
		if(level == 2) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.yellowGhost.inisializeData(new Pair(16,16), new Pair(0,0), "l");
			this.whiteGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
		}
		if(level == 3) {
			this.greenGhost.inisializeData(new Pair(16,15), new Pair(0,0), "u");
			this.redGhost.inisializeData(new Pair(16,14), new Pair(0,0), "r");
			this.yellowGhost.inisializeData(new Pair(16,16), new Pair(0,0), "l");
		}
		this.numTicksOfGame = 1;
	}

	/*The function is called when there is a press on space and then the game starts*/
	public void start() {
		this.start = true;
	}
	/*The function purpose is to check if Pacman ate a fruit. If he does, the function delete the food of the Food vector
	 * and of the BoardTile that he was on*/ 
	public void checkIfPacEat() {
		int i = this.pacman.getCurrentPosition().getX();
		int j = this.pacman.getCurrentPosition().getY();

		BoardTile pacTile = this.boardTiles[i][j];
		Food food = pacTile.getFood();
		pacTile.setFood(null);
		//if it was a fruit
		if(this.fruitsTiles.remove(pacTile)) {
			this.fruitsLeft.remove(food);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		this.pacman.manageMovement(e);
		checkIfPacEat();
		repaint();
	}

	/*The function is responsible for painting the board: all its tiles and all the figures: pacman, ghosts and food*/
	public void paintComponent(Graphics g){	
		//draw board
		g.fillRect(0, 0, 640, 640);
		Image offIm = this.createImage(640 , 640);
		Graphics offGr = offIm.getGraphics();	
		for(int i=0; i<this.boardTiles.length; i = i+1) {
			for(int j=0; j<this.boardTiles.length; j =j+1) {
				offGr.drawImage(this.boardTiles[i][j].getImage(), j*20, i*20, this);
			}
		}
		g.drawImage(offIm, 0, 0, this);
		
		//draw ghost 
		this.greenGhost.draw(this, g);
		this.whiteGhost.draw(this, g);		
		this.pinkGhost.draw(this, g);
		this.redGhost.draw(this, g);		
		this.yellowGhost.draw(this, g);
		
		
		//pacman draw
		this.pacman.draw(this, g);
		
		//draw fruits
		for(int i=0; i<this.fruitsTiles.size(); i = i+1) {
			if(!isFruitsOn) {
				g.drawImage(RoadTile.road.getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);
			}
			else {
				g.drawImage(this.fruitsTiles.get(i).getImage(), this.fruitsTiles.get(i).getY()*20, this.fruitsTiles.get(i).getX()*20, this);
			}
		}
		
		if(!start) {
			drawGetReady(g);
		}
	}

	/*The function finds an empty RoadTiles for putting fruits on them by using random Object*/
	public void drawFruits() {
		Random rand = new Random();
		int x =0;
		int y=0;
		BoardTile b = this.boardTiles[x][y];
		for(int i=0; i< this.fruitsTiles.size(); i = i+1) {
			this.fruitsTiles.get(i).setFood(null);
		}
		this.fruitsTiles.clear();
		for(int i=0; i< this.fruitsLeft.size(); i = i+1) {
			while(b.getIsSomethingOn()) {
				x = rand.nextInt(32);
				y = rand.nextInt(32);
				b = this.boardTiles[x][y];
			}
			if(!b.getIsSomethingOn()) {
				b.setFood(this.fruitsLeft.get(i));
				this.fruitsTiles.add(b);
			} 
		}
	}
	
	/*draw "Get Ready" string in the middle of the board*/
	private void drawGetReady(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));    
		g.setColor(Color.red);
		g.drawString("Get Ready", 270, 380);
	}

	public BoardTile getBoardTile(Pair place) {
		return this.boardTiles[place.getX()][place.getY()];
	}
	
	/*Build a string matrix according to the level that defines the board of the game*/
	public void initializeBoardTilesS() {
		if(level == 1)
			this.boardTilesS = new String[][] 
					{{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
			{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","0","0","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","w","w","w","w","w"},
			{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
			{"w", "d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
			{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","d","w","w","d","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
			{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
			{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
			{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};
			else if(level == 2)
				this.boardTilesS = new String[][] 	{{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
				{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
				{"w", "d","w","w","w","w","d","w","w","d","w","w","w","w","d","w","w","d","w","w","d","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","w","d","d","d","w","w","0","w","w","0","w","w","d","d","d","w","w","d","w","w","w","w","d","w"},
				{"w", "d","d","d","d","d","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","d","d","d","d","d","w"},
				{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","w","d","w","d","w","w"},
				{"w", "0","w","w","w","w","d","w","w","0","w","0","w","w","0","w","w","0","w","w","0","w","0","w","w","d","w","d","d","d","w","w"},
				{"w", "0","w","w","w","w","d","w","0","0","0","0","0","0","0","w","w","0","0","0","0","0","0","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","w","0","w","w","w","w","w","0","w","w","0","w","w","w","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "w","w","0","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","d","d","d","w","w"},
				{"w", "w","w","0","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","d","d","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","w","d","d","d","w","w"},
				{"w", "w","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "w","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","d","w","d","w","w"},
				{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","d","w","d","w","w","w","d","w","d","w","w","d","w","w","w","d","w","d","w","d","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","d","w","d","w","w","w","d","w","d","w","w","d","w","w","w","d","w","d","w","d","w","w","w","w","d","w"},
				{"w", "0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","w","w"},
				{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
				{"w", "w","w","d","w","w","d","w","d","w","w","w","w","w","d","w","w","d","w","w","w","w","w","d","w","d","w","w","d","w","w","w"},
				{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
				{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
				{"w", "d","w","w","w","w","w","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","w","w","w","w","w","d","w"},
				{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
				{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};	
				else
					this.boardTilesS = new String[][]  {{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"},
					{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","0","w","w","0","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","0","w","w","w","w","w","w","0","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","0","0","0","0","0","0","0","0","0","0","0","0","0","0","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","w","w","d","d","d","d","d","d","w","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","w","0","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","w","0","w","w","w","0","w","0","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","0","0","0","0","d","w","0","w","w","0","0","0","0","w","w","w","0","0","0","0","0","0","w","d","0","0","0","0","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","w","w","0","w","w","w","0","w","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","0","0","w","w","0","w","g","g","g","g","g","w","w","0","w","w","0","0","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","w","gh","gh","gh","gh","gh","w","w","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","0","0","0","0","d","w","0","w","w","0","w","w","w","w","w","w","w","w","0","w","w","0","w","d","0","0","0","0","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","0","0","0","0","0","0","0","0","0","0","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","0","w","w","w","w","w","w","w","w","w","w","w","w","w","w","0","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","w","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","d","w","w","d","w","w","w","w","w","w","w","d","w","w","w","w","d","w"},
					{"w", "d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","d","w","w","d","w","d","w","w","w","w","w","w","w","w","w","w","w","w","w","w","d","w","d","w","w","d","w","d","w"},
					{"w", "d","d","d","w","w","d","w","d","d","d","d","d","d","d","d","w","d","d","d","d","d","d","d","w","d","w","w","d","d","d","w"},
					{"w", "d","w","d","w","w","d","w","d","w","w","w","w","w","w","d","w","d","w","w","w","w","w","d","w","d","w","w","d","w","d","w"},
					{"w", "d","d","d","d","d","d","w","d","d","d","d","d","d","d","d","w","d","d","d","d","d","d","d","d","d","d","d","d","d","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","w","w","d","w","w","w","w","w","d","w"},
					{"w", "d","w","w","w","w","d","w","w","w","w","w","w","w","w","d","w","d","w","w","w","w","w","w","d","w","w","w","w","w","d","w"},
					{"w", "e","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","d","e","w"},
					{"w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w","w"}};

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}




}


