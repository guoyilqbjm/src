package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import database.*;

/**
 * Servlet implementation class LoginInServlet
 */
@WebServlet("/LoginInServlet")
public class LoginInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
       
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(600);
        String username=request.getParameter("username"),password=request.getParameter("password");
        try {
        	if(DataBaseInfo.connection == null){
        		DataBaseInfo.init();
        	}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(username.equals("admin") && password.equals("admin")){
        	session.setAttribute("username", "admin");
        	response.sendRedirect("ManagerPage.jsp");
        	return;
        }
        
        try {
			if(LoginIn.loginIn(username, password)){
				session.setAttribute("username", username);
				response.sendRedirect("managetasks.jsp");
			}
			else{
				response.sendRedirect("LoginInError.jsp");
			}
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
