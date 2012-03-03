package parsing;

import java.io.*;

public class ParserException extends RuntimeException {

	public static enum Type {WRONG_TYPE, NONEXISTENT_FIELD, INVALID_FIELD};
	private Type myType;

	public ParserException (String message) {
		this(message, Type.INVALID_FIELD);
	}

	public ParserException (String message, Type type) {
		super(message);
		myType = type;
		makeLogEntry("ParserException: " + message);
	}

	/**
	 * Returns type of exception
	 * @return Type
	 */
	public Type getType() {
		return myType;
	}

	/**
	 * Creates logfile and records error messages in it
	 */
	public void makeLogEntry(String logMessage) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("errorLog.txt"));
			out.write(logMessage+'\n');
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

