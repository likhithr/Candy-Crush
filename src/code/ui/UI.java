package code.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;

import code.model.Model;

public class UI implements Runnable {

	private JFrame _frame;
	private Model _model;
	private ArrayList<ArrayList<JButton>> _viewBoard;

	public UI() {
		_model = new Model();
		_viewBoard = new ArrayList<ArrayList<JButton>>();
	}

	@Override
	public void run() {
		_frame = new JFrame("Likhith Doddi's Lab 10");
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(_model.rows(), _model.cols()));
		_frame.getContentPane().setLayout(new GridLayout(1,0));
		_frame.add(board);
		_viewBoard = new ArrayList<ArrayList<JButton>>();
		for (int r=0; r<_model.rows(); r++) {
			_viewBoard.add(new ArrayList<JButton>());
			for (int c=0; c<_model.cols(); c=c+1) {
				JButton button = new JButton();
				button.setOpaque(true);
				_viewBoard.get(r).add(button);
				board.add(button);
				button.addActionListener(new EventHandler(_model, r, c));
			}
		}		

		_model.addObserver(this);
		
		update();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.pack();
		_frame.setVisible(true);
	}
	
	public void update() {
		// UPDATE BOARD - redraw the whole thing
		for (int r=0; r<_model.rows(); r=r+1) {
			for (int c=0; c<_model.cols(); c=c+1) {
				JButton button = _viewBoard.get(r).get(c);
				button.setIcon(new ImageIcon(_model.get(new Point(r,c))));
				button.setBackground(Color.WHITE);
			}
		}
		// MARK FIRST SELECTED - if applicable
		Point p = _model.selectedFirst();
		if (p != null) {
			_viewBoard.get(p.x).get(p.y).setBackground(Color.RED);
		}
		Point x=_model.hint();
		
		if (x != null) {
			_viewBoard.get(x.x).get(x.y).setBackground(Color.DARK_GRAY);
		}
		
		
		// REPAINT JFrame
		_frame.repaint();
	}

}
