/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.DBManager;

/**
 *
 * @author Shashank
 */
public class LoginServlet extends HttpServlet {
	
	

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		AppContext.newInstance();
	}

	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            String from=request.getParameter("from");
                if(username!=null && password!=null && from!=null){
                    DBManager dbManager=DBManager.getDBManager();
                    boolean result=dbManager.isValidUser(username, password);
                    System.out.println("_isValidUser= "+result);
                    //from can be either "device" or "portal"
                    if(from.equals("device")){
                        System.out.println("_from= "+from);
                        response.setContentType("text/xml;charset=UTF-8");
                        out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                        if(result){
                            System.out.println("_A valid user login in from device...");
                            out.println("<loginresult><status>true</status></loginresult>");
                        }else{
                            out.println("<loginresult><status>false</status><error>Invalid Username and/or Password</error></loginresult>");
                        }
                    }else{
                        //from is "portal"
                        System.out.println("_from= "+from);
                        response.setContentType("text/html; charset=UTF-8");
                        HttpSession session = request.getSession();
                        session.setAttribute("from",from);
                        if (result) {
                            System.out.println("_A valid user login in from portal...");
                            //If the username is valid,keep it in session.
                            session.setAttribute("username", username);
                            RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/UserRegistration.jsp");
                            dispatcher.forward(request, response);
                        }else{
                            session.setAttribute("isUserAuthenticated",Boolean.FALSE);
                            session.setAttribute("username", username);
                            session.setAttribute("password", password);
                            RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/UserLogin.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                }

            


        } finally {
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}