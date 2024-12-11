package smart.gestion.des.equipments.admin.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class FirebaseMessagingService {

    private final FirebaseApp firebaseApp;

    public void sendNotificationToDevice(String token,String message) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Message fcmMessage = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle("Notification")
                                .setBody(message)
                                .build()
                )
                .build();

        String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
        log.info("sendNotificationToDevice response: {}", response);
    }


}