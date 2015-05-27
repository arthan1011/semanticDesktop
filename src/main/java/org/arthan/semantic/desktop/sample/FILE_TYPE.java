package org.arthan.semantic.desktop.sample;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public enum FILE_TYPE {
    MUSIC("http://artur.lazy-magister.org/types/music", "Музыка", "mp3", "flac"),
    DOCUMENT("http://artur.lazy-magister.org/types/document", "Документ", "doc", "docx", "txt", "pdf", "rdf"),
    IMAGE("http://artur.lazy-magister.org/types/picture", "Изображение", "png", "jpg", "gif"),
    UNKNOWN("http://artur.lazy-magister.org/types/unknown", "Неизвестно");

    private String uri;
    private String title;
    private String[] extensions;

    FILE_TYPE(String uri, String title, String... extensions) {
        this.uri = uri;
        this.title = title;
        this.extensions = extensions;
    }

    public String getUri() {
        return uri;
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
