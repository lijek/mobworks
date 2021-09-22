package pl.lijek.mobworks.gui;

import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import pl.lijek.mobworks.Mobworks;
import pl.lijek.mobworks.gui.custom.ButtonWithImage;
import pl.lijek.mobworks.gui.custom.MultipleStatusButtonWithImage;
import pl.lijek.mobworks.gui.custom.NumberInput;
import pl.lijek.mobworks.tileentity.TileEntityVacuumMachine;

public class VacuumMachineGUI extends ContainerBase {
    private TileEntityVacuumMachine entity;
    private NumberInput offsetX, offsetY, offsetZ, workingSize;

    public VacuumMachineGUI(PlayerInventory inventory, TileEntityVacuumMachine vacuumMachine) {
        super(new VacuumMachineContainer(inventory, vacuumMachine));
        entity = vacuumMachine;
        containerWidth = 219;
        containerHeight = 181;
    }

    @Override
    public void init() {
        super.init();
        int containerX = (this.width - this.containerWidth) / 2;
        int containerY = (this.height - this.containerHeight) / 2;

        ButtonWithImage toggleShowingWorkingArea = new ButtonWithImage(0, containerX + 104,  containerY + 19, 0, 0, Mobworks.translateTooltip("toggleShowingWorkingArea"));
        toggleShowingWorkingArea.setState(entity.isRenderingWorkingRange());
        buttons.add(toggleShowingWorkingArea);

        MultipleStatusButtonWithImage changeRedstoneMode = new MultipleStatusButtonWithImage(1, containerX + 126, containerY + 19, 16, 0, Mobworks.translateTooltip("redstoneIgnored"), 2);
        changeRedstoneMode.state = entity.getRedstoneMode();
        buttons.add(changeRedstoneMode);

        offsetX = new NumberInput(containerX, containerY, 8, 42, -entity.getMaxOffset(), entity.getMaxOffset(), Mobworks.translateText("offsetX"), buttons, textManager);
        offsetX.inputValue = entity.getOffsetX();
        offsetX.onUpdate = entity::setOffsetX;

        offsetY = new NumberInput(containerX, containerY, offsetX.getX() + offsetX.getWidth() + 2 + 8, 42, -entity.getMaxOffset(), entity.getMaxOffset(), Mobworks.translateText("offsetY"), buttons, textManager);
        offsetY.inputValue = entity.getOffsetY();
        offsetY.onUpdate = entity::setOffsetY;

        offsetZ = new NumberInput(containerX, containerY, 8, 64, -entity.getMaxOffset(), entity.getMaxOffset(), Mobworks.translateText("offsetZ"), buttons, textManager);
        offsetZ.inputValue = entity.getOffsetZ();
        offsetZ.onUpdate = entity::setOffsetZ;

        workingSize = new NumberInput(containerX, containerY, offsetZ.getX() + offsetZ.getWidth() + 2 + 8, 64, 0, entity.getMaxWorkingSize(), Mobworks.translateText("range"), buttons, textManager);
        workingSize.inputValue = entity.getWorkingSize();
        workingSize.onUpdate = entity::setWorkingSize;
        workingSize.setCannotBeZero(true);
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

        offsetX.buttonClicked(button);
        offsetY.buttonClicked(button);
        offsetZ.buttonClicked(button);
        workingSize.buttonClicked(button);
        
        offsetX.render();
        offsetY.render();
        offsetZ.render();
        workingSize.render();
    }

    @Override
    protected void renderForeground() {
        super.renderForeground();
        textManager.drawText(Mobworks.translateText("vacuumMachine"), (containerWidth / 2) - (textManager.getTextWidth(Mobworks.translateText("vacuumMachine")) / 2), 6, 0x404040);
        textManager.drawText(Mobworks.translateText("inventory"), 8, 89, 0x404040);

        offsetX.render();
        offsetY.render();
        offsetZ.render();
        workingSize.render();
    }

    @Override
    protected void renderContainerBackground(float f) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/mobworks/textures/gui/vacuumMachine.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int var3 = (this.width - this.containerWidth) / 2;
        int var4 = (this.height - this.containerHeight) / 2;
        this.blit(var3, var4, 0, 0, this.containerWidth, this.containerHeight);
    }
}
