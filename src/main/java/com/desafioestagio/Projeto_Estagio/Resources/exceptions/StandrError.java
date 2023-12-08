package com.desafioestagio.Projeto_Estagio.Resources.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.Instant;

public class StandrError implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "GMT")
    private Instant timesTamp;
    private int status;
    private String message;
    private String path;



    public StandrError(Instant timesTamp, int status, String message, String path, String requestURI) {
        this.timesTamp = timesTamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public Instant getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(Instant timesTamp) {
        this.timesTamp = timesTamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
