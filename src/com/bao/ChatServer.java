package com.bao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread {
	
	ServerSocket serverSocket;//����������socket
	int port;
	List clientSocketList;//����һ��List�����洢�û�����Ϣ
	public ChatServer(int port){
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientSocketList = new ArrayList();//����һ��̬����
	}
	
	
	
	//��������������ClientThread����socket����
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("========�������Ѿ�����=========");
		while(!Thread.currentThread().isInterrupted()){  //����߳�δ�ж���ִ��
			try {
				Socket sorket = serverSocket.accept();//�����ȴ��ͻ������� 
				System.out.println("�ͻ���������");
				clientSocketList.add(sorket);//��socket����List�洢
				new ClientThread(sorket,clientSocketList).start();//�Դ�socket����ClientThread
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
