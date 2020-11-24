package com.doraemon.wish.pack.util;

import com.doraemon.wish.pack.dao.model.Plugin;
import com.doraemon.wish.pack.dao.model.PluginVersion;

import java.util.Comparator;

public class PluginUtil {

    public static PluginVersion getLatestVersion(Plugin plugin) {
        plugin.getVersions().sort((version1, version2) -> {
            int value1= Integer.valueOf(version1.getVersion().replace(".", ""));
            int value2 = Integer.valueOf(version2.getVersion().replace(".", ""));
            return Integer.compare(value1, value2);
        });

        return plugin.getVersions().get(plugin.getVersions().size()-1);
    }

    public static PluginVersion getVersion(Plugin plugin, Long versionId) {
        for(PluginVersion item : plugin.getVersions()) {
            if(item.getId().equals(versionId)) {
                return item;
            }
        }

        return null;
    }
}
