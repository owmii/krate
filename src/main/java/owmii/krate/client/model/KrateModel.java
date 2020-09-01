package owmii.krate.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import owmii.krate.Krate;
import owmii.krate.block.KrateTile;
import owmii.krate.client.render.tile.KrateRenderer;
import owmii.lib.client.model.AbstractModel;

import java.util.function.Function;

public class KrateModel extends AbstractModel<KrateTile, KrateRenderer> {
    private final ModelRenderer core;
    private final ModelRenderer frame0;
    private final ModelRenderer frame1;
    private final ModelRenderer frame2;
    private final ModelRenderer frame3;
    private final ModelRenderer frame4;
    private final ModelRenderer frame5;
    private final ModelRenderer frame6;
    private final ModelRenderer frame7;

    public KrateModel(Function<ResourceLocation, RenderType> function) {
        super(function);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.core = new ModelRenderer(this, 0, 0);
        this.core.addBox(-7.5F, -7.5F, -7.5F, 15, 15, 15);
        this.core.setRotationPoint(0F, 16F, 0F);
        this.core.setTextureSize(64, 64);
        this.core.mirror = true;
        setRotation(this.core, 0F, 0F, 0F);
        this.frame0 = new ModelRenderer(this, 0, 30);
        this.frame0.addBox(-8F, -8F, -8F, 3, 3, 16);
        this.frame0.setRotationPoint(0F, 16F, 0F);
        this.frame0.setTextureSize(64, 64);
        this.frame0.mirror = true;
        setRotation(this.frame0, 0F, 0F, 0F);
        this.frame1 = new ModelRenderer(this, 0, 30);
        this.frame1.addBox(5F, -8F, -8F, 3, 3, 16);
        this.frame1.setRotationPoint(0F, 16F, 0F);
        this.frame1.setTextureSize(64, 64);
        this.frame1.mirror = true;
        setRotation(this.frame1, 0F, 0F, 0F);
        this.frame2 = new ModelRenderer(this, 0, 30);
        this.frame2.addBox(5F, 5F, -8F, 3, 3, 16);
        this.frame2.setRotationPoint(0F, 16F, 0F);
        this.frame2.setTextureSize(64, 64);
        this.frame2.mirror = true;
        setRotation(this.frame2, 0F, 0F, 0F);
        this.frame3 = new ModelRenderer(this, 0, 30);
        this.frame3.addBox(-8F, 5F, -8F, 3, 3, 16);
        this.frame3.setRotationPoint(0F, 16F, 0F);
        this.frame3.setTextureSize(64, 64);
        this.frame3.mirror = true;
        setRotation(this.frame3, 0F, 0F, 0F);
        this.frame4 = new ModelRenderer(this, 22, 30);
        this.frame4.addBox(-5F, -8F, 5F, 10, 3, 3);
        this.frame4.setRotationPoint(0F, 16F, 0F);
        this.frame4.setTextureSize(64, 64);
        this.frame4.mirror = true;
        setRotation(this.frame4, 0F, 0F, 0F);
        this.frame5 = new ModelRenderer(this, 22, 30);
        this.frame5.addBox(-5F, -8F, -8F, 10, 3, 3);
        this.frame5.setRotationPoint(0F, 16F, 0F);
        this.frame5.setTextureSize(64, 64);
        this.frame5.mirror = true;
        setRotation(this.frame5, 0F, 0F, 0F);
        this.frame6 = new ModelRenderer(this, 22, 30);
        this.frame6.addBox(-5F, 5F, 5F, 10, 3, 3);
        this.frame6.setRotationPoint(0F, 16F, 0F);
        this.frame6.setTextureSize(64, 64);
        this.frame6.mirror = true;
        setRotation(this.frame6, 0F, 0F, 0F);
        this.frame7 = new ModelRenderer(this, 22, 30);
        this.frame7.addBox(-5F, 5F, -8F, 10, 3, 3);
        this.frame7.setRotationPoint(0F, 16F, 0F);
        this.frame7.setTextureSize(64, 64);
        this.frame7.mirror = true;
        setRotation(this.frame7, 0F, 0F, 0F);
    }

    @Override
    public void render(KrateTile te, KrateRenderer renderer, MatrixStack matrix, IRenderTypeBuffer rtb, int light, int ov) {
        IVertexBuilder buffer = rtb.getBuffer(getRenderType(new ResourceLocation(Krate.MOD_ID, "textures/model/tile/krate_" + te.getVariant().getName() + ".png")));
        this.core.render(matrix, buffer, light, ov);
        this.frame0.render(matrix, buffer, light, ov);
        this.frame1.render(matrix, buffer, light, ov);
        this.frame2.render(matrix, buffer, light, ov);
        this.frame3.render(matrix, buffer, light, ov);
        this.frame4.render(matrix, buffer, light, ov);
        this.frame5.render(matrix, buffer, light, ov);
        this.frame6.render(matrix, buffer, light, ov);
        this.frame7.render(matrix, buffer, light, ov);
    }
}
