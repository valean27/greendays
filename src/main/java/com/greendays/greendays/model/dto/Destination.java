package com.greendays.greendays.model.dto;

public enum Destination {
    TRANSFER_STATION("Statia de Transfer Blaj"),
    CMID_GALDA("CMID Galda de Jos");

    private final String destinationName;

    Destination(String s) {
        this.destinationName = s;
    }

    public String getDestinationName() {
        return destinationName;
    }
}
