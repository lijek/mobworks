package pl.lijek.mobworks.gui.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class MultipleStatusButtonWithImage extends Button {
    public int u, v;
    public int state = 0;
    public int maxState;
    public String hoverText;
    public MultipleStatusButtonWithImage(int id, int x, int y, int u, int v, String hoverText, int maxState) {
        super(id, x, y, 20, 20, "");
        this.hoverText = hoverText;
        this.u = u;
        this.v = v;
        this.maxState = maxState;
    }

    @Override
    public void render(Minecraft minecraft, int mouseX, int mouseY) {
        super.render(minecraft, mouseX, mouseY);
        if(visible) {
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId("/assets/mobworks/textures/buttons.png"));
            blit(x + 2, y + 2, u, v + (state * 16), width - 2, height - 2);

            if (isMouseOver(minecraft, mouseX, mouseY)) {
                mouseY -= 8;
                mouseX += 4;

                int var11 = minecraft.textRenderer.getTextWidth(hoverText);
                this.fillGradient(mouseX - 3, mouseY - 3, mouseX + var11 + 3, mouseY + 8 + 3, -1073741824, -1073741824);
                minecraft.textRenderer.drawTextWithShadow(hoverText, mouseX, mouseY, -1);
            }
        }
    }

    public int incrementState(){
        state++;
        if(state > maxState)
            state = 0;
        return state;
    }
}
