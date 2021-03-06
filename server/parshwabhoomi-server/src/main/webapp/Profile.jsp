<%@page import="org.cs.parshwabhoomi.server.model.UserCredential.Role"%>
<%@page import="org.cs.parshwabhoomi.server.model.UserCredential"%>
<%@page import="org.apache.logging.log4j.LogManager"%>
<%@page import="org.cs.parshwabhoomi.server.dao.raw.impl.*"%>
<%@page import="org.cs.parshwabhoomi.server.AppContext"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transit profile page</title>
</head>

<body>
	<%
		String userType = request.getParameter("userType");
		LogManager.getLogger().info("User type:"+userType);
		
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		long id = -1;
		
		String from = request.getParameter("from");
		
		if(from.equalsIgnoreCase("login")){
			session.setAttribute("from", "login");
			
			UserCredentialDaoImpl userCredentialDaoImpl = (UserCredentialDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserCredentialDaoImpl");
	        boolean result = userCredentialDaoImpl.isValidUser(username, password);
	        userCredentialDaoImpl.close();
	        
	        if(!result){
	        	//Invalid creds; re-route to login page
	        	session.setAttribute("invalidCreds",Boolean.TRUE);
	            session.setAttribute("username", username);
	            RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/Login.jsp");
	            dispatcher.forward(request, response);
	            return;
	        }
		}else{
			//signup flow
			session.setAttribute("from", "signup");
			
			UserCredential credential = new UserCredential();
			credential.setUsername(username);
			credential.setPassword(password);
			credential.setRole(Role.valueOf(userType));
			
			UserCredentialDaoImpl userCredentialDaoImpl = (UserCredentialDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserCredentialDaoImpl");
			id = userCredentialDaoImpl.addUserCredential(credential);
			userCredentialDaoImpl.close();
			
			if(id == -1){
				session.setAttribute("invalidCreds",Boolean.TRUE);
	            session.setAttribute("username", username);
	            RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/Signup.jsp");
	            dispatcher.forward(request, response);
	            return;
			}
		}
		
		session.setAttribute("username", username);
		session.setAttribute("userType", userType);
		session.setAttribute("mode", "view");
		session.setAttribute("userId", id);
		if(userType.equalsIgnoreCase("END_USER")){
			request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/VendorProfile.jsp").forward(request, response);
		}	
	%>
</body>
</html>