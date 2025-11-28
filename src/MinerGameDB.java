import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MinerGameDB {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Driver Loaded");
			String dbUrl = "jdbc:sqlite:minergame.db";
			conn = DriverManager.getConnection(dbUrl);

			if (conn != null) {
				System.out.println("Successful Connection");

				String sqlCreateTable = "CREATE TABLE IF NOT EXISTS PLAYERSCORES " +
										"(PLAYERNAME TEXT NOT NULL, " +
										"SCORE INT)";

				try (PreparedStatement pstmtCreateTable = conn.prepareStatement(sqlCreateTable)) {
                    pstmtCreateTable.executeUpdate();
                    System.out.println("Table Successfully Created");
                }

				conn.close();
			}


		} catch(Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateScore(String playerName, int score) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			String dbUrl = "jdbc:sqlite:minergame.db";
			conn = DriverManager.getConnection(dbUrl);

			if (conn != null) {
				String sqlUpdate = "UPDATE PLAYERSCORES SET SCORE = ? WHERE PLAYERNAME = ?";
				pstmt = conn.prepareStatement(sqlUpdate);
				pstmt.setInt(1, score);
				pstmt.setString(2, playerName);
				pstmt.executeUpdate();

				pstmt.close();
				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
