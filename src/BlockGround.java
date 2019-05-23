import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class BlockGround implements Block {
	Image image;

	BlockGround() {
		image = new Image("ground.png");
	}

	public Image getImage() {
		return image;
	}
}
