package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine11;
import com.doubleos.gw.entity.EntityGeoAutoMine12;
import com.doubleos.gw.geko_model.AutoMineModel11;
import com.doubleos.gw.geko_model.AutoMineModel12;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer12 extends GeoEntityRenderer<EntityGeoAutoMine12>
{
    public EntityAutoMineRenderer12(RenderManager renderManager) {
        super(renderManager, new AutoMineModel12());
    }
}
