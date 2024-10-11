package com.doubleos.gw.geko_render;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.geko_model.jackModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class jackRender extends GeoItemRenderer<OBJItemBase> {
    public jackRender() {
        super(new jackModel());
    }
}
