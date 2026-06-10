package com.tyrservices.agronetb.Configs;

public class JwtUserDetails {

    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final String tipo;

    public JwtUserDetails(Long userId, String userName, String userEmail, String tipo) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.tipo = tipo;
    }

    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public String getTipo() { return tipo; }
}
