package pl.lijek.mobworks.gui.custom;

import lombok.Getter;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.TextRenderer;

import java.util.List;
import java.util.function.Consumer;

public class NumberInput extends DrawableHelper {
    private String text;
    private Button plus, minus;
    private TextRenderer font;
    @Getter
    private int x, y;
    private int containerX;
    private int max, min;
    private boolean cannotBeZero = false;

    public int inputValue = 0,
                increment = 1;
    public Consumer<Integer> onUpdate = (x) -> {};

    public NumberInput(int containerX, int containerY, int x, int y, int min, int max, String text, List<Button> buttons, TextRenderer font){
        this.x = x;
        this.y = y;
        this.containerX = containerX;
        this.min = min;
        this.max = max;
        this.text = text;
        this.font = font;

        this.plus = new Button(buttons.size(), x + containerX, y + containerY, 20, 20, "+");
        buttons.add(plus);
        this.minus = new Button(buttons.size(), x + containerX + 20 + 2 + font.getTextWidth(text + inputValue) + 2, y + containerY, 20, 20, "-");
        buttons.add(minus);
    }

    public void buttonClicked(Button button){
        if(button.id == plus.id){
            inputValue += increment;
        }else if(button.id == minus.id){
            inputValue -= increment;
        }

        if(inputValue > max)
            inputValue = min;
        else if(inputValue < min)
            inputValue = max;

        if(cannotBeZero && inputValue == 0)
            inputValue = 1;

        minus.x = x + containerX + 20 + 2 + font.getTextWidth(text + inputValue) + 2;

        onUpdate.accept(inputValue);
    }

    public void render(){
        font.drawText(text + inputValue, x + 20 + 2, y + 6, 0x404040);
    }

    public void setMaxValue(int max){
        this.max = max;
        if(inputValue > max)
            inputValue = max;
    }

    public int getWidth(){
        return 20 + 2 + font.getTextWidth(text + inputValue) + 2 + 20;
    }

    public void setCannotBeZero(boolean cannotBeZero) {
        this.cannotBeZero = cannotBeZero;
    }
}
