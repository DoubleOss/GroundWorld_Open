package com.doubleos.gw.entity;


import com.doubleos.gw.variable.Variable;
import javafx.animation.Animation;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySubWay extends EntityCreature implements IAnimatable {

    public boolean m_revers = false;

    private AnimationFactory factory = new AnimationFactory(this);


    public double vectorDirX = (0.812f * 0.02 * 1.0F);
    public double vectorDirZ = (0.812f * 0.02 * 1.0F);

    Variable variable = Variable.Instance();
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {

        //모델링 애니메이션 상태에 따른 모션
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
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        if(this.world.isRemote)
        {

            double currentSpeed = 1.0d;

            //지하철 움직임 로직
            vectorDirX = 0;
            vectorDirZ = variable.m_subWaySpeed * -1 * (0.812f * 0.019f * 1.0F);

            this.motionX = vectorDirX;
            this.motionY = 0;
            this.motionZ = vectorDirZ;

            if(variable.m_subWayStatus.equals(Variable.eSubWayStatus.IDLE))
            {
                if(variable.endPosZ < this.getPosition().getZ()) // 북쪽 기준
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

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    public EntitySubWay(World worldIn) {
        super(worldIn);
        this.setSize(1f, 1f);
        this.ignoreFrustumCheck = true;
        this.setNoGravity(true);

    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<EntitySubWay>(this, "controller", 0, this::predicate));
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
