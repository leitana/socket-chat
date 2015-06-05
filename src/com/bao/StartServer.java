package com.bao;

public class StartServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//启动ChatServer并给出端口号
		new ChatServer(9998).start();//调用ChatServer线程.并使PORT为9999
	}

}
