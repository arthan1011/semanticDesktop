package org.arthan.semantic.desktop.sample;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public enum FILE_TYPE {
    MUSIC("Музыка", "mp3"),
    UNKNOWN("Неизвестно");

    private String title;
    private String[] extensions;

    FILE_TYPE(String title, String... extensions) {
        this.title = title;
        this.extensions = extensions;
    }

    public String getTitle() {
        return title;
    }

    public static FILE_TYPE forExtension(String extension) {
        Preconditions.checkNotNull(Strings.emptyToNull(extension));

        FILE_TYPE[] values = values();
        for (FILE_TYPE type : values) {
            for (String ext : type.extensions) {
                if (ext.equals(extension)) {
                    return type;
                }
            }
        }
        return UNKNOWN;
    }
}
