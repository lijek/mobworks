package pl.lijek.mobworks.tileentity;

import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntityBase;
import pl.lijek.mobworks.RenderHelper;

import static org.lwjgl.opengl.GL11.*;

public class MobGrinderRenderer extends TileEntityRenderer {
    @Override
    public void render(TileEntityBase entity, double x, double y, double z, float f) {
        TileEntityMobGrinder grinder = (TileEntityMobGrinder) entity;
        if(!grinder.isRenderingWorkingRange())
            return;
        y += 0.01d;

        int[] sizesAndOffsets = grinder.getSizesAndOffsets();

        glDisable(GL_TEXTURE_2D);
        glPushMatrix();
        float brightness = renderDispatcher.cameraEntity.getBrightnessAtEyes(f);
        float r = 255.0f / 255.0f * brightness;
        float g = 200.0f / 255.0f * brightness;
        float b = 0.0f / 255.0f * brightness;
        glColor4f(r, g, b, 1.0f);
        glTranslatef(sizesAndOffsets[2], 0, sizesAndOffsets[3]);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2.0f);
        RenderHelper.drawCuboid(GL_LINE_LOOP, x, y, z, x + sizesAndOffsets[0], y + 1, z + sizesAndOffsets[1]);
        glDisable(GL_LINE_SMOOTH);

        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
    }
}
