package zatacka;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.JOptionPane;

import zatacka.Control;
import zatacka.Player.TDirection;
import zatacka.Player;

public class SerialClient extends Network {

	private Socket socket = null;
	private Socket socketGift = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ObjectInputStream inGift = null;
	private ObjectOutputStream outGift = null;		//No one uses, just for the overriding..

	SerialClient(Control c) {
		super(c);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			System.out.println("Waiting for key presses...");
			try {
				while (true) {
					Player playerReceived = (Player) in.readObject();
					ctrl.playerReceived(playerReceived);
					
					Gift giftReceived = (Gift) inGift.readObject();
					ctrl.giftReceived(giftReceived);
					
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Server disconnected!");
			} finally {
				disconnect();
			}
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			socket = new Socket(ip, 10007);
			socketGift = new Socket(ip, 10008);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			
			outGift = new ObjectOutputStream(socketGift.getOutputStream());
			inGift = new ObjectInputStream(socketGift.getInputStream());
			outGift.flush();
			
			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection. ");
			JOptionPane.showMessageDialog(null, "Cannot connect to server!");
		}
	}

	@Override
	void send(Player player) {
		if (out == null)
			return;
		try 
		{
			out.writeObject(player);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}
	
	void sendNewGift(Gift gift) {
		if (outGift == null)
			return;
		try {
			outGift.writeObject(gift);
			outGift.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}

	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Error while closing conn.");
		}
	}
}