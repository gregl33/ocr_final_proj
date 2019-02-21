import org.opencv.core.Rect;

public class Text_Rect extends Rect{

	String text;

	public Text_Rect(Rect re) {
		this.x = re.x;
		this.y = re.y;
		this.height = re.height;
		this.width = re.width;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
