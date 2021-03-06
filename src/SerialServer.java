package zatacka;

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
import zatacka.SendSocket.socket_type;
import zatacka.Player;

public class SerialServer extends Network {

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	SerialServer(Control c) {
		super(c);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			try {
				System.out.println("Waiting for Client");
				clientSocket = serverSocket.accept();
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
			} catch (IOException e) {
				System.err.println("Error while getting streams.");
				disconnect();
				return;
			}

			try {
				while (true) 
				{
					SendSocket socketRecived = (SendSocket) in.readObject();
					//System.out.println(playerReceived.direction);
					if(socketRecived.type.equals(socket_type.gift))
					{
						ctrl.giftReceived(socketRecived.sendGift);
					}
					else if(socketRecived.type.equals(socket_type.player))
					{
						ctrl.playerReceived(socketRecived.sendPlayer);
				
					}
					else
					{	
						System.err.println("Unknown class type");
					}
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

			Thread rec = new Thread(new ReceiverThread());
			rec.start();
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007.");
		}
	}

	@Override
	void send(SendSocket socket) {
		if (out == null)
			return;
		//System.out.println("Sending point: " + direction + " to Client");
		try {
			out.writeObject(socket);
			out.flush();
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
			if (clientSocket != null)
				clientSocket.close();
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}