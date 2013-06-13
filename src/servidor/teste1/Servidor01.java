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

	// método responsavel pela movimentação do robô
	// o método receberá um caracter para representar a velocidade e
	// a variável byte para definir a velocidade
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
		
		// Classe de conexão que proporciona acesso ao padrão E/S e esperando por uma conexão
		NXTConnection conexao = Bluetooth.waitForConnection();

		// configurando a conxão para se comunicar com dispositivos móveis, como um celular Android
		conexao.setIOMode(NXTConnection.RAW);
		
		// fluxo de entrada de dados
		DataInputStream in = conexao.openDataInputStream();
		
		
		LCD.clear(); // limpando e tela
		
		LCD.drawString("Conectado", 0, 0);
		char i = 0;
		byte j = 0;

		// o loop será finalizado quando o botão escape do nxt for pressionado
		while (!Button.ESCAPE.isDown()) {

				try {
					
					i = in.readChar(); // leitura do caracter representando a direção
					j = in.readByte(); // leitura do byte representando a velocidade
					
					LCD.clear();
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// imprimindo o valor recebido na tela, apenas usado para teste iniciais
				System.out.print(i + ", " + j);
	
				// chamada do método que execultara a movimentação do robô
				performMove(i, j);
			

		}
	}

}
