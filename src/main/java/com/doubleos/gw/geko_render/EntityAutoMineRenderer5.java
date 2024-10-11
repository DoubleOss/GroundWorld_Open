package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine4;
import com.doubleos.gw.entity.EntityGeoAutoMine5;
import com.doubleos.gw.geko_model.AutoMineModel4;
import com.doubleos.gw.geko_model.AutoMineModel5;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer5 extends GeoEntityRenderer<EntityGeoAutoMine5>
{
    public EntityAutoMineRenderer5(RenderManager renderManager) {
        super(renderManager, new AutoMineModel5());
    }
}
