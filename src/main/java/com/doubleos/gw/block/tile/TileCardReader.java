package com.doubleos.gw.block.tile;

import com.doubleos.gw.variable.Variable;
import net.minecraft.tileentity.TileEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TileCardReader extends TileEntity implements IAnimatable
{
    private final AnimationFactory factory = new AnimationFactory(this);



    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().transitionLengthTicks = 0;

        event.getController().setAnimation(new AnimationBuilder().addAnimation(Variable.Instance().cardTileAnimationName, true));
        //System.out.println(variable.m_emerganPlayType.name() + "  " + variable.m_playLoopCheck);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
