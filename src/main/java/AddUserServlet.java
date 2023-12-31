import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/addUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	PreparedStatement statement;
	@Override
	public void init() {
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2001349_java_demo_2","e2001349","ZAYsTnNcew8");
			statement = connection.prepareStatement("INSERT INTO users (firstName, lastName, email) VALUES (?, ?, ?)");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		
		try {
			statement.setString(1, firstName);
			statement.setString(2,  lastName);
			statement.setString(3, email);
			
			int result = statement.executeUpdate();
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.print("<b> " + result + " users created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}