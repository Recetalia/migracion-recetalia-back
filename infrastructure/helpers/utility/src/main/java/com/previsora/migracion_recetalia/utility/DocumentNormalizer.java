package com.previsora.migracion_recetalia.utility;

import java.util.regex.Pattern;

/**
 * Normalizes patient document JSON strings of the form
 * {@code {"number":"...","type":"..."}} by trimming leading/trailing
 * whitespace inside each quoted value.
 *
 * <p>Prod data contains artifacts like {@code "        782642-3"} (padding
 * from a fixed-width source). This class ensures dev stores clean values
 * and that lookups from prescription migration use the same canonical form.
 */
public final class DocumentNormalizer {

    private static final Pattern JSON_STRING_FIELD =
            Pattern.compile("(\"(?:number|type)\"\\s*:\\s*\")\\s*([^\"]*?)\\s*(\")");

    private DocumentNormalizer() {
    }

    public static String normalize(String documentJson) {
        if (documentJson == null) {
            return null;
        }
        return JSON_STRING_FIELD.matcher(documentJson).replaceAll("$1$2$3");
    }
}
