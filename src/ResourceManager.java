/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import javafx.scene.image.Image;

/**
 *
 * This class manages all of the items and blocks in the game
 *
 * Variables:
 *
 * playerSprites -An EnumMap of the various images of the player (walking left, right, up, down etc.)
 * blocks    -An EnumMap that stores each Block object with it's associated enum value
 * items     -An EnumMap that stores each Item object with it's associated enum value
 * fireSprite     -Stores the image of the fire
 *
 */
public final class ResourceManager {
        private static final EnumMap<Direction, Image[]> playerSprites = new EnumMap<Direction, Image[]>(Direction.class){{
                try {
                        for(Direction d : Direction.values()) {
                                Image[] images = new Image[3];
                                System.out.print(d + "\t");
                                for(int i = 0; i < images.length; i++) {
                                        images[i] = new Image("Characters/sprite" + (i + 1 + d.ordinal() * 3) + ".png");
                                        System.out.print((i + 1 + d.ordinal() * 3) + " ");
                                        
                                }
                                System.out.println();
                                put(d, images);
                        }
                } catch (Throwable e) {
                        System.out.println("Error " + e.getMessage());
                        e.printStackTrace();
                }
        }};
        
        private static final Image fireSprite = new Image("Artwork/fire_small.png");
        
        private static final EnumMap<BlockKey, Block> blocks = new EnumMap<BlockKey, Block>(BlockKey.class){{
                put(BlockKey.GROUND, new BlockGround());
                put(BlockKey.WATER, new BlockWater());
                put(BlockKey.TREE, new BlockTree());
                put(BlockKey.HOGWEED, new BlockHogweed());
                put(BlockKey.WOOD, new BlockWood());
                put(BlockKey.KINGBOLETE, new BlockKingBolete());
                put(BlockKey.NORTHERNBLUEFLAG, new BlockNorthernBlueFlag());
                put(BlockKey.FLYAGARIC, new BlockFlyAgaric());
                put(BlockKey.CHANTERELLE, new BlockChanterelle());
                put(BlockKey.DESTROYINGANGEL, new BlockDestroyingAngel());
                put(BlockKey.FLINT, new BlockFlint());
                put(BlockKey.BEDSTRAW, new BlockBedstraw());
                put(BlockKey.ELDERBERRY, new BlockElderberry());
                put(BlockKey.FIDDLEHEADS, new BlockFiddleheads());
                put(BlockKey.INDIANPIPE, new BlockIndianPipe());
                put(BlockKey.MOONSEED, new BlockMoonseed());
                put(BlockKey.MOREL, new BlockMorel());
                put(BlockKey.MUSHROOM, new BlockMushroom());
                put(BlockKey.STRAWBERRIES, new BlockStrawberries());
                put(BlockKey.ANT, new BlockAnt());
                put(BlockKey.GRASSHOPPER, new BlockGrasshopper());
                put(BlockKey.WORM, new BlockWorm());
                put(BlockKey.FIRE, new BlockFire());
        }};
        
        private static final EnumMap<ItemKey, Item> items = new EnumMap<ItemKey, Item>(ItemKey.class){{
                put(ItemKey.KNIFE, new ItemKnife());
                put (ItemKey.WOOD, new ItemWood());
                put(ItemKey.KINGBOLETE, new ItemKingBolete());
                put(ItemKey.NORTHERNBLUEFLAG, new ItemNorthernBlueFlag());
                put (ItemKey.FLYAGARIC, new ItemFlyAgaric());
                put(ItemKey.CHANTERELLE, new ItemChanterelle());
                put(ItemKey.DESTROYINGANGEL, new ItemDestroyingAngel());
                put(ItemKey.FLINT, new ItemFlint());
                put(ItemKey.BEDSTRAW, new ItemBedstraw());
                put (ItemKey.ELDERBERRY, new ItemElderberry());
                put (ItemKey.FIDDLEHEADS, new ItemFiddleheads());
                put(ItemKey.INDIANPIPE, new ItemIndianPipe());
                put(ItemKey.MOONSEED, new ItemMoonseed());
                put (ItemKey.MOREL, new ItemMorel());
                put(ItemKey.MUSHROOM, new ItemMushroom());
                put(ItemKey.STRAWBERRIES, new ItemStrawberries());
                put(ItemKey.ANT, new ItemAnt());
                put(ItemKey.GRASSHOPPER, new ItemGrasshopper());
                put(ItemKey.WORM, new ItemWorm());
                put(ItemKey.FLINTSTEEL, new ItemFlintSteel());
                put(ItemKey.WATERBOTTLE, new ItemWaterBottle());
                put(ItemKey.HOGWEED, new ItemHogweed());
                put(ItemKey.WATERPURIFICATIONTABLETS, new ItemWaterPurificationTablets());
        }};
        
        /**
         * @returns The image of the fire
         */
        public static Image getFireSprite() {
                return fireSprite;
        }
        
        /**
         * @returns The image of the player in a particular direction
         * 
         * @param d The direction in which the player is facing
         * @param frame The stage of motion the player is in
         */
        public static Image getPlayerSprite(Direction d, int frame) {
                return playerSprites.get(d)[frame];
        }
        
        /**
         * @returns the Block object associated with the enum value b
         * @param b the enum value
         */
        public static Block getBlock(BlockKey b) {
                Block c = blocks.get(b);
                if(c == null) {
                        if(ItemKey.valueOf(b.name()) != null)
                                blocks.put(b, new PortableBlock("Artwork/missing.png", ItemKey.valueOf(b.name())));
                }
                return blocks.get(b);
        }
        
        /**
         * @returns the Item object associated with the enum value b
         * @param b the enum value
         */
        public static Item getItem(ItemKey b) {
                return items.get(b);
        }
        
        /**
         * @returns the block enum value associated with an item enum value
         * @param k the ItemKey enum value
         */
        public static BlockKey getPortableBlock(ItemKey k) {
                if(blocks.get(BlockKey.valueOf(k.name())) == null)
                        blocks.put(BlockKey.valueOf(k.name()), new PortableBlock("Artwork/missing.png", k));
                return BlockKey.valueOf(k.name());
        }
}
