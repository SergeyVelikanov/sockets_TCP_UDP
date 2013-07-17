package com.TCP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Clientetcp {
	
	/**
	 * @param args
	 *           server IP(127.0.0.1 for localhost)
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("ERROR: Enter a valid server IP, please.");
			System.exit(1);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Socket socket;
		InetAddress address;		
		String mensaje = "";
		try {
			address = InetAddress.getByName(args[0]);
			socket = new Socket(address, 6001);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream inClient = new DataInputStream(socket.getInputStream());
			out.writeUTF("HELLO");
			System.out.println(inClient.readUTF());
			do {
				mensaje = in.readLine();
				out.writeUTF(mensaje);
				String mensajeServer = "";
				mensajeServer = inClient.readUTF();
				System.out.println(mensajeServer);
			} while (!mensaje.startsWith("END"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
