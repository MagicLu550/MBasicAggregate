package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 响应对象
 * 该类的每个实例用于表示发送给客户端的一个HTTP响应内容
 * @author ta
 *
 */
public class HttpResponse {
	/*
	 * 状态行相关信息定义
	 */
	//状态代码
	private int statusCode = 200;
	//状态描述
	private String statusReason = "OK";
	
	/*
	 * 响应头相关信息定义
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	
	/*
	 * 响应正文相关信息定义
	 */
	//响应的实体文件
	private File entity;
	//正文数据
	private byte[] data;
	
	/*
	 * 与连接相关的属性
	 */
	private Socket socket;
	private OutputStream out;
	
	public HttpResponse(Socket socket) {
		try {
			this.socket = socket;
			this.out = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将当前响应对象内容以HTTP响应格式发送给客户端
	 */
	public void flush() {
		/*
		 * 响应客户端发送的三步:
		 * 1:发送状态行
		 * 2:发送响应头
		 * 3:发送响应正文
		 */
		sendStatusLine();
		sendHeaders();
		sendContent();
	}
	/**
	 * 发送状态行
	 */
	private void sendStatusLine() {
		try {
			String line = "HTTP/1.1"+" "+statusCode+" "+statusReason;
			out.write(line.getBytes("ISO8859-1"));
			out.write(13);//written CR
			out.write(10);//written LF
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送响应头
	 */
	private void sendHeaders() {
		try {
			//遍历headers，将所有响应头发送给客户端
			Set<Entry<String,String>> entrySet 
								= headers.entrySet();
			for(Entry<String,String> header : entrySet) {
				String name = header.getKey();
				String value = header.getValue();
				String line = name + ": " + value;
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);//written CR
				out.write(10);//written LF
			}
			
			
			//单独发送CRLF表示响应头发送完毕
			out.write(13);//written CR
			out.write(10);//written LF
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送响应正文
	 */
	private void sendContent() {
		if(entity!=null) {
			try (
				FileInputStream fis
					= new FileInputStream(entity);	
			){			
				byte[] data = new byte[1024*10];
				int len = -1;
				while((len = fis.read(data))!=-1) {
					out.write(data,0,len);
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(data != null) {
			try {
				out.write(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	public File getEntity() {
		return entity;
	}
	/**
	 * 设置响应正文的实体文件
	 * 
	 * 设置该文件意味着这个响应是包含正文的，而一个响应
	 * 只要包含正文，一定会包含两个响应头:
	 * Content-Type和Content-Length
	 * 用于告知客户端正文的数据类型以及字节量
	 * 
	 * 对此，这个方法调用后，会自动根据给定的文件设置
	 * 这两个响应头。
	 * 
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		
		//Content-Length
		headers.put("Content-Length", entity.length()+"");
		
		//Content-Type
		//1获取该资源文件的后缀名
		String fileName = entity.getName();
		int index = fileName.lastIndexOf(".")+1;
		String ext = fileName.substring(index);
		//2根据后缀获取对应的Content-Type的值
		String line = HttpContext.getMimeType(ext);		
		headers.put("Content-Type", line);
	}

	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * 设置指定的状态代码
	 * 同时会自动设置对应的状态描述默认值
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		this.statusReason = HttpContext.getStatusReason(statusCode);
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	/**
	 * 添加指定的响应头
	 * @param name
	 * @param value
	 */
	public void putHeader(String name,String value) {
		this.headers.put(name, value);
	}
	/**
	 * 获取指定响应头的值
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return this.headers.get(name);
	}

	public byte[] getContentData() {
		return data;
	}

	public void setContentData(byte[] data) {
		this.data = data;
	}
	
	
}










