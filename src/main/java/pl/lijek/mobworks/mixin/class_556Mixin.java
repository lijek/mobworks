package pl.lijek.mobworks.mixin;

import net.minecraft.class_556;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.Living;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lijek.mobworks.CustomItemOverlay;
import pl.lijek.mobworks.TextureRGB;
import pl.lijek.mobworks.items.SpawnEgg;

@Mixin(class_556.class)
public class class_556Mixin {

    @Inject(method = "method_1862", at = @At("TAIL"), cancellable = true)
    private void renderSpawnEggInHand(Living arg, ItemInstance itemInstance, CallbackInfo info){
        if(itemInstance.getType() instanceof SpawnEgg){
            SpawnEgg egg = (SpawnEgg) itemInstance.getType();
            int damage = itemInstance.getDamage();

            int color = egg.getBackColor(damage);
            float cr = (color >> 16 & 255) / 255.0F;
            float cg = (color >> 8 & 255) / 255.0F;
            float cb = (color & 255) / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderTextureInHand(egg.backTexture);

            color = egg.getDotsColor(damage);
            cr = (color >> 16 & 255) / 255.0F;
            cg = (color >> 8 & 255) / 255.0F;
            cb = (color & 255) / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderTextureInHand(egg.overlayTexture);

            info.cancel();
        }

        if (itemInstance.getType() instanceof CustomItemOverlay){
            ItemBase item = itemInstance.getType();
            CustomItemOverlay overlay = (CustomItemOverlay) item;
            int damage = itemInstance.getDamage();

            int color = item.getNameColour(damage);
            float cr = (color >> 16 & 255) / 255.0F;
            float cg = (color >> 8 & 255) / 255.0F;
            float cb = (color & 255) / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderTextureInHand(overlay.getBaseTexture());

            TextureRGB data = overlay.getTextureAndColorForRender(itemInstance);
            cr = data.r / 255.0F;
            cg = data.g / 255.0F;
            cb = data.b / 255.0F;
            GL11.glColor4f(cr, cg, cb, 1.0F);
            renderTextureInHand(data.texture);

            info.cancel();
        }
    }

    private void renderTextureInHand(Atlas.Texture  texture) {
        GL11.glPushMatrix();

        texture.getAtlas().bindAtlas();
        if(texture.getAtlas().getTessellator() == null)
            texture.getAtlas().initTessellator();
        Tessellator tessellator = texture.getAtlas().getTessellator();

        double u1 = texture.getStartU();
        double u2 = texture.getEndU();
        double v1 = texture.getStartV();
        double v2 = texture.getEndV();

        GL11.glEnable(32826);
        GL11.glTranslatef(0.0F, -0.3F, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);

        tessellator.start();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.vertex(0.0D, 0.0D, 0.0D, u2, v2);
        tessellator.vertex(1.0F, 0.0D, 0.0D, u1, v2);
        tessellator.vertex(1.0F, 1.0D, 0.0D, u1, v1);
        tessellator.vertex(0.0D, 1.0D, 0.0D, u2, v1);
        tessellator.draw();

        tessellator.start();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.vertex(0.0D, 1.0D, 0.0F - 0.0625F, u2, v1);
        tessellator.vertex(1.0F, 1.0D, 0.0F - 0.0625F, u1, v1);
        tessellator.vertex(1.0F, 0.0D, 0.0F - 0.0625F, u1, v2);
        tessellator.vertex(0.0D, 0.0D, 0.0F - 0.0625F, u2, v2);
        tessellator.draw();

        tessellator.start();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        for(int var14 = 0; var14 < 16; ++var14) {
            double py = var14 / 16.0F;
            double u = u2 + (u1 - u2) * py - 0.001953125F;
            tessellator.vertex(py, 0.0D, 0.0F - 0.0625F, u, v2);
            tessellator.vertex(py, 0.0D, 0.0D, u, v2);
            tessellator.vertex(py, 1.0D, 0.0D, u, v1);
            tessellator.vertex(py, 1.0D, 0.0F - 0.0625F, u, v1);
        }

        tessellator.draw();

        tessellator.start();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for(int i = 0; i < 16; ++i) {
            double delta = i / 16.0F;
            double u = u2 + (u1 - u2) * delta - 0.001953125F;
            double py = delta + 0.0625F;
            tessellator.vertex(py, 1.0D, 0.0F - 0.0625F, u, v1);
            tessellator.vertex(py, 1.0D, 0.0D, u, v1);
            tessellator.vertex(py, 0.0D, 0.0D, u, v2);
            tessellator.vertex(py, 0.0D, 0.0F - 0.0625F, u, v2);
        }

        tessellator.draw();

        tessellator.start();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for(int i = 0; i < 16; ++i) {
            double delta = i / 16.0F;
            double v = v2 + (v1 - v2) * delta - 0.001953125F;
            double py = delta + 0.0625F;
            tessellator.vertex(0.0D, py, 0.0D, u2, v);
            tessellator.vertex(1.0F, py, 0.0D, u1, v);
            tessellator.vertex(1.0F, py, 0.0F - 0.0625F, u1, v);
            tessellator.vertex(0.0D, py, 0.0F - 0.0625F, u2, v);
        }

        tessellator.draw();

        tessellator.start();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for(int i = 0; i < 16; ++i) {
            double py = i / 16.0F;
            double v = v2 + (v1 - v2) * py - 0.001953125F;
            tessellator.vertex(1.0F, py, 0.0D, u1, v);
            tessellator.vertex(0.0D, py, 0.0D, u2, v);
            tessellator.vertex(0.0D, py, 0.0F - 0.0625F, u2, v);
            tessellator.vertex(1.0F, py, 0.0F - 0.0625F, u1, v);
        }

        tessellator.draw();
        GL11.glDisable(32826);

        GL11.glPopMatrix();
    }
}
