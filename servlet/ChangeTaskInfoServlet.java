package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ChangeMessage;
import database.ChangeTaskInformation;

/**
 * Servlet implementation class ChangeTaskInfoServlet
 */
@WebServlet("/ChangeTaskInfoServlet")
public class ChangeTaskInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeTaskInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(username==null){
			response.sendRedirect("LoginInError.jsp");
		}
		else{
			String title=request.getParameter("title");
			String thismode=request.getParameter("thismode");
			String thatmode=request.getParameter("thatmode");
			ChangeTaskInformation.change(username, title, thismode, thatmode);
			response.sendRedirect("managetasks.jsp");
		}
	}

}
