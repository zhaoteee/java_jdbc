package com.imooc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.bean.Message;
 
 

/**
 *    列表页面初始化
 *
 */
@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = 
			DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/micro_message?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true", "root", "123456");
			
			String sql = "select ID,COMMAND,DESCRIPTION,CONTENT from MESSAGE";
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			java.util.List<Message> messageList = new ArrayList<Message>();
			while (resultSet.next()) {
				Message message = new Message();
				messageList.add(message);
				message.setId(resultSet.getString("ID"));
				message.setCommand(resultSet.getString("COMMAND"));
				message.setDescription(resultSet.getString("DESCRIPTION"));
				message.setContent(resultSet.getString("content"));
			}
			resultSet.close();
			statement.close();
			connect.close();
			req.setAttribute("messageList", messageList);
			
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
		 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
	
}
