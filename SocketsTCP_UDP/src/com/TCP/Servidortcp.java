package com.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidortcp {
	public static void main(String argv[]) {
		int switchOperator = 0;
		String hello = "HELLO";
		String buy = "BUYUP";
		String annul = "ANNUL";
		String exit = "END";
		ServerSocket serverSocket;		
		int[] subwayZoneList;
		int total;
		while (true) {
			try {
				serverSocket = new ServerSocket(6001);
				Socket clientSocket = serverSocket.accept();
				DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
				if (dataInputStream.readUTF().equals(hello)) {
					dataOutputStream.writeUTF(hello +  "\nBuy yor subway ticket for one of five zones.\n"
							+ "Options allowed are: \"BUYUP\",\"ANNUL\" followed by 1,2,3,4 or 5.\n"
							+ "for example: BUYUP 5 if you want to buy a ticket for zone 5\n"
							+ "type \"END\" to exit.");
					System.out.println(hello);
					total = 0;
					subwayZoneList = new int[5]; // position = Subway zone(there are 5 subway zones);				
					do {
						String message = "";
						message = dataInputStream.readUTF();
						String aux = message.replaceAll(" +", ""); // remove empty spaces
						if (aux.length() == buy.length() + 1
								&& message.substring(0, buy.length()).equals(buy)
								|| aux.length() == buy.length() + 1
								&& message.substring(0, buy.length()).equals(annul)) {
							String operationCode = message.substring(0, buy.length()); // operation
							switchOperator = Integer.valueOf(aux.substring(buy.length())); // zone
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

							message += ": " + subwayZoneList[0] + ";" + subwayZoneList[1] + ";"
									+ subwayZoneList[2] + ";" + subwayZoneList[3] + ";"
									+ subwayZoneList[4] + ": " + total;
						} else if (message.equals(exit)) {
							dataOutputStream.writeUTF(message);
							dataInputStream.close();
							dataOutputStream.close();
							serverSocket.close();
							break;

						} else
							message += ": ERROR";
						System.out.println(message);
						dataOutputStream.writeUTF(message);
					} while (1 > 0);
				}

			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}

		}
	}
}
