package servidor.teste1;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.InputConnection;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class Servidor01 {

	public static final char LEFT = 'a';
	public static final char RIGHT = 'd';
	public static final char FWD = 'w';
	public static final char BWD = 's';
	public static final char STOP = 'q';

	// m�todo responsavel pela movimenta��o do rob�
	// o m�todo receber� um caracter para representar a velocidade e
	// a vari�vel byte para definir a velocidade
	public static void performMove(char cmd, int speed) {
		int velocidade = speed * 9;
		Motor.B.setSpeed(velocidade);
		Motor.C.setSpeed(velocidade);
		switch (cmd) {
		case FWD:
			Motor.B.forward();
			Motor.C.forward();
			break;
		case BWD:
			Motor.B.backward();
			Motor.C.backward();
			break;
		case LEFT:
			Motor.B.backward();
			Motor.C.forward();
			break;
		case RIGHT:
			Motor.C.backward();
			Motor.B.forward();
			break;
		case STOP:
			Motor.C.stop();
			Motor.B.stop();
			break;
		}
	
	}
	
	public static void main(String[] args) {
		
		LCD.drawString("Esperando", 0, 0); // escrevendo na tela
		
		// Classe de conex�o que proporciona acesso ao padr�o E/S e esperando por uma conex�o
		NXTConnection conexao = Bluetooth.waitForConnection();

		// configurando a conx�o para se comunicar com dispositivos m�veis, como um celular Android
		conexao.setIOMode(NXTConnection.RAW);
		
		// fluxo de entrada de dados
		DataInputStream in = conexao.openDataInputStream();
		
		
		LCD.clear(); // limpando e tela
		
		LCD.drawString("Conectado", 0, 0);
		char i = 0;
		byte j = 0;

		// o loop ser� finalizado quando o bot�o escape do nxt for pressionado
		while (!Button.ESCAPE.isDown()) {

				try {
					
					i = in.readChar(); // leitura do caracter representando a dire��o
					j = in.readByte(); // leitura do byte representando a velocidade
					
					LCD.clear();
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// imprimindo o valor recebido na tela, apenas usado para teste iniciais
				System.out.print(i + ", " + j);
	
				// chamada do m�todo que execultara a movimenta��o do rob�
				performMove(i, j);
			

		}
	}

}
