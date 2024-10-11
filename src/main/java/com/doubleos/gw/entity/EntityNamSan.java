package com.doubleos.gw.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNamSan extends Entity
{

    public EntityNamSan(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {


    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @Override
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }

    @Override
    public void onUpdate() 
    {
        System.out.println("활성화");
    }
}
