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
	}

	public Type getType() {
		return myType;
	}
	
	//ToDo: fix so that each call to makeLogEntry doesn't overwrite file
	public void makeLogEntry() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("errorLog.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

