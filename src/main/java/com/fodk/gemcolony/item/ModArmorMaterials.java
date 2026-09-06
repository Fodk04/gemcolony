package com.fodk.gemcolony.item;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.tags.ModTags;
import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.Map;

public class ModArmorMaterials {

    public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> CHROMA_KEY = ResourceKey.create(ROOT_ID, Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "chroma"));
    public static final ArmorMaterial CHROMA_ARMOR_MATERIAL = new ArmorMaterial(1200,
            makeDefense(4, 6, 8, 4, 10), 12, SoundEvents.ARMOR_EQUIP_COPPER,
            2, 0.1f, ModTags.Items.CHROMA_REPAIRABLE, CHROMA_KEY);


    private static Map<ArmorType, Integer> makeDefense(int boots, int legs, int chest, int helm, int body) {
        return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body));
    }
}
