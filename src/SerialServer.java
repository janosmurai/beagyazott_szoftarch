package zatacka;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import zatacka.Control;
import zatacka.Player.TDirection;
import zatacka.Player;

public class SerialServer extends Network {

	private ServerSocket serverSocket = null;
	private ServerSocket serverSocketGift = null;
	private Socket clientSocket = null;
	private Socket clientSocketGift = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream outGift = null;
	private ObjectInputStream inGift = null;
	
	SerialServer(Control c) {
		super(c);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			try {
				System.out.println("Waiting for Client");
				clientSocket = serverSocket.accept();
				clientSocketGift = serverSocketGift.accept();
				System.out.println("Client connected.");
			} catch (IOException e) {
				System.err.println("Accept failed.");
				disconnect();
				return;
			}

			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				out.flush();
				
				outGift = new ObjectOutputStream(clientSocketGift.getOutputStream());
				inGift = new ObjectInputStream(clientSocketGift.getInputStream());
				outGift.flush();
				
			} catch (IOException e) {
				System.err.println("Error while getting streams.");
				disconnect();
				return;
			}

			try {
				while (true) {
					Player playerReceived = (Player) in.readObject();
					ctrl.playerReceived(playerReceived);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				System.err.println("Client disconnected!");
			} finally {
				disconnect();
			}
			
			
		}
	}

	@Override
	void connect(String ip) {
		disconnect();
		try {
			serverSocket = new ServerSocket(10007);
			//serverSocketGift = new ServerSocket(10008);

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007.");
		}
	}

	@Override
	void send(Player player) {
		if (out == null)
			return;
		//System.out.println("Sending point: " + direction + " to Client");
		try {
			//if(player.p.color.equals(Color.BLUE)) System.out.println(player.p.x);
			out.writeObject(player);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error /player/ .");
		}
	}
	
	void sendNewGift(Gift gift) {
		if (outGift == null)
			return;
		try {
			outGift.writeObject(gift);
			outGift.flush();
		} catch (IOException ex) {
			System.err.println("Send error /gift/ .");
		}
	}

	@Override
	void disconnect() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
			
			if (outGift != null)
				outGift.close();
			if (inGift != null)
				inGift.close();
			if (clientSocketGift != null)
				clientSocketGift.close();
			if (serverSocketGift != null)
				serverSocketGift.close();
			
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}