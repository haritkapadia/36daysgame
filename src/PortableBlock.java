public class PortableBlock extends Block implements Interactable {
	private ItemKey equivalentItem;

	public PortableBlock(String file, ItemKey equivalentItem){
		super (file);
		this.equivalentItem = equivalentItem;
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}


	public void onInteract(Entity e, World world, int x, int y, int z) {
		for (int i = 0; i < e.getInventory().length; i++){
			if(e.getInventory(i) == null) {
				e.setInventory(i, equivalentItem);
				world.setBlock(x, y, z, null);
				synchronized(this) {
					notifyAll();
				}
				return;
			}
		}
	}

	public double getInteractRadius() {
		return 2;
	}
}
