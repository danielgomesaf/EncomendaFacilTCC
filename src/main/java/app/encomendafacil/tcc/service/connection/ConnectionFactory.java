package app.encomendafacil.tcc.service.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			String url = "jdbc:mysql://localhost:3306/encomendafacil";
			String usuario = "root";
			String senha = "root";
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(url,usuario,senha);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}