package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

/**
 * @Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Method method = null;
        String methodName = request.getParameter("method");
        try {
            method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, request, response);
        } catch (Exception e) {
            throw new RuntimeException("Wrong Method��");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void signIn(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute("authenticated_user");
		if(user != null) {
			response.sendRedirect("index.jsp");
		} else {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserDAO dao = new UserDAO();
			user = dao.confirmUser(username, password);
			if(user != null) {
				session.setAttribute("authenticated_user", user);
				response.sendRedirect("index.jsp");
			} else {
				request.setAttribute("message", "Invalid username or password, enter again!<br>");
				request.getRequestDispatcher("signin.jsp").forward(request, response);
			}
		}
	}
	
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(); 
		User user = (User) session.getAttribute("authenticated_user");
		if(user != null) {
			response.sendRedirect("index.jsp");
		} else {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String confpass = request.getParameter("confpass");
			boolean flag = true;
			UserDAO dao = new UserDAO();
			User u = new User();
			try {
				if (dao.userExist(username)) {
					flag = false;
					request.setAttribute("message", "Username is already exist!<br>");
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				} else if(!password.equals(confpass)) {
					flag = false;
					request.setAttribute("message", "Two passwords don't match!<br>");
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				} else {
					u.setUsername(username);
					u.setPassword(password);
					u.setPosition(0);
					dao.insertUser(u);
				}
			} catch (ClassNotFoundException e) {
				flag = false;
				e.printStackTrace();
			}
			if(flag) {
				session.setAttribute("authenticated_user", u);
				response.sendRedirect("index.jsp");
			}
		}
	}

	private void logOut (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.removeAttribute("authenticated_user");
		response.sendRedirect("index.jsp");
	}
	
}
