package com.mikedeejay2.mikedeejay2lib.text.language;

import java.util.HashMap;
import java.util.Map;

public final class TranslationManager {
    private static final Map<String, Map<String, String>> TRANSLATIONS = new HashMap<>();

    private TranslationManager() {
        throw new UnsupportedOperationException("TranslationManager cannot be instantiated");
    }


}
