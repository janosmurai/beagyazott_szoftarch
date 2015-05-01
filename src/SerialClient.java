package zatacka;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import zatacka.Control;
import zatacka.GUI.ColoredPoint;
import zatacka.GUI.TDirection;

public class SerialClient extends Network {

	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	SerialClient(Control c) {
		super(c);
	}

	private class ReceiverThread implements Runnable {

		public void run() {
			System.out.println("Waiting for key presses...");
			try {
				while (true) {
					ColoredPoint point_received = (ColoredPoint) in.readObject();
					ctrl.pointReceived(point_received);
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

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();

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
	void send(TDirection direction) {
		if (out == null)
			return;
		//System.out.println("Sending KeyEvent: " + direction + " to Server");
		try {
			out.writeObject(direction);
			out.flush();
		} catch (IOException ex) {
			System.err.println("Send error.");
		}
	}
	
	void sendNewP(ColoredPoint p) {
		if (out == null)
			return;
		try {
			out.writeObject(p);
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
			if (socket != null)
				socket.close();
		} catch (IOException ex) {
			System.err.println("Error while closing conn.");
		}
	}
}