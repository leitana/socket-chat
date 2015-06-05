package com.bao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends Thread {
	
	ServerSocket serverSocket;//声明服务器socket
	int port;
	List clientSocketList;//声明一个List用来存储用户的信息
	public ChatServer(int port){
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientSocketList = new ArrayList();//创建一动态数组
	}
	
	
	
	//启动服务器并向ClientThread传入socket数据
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("========服务器已经启动=========");
		while(!Thread.currentThread().isInterrupted()){  //如果线程未中断则执行
			try {
				Socket sorket = serverSocket.accept();//阻塞等待客户端连接 
				System.out.println("客户端已连接");
				clientSocketList.add(sorket);//将socket加入List存储
				new ClientThread(sorket,clientSocketList).start();//对此socket调用ClientThread
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
