public interface Interactable {
	public double getInteractRadius();
	public void onInteract(Entity e, World w, int x, int y, int z);
}
