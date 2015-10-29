

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowAverage
 */
@WebServlet("/ShowAverage")
public class ShowAverage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAverage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.print("<link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\"	integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\" rel=\"stylesheet\">");
		out.print("<link rel=\"stylesheet\"	href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css\"	integrity=\"sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX\"	crossorigin=\"anonymous\">");
		out.println("<head>");
		out.println("<title>Ms Mackleberry's Gradebook</title>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\" style=\"font-weight: bold;\">Ms. Mackleberry's Grades</div>");
		out.println("<div class=\"panel-body\">");
		out.println("<p>Class Average = " + this.getAverage() + "</p>");
		out.println("</div>");
		out.println("</div>");
		out.println("<a class=\"btn btn-default\" href=\"gradeForm.html\">Add Grade</a>");
		out.println("</div>");
		out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
		out.print("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\" integrity=\"sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==\" crossorigin=\"anonymous\"></script>");
		out.print("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private Connection getConnection() throws SQLException {

		Connection connection = null;

		// TODO Auto-generated method stub
		try {

			String url = "jdbc:oracle:thin:system/password@localhost";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// properties for creating connection to Oracle database
			Properties props = new Properties();
			props.setProperty("user", "testuserdb");
			props.setProperty("password", "password");

			// creating connection to Oracle database using JDBC
			connection = DriverManager.getConnection(url, props);

			return connection;
		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getAverage() {

		String sql = "Select * from Grades";

		try {
			Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			double avg = 0;
			int counter = 0;
			while (rs.next()){
				counter++;
				avg +=Double.parseDouble(rs.getString("grade"));
				
		
			}
			avg = avg/counter;
			DecimalFormat df = new DecimalFormat("#.##");
			String avgFormatted = df.format(avg);
			rs.close();
			return avgFormatted;
		}

		catch (SQLException e) {
			System.err.println(e);
			return null;

		}
	}

}
