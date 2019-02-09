package com.webserver.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;

import com.webserver.http.HttpContext;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 用户处理客户端请求并予以响应的处理类
 * 
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			/*
			 * 处理客户端请求分为三步:
			 * 1:准备工作
			 * 2:处理请求
			 * 3:发送响应
			 */
			//1.1解析请求
			HttpRequest request = new HttpRequest(socket);
			//1.2创建响应对象
			HttpResponse response = new HttpResponse(socket);
			
			//2
			//2.1获取请求的抽象路径
			String path = request.getRequestURI();
			//是否为请求一个业务
			//根据URL 路径查找类名 如：/myweb/index.html
			String className=
					HttpContext.getServletClass(path);
			// 如果 用户的请求URL path能用找到对应的
			// Servlet 类，则利用反射执行这个Servlet
			// 的service方法，响应用户请求
			// 相反如果 没有对应的 Servlet 类，则查找
			// 磁盘URL对应的磁盘文件资源作为响应结果
			// className = null
			if(className!=null) {
				//利用反射执行 RegServlet 的service
				//1. 动态加载类
				Class cls=Class.forName(className);
				//2. 动态创建对象
				Object obj = cls.newInstance();
				//3. 动态查找service方法
				Method method=cls.getDeclaredMethod("service",
						HttpRequest.class,
						HttpResponse.class);
				//4. 利用反射API执行方法
				method.invoke(obj, request, response);
			}else {	
				//2.2去webapps目录下找到对应资源
				File file = new File("webapps"+path);
				//2.3判断该资源是否真实存在
				if(file.exists()) {
					//将要响应给客户端的资源设置到响应对象中
					response.setEntity(file);	
				}else {
					//设置状态代码为404
					response.setStatusCode(404);					
					//响应404页面
					response.setEntity(new File("webapps/root/404.html"));					
				}
			}
			
			
			//3响应客户端
			response.flush();
				
		} catch(EmptyRequestException e) {
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//最后要与客户端断开链接
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	

}




