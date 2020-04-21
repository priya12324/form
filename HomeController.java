package com.loginform.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping("/")
	public ModelAndView enter() {
	return new ModelAndView("home");
	}
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req , HttpServletResponse res) throws IOException{
	return new ModelAndView("login");
	}
	    @RequestMapping("/new")
	    public ModelAndView neww() {
	    return new ModelAndView("neww");
	    }
	    @RequestMapping("/check")
	    public ModelAndView check(HttpServletRequest req, HttpServletResponse res) {
	    try{
	       String username =req.getParameter("username");  
	       String password = req.getParameter("password");
	       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	       Connection conn=DriverManager.getConnection(  
	        "jdbc:mysql://localhost:3306/db","root","1234");
	       
	       PreparedStatement pst = conn.prepareStatement("Select id,pwd from db.logins");
	       ResultSet rs = pst.executeQuery();
	       while(rs.next()){
	        if (rs.getString("id").equals(username)){
	        return new ModelAndView("alertOfExisting");
	        }
	       }
	       pst = conn.prepareStatement("INSERT INTO db.logins (id,pwd)VALUES (?,?)");
	       pst.setString(1, username);
	       pst.setString(2, password);
	       int n=pst.executeUpdate();
	    }
	       catch (Exception e) {
	System.out.print(e);
	}
	    return new ModelAndView("home");
	    }
	@RequestMapping(value="/validate")
	public ModelAndView test(HttpServletRequest req , HttpServletResponse res) throws IOException{
	try{
	       String username = req.getParameter("username");  
	       String password = req.getParameter("password");
	       Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	       Connection conn=DriverManager.getConnection(  
	        "jdbc:mysql://localhost:3306/db","root","1234");      
	       PreparedStatement pst = conn.prepareStatement("Select id,pwd from db.logins where id=? and pwd=?");
	       pst.setString(1, username);
	       pst.setString(2, password);
	       ResultSet rs = pst.executeQuery();    
	       
	       if(rs.next()) {
	        return new ModelAndView("welcome");
	       }
	       
	       else {
	           return new ModelAndView("home1");
	           
	       }
	  }
	  catch(Exception e){      
	  System.out.print(e);
	  return new ModelAndView("home1");
	  }      

	}
}
