package pl.czerpak.system;

public class Out {

	public static final String LEVEL_DEBUG = "LEVEL_DEBUG";

	public static final String LEVEL_INFO = "LEVEL_INFO";

	private static Out instance = null;

	private String level = LEVEL_DEBUG;

	private String output = "";

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public static Out getInstance() {
		if (instance == null)
			instance = new Out();

		return instance;
	}

	private Out() {
	}

	public void debug(String msg) {
		if (level == LEVEL_DEBUG)
			print(msg);
	}

	public void print(String msg) {
		if (output == null)
			return;

		output += msg;
	}

	public void println(String msg) {
		print(msg + "\n");
	}
}
