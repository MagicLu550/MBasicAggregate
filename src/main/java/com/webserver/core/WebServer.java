package com.webserver.core;

import cn.gulesberry.www.Extend.core.project.Beta;
import cn.gulesberry.www.Extend.core.project.Progress;
import net.noyark.www.game.minecraft.basic.MFunCore;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer主类
 * 
 *
 */
public class WebServer implements Runnable{
	MFunCore core = MFunCore.getMFunCore();
	private ServerSocket server;
	//声明线程池变量
	private ExecutorService pool;

	public WebServer() {
		try {
			//创建线程池对象，最多并发max个线程
			pool = Executors.newFixedThreadPool(core.getCfg().maxPeople);
			server = new ServerSocket(8088);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Beta(where="run",progress= Progress.BUG)
	public void run() {
		try {
			while(true) {
				Socket socket = server.accept();
				//启动一个线程处理该客户端交互
				ClientHandler handler = new ClientHandler(socket);
				//Thread t = new Thread(handler);
				//将客户端服务任务提交到线程池
				pool.execute(handler);
				//t.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}









