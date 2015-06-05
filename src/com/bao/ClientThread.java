package com.bao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientThread extends Thread {

	Socket clientSocket;
	String name;
	List clientSocketList;
	InputStream is;
	BufferedReader br;

	public ClientThread(Socket clientSocket, List clientSocketList) {
		this.clientSocket = clientSocket;
		this.clientSocketList = clientSocketList;

		try {
			is = clientSocket.getInputStream();//��ȡsocket��������
			br = new BufferedReader(new InputStreamReader(  
					clientSocket.getInputStream() , "utf-8"));//utf-8����� ��������ʾ���ĵ�
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		super.run();
		try {
			//String msg = "";
			String readerLine;
			while ((readerLine = br.readLine()) != null) {
				if (readerLine.startsWith("start:")) {   //�����⵽�ַ�Ϊstart: ����ʾ
					name = readerLine.substring(6);
					//System.out.println(name);
					writeMsg(name + "������");
				} else if ("end".equals(readerLine)) {
					writeMsg(name + "������");
					clientSocketList.remove(clientSocket);
					br.close();
					is.close();
					clientSocket.close();
					clientSocket = null;
					break;
				} else {
					//msg = msg + readerLine + "\n";
					//System.out.println("1");
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					//writeMsg("[" + df.format(new Date()) + "]" + name + "˵��" + msg);
					writeMsg("[" + df.format(new Date()) + "]" + name + "˵��" + readerLine);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeMsg(String msg) {
		try {
			//System.out.println(clientSocketList.size());
			for (int i = 0; i < clientSocketList.size(); i++) {
				Socket socket = (Socket) clientSocketList.get(i);
				OutputStream os = socket.getOutputStream();
				String str = msg + "\n";
				os.write(str.getBytes("utf-8"));
				//System.out.println(os);
				System.out.println(str);
				//msg = "";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}
