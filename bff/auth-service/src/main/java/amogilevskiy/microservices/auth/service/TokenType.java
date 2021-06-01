package amogilevskiy.microservices.auth.service;

import lombok.Getter;

@Getter
public enum TokenType {

    ACCESS("access"),
    REFRESH("refresh");

    private final String code;

    private TokenType(String code) {
        this.code = code;
    }

}
