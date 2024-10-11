package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityRobot;
import com.doubleos.gw.entity.ZombieToRobot;
import com.doubleos.gw.geko_model.EntityShopModel4;
import com.doubleos.gw.geko_model.ZombieToRobotModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

public class ReplaceZombieToRobot extends GeoEntityRenderer<EntityRobot>
{
	public ReplaceZombieToRobot(RenderManager renderManager) {
		super(renderManager, new ZombieToRobotModel());
	}


}
