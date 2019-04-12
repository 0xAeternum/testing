import java.awt.Color;

public class ColoredInk implements InkOperator {
	
	private int inkLevel;
	private Color color;
	
	public ColoredInk(int inkLevel, Color color) {
		this.inkLevel = inkLevel;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public void loadInk() {
		inkLevel = 100;
	}
	@Override
	public int getInk() {
		return inkLevel;
	}
	@Override
	public boolean isInkLow() {
		return inkLevel < 50;
	}
	
	

}
