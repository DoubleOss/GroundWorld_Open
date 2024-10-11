package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntitySubWay;
import com.doubleos.gw.geko_model.SubWayModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import software.bernie.example.client.model.entity.BikeModel;
import software.bernie.example.entity.BikeEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SubWayGeoRenderer extends GeoEntityRenderer<EntitySubWay> {
    public SubWayGeoRenderer(RenderManager renderManager) {
        super(renderManager, new SubWayModel());
    }

    @Override
    public boolean shouldRender(EntitySubWay livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void doRender(EntitySubWay entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(180, 0, 1, 0);
            GlStateManager.translate(-x, -y, -z);
            //북쪽을 가게 됨
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        GlStateManager.popMatrix();


    }
}
