package ru.yandex.praktikum.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class TestData {

    private static final String BASE_URL =
            "https://qa-stellarburgers.education-services.ru";

    private String email;
    private String password;
    private String name;
    private String accessToken;

    public void prepareUserData() {
        email = "test_" + UUID.randomUUID() + "@mail.ru";
        password = "Password123";
        name = "TestUser";
    }

    public void createUser() {

        if (email == null) {
            prepareUserData();
        }

        RestAssured.baseURI = BASE_URL;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(
                        "{"
                                + "\"email\":\"" + email + "\","
                                + "\"password\":\"" + password + "\","
                                + "\"name\":\"" + name + "\""
                                + "}"
                )
                .when()
                .post("/api/auth/register");

        response.then().statusCode(200);

        accessToken = response.jsonPath().getString("accessToken");

        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException(
                    "Не удалось получить accessToken при создании пользователя"
            );
        }
    }

    public void deleteUser() {

        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }

        RestAssured.baseURI = BASE_URL;

        given()
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }

    public void deleteUserAfterUiRegistration() {

        if (email == null || password == null) {
            return;
        }

        RestAssured.baseURI = BASE_URL;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(
                        "{"
                                + "\"email\":\"" + email + "\","
                                + "\"password\":\"" + password + "\""
                                + "}"
                )
                .when()
                .post("/api/auth/login");

        if (response.statusCode() == 200) {

            String token =
                    response.jsonPath().getString("accessToken");

            if (token != null && !token.isEmpty()) {

                given()
                        .header("Authorization", token)
                        .when()
                        .delete("/api/auth/user");
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }
}