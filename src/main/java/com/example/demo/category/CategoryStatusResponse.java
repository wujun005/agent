package com.example.demo.category;

public record CategoryStatusResponse(Integer code, String msg) {
    public static CategoryStatusResponse sucess() { return new CategoryStatusResponse(200, "sucess");}
}
