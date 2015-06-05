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
			is = clientSocket.getInputStream();//获取socket的输入流
			br = new BufferedReader(new InputStreamReader(  
					clientSocket.getInputStream() , "utf-8"));//utf-8万国码 可用来显示中文等
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
				if (readerLine.startsWith("start:")) {   //如果检测到字符为start: 则显示
					name = readerLine.substring(6);
					//System.out.println(name);
					writeMsg(name + "上线了");
				} else if ("end".equals(readerLine)) {
					writeMsg(name + "下线了");
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
					//writeMsg("[" + df.format(new Date()) + "]" + name + "说：" + msg);
					writeMsg("[" + df.format(new Date()) + "]" + name + "说：" + readerLine);
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
