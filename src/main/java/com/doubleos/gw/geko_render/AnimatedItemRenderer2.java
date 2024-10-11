package com.doubleos.gw.geko_render;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.block.OBJItemBase2;
import com.doubleos.gw.geko_model.AnimatedItemModel;
import com.doubleos.gw.geko_model.UpgradegenedrillModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class AnimatedItemRenderer2 extends GeoItemRenderer<OBJItemBase2>
{

    public AnimatedItemRenderer2()
    {
        super(new UpgradegenedrillModel());
    }


}
