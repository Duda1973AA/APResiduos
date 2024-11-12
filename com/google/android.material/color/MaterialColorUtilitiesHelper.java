package com.google.android.material.color;

import com.google.android.material.R;
import com.google.android.material.color.utilities.Scheme;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
final class MaterialColorUtilitiesHelper {
    private MaterialColorUtilitiesHelper() {
    }

    static Map<Integer, Integer> createColorResourcesIdsToColorValues(Scheme scheme) {
        HashMap hashMap = new HashMap();
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_primary), Integer.valueOf(scheme.getPrimary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_primary), Integer.valueOf(scheme.getOnPrimary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_primary_inverse), Integer.valueOf(scheme.getInversePrimary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_primary_container), Integer.valueOf(scheme.getPrimaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_primary_container), Integer.valueOf(scheme.getOnPrimaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_secondary), Integer.valueOf(scheme.getSecondary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_secondary), Integer.valueOf(scheme.getOnSecondary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_secondary_container), Integer.valueOf(scheme.getSecondaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_secondary_container), Integer.valueOf(scheme.getOnSecondaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_tertiary), Integer.valueOf(scheme.getTertiary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_tertiary), Integer.valueOf(scheme.getOnTertiary()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_tertiary_container), Integer.valueOf(scheme.getTertiaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_tertiary_container), Integer.valueOf(scheme.getOnTertiaryContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_background), Integer.valueOf(scheme.getBackground()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_background), Integer.valueOf(scheme.getOnBackground()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_surface), Integer.valueOf(scheme.getSurface()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_surface), Integer.valueOf(scheme.getOnSurface()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_surface_variant), Integer.valueOf(scheme.getSurfaceVariant()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_surface_variant), Integer.valueOf(scheme.getOnSurfaceVariant()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_surface_inverse), Integer.valueOf(scheme.getInverseSurface()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_surface_inverse), Integer.valueOf(scheme.getInverseOnSurface()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_surface_outline), Integer.valueOf(scheme.getOutline()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_error), Integer.valueOf(scheme.getError()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_error), Integer.valueOf(scheme.getOnError()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_error_container), Integer.valueOf(scheme.getErrorContainer()));
        hashMap.put(Integer.valueOf(R.color.material_personalized_color_on_error_container), Integer.valueOf(scheme.getOnErrorContainer()));
        return hashMap;
    }
}