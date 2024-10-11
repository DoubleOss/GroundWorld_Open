package com.doubleos.gw.item;

import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nullable;
import javax.swing.text.html.parser.Entity;

public class ItemHelmet extends ItemArmor
{
    public ItemHelmet(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
    {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        setRegistryName("trans");
        setUnlocalizedName("trans");
    }

    public ItemHelmet(ArmorMaterial customArmorMaterial)
    {
        super(customArmorMaterial, 0, EntityEquipmentSlot.HEAD);
        setRegistryName("trans");
        setUnlocalizedName("trans");
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, net.minecraft.entity.Entity entity, EntityEquipmentSlot slot, String type)
    {
        return "grodworld:textures/item/trans.png";
    }


}
