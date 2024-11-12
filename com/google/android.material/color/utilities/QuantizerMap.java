package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class QuantizerMap implements Quantizer {
    Map<Integer, Integer> colorToCount;

    @Override // com.google.android.material.color.utilities.Quantizer
    public QuantizerResult quantize(int[] iArr, int i) {
        HashMap hashMap = new HashMap();
        for (int i2 : iArr) {
            Integer num = (Integer) hashMap.get(Integer.valueOf(i2));
            int i3 = 1;
            if (num != null) {
                i3 = 1 + num.intValue();
            }
            hashMap.put(Integer.valueOf(i2), Integer.valueOf(i3));
        }
        this.colorToCount = hashMap;
        return new QuantizerResult(hashMap);
    }

    public Map<Integer, Integer> getColorToCount() {
        return this.colorToCount;
    }
}