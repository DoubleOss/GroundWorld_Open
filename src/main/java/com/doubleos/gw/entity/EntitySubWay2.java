package com.doubleos.gw.entity;

import com.doubleos.gw.variable.Variable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class EntitySubWay2 extends EntityCreature implements IAnimatable {

    public boolean m_revers = false;


    private AnimationFactory factory = new AnimationFactory(this);


    public double vectorDirX = (0.812f * 0.02 * 1.0F);
    public double vectorDirZ = (0.812f * 0.02 * 1.0F);

    Variable variable = Variable.Instance();
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (variable.m_subWayStatus.equals(Variable.eSubWayStatus.IDLE))
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        else if (variable.m_subWayStatus.equals(Variable.eSubWayStatus.DOOR_OPEN_L))
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("right openimng", true));
        }
        else if (variable.m_subWayStatus.equals(Variable.eSubWayStatus.DOOR_OPEN_L_ING))
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("right open", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }


    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        if(this.world.isRemote)
        {


            double currentSpeed = 1.0d;

            vectorDirX = variable.m_subWaySpeed * 1 * (0.812f * 0.01675 * 1.0F);
            vectorDirZ = 0;

            this.motionX = vectorDirX;
            this.motionY = 0;
            this.motionZ = vectorDirZ;

            if(variable.m_subWayStatus.equals(Variable.eSubWayStatus.IDLE))
            {
                if(variable.endPosZ > this.getPosition().getX()) // 북쪽 기준
                {
                    move(MoverType.SELF, this.motionX * currentSpeed, this.motionY*currentSpeed, this.motionZ * currentSpeed);
                }
                else
                {
                    variable.m_subWayStatus = Variable.eSubWayStatus.BREAK;
                    variable.m_subWaySpeed = 0;
                    variable.m_subwayTick = 0;

                }

            }


        }




    }



    public EntitySubWay2(World worldIn) {
        super(worldIn);
        this.setSize(1f, 1f);
        this.ignoreFrustumCheck = true;
        this.setNoGravity(true);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<EntitySubWay2>(this, "controller", 0, this::predicate));
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
