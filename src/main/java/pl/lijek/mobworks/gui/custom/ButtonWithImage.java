package pl.lijek.mobworks.gui.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class ButtonWithImage extends Button {
    int u, v;
    String hoverText;
    boolean state = false;

    public ButtonWithImage(int id, int x, int y, int u, int v, String hoverText) {
        super(id, x, y, 20, 20, "");
        this.u = u;
        this.v = v;
        this.hoverText = hoverText;
    }

    @Override
    public void render(Minecraft minecraft, int mouseX, int mouseY) {
        super.render(minecraft, mouseX, mouseY);
        if(visible) {
            String texturePath = "/assets/mobworks/textures/buttons.png";
            GL11.glBindTexture(3553, minecraft.textureManager.getTextureId(texturePath));

            if (state)
                blit(x + 2, y + 2, u, v + 16, width - 2, height - 2);
            else
                blit(x + 2, y + 2, u, v, width - 2, height - 2);

            if (isMouseOver(minecraft, mouseX, mouseY)) {
                mouseY -= 8;
                mouseX += 4;

                int var11 = minecraft.textRenderer.getTextWidth(hoverText);
                this.fillGradient(mouseX - 3, mouseY - 3, mouseX + var11 + 3, mouseY + 8 + 3, -1073741824, -1073741824);
                minecraft.textRenderer.drawTextWithShadow(hoverText, mouseX, mouseY, -1);
            }
        }
    }

    public boolean changeState(){
        return state = !state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
