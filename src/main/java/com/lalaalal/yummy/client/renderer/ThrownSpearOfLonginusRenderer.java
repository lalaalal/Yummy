package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.model.ThrownSpearOfLonginusModel;
import com.lalaalal.yummy.entity.ThrownSpearOfLonginus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ThrownSpearOfLonginusRenderer extends ThrownSpearRenderer<ThrownSpearOfLonginus> {
    public ThrownSpearOfLonginusRenderer(EntityRendererProvider.Context context) {
        super(context, "spear_of_longinus", () -> new ThrownSpearOfLonginusModel(context.bakeLayer(ThrownSpearOfLonginusModel.LAYER_LOCATION)));
    }
}
