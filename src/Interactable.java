/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public interface Interactable {
        public double getInteractRadius();
        public void onInteract(Entity e, World w, int x, int y, int z);
}
