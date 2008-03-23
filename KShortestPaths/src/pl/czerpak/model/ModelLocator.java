package pl.czerpak.model;

public class ModelLocator {
	
	private static ModelLocator instance = null;
	
	public static ModelLocator getInstance() {
		if (instance == null) instance = new ModelLocator();
		return instance;
	}
	
	private ModelLocator() { }
}
