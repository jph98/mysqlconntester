import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnTest {
 
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";	
	private static final String USAGE = "Usage: ConnTest <url> <database> <username> <password>";
 
	public static void main(String[] argv) {
 
 		if (argv.length < 4) {
 
 			System.out.println(USAGE); 			 			
 		} else {

 			checkVars(argv[0], argv[1], argv[2], argv[3]); 			
			try {
				selectRecordsFromDbUserTable(argv[0], argv[1], argv[2], argv[3]);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void checkVars(String...vars) {

		for (String var: vars) {

			if (var == "" || var == null) {
				System.out.println(USAGE);
				System.exit(-1);
			}
		}
	}
 
	private static void selectRecordsFromDbUserTable(String hostname, String db, String user, String pass) throws SQLException {
 
		Connection dbConnection = null;
		Statement statement = null;
 
		String selectTableSQL = "SELECT 1;";
 
		try {
			dbConnection = getDBConnection(hostname, db, user, pass);

			if (dbConnection != null) {
				statement = dbConnection.createStatement();
	 
				// execute select SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
	 
				if (rs.next()) {
					System.out.println("Connection Successful");
				} else {
					System.out.println("Connection FAILED");
				}
				
			} else {
				System.out.println("Connection FAILED");
			}
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
 
			if (statement != null) {
				statement.close();
			}
 
			if (dbConnection != null) {
				dbConnection.close();
			}
 
		}
 
	}
 
	private static Connection getDBConnection(String hostname, String db, String user, String pass) {
 
		Connection dbConnection = null;
 
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
 
		try {
						
			String details = "jdbc:mysql://" + hostname + ":3306/" + db;
			dbConnection = DriverManager.getConnection(details, user, pass);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
 
		return dbConnection;
 
	}
 
}
