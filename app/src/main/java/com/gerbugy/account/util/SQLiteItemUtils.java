package com.gerbugy.account.util;

import java.util.List;

public final class SQLiteItemUtils {

    private static final int NO_POSITION = -1;

    private SQLiteItemUtils() {

    }

    public static int findPosition(List<SQLiteItem> items, long _id) {
        return findPosition(items, "_id", _id);
    }

    public static int findPosition(List<SQLiteItem> items, String key, long value) {
        for (int i = 0; i < items.size(); i++) {
            SQLiteItem item = items.get(i);
            if (item.getLong(key) == value) {
                return i;
            }
        }
        return NO_POSITION;
    }

    public static int findPosition(List<SQLiteItem> items, String key, int value) {
        for (int i = 0; i < items.size(); i++) {
            SQLiteItem item = items.get(i);
            if (item.getInt(key) == value) {
                return i;
            }
        }
        return NO_POSITION;
    }
}