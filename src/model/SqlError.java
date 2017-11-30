package model;

import java.sql.SQLException;

public class SqlError {
	private String message;
	private String sqlState;
	private int errorCode;
	private Throwable cause;

	public SqlError(SQLException sqlEx) {
		this.message = sqlEx.getMessage();
		this.sqlState = sqlEx.getSQLState();
		this.errorCode = sqlEx.getErrorCode();
		this.cause = sqlEx.getCause();
	}

	public String getMessage() {
		return message;
	}

	public String getSqlState() {
		return sqlState;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Throwable getCause() {
		return cause;
	}
}
