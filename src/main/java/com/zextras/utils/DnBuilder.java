package com.zextras.utils;

import com.zextras.persistence.annotations.Table;
import com.zextras.persistence.mapping.SlcwEntry;

public class DnBuilder {
    // todo
    public static<T> void createDn(SlcwEntry entry, T object) {
        String dn =
                entry.getId().getFieldName()
                        + "="
                        + entry.getId().getFiledValue()
                        + ","
                        + object.getClass().getAnnotation(Table.class).property()
                        + "="
                        + object.getClass().getAnnotation(Table.class).name()
                        + ","
                        + entry.getBaseDn();

        entry.setDn(dn);
    }

    public static <T> void createDnForGet(SlcwEntry entry, T object) {
        String dn =
                object.getClass().getAnnotation(Table.class).property()
                        + "="
                        + object.getClass().getAnnotation(Table.class).name()
                        + ","
                        + entry.getBaseDn();

        entry.setDn(dn);
    }
}
