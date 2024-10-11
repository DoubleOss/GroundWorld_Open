package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntitySubWay;
import com.doubleos.gw.entity.EntitySubWay2;
import com.doubleos.gw.geko_model.SubWayModel;
import com.doubleos.gw.geko_model.SubWayModel2;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SubWayGeoRenderer2 extends GeoEntityRenderer<EntitySubWay2> {
    public SubWayGeoRenderer2(RenderManager renderManager) {
        super(renderManager, new SubWayModel2());
    }

    @Override
    public boolean shouldRender(EntitySubWay2 livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void doRender(EntitySubWay2 entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {

            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(90, 0, 1, 0);
            GlStateManager.translate(-x, -y, -z);

            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        GlStateManager.popMatrix();


    }
}
