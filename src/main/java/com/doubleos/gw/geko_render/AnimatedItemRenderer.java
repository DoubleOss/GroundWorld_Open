package com.doubleos.gw.geko_render;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.geko_model.AnimatedItemModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedItemRenderer extends GeoItemRenderer<OBJItemBase>
{

    public AnimatedItemRenderer()
    {
        super(new AnimatedItemModel());
    }


}
