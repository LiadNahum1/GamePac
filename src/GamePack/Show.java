package GamePack;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Food.Food;
import Pacmen.*;

public class Show extends JPanel implements ActionListener {
	private PacTimer timer;
	private Pacman pac;
	private boolean isStop; 
	private Vector<Food> fruits; 
	private int numTicksWithoutStop;
	private int numOfLives = 3;
	private JLabel[]lifePic;
	private JLabel scors;
	private JLabel timesShow;
	private JButton stop;
	private JButton fastForword;
	private JButton exit;
	private JPanel buttons;
	private JPanel data;
	
	
public Show(Pacman pac, Vector<Food> fruits , PacTimer timer ,  int numOfLives) {
this.pac = pac;
this.fruits = fruits;
this.timer = timer;
this.numOfLives = numOfLives;
this.isStop = true;
timer.addLisenerArg(this);
	this.setOpaque(false);
	this.setBackground(Color.BLACK);
	this.setLayout(new GridLayout(2,1));
	inisializeBattons();
	inisializeData();
	this.setFocusable(true);
	
}


private void inisializeBattons() {
	JPanel buttons = new JPanel();
	buttons.setOpaque(false);
	buttons.setLayout(new FlowLayout());
	this.exit = new JButton("Exit Game");
	exit.setFont(new Font("Serif", Font.BOLD, 30));
	exit.addActionListener(this);
	buttons.add(exit);
	this.stop = new JButton("stop");
	stop.setFont(new Font("Serif", Font.BOLD, 30));
	stop.addActionListener(this);
	buttons.add(stop);
	this.fastForword = new JButton("next speed");
	fastForword.setFont(new Font("Serif", Font.BOLD, 30));
	fastForword.addActionListener(this);
	buttons.add(fastForword);
	this.add(buttons);
	
	
	
}


private void inisializeData() {
	JPanel data = new JPanel();
	data.setBackground(Color.CYAN);
	data.setLayout(new GridLayout(2,4));
	JLabel t = new JLabel("time:");
	JLabel s = new JLabel("score:");
	t.setFont(new Font("Serif", Font.BOLD, 50));
	s.setFont(new Font("Serif", Font.BOLD, 50));
	timesShow = new JLabel("0");
	timesShow.setFont(new Font("Serif", Font.BOLD, 50));
	scors = new JLabel("0");
	scors.setFont(new Font("Serif", Font.BOLD, 50));
	data.add(t);
	data.add(timesShow);
	data.add(s);
	data.add(scors);
	lifePic = new JLabel[numOfLives];
	for(int i=0 ;i<this.numOfLives; i = i +1) {
		lifePic[i] = new JLabel (new ImageIcon("pictures\\boards\\lives.png"));
		data.add(lifePic[i]);
	}
	this.add(data);
}


@Override
public void actionPerformed(ActionEvent arg0) {
	if(arg0.getSource().equals(timer.getGameTimer())) {
	this.pac.getScore();
	scors.setText(String.valueOf(this.pac.getScore()));
	timesShow.setText(String.valueOf(numTicksWithoutStop));
	numTicksWithoutStop++;
	}
	if(arg0.getSource().equals(stop)){
		if(isStop) {
		this.timer.getGameTimer().stop();
		stop.setText("start");
		}
		else {
			this.timer.getGameTimer().start();
			stop.setText("stop");
		}
		isStop =!isStop;
	}

	if(arg0.getSource().equals(exit)){
		setVisible(false);
		new MainMenu();
	}
}	
}
