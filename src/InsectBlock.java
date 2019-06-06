/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;

public class InsectBlock extends PortableBlock{
        private Image image;
        
        public InsectBlock(String file, ItemKey equivalentItem){
                super (file, equivalentItem);
                try {
                        image = new Image(file);
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
        
        public Image getImage() {
                return image;
        }
}
