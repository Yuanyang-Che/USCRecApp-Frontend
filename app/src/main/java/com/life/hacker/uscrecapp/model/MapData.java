package com.life.hacker.uscrecapp.model;

import java.util.List;

public class MapData {
    private MapData() { }

    private static final class InstanceHolder {
        private static final MapData instance = new MapData();
    }

    public static MapData getInstance() {
        return MapData.InstanceHolder.instance;
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

    public Center findCenterByName(String name) {
        if (centers != null && !centers.isEmpty()) {
            for (Center c : centers) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    private List<Center> centers;
}
