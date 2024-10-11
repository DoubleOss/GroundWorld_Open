package com.doubleos.gw.block.tile;

import com.doubleos.gw.variable.Variable;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.UUID;

public class BombTile extends TileEntity implements IAnimatable, ITickable
{
    private final AnimationFactory factory = new AnimationFactory(this);



    Variable variable = Variable.Instance();
    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.getController().getAnimationState().name().equals(AnimationState.Stopped.name()) && Variable.Instance().boomIsActive
        && boomTick >= 48)
        {

            world.setBlockToAir(this.pos);
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(variable.boomAni, false));
        //System.out.println(variable.m_emerganPlayType.name() + "  " + variable.m_playLoopCheck);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController(this, "controller", 0, this::predicate));
    }

    int boomTick = 0;

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void update()
    {

        if(variable.boomIsActive)
        {
            //System.out.println("값 들어오는중");
            boomTick++;
            if(boomTick == 48)
            {
                int random = new Random().nextInt(3);
                EnumParticleTypes types = EnumParticleTypes.EXPLOSION_HUGE;
                if(random == 0)
                    types = EnumParticleTypes.EXPLOSION_HUGE;
                else if (random == 1)
                    types = EnumParticleTypes.EXPLOSION_NORMAL;
                else
                    types = EnumParticleTypes.EXPLOSION_LARGE;
                world.spawnParticle(types, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 0, 0, 0, 0);

                float randomX = new Random().nextFloat();
                float randomY = new Random().nextFloat();
                float randomZ = new Random().nextFloat();

                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX(), this.pos.getY(), this.pos.getZ(), randomX, randomY, randomZ, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ(), randomX*1.25, randomY*1.25f, randomZ*1.25f, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ(), randomX*-1.25, randomY*-1.25, randomZ*-1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ()+1, randomX*1.25, randomY*1.25, randomZ*1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ()-1, randomX*-1.25, randomY*-1.25, randomZ*-1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY()+1, this.pos.getZ()+1, randomX*1.25, randomY*1.25, randomZ*1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY()-1, this.pos.getZ()-1, randomX*-1.25, randomY*-1.25, randomZ*-1.25, 0);

                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ(), randomX*1.25, randomY*1.25f, 0, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ(), randomX*-1.25, randomY*-1.25, 0, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ()+1, randomX*1.25, randomY*1.25, 0, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ()-1, randomX*-1.25, randomY*-1.25, 0, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY()+1, this.pos.getZ()+1, randomX*1.25, randomY*1.25, 0, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY()-1, this.pos.getZ()-1, randomX*-1.25, randomY*-1.25, 0, 0);

                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ(), 0, randomY*1.25f, randomZ*1.25f, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ(), 0, randomY*-1.25, randomZ*-1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY(), this.pos.getZ()+1, 0, randomY*1.25, randomZ*1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY(), this.pos.getZ()-1, 0, randomY*-1.25, randomZ*-1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()+1, this.pos.getY()+1, this.pos.getZ()+1, 0, randomY*1.25, randomZ*1.25, 0);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.pos.getX()-1, this.pos.getY()-1, this.pos.getZ()-1, 0, randomY*-1.25, randomZ*-1.25, 0);


                if(! this.world.isRemote)
                {
                    EntityCreeper creeper = new EntityCreeper(world);
                    world.newExplosion(creeper, this.pos.getX()+1, this.pos.getY(), this.pos.getZ(), 4f, false, true);
                }




            }

        }
        else
        {
            boomTick = 0;
        }
    }
}
