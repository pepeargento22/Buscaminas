import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Buscaminas extends JFrame implements ActionListener {
	private JButton[][] minas; 
	private JButton boton;
	private JCheckBox check;
	private boolean ganaste=false, perdiste=false;
	private int contadorGanar;
	
	public Buscaminas() {
		setLayout(null);
		minas=new JButton[10][10];
		for (int f=0; f<10; f++) {
			for (int c=0; c<10; c++) {
				minas[f][c]=new JButton();
				minas[f][c].setBounds(10+41*f,10+41*c,41,41);
				add(minas[f][c]);
				minas[f][c].addActionListener(this);
			}
		}
		boton=new JButton("Jugar");
		boton.setBounds(162,455,120,30);
		add(boton);
		boton.addActionListener(this);
		check=new JCheckBox("Marcar minas");
		check.setBounds(300, 455, 120, 30);
		add(check);
	}
	
	public void borrarMinas() {
		for (int f=0; f<10; f++) {
			for (int c=0; c<10; c++) {
				if (minas[f][c].getText().equals("b")) {
					minas[f][c].setText("");
				}
			}
		}	
	}
	
	public void ponerMinas() {
		int F, C, contador=0;
		while (contador < 10) {
			F= (int) (Math.random() * 9);
			C= (int) (Math.random() * 9);
			if (minas[F][C].getText().equals("b")==false) {
				contador++;
				minas[F][C].setText("b");
				minas[F][C].setForeground(Color.gray);
				minas[F][C].setBackground(Color.gray);
			}
		}
	}
	
	public void ponerNumeros(int F,int C) {
		if (minas[F][C].getText().equals("b")==false) {
			int contador=0;
			minas[F][C].setForeground(Color.gray);
			minas[F][C].setBackground(Color.gray);
			if (C<9 && minas[F][C+1].getText().equals("b")) {
				contador++;
			}
			if (F<9 && C<9 && minas[F+1][C+1].getText().equals("b")) {
				contador++;
			}
			if (F<9 && minas[F+1][C].getText().equals("b")) {
				contador++;
			}
			if (F<9 && C>0 && minas[F+1][C-1].getText().equals("b")) {
				contador++;
			}
			if (C>0 && minas[F][C-1].getText().equals("b")) {
				contador++;
			}
			if (F>0 && C>0 && minas[F-1][C-1].getText().equals("b")) {
				contador++;
			}
			if (F>0 && minas[F-1][C].getText().equals("b")) {
				contador++;
			}
			if (F>0 && C<9 && minas[F-1][C+1].getText().equals("b")) {
				contador++;
			}
			minas[F][C].setText(String.valueOf(contador));
		}
		if (C<9) {
			ponerNumeros(F, C+1);	
		}
		if (F<9) {
			ponerNumeros(F+1,C);
		}
	}
	
	public void ceros(int F, int C) {
		if (minas[F][C].getText().equals("0") && minas[F][C].getForeground().equals(Color.gray)) {
			minas[F][C].setForeground(Color.white);
			minas[F][C].setBackground(Color.lightGray);
			contadorGanar++;
			if (C < 9) {
				ceros(F,C+1);
			}
			if (F < 9) {
				ceros(F+1,C);
			}
			if (C > 0) {
				ceros(F,C-1);
			}
			if (F > 0) {
				ceros(F-1,C);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==boton) {
			setTitle("");
			borrarMinas();
			ponerMinas();
			ponerNumeros(0,0);
			perdiste=false;
			ganaste=false;
			contadorGanar=0;
		}
		for (int f=0; f<10; f++) {
			for (int c=0; c<10; c++) {
				if (e.getSource()==minas[f][c]) {
					if (check.isSelected()==false && minas[f][c].getBackground().equals(Color.yellow)==false) {
						ceros(f,c);
						if (minas[f][c].getText().equals("1")) {
							minas[f][c].setForeground(Color.blue);
						}
						if (minas[f][c].getText().equals("2")) {
							minas[f][c].setForeground(Color.green);
						}
						if (minas[f][c].getText().equals("3")) {
							minas[f][c].setForeground(Color.red);
						}
						if (minas[f][c].getText().equals("4")) {
							minas[f][c].setForeground(Color.magenta);
						}
						if (minas[f][c].getText().equals("5") || minas[f][c].getText().equals("6") || minas[f][c].getText().equals("7") || minas[f][c].getText().equals("8")) {
							minas[f][c].setForeground(Color.pink);
						}
						if (minas[f][c].getText().equals("b")) {
							minas[f][c].setForeground(Color.black);
							contadorGanar--;
							if (ganaste==false) {
								setTitle("Felicitaciones PERDISTE!");
								perdiste=true;
								boton.setText("Reiniciar");
							}
						}
						if (minas[f][c].getBackground().equals(Color.gray)) {
							contadorGanar++;
							minas[f][c].setBackground(Color.lightGray);
						}
						if (contadorGanar>=90 && perdiste==false) {
							setTitle("Felicitaciones GANASTE!");
							ganaste=true;
							boton.setText("Reiniciar");
						}	
					} else {
						if (minas[f][c].getForeground().equals(Color.yellow) && check.isSelected()) {
							minas[f][c].setForeground(Color.gray);
							minas[f][c].setBackground(Color.gray);
						} else {
							if (minas[f][c].getBackground().equals(Color.gray)) {
								minas[f][c].setForeground(Color.yellow);
								minas[f][c].setBackground(Color.yellow);
							}
						}
					}
					
				}
			}
		}
	}
	
	public static void main(String[] ar) {
		Buscaminas ventana=new Buscaminas();
		ventana.setBounds(10, 10, 460, 550);
		ventana.setVisible(true);
	}
}
