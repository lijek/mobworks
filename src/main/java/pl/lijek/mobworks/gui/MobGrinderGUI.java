package pl.lijek.mobworks.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import pl.lijek.mobworks.Mobworks;
import pl.lijek.mobworks.gui.custom.ButtonWithImage;
import pl.lijek.mobworks.gui.custom.MultipleStatusButtonWithImage;
import pl.lijek.mobworks.gui.custom.NumberInput;
import pl.lijek.mobworks.tileentity.TileEntityMobGrinder;

@Environment(EnvType.CLIENT)
public class MobGrinderGUI extends ContainerBase {
    private TileEntityMobGrinder entity;
    private NumberInput rangeInput;
    private int grinderDamage = 1;

    public MobGrinderGUI(PlayerInventory inventory, TileEntityMobGrinder mobGrinder) {
        super(new MobGrinderContainer(inventory, mobGrinder));
        entity = mobGrinder;
    }

    @Override
    public void init() {
        super.init();
        int containerX = (this.width - this.containerWidth) / 2;
        int containerY = (this.height - this.containerHeight) / 2;

        ButtonWithImage toggleShowingWorkingArea = new ButtonWithImage(0, containerX + 149,  containerY + 37, 0, 0, Mobworks.translateTooltip("toggleShowingWorkingArea"));
        toggleShowingWorkingArea.setState(entity.isRenderingWorkingRange());
        buttons.add(toggleShowingWorkingArea);

        MultipleStatusButtonWithImage changeRedstoneMode = new MultipleStatusButtonWithImage(1, containerX + 127, containerY + 37, 16, 0, Mobworks.translateTooltip("redstoneIgnored"), 2);
        changeRedstoneMode.state = entity.getRedstoneMode();
        buttons.add(changeRedstoneMode);

        this.rangeInput = new NumberInput(containerX, containerY, 7, 37, 1, 9, Mobworks.translateText("range"), buttons, textManager);
        this.rangeInput.inputValue = entity.getRange();
        this.rangeInput.inputValue = 2;
        this.rangeInput.setMaxValue(entity.getMaxRange());

        grinderDamage = entity.getDamage();
    }

    @Override
    protected void buttonClicked(Button button) {
        if(button.id == 0){
            entity.setRenderingWorkingRange(((ButtonWithImage) button).changeState());
        }else if(button.id == 1){
            MultipleStatusButtonWithImage changeRedstoneMode = ((MultipleStatusButtonWithImage) button);
            entity.setRedstoneMode(changeRedstoneMode.incrementState());
            switch (changeRedstoneMode.state){
                case 0:
                    changeRedstoneMode.hoverText = Mobworks.translateTooltip("redstoneIgnored");
                    break;
                case 1:
                    changeRedstoneMode.hoverText = Mobworks.translateTooltip("redstoneRequired");
                    break;
                case 2:
                    changeRedstoneMode.hoverText = Mobworks.translateTooltip("redstoneReversed");
                    break;
            }
        }
        rangeInput.buttonClicked(button);
        entity.setRange(rangeInput.inputValue);
    }

    @Override
    protected void renderForeground() {
        super.renderForeground();
        rangeInput.render();
        textManager.drawText(Mobworks.translateText("mobGrinder"), 60, 6, 0x404040);
        textManager.drawText(Mobworks.translateText("inventory"), 8, 72, 0x404040);
        textManager.drawText(Mobworks.translateText("currentDamage") + grinderDamage, 8, 62, 0x404040);
    }

    @Override
    protected void renderContainerBackground(float f) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/mobworks/textures/gui/mobGrinderGui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int var3 = (this.width - this.containerWidth) / 2;
        int var4 = (this.height - this.containerHeight) / 2;
        this.blit(var3, var4, 0, 0, this.containerWidth, this.containerHeight);
    }

    @Override
    public void tick() {
        super.tick();
        rangeInput.setMaxValue(entity.getMaxRange());
        grinderDamage = entity.getDamage();
    }
}
