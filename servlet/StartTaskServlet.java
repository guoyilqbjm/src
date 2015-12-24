package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Task.TaskList;
import database.GetTaskInfo;
import error.ErrorInformation;

/**
 * Servlet implementation class StartTaskServlet
 */
@WebServlet("/StartTaskServlet")
public class StartTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if(username == null){
			ErrorInformation.set("ÄúÉÐÎ´µÇÂ½£¡");
			response.sendRedirect("LoginInError.jsp");
			return;
		}
		String title = request.getParameter("title");
		String mode[] = GetTaskInfo.get(username, title);
		TaskList.startTask(mode);
		response.sendRedirect("managetasks.jsp");
		
		/*
		 * to-do:response´¦Àí
		 * */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
