package com.juzizhen.MoreVanillaArmorFabric.util;


import com.juzizhen.MoreVanillaArmorFabric.block.RedstoneEssenceBlock;
import com.juzizhen.MoreVanillaArmorFabric.block.RedstoneEssenceBlockEntity;
import com.juzizhen.MoreVanillaArmorFabric.items.Armor;
import com.juzizhen.MoreVanillaArmorFabric.items.ArmorTiers;
import com.juzizhen.MoreVanillaArmorFabric.morevanillaarmorfabric;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ModRegistries {

    public static final Map<ArmorPiece, Armor> armor = new HashMap<>();
    public static Block REDSTONE_ESSENCE;
    public static BlockEntityType<RedstoneEssenceBlockEntity> REDSTONE_ESSENCE_BLOCK_ENTITY;

    public static void register() {
        REDSTONE_ESSENCE = Registry.register(
                Registries.BLOCK,
                Identifier.of(morevanillaarmorfabric.MODID, "redstone_essence"),
                new RedstoneEssenceBlock(
                        AbstractBlock.Settings.copy(Blocks.AIR)
                                .noCollision()
                                .breakInstantly()
                                .nonOpaque()
                                .dropsNothing()
                                .luminance(state -> 0)
                )
        );

        REDSTONE_ESSENCE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(morevanillaarmorfabric.MODID, "redstone_essence"),
                BlockEntityType.Builder.create(
                        RedstoneEssenceBlockEntity::new,
                        REDSTONE_ESSENCE
                ).build(null)
        );

        for (ArmorTiers armorType : ArmorTiers.values()) {
            for (ArmorItem.Type type : ArmorItem.Type.values()) {
                ArmorPiece piece = new ArmorPiece(armorType, ArmorSlot.get(Armor.getSlotFromType(type)));
                Armor armorItem = new Armor(armorType, type);
                ModRegistries.armor.put(piece, armorItem);

                Registry.register(
                        Registries.ITEM,
                        new Identifier(morevanillaarmorfabric.MODID, piece.name()),
                        armorItem
                );
            }
        }

        Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(morevanillaarmorfabric.MODID, "morevanillaarmorfabric"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(armor.get(new ArmorPiece(ArmorTiers.EMERALD, ArmorSlot.CHESTPLATE))))
                        .displayName(Text.translatable("itemGroup.morevanillaarmorfabric"))
                        .entries((context, entries) -> {
                            for (Item item : Registries.ITEM) {
                                if (Registries.ITEM.getId(item).getNamespace().equals(morevanillaarmorfabric.MODID)) {
                                    entries.add(item);
                                }
                            }
                        })
                        .build()
        );

    }

    public enum ArmorSlot {
        HELMET(EquipmentSlot.HEAD),
        CHESTPLATE(EquipmentSlot.CHEST),
        LEGGINGS(EquipmentSlot.LEGS),
        BOOTS(EquipmentSlot.FEET);

        private final EquipmentSlot slot;

        ArmorSlot(EquipmentSlot slot) {
            this.slot = slot;
        }

        public static ArmorSlot get(EquipmentSlot slot) {
            for (ArmorSlot value : ArmorSlot.values()) {
                if (value.slot == slot) {
                    return value;
                }
            }

            throw new IllegalArgumentException("That shouldn't happen. Why?");
        }
    }

    record ArmorPiece(ArmorTiers type, ArmorSlot slot) {
        public String name() {
            return this.type.toString().toLowerCase(Locale.ROOT) + "_" + this.slot.toString().toLowerCase(Locale.ROOT);
        }
    }
}