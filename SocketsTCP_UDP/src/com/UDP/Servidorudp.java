package com.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidorudp {
	public static void main(String argv[]) {
		String hello = "HELLO";
		String buy = "BUYUP";
		String annul = "ANNUL";
		String exit = "END";
		int switchOperator = 0;
		int[] subwayZoneList = new int[5]; // position = Subway zone(there are 5 subway zones);
		int total = 0;
		DatagramSocket datagramSocket = null;
		boolean fin = false;
		// Create package to send back to the client
		DatagramPacket datagramPacketOut;
		while (true) {
			try {
				datagramSocket = new DatagramSocket(6000);
				do {
					byte[] menssage_bytes = new byte[256];
					DatagramPacket datagramPacket = new DatagramPacket(
							menssage_bytes, 256);
					datagramSocket.receive(datagramPacket);
					String message = "";
					message = new String(menssage_bytes).trim();

					String aux = message.replaceAll(" +", ""); // remove empty spaces
					if (aux.length() == buy.length() + 1
							&& message.substring(0,  buy.length()).equals(buy)
							|| aux.length() == buy.length() + 1
							&& message.substring(0,  buy.length()).equals(annul)) {

						char w = aux.charAt(buy.length());
						String str = String.valueOf(w);
						String operationCode = message.substring(0, buy.length()); // operation
						switchOperator = Integer.parseInt(str); // subway zone
						switch (switchOperator) {
						case 1:
							if (operationCode.equals(buy)) {
								subwayZoneList[0]++;
								total += 5;
								message += ": 5 Euros"; // price
							} else {
								if (subwayZoneList[0] >= 1) {
									subwayZoneList[0]--;
									total -= 5;
									message += ": -5 Euros";
								} else
									message += ": ERROR";
							}
							break;
						case 2:
							if (operationCode.equals(buy)) {
								subwayZoneList[1]++;
								total += 7;
								message += ": 7 Euros";
							} else {
								if (subwayZoneList[1] >= 1) {
									subwayZoneList[1]--;
									total -= 7;
									message += ": -7 Euros";
								} else
									message += ": ERROR";
							}
							break;
						case 3:
							if (operationCode.equals(buy)) {
								subwayZoneList[2]++;
								total += 10;
								message += ": 10 Euros";
							} else {
								if (subwayZoneList[2] >= 1) {
									subwayZoneList[2]--;
									total -= 10;
									message += ": -10 Euros";
								} else
									message += ": ERROR";
							}
							break;
						case 4:
							if (operationCode.equals(buy)) {
								subwayZoneList[3]++;
								total += 16;
								message += ": 16 Euros";
							} else {
								if (subwayZoneList[3] >= 1) {
									subwayZoneList[3]--;
									total -= 16;
									message += ": -16 Euros";
								} else
									message += ": ERROR";
							}
							break;
						case 5:
							if (operationCode.equals(buy)) {
								subwayZoneList[4]++;
								total += 25;
								message += ": 25 Euros";
							} else {
								if (subwayZoneList[4] >= 1) {
									subwayZoneList[4]--;
									total -= 25;
									message += ": -25 Euros";
								} else
									message += ": ERROR";
							}
							break;

						default:
							message += ": ERROR";
						}

					} else if (message.equals("TOTAL")) {

						message += ": " + subwayZoneList[0] + ";"
								+ subwayZoneList[1] + ";" + subwayZoneList[2]
								+ ";" + subwayZoneList[3] + ";"
								+ subwayZoneList[4] + ": " + total;
					} else if (message.equals("HELLO"))
						message = "HELLO\nBuy yor subway ticket for one of five zones.\n"
								+ "Options allowed are: \"BUYUP\",\"ANNUL\" followed by 1,2,3,4 or 5.\n"
								+ "for example: BUYUP 5 if you want to buy a ticket for zone 5\n"
								+ "type \"END\" to exit.";
					else if (message.equals(exit)) {

						fin = true;
					} else
						message += ": ERROR";
					System.out.println(hello);

					// Port the package was sent from
					int prt = datagramPacket.getPort();
					// Internet address the package was sent from
					InetAddress address = datagramPacket.getAddress();
					// Prepare sending format
					byte[] mensaje_bytesOut = new byte[256];
					mensaje_bytesOut = message.getBytes();
					// Generate the package to sent to the sender
					// with it's data obtained from the original package
					datagramPacketOut = new DatagramPacket(mensaje_bytesOut,
							message.length(), address, prt);

					datagramSocket.send(datagramPacketOut);

				} while (!fin);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			datagramSocket.close(); // close and get back to the loop start
		}
	}
}
