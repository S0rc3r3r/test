package com.muso.enums;

public enum UserType {
    NORMAL(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjg1LCJjcmVhdGVkIjoxNDkxMzc2Mjc3NTg2LCJleHAiOjE1MjI5MTYyNzd9.Hrjq0SqgyMWwhXuTNVHxecsUs20kOwUKPJd_kvvl3SewVyKh8hyC0eRX5ubrE48gCAiPbDVmpre5ccXu3U-96A"),
    MARKET_ANALYTICS(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEsImNyZWF0ZWQiOjE1MDM0MDMzMDUyNzgsInNlcnZpY2VzIjpbeyJleHBpcnlEYXRlIjoxNTAzNDAzMzA0OTg2LCJzZXJ2aWNlVHlwZSI6Ik1BUktFVF9BTkFMWVRJQ1MifV0sImV4cCI6MzA4MDQwMzMwNX0.JhIrxyIrlp4PoG4QPuth2uHj_6ZZ-0bD7vt6tPVyBlp1c0OGRND42lWhgJjhusim833pSg8yXLcrFb8mnPm-rg"),
    ADMIN(
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjg4MjgsImNoaWxkVXNlcnMiOlt7InVzZXJJZCI6OTAxMSwiZnVsbE5hbWUiOiJDaGlsZCB1c2VyIGZ1bGwgbmFtZSBvbmUifSx7InVzZXJJZCI6OTAxMywiZnVsbE5hbWUiOiJDaGlsZCB1c2VyIGZ1bGwgbmFtZSB0d28ifV0sImNyZWF0ZWQiOjE0OTE5MjA2NjY1MjQsImV4cCI6MTUyMzQ2MDY2N30.8DXXX8L0s07y02Dto66rQeAzxOKxi_nUnqFju6lkpMBgmwUwTlWm9qjqss4V4lqGUkShzhGT0yx0h98nfZlW_g");

    private String jwt;

    public String getJwt() {
        return this.jwt;
    }

    private UserType(String jwt) {
        this.jwt = jwt;
    }

}
