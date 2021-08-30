package pl.lijek.mobworks.mixin;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.tileentity.MobSpawnerRenderer;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityMobSpawner;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.lijek.mobworks.MobSpawnerModifiers;
import pl.lijek.mobworks.items.SpawnerUpgrade;

@Mixin(MobSpawnerRenderer.class)
public abstract class MobSpawnerRendererMixin extends TileEntityRenderer {

    private void renderString(String string, int offset, double x, double y, double z){
        TextRenderer textRenderer = method_1063();
        float var12 = 1.6F;
        float var13 = 0.016666668F * var12;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 2.3F, (float)z + 0.5F);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderDispatcher.field_1559, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(renderDispatcher.field_1560, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-var13, -var13, var13);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        Tessellator tessellator = Tessellator.INSTANCE;

        GL11.glDisable(3553);
        tessellator.start();
        int var16 = textRenderer.getTextWidth(string) / 2;
        tessellator.colour(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex(-var16 - 1, -1 + offset, 0.0D);
        tessellator.addVertex(-var16 - 1, 8 + offset, 0.0D);
        tessellator.addVertex(var16 + 1, 8 + offset, 0.0D);
        tessellator.addVertex(var16 + 1, -1 + offset, 0.0D);
        tessellator.draw();
        GL11.glEnable(3553);
        textRenderer.drawText(string, -textRenderer.getTextWidth(string) / 2, offset, 553648127);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        textRenderer.drawText(string, -textRenderer.getTextWidth(string) / 2, offset, -1);
        GL11.glEnable(2896);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(TileEntityMobSpawner tileEntityMobSpawner, double x, double y, double z, float f, CallbackInfo ci){
        if(renderDispatcher.cameraEntity instanceof PlayerBase) {
            PlayerBase player = ((PlayerBase) renderDispatcher.cameraEntity);
            ItemInstance item = player.inventory.main[player.inventory.selectedHotbarSlot];
            if (item == null || !(item.getType() instanceof SpawnerUpgrade) || player.distanceTo(tileEntityMobSpawner.x, tileEntityMobSpawner.y, tileEntityMobSpawner.z) > 12)
                return;
        }

        MobSpawnerModifiers modifiers = (MobSpawnerModifiers)tileEntityMobSpawner;

        renderString("Entities to spawn: " + modifiers.getEntitiesToSpawn(), 0, x, y, z);
        renderString("Max entities nearby: " + modifiers.getMaxEntitiesNearby(), 10, x, y, z);
        renderString("Requires redstone: " + modifiers.getRequiresRedstone(), 20, x, y, z);
        renderString("Spawn range: " + modifiers.getSpawnRange(), 30, x, y, z);

    }
}
