package com.UDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Clienteudp
{
	/**
	 * @param args
	 *           server IP(127.0.0.1 for localhost)
	 */
	public static void main(String[] args){
		if(args.length==0){
			System.err.println("ERROR: Enter a valid server IP, please.");
			System.exit(1);
		}

		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket datagramSocket;
		InetAddress inetAddress;
		byte[] message_bytes=new byte[256];
		byte[] mensaje_bytesServ=new byte[256];
		String message="";
		String messageFromServer="";
		// Package to send to the server
		DatagramPacket datagramPacketOut;
		// Package to receive from the server
		DatagramPacket datagramPacketIn;

		message_bytes=message.getBytes();
		try{
			// Create the socket
			datagramSocket=new DatagramSocket();
			// Get server IP
			inetAddress=InetAddress.getByName(args[0]);
			message="HELLO";
			message_bytes=message.getBytes();
			// Create the package
			datagramPacketOut=new DatagramPacket(message_bytes,message.length(),inetAddress,6000);
			// Send the package
			datagramSocket.send(datagramPacketOut);
			// Received message in bytes format
			mensaje_bytesServ=new byte[256];
			// The package to receive
			datagramPacketIn=new DatagramPacket(mensaje_bytesServ,256);
			datagramSocket.receive(datagramPacketIn);
			// Convert bytes to string
			messageFromServer=new String(mensaje_bytesServ);
			// Print received message
			System.out.println(messageFromServer);

			do{
				// Read characters introduced in console
				message=bufferedReader.readLine();
				message_bytes=message.getBytes();
				// Create the package
				datagramPacketOut=new DatagramPacket(message_bytes,message.length(),inetAddress,6000);
				// Send the package
				datagramSocket.send(datagramPacketOut);
				// Received message in bytes format
				mensaje_bytesServ=new byte[256];
				// The package to receive
				datagramPacketIn=new DatagramPacket(mensaje_bytesServ,256);
				datagramSocket.receive(datagramPacketIn);
				// Convert bytes to string
				messageFromServer=new String(mensaje_bytesServ).trim();
				// Print received message
				System.out.println(messageFromServer);
			}while(!message.startsWith("END"));
		}catch(Exception e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
