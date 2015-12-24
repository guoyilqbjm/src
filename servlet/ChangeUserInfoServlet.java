package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AddOneTransaction;
import database.ChangeInformation;
import error.ErrorInformation;

/**
 * Servlet implementation class ChangeUserInfoServlet
 */
@WebServlet("/ChangeUserInfoServlet")
public class ChangeUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(username == null){
			ErrorInformation.set("ÄúÉÐÎ´µÇÂ½£¡");
			response.sendRedirect("LoginInError.jsp");
			return;
		}
		String newPassword=request.getParameter("newPassword");
		int deposit=Integer.parseInt(request.getParameter("deposit"));
		if(!newPassword.equals(""))
			ChangeInformation.change(username, newPassword);
		if(deposit!=0){
			AddOneTransaction.add(username, deposit);
		}
		response.sendRedirect("managetasks.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
