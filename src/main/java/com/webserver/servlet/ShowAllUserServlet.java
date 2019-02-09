package com.webserver.servlet;


import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import net.noyark.www.game.minecraft.Listener.PlayerListener;
import net.noyark.www.game.minecraft.basic.MFunCore;
/**
 * 显示所有用户列表
 *
 *
 */
public class ShowAllUserServlet {
	public void service(HttpRequest request,HttpResponse response) {
		MFunCore core = new MFunCore();
		PlayerListener listener = new PlayerListener();
		while(true) {
			try {
				StringBuilder builder = new StringBuilder();
				builder.append("<html>");
				builder.append("	<head>");
				builder.append("		<meta charset='UTF-8'>");
				builder.append("		<title>用户列表</title>");
				builder.append("	</head>");
				builder.append("	<body>");
				builder.append("		<center>");
				builder.append("			<h1>用户列表</h1>");
				builder.append("			<table border='1'>");
				builder.append("				<tr><td>在线人数:"+listener.getPlayerOnline()+"</td>");
				builder.append("			</table>");
				builder.append("		</center>");
				builder.append("	</body>");
				builder.append("</html>");
				byte[] data = builder.toString().getBytes("UTF-8");
				response.putHeader("Content-Length", data.length + "");
				response.putHeader("Content-Type", "text/html");
				response.setContentData(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}






