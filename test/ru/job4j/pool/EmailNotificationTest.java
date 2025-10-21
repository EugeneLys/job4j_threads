package ru.job4j.pool;

import org.junit.jupiter.api.Test;

class EmailNotificationTest {
    @Test
    void whenCorrect() {
        EmailNotification emailNotification = new EmailNotification();
        User user1 = new User();
        user1.username = "User1";
        user1.email = "email1";
        User user2 = new User();
        user2.username = "User2";
        user2.email = "email2";
        emailNotification.emailTo(user1);
        emailNotification.emailTo(user2);
    }

}