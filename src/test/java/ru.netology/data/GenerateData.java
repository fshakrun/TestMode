package ru.netology.data;

import com.github.javafaker.Faker;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class GenerateData {
    private static Faker faker = new Faker(new Locale("ru"));

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static class Registration {


        public static User generateUser(String status) {
            return new User(generateLogin(), generatePassword(), status);
        }

        public static void sendRequest(User user) {
            given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static User generateRegisteredUser(String status) {
            User user = generateUser(status);
            sendRequest(user);
            return user;

        }
    }
}
