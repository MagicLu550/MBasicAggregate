package com.webserver.http;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * HTTP协议规定的相关内容
 * 
 *
 */
public class HttpContext {
	/**
	 * 状态代码与对应的描述
	 * key:状态代码
	 * value:状态描述
	 */
	private static final Map<Integer,String> STATUS_MAPPING = new HashMap<Integer,String>();

	/**
	 * 介质类型映射
	 * key:文件的后缀名
	 * value:Content-Type对应的值
	 */
	private static final Map<String,String> MIME_TYPE_MAPPING = new HashMap<String,String>();
	
	/**
	 * URL 与 Servlet 之间的映射
	 * key：URL
	 * value: Servlet 的类名
	 */
	private static final Map<String,String> URL_MAPPING=new HashMap<>();
	
	static {
		//初始化
		initStatusMapping();
		initMimeTypeMapping();
		initUrlMapping();
	}
	
	/**
	 * 初始化URL 与 Servlet 之间的对应关系
	 */
	private static void initUrlMapping() {
		try {
			SAXReader reader = new SAXReader();
			Document doc =
				reader.read(new File("./plugins/MBasicAggregate/conf/web.xml"));
			Element root = doc.getRootElement();
			//解析xml算法
			List<Element> mappings=
					root.elements("servlet-mapping");
			List<Element> servlets=
					root.elements("servlet");
			//System.out.println("mappings:"+mappings);
			//System.out.println("servlets:"+servlets);
			for (Element mapping : mappings) {
				String url=mapping
						.elementTextTrim("url-pattern");
				String name=mapping
						.elementTextTrim("servlet-name");
				//System.out.println(url+":"+name); 
				//根据name找到对应的className
				for(Element servlet:servlets) {
					String name_ = servlet
							.elementTextTrim("servlet-name");
					String className = servlet
							.elementTextTrim("servlet-class");
					//System.out.println(name_+":"+className);
					if(name.equals(name_)) {
						//System.out.println(
						//		"找到："+url+":"+className);
						URL_MAPPING.put(url, className);
					}
				}
			}
			
			//System.out.println("URL_MAPPING:"+URL_MAPPING);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初始化介质类型
	 */
	private static void initMimeTypeMapping() {
//		MIME_TYPE_MAPPING.put("html", "text/html");
//		MIME_TYPE_MAPPING.put("css", "text/css");
//		MIME_TYPE_MAPPING.put("png", "image/png");
//		MIME_TYPE_MAPPING.put("gif", "image/gif");
//		MIME_TYPE_MAPPING.put("jpg", "image/jpeg");
//		MIME_TYPE_MAPPING.put("js", "application/javascript");
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("./plugins/MBasicAggregate/conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements("mime-mapping");
			for(Element mimeEle : list) {
				String key = mimeEle.elementText("extension");
				String value = mimeEle.elementText("mime-type");
				MIME_TYPE_MAPPING.put(key, value);
			}
			System.out.println(MIME_TYPE_MAPPING.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	/**
	 * 初始化状态代码与对应描述
	 */
	private static void initStatusMapping() {		
		STATUS_MAPPING.put(200, "OK");
		STATUS_MAPPING.put(201, "Created");
		STATUS_MAPPING.put(202, "Accepted");
		STATUS_MAPPING.put(204, "No Content");
		STATUS_MAPPING.put(301, "Moved Permanently");
		STATUS_MAPPING.put(302, "Moved Temporarily");
		STATUS_MAPPING.put(304, "Not Modified");
		STATUS_MAPPING.put(400, "Bad Request");
		STATUS_MAPPING.put(401, "Unauthorized");
		STATUS_MAPPING.put(403, "Forbidden");
		STATUS_MAPPING.put(404, "Not Found");
		STATUS_MAPPING.put(500, "Internal Server Error");
		STATUS_MAPPING.put(501, "Not Implemented");
		STATUS_MAPPING.put(502, "Bad Gateway");
		STATUS_MAPPING.put(503, "Service Unavailable");
	}
	
	/**
	 * 根据给定的状态代码获取对应的状态描述
	 * @param code
	 * @return
	 */
	public static String getStatusReason(int code) {
		return STATUS_MAPPING.get(code);
	}
	/**
	 * 根据资源后缀名获取对应的Content-Type值
	 * @param ext
	 * @return
	 */
	public static String getMimeType(String ext) {
		return MIME_TYPE_MAPPING.get(ext);
	}
	/**
	 * 根据URL 映射到对应的 Servlet Class Name
	 * @param url 用户请求URL
	 * @return 类名
	 */
	public static String getServletClass(String url) {
		return URL_MAPPING.get(url);
	}

}












