package pl.lijek.mobworks.tileentity;

import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntityBase;
import pl.lijek.mobworks.RenderHelper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class VacuumMachineRenderer extends TileEntityRenderer {
    @Override
    public void render(TileEntityBase entity, double x, double y, double z, float f) {
        TileEntityVacuumMachine vacuumMachine = (TileEntityVacuumMachine) entity;

        if(!vacuumMachine.isRenderingWorkingRange())
            return;

        glDisable(GL_TEXTURE_2D);
        glPushMatrix();
        float brightness = renderDispatcher.cameraEntity.getBrightnessAtEyes(f);
        float r = 255.0f / 255.0f * brightness;
        float g = 200.0f / 255.0f * brightness;
        float b = 0.0f / 255.0f * brightness;
        glColor4f(r, g, b, 1.0f);
        glTranslatef(vacuumMachine.getOffsetX(), vacuumMachine.getOffsetY(), vacuumMachine.getOffsetZ());

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(2.0f);
        RenderHelper.drawCuboid(GL_LINE_LOOP, x, y, z, x + vacuumMachine.getWorkingSize(), y + vacuumMachine.getWorkingSize(), z + vacuumMachine.getWorkingSize());
        glDisable(GL_LINE_SMOOTH);

        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
    }
}
