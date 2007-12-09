package pl.czerpak.util;

public class IllegalLineFormatException extends Exception {

	private static final long serialVersionUID = -4374098631922218240L;

	public IllegalLineFormatException(String line) {
		super(line);
	}

}
