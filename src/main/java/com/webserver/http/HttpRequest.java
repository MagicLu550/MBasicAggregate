package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.webserver.core.EmptyRequestException;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送过来的一个实际的HTTP
 * 请求内容。
 * 每个请求由三部分组成:
 * 1:请求行
 * 2:消息头
 * 3:消息正文(可以不包含)
 * @author ta
 *
 */
public class HttpRequest {
	/*
	 * 请求行相关信息定义
	 */
	//请求的方式
	private String method;
	//请求的抽象路径
	private String url;
	//请求使用的协议版本
	private String protocol;
	
	//url中的请求路径部分
	private String requestURI;
	//url中的参数部分
	private String queryString;
	//每一个参数
	private Map<String,String> parameters = new HashMap<>();
	
	/*
	 * 消息头相关信息定义
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	
	/*
	 * 消息正文相关信息定义
	 */
	private byte[] content;
	
	/*
	 * 与连接相关的属性
	 */
	private Socket socket;
	private InputStream in;
	
	public HttpRequest(Socket socket) throws EmptyRequestException {
		try {
			this.socket = socket;
			this.in = socket.getInputStream();
			/*
			 * 实例化一个HttpRequest要解析客户端发送
			 * 过来的请求内容，并分别解析其中的每部分
			 * 1:解析请求行
			 * 2:解析消息头
			 * 3:解析消息正文
			 */
			//1
			parseRequestLine();
			//2
			parseHeaders();
			//3
			parseContent();
			
			
		} catch(EmptyRequestException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * 解析请求行
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException {
		/*
		 * 解析请求行:
		 * 1:读取一行字符串，即:请求行的内容
		 * 2:将字符串按照"空格"拆分为三部分
		 * 3:将拆分的三部分内容分别设置到
		 *   method,url,protocol属性上即可
		 */
		try {
			String line = readLine();

			/*
			 * 首先判断当前请求是否为空请求，若是空请求
			 * 那么读取的这一行字符串应当是个空字符串
			 */
			if(line.length()==0) {
				throw new EmptyRequestException();
			}
			
			/*
			 * 下面的代码可能抛出数组下标越界异常
			 * 这是由于“空请求”引起的，后期遇到后再
			 * 解决
			 */
			String[] data = line.split(" ");
			this.method = data[0];
			this.url = data[1];
			this.protocol = data[2];
			//进一步解析url
			parseURL();
			


		} catch(EmptyRequestException e) {
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 进一步解析url
	 */
	private void parseURL() {
		/*
		 * 实现思路
		 * 首先判断当前url是否需要进一步解析
		 * 而判定的标准是看url中是否含有"?",如果含有
		 * 则说明需要解析，若没有则不需要进一步解析
		 * 
		 * 如果不需要进一步解析，那么直接将url的值赋值
		 * 给requestURI即可。
		 * 
		 * 若需要解析，则应进行如下操作
		 * 先将url按照"?"拆分为两部分
		 * 第一部分设置到requestURI上，第二部分设置到
		 * queryString属性上
		 * 
		 * 然后进一步拆分参数:
		 * 将queryString按照"&"拆分为若干个参数。
		 * 每个参数再按照"="拆分为参数名与参数值，再将
		 * 各参数的名字作为key，参数的值作为value保存
		 * 到parameters这个Map类型的属性上。
		 * 
		 * url可能存在的情况如下:
		 * /myweb/reg.html
		 * /myweb/reg?username=xxx&password=xxx&....
		 * 
		 */
		//是否含有"?"
		if(url.indexOf("?")!=-1) {
			String[] data = url.split("\\?");
			requestURI = data[0];
			if(data.length>1) {
				queryString = data[1];
				/*
				 * 对参数部分按照UTF-8编码进行转码操作
				 * 将所有%XX的内容替换为实际字符
				 * /myweb/reg?username=%E8%8C%83
				 * /myweb/reg?username=范
				 */
				try {
					queryString = URLDecoder.decode(queryString,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				
				//拆分每一个参数
				parseParameter(queryString);
				
			}
		}else {
			//不含有"?"
			requestURI = url;
		}

	}
	/**
	 * 解析参数
	 * 参数的格式应当为:
	 * name=value&name=value&name=value&....
	 * @param line
	 */
	private void parseParameter(String line) {
		String[] data = line.split("&");
		//paraLine: username=zhangsan
		for(String paraLine : data) {
			String[] paras = paraLine.split("=");
			if(paras.length>1) {
				parameters.put(paras[0], paras[1]);
			}else {
				parameters.put(paras[0], null);
			}
		}
	}
	
	
	
	/**
	 * 解析消息头
	 */
	private void parseHeaders() {
		try {
			String line = null;
			while(true) {
				line = readLine();
				if("".equals(line)) {
					//单独读取了CRLF
					break;
				}
				/*
				 * 将消息头按照": "拆分为两项
				 * 将详细头名字作为key，消息头的值作为
				 * value保存到headers中
				 */
				String[] data = line.split(": ");
				headers.put(data[0], data[1]);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 解析消息正文
	 */
	private void parseContent() {
		/*
		 * 首先判断当前请求是否包含消息正文。判断依据
		 * 是查看消息头中是否含有Content-Length.因为
		 * 这个头是用来指明消息正文的长度。
		 */
		if(headers.containsKey("Content-Length")) {
			/*
			 * 根据Content-Length准备好字节数组
			 */
			int len = Integer.parseInt(
				headers.get("Content-Length")
			);
			content = new byte[len];
			try {
				//读取消息正文内容
				in.read(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//根据Content-Type分析消息正文内容
			String contentType = headers.get("Content-Type");
			//是否为form表单提交数据
			if("application/x-www-form-urlencoded".equals(contentType)) {
				try {
					String line = new String(content,"ISO8859-1");
					//先对内容转码
					line = URLDecoder.decode(line,"UTF-8");
					//解析参数
					parseParameter(line);

					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			
			
			
		}

	}
	/**
	 * 通过输入流读取客户端发送的一行字符串.
	 * 该方法会连续读取若干字符，当连续读取到CR,LF
	 * 时停止读取，并将回车符与换行符之前的所有字符
	 * 以一个字符串的形式返回。
	 * @return
	 * @throws IOException
	 */
	private String readLine() throws IOException {
		StringBuilder builder = new StringBuilder();
		int cur = -1;//本次读取的字符
		int pre = -1;//上次读取的字符
		while((cur = in.read())!=-1) {
			//若上次读取回车符，本次读取换行符就停止读取
			if(pre==13&&cur==10) {
				break;
			}
			builder.append((char)cur);
			pre = cur;
		}		
		//返回时要去除空白字符(最后会有一个回车符)
		return builder.toString().trim();
	}
	
	
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	/**
	 * 根据给定的消息头名字获取对应的值
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return headers.get(name);
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getQueryString() {
		return queryString;
	}
	/**
	 * 根据给定的参数名获取对应的参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	
	
	
}











