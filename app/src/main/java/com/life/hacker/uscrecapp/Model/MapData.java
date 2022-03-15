package com.life.hacker.uscrecapp.Model;

public class MapData {
    public MapData(Center[] centers) {
        this.centers = centers;
    }

    public Center[] getCenters() {
        return centers;
    }

    public void setCenters(Center[] centers) {
        this.centers = centers;
    }

    private Center[] centers;
}
