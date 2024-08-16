package dev.aresiel.ae2extrachannelmodes.mixin;

import dev.aresiel.ae2extrachannelmodes.IntegerToWord;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(appeng.api.networking.pathing.ChannelMode.class)
public class AddExtraChannelModes {
    @Mutable
    @Shadow
    @Final
    private static appeng.api.networking.pathing.ChannelMode[] $VALUES;

    @Invoker(value = "<init>")
    private static appeng.api.networking.pathing.ChannelMode create(String name, int ordinal, int adHocNetworkChannels, int cableCapacityFactor){
        throw new IllegalStateException();
    }

    static {
        //appeng.api.networking.pathing.ChannelMode NONE = create("NONE", $VALUES.length, 0, 0);

        int count = 10_000;

        appeng.api.networking.pathing.ChannelMode[] newValues = new appeng.api.networking.pathing.ChannelMode[count+1-5];
        appeng.api.networking.pathing.ChannelMode[] newWordValues = new appeng.api.networking.pathing.ChannelMode[count+1];

        appeng.api.networking.pathing.ChannelMode x0 = create("x0", $VALUES.length, 0, 0);
        appeng.api.networking.pathing.ChannelMode x1 = create("x1", $VALUES.length, 8, 1);

        for(int i = 5; i <= count && 16 * i < Integer.MAX_VALUE; i++){
            if(i == 2 || i == 3 || i == 4)
                continue;

            newValues[i-5] = create("x" + i, $VALUES.length, 8 * i, 1 * i);
        }

        for(int i = 0; i <= count && 16 * i < Integer.MAX_VALUE; i++){
            newWordValues[i] = create("times" + IntegerToWord.numberToWords(i), $VALUES.length, 8 * i, 1 * i);
        }

        $VALUES = ArrayUtils.add($VALUES, x0);
        $VALUES = ArrayUtils.add($VALUES, x1);
        $VALUES = ArrayUtils.addAll($VALUES, newValues);
        $VALUES = ArrayUtils.addAll($VALUES, newWordValues);
    }
}