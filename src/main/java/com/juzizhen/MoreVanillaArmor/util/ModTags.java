package com.juzizhen.MoreVanillaArmor.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Item> BONES_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "bones"));
    public static final TagKey<Item> EMERALD_GEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "gems/emerald"));
    public static final TagKey<Item> ENDER_PEARLS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "ender_pearls"));
    public static final TagKey<Item> LAPIS_GEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "gems/lapis"));
    public static final TagKey<Item> OBSIDIAN_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "obsidian"));
    public static final TagKey<Item> PRISMARINE_DUSTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "dusts/prismarine"));
    public static final TagKey<Item> QUARTZ_GEMS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "gems/quartz"));
    public static final TagKey<Item> REDSTONE_DUSTS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "dusts/redstone"));
    public static final TagKey<Item> SLIMEBALLS = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "slimeballs"));
    public static final TagKey<Item> STONE_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier("c", "stone"));
}