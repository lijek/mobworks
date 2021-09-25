package pl.lijek.mobworks.mixin;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lijek.mobworks.CustomItemOverlay;
import pl.lijek.mobworks.TextureRGB;
import pl.lijek.mobworks.items.SpawnEgg;

import java.util.Random;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin extends EntityRenderer {

    @Shadow private BlockRenderer field_1708;

    @Shadow public boolean field_1707;

    @Shadow public abstract void method_1483(int i, int j, int k, int i1, int j1, int k1);

    @Shadow private Random rand;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderSpawnEggInWorld(Item arg, double d, double d1, double d2, float f, float f1, CallbackInfo info) {
        if (ItemBase.byId[arg.item.itemId] instanceof SpawnEgg) {
            SpawnEgg egg = (SpawnEgg) ItemBase.byId[arg.item.itemId];
            int damage = arg.item.getDamage();

            int color = egg.getBackColor(damage);
            int cr = (color >> 16 & 255);
            int cg = (color >> 8 & 255);
            int cb = (color & 255);
            renderTextureOnGround(new TextureRGB(egg.backTexture, cr, cg, cb), arg, d, d1, d2, f, f1);

            color = egg.getDotsColor(damage);
            cr = (color >> 16 & 255);
            cg = (color >> 8 & 255);
            cb = (color & 255);
            renderTextureOnGround(new TextureRGB(egg.overlayTexture, cr, cg, cb), arg, d, d1, d2, f, f1);

            info.cancel();
        }

        if(ItemBase.byId[arg.item.itemId] instanceof CustomItemOverlay){
            CustomItemOverlay overlay = (CustomItemOverlay) ItemBase.byId[arg.item.itemId];
            renderTextureOnGround(new TextureRGB(overlay.getBaseTexture(), 255, 255, 255), arg, d, d1, d2, f, f1);
            renderTextureOnGround(overlay.getTextureAndColorForRender(arg.item), arg, d, d1, d2, f, f1);
            info.cancel();
        }
    }

    @Inject(method = "renderItemOnGui", at = @At("HEAD"), cancellable = true)
    private void renderSpawnEggInGUI(TextRenderer textRenderer, TextureManager manager, int itemID, int damage, int texture, int renderX, int renderY, CallbackInfo info) {
        if (ItemBase.byId[itemID] instanceof SpawnEgg) {
            SpawnEgg egg = (SpawnEgg) ItemBase.byId[itemID];
            GL11.glDisable(2896);

            int color = egg.getBackColor(damage);
            float r = (color >> 16 & 255) / 255.0F;
            float g = (color >> 8 & 255) / 255.0F;
            float b = (color & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 1.0F);
            renderItemInGui(egg.backTexture, renderX, renderY, 16, 16);

            color = egg.getDotsColor(damage);
            r = (color >> 16 & 255) / 255.0F;
            g = (color >> 8 & 255) / 255.0F;
            b = (color & 255) / 255.0F;
            GL11.glColor4f(r, g, b, 1.0F);
            renderItemInGui(egg.overlayTexture, renderX, renderY, 16, 16);

            GL11.glEnable(2896);
            GL11.glEnable(2884);
            info.cancel();
        }

        if (ItemBase.byId[itemID] instanceof CustomItemOverlay){
            ItemBase item = ItemBase.byId[itemID];
            CustomItemOverlay overlay = (CustomItemOverlay) item;

            int color = item.getNameColour(damage);
            float cr = (color >> 16 & 255) / 255.0F;
            float cg = (color >> 8 & 255) / 255.0F;
            float cb = (color & 255) / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderItemInGui(overlay.getBaseTexture(), renderX, renderY, 16, 16);

            TextureRGB data = overlay.getTextureAndColorForRender(new ItemInstance(item, 1, damage));
            cr = data.r / 255.0F;
            cg = data.g / 255.0F;
            cb = data.b / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderItemInGui(data.texture, renderX, renderY, 16, 16);

            info.cancel();
        }
    }

    private void renderTextureOnGround(TextureRGB data, Item arg, double d, double d1, double d2, float f, float f1){
        this.rand.setSeed(187L);
        GL11.glPushMatrix();
        float var11 = MathHelper.sin(((float)arg.age + f1) / 10.0F + arg.field_567) * 0.1F + 0.1F;

        GL11.glTranslatef((float)d, (float)d1 + var11, (float)d2);
        GL11.glEnable(32826);

        data.texture.getAtlas().bindAtlas();
        float brightness = arg.getBrightnessAtEyes(f1);
        float cr = data.r / 255.0F * brightness;
        float cg = data.g / 255.0F * brightness;
        float cb = data.b / 255.0F * brightness;
        GL11.glColor4f(cr, cg, cb, 1.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        if(data.texture.getAtlas().getTessellator() == null)
            data.texture.getAtlas().initTessellator();
        Tessellator tessellator = data.texture.getAtlas().getTessellator();

        float u1 = (float) data.texture.getStartU();
        float u2 = (float) data.texture.getEndU();
        float v1 = (float) data.texture.getStartV();
        float v2 = (float) data.texture.getEndV();

        GL11.glPushMatrix();
        GL11.glRotatef(180.0F - this.dispatcher.field_2497, 0.0F, 1.0F, 0.0F);
        tessellator.start();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.vertex(-0.5F, -0.25F, 0.0D, u1, v2);
        tessellator.vertex(0.5F, -0.25F, 0.0D, u2, v2);
        tessellator.vertex(0.5F, 0.75F, 0.0D, u2, v1);
        tessellator.vertex(-0.5F, 0.75F, 0.0D, u1, v1);
        tessellator.draw();
        GL11.glPopMatrix();
    }

    private void renderItemInGui(Atlas.Sprite texture, int renderX, int renderY,int width, int height) {
        float zIndex = 0.0F;
        texture.getAtlas().bindAtlas();
        if(texture.getAtlas().getTessellator() == null)
            texture.getAtlas().initTessellator();
        Tessellator tessellator = texture.getAtlas().getTessellator();
        tessellator.start();
        tessellator.vertex(renderX + 0, renderY + height, zIndex, texture.getStartU(), texture.getEndV());
        tessellator.vertex(renderX + width, renderY + height, zIndex, texture.getEndU(), texture.getEndV());
        tessellator.vertex(renderX + width, renderY + 0, zIndex, texture.getEndU(), texture.getStartV());
        tessellator.vertex(renderX + 0, renderY + 0, zIndex, texture.getStartU(), texture.getStartV());
        tessellator.draw();
    }
}
