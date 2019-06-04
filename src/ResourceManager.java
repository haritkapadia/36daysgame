/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;

public final class ResourceManager {
        private static final EnumMap<BlockKey, Block> blocks = new EnumMap<BlockKey, Block>(BlockKey.class){{
                put(BlockKey.GROUND, new BlockGround());
                put(BlockKey.WATER, new BlockWater());
                put(BlockKey.POISON, new BlockPoison());
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
        }};
        
        public static Block getBlock(BlockKey b) {
                return blocks.get(b);
        }
        
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
        }};
        
        public static Item getItem(ItemKey b) {
                return items.get(b);
        }
}
