package de.nehlen.gameapi.util;

import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

public class ServerID {

    @Getter private String id;

    public ServerID(String id) {
        this.id = id;
    }

    public static String generateRandomServerID() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
