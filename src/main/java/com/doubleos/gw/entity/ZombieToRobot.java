package com.doubleos.gw.entity;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ZombieToRobot implements IAnimatable
{


	AnimationFactory factory = new AnimationFactory(this);

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<ZombieToRobot>(this, "controller", 5, this::predicate));
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
	{
		//System.out.println(event.getLimbSwingAmount());
		if ((event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))
		{
			event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		}
		else
		{
			if(event.getController().getCurrentAnimation() != null)
			{
				if(event.getController().getCurrentAnimation().animationName.equals("idle"))
					event.getController().clearAnimationCache();
				//System.out.println(event.getController().getCurrentAnimation().animationName);
			}

			event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
		}


		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
