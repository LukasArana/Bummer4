package com.bummer4;

public class Season {
    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currentMatchday=" + currentMatchday +
                ", winner=" + winner +
                '}';
    }

    int id;
    String startDate;
    String endDate;
    int currentMatchday;
    Object winner;
}
