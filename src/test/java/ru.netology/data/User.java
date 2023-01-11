package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class User {
    String login;
    String password;/**/
    String status;

}