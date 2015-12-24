package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Task.TaskList;
import database.AddTask;

/**
 * Servlet implementation class NewTaskServlet
 */
@WebServlet("/NewTaskServlet")
public class NewTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewTaskServlet() {
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
			response.sendRedirect("LoginInError.jsp");
		}
		else
		{
			String title = request.getParameter("tasktitle");
			String thismode = request.getParameter("thismode");
			String thatmode = request.getParameter("thatmode");
			System.out.println(title+";"+thismode+";"+thatmode);
			AddTask.newTask(username, title, thismode, thatmode);//将当前任务添加到数据库中
			
			response.sendRedirect("managetasks.jsp");
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
