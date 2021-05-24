package com.lgu.seamless;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class UDPClient {

	public static void main(String[] args) {
		// 키보드 입력 받기 위한 변수
		Scanner scanner = new Scanner(System.in);
		System.out.println("보낼 메세지 입력 :");
		// String msg = scanner.next();

		try {
			// 전송할 수 있는 UDP 소켓 생성
			DatagramSocket dsoc = new DatagramSocket();

			// 받을 곳의 주소 생성
			InetAddress ia = InetAddress.getByName("localhost");

			while (true) {
				String msg = scanner.nextLine();

				// 전소할 데이터 생성
				DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, ia, 10010);

				// epdlxj wjsthd
				dsoc.send(dp);
				System.out.println("sent msg :: " + msg);
				
				if(msg.equals("bye")){
					dsoc.close();
					break;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
