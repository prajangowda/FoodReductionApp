package com.prajan.FoodReduction.userservice;

import com.prajan.FoodReduction.model.Donation;
import com.prajan.FoodReduction.model.Volunteer;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Ngo Informative Email
    public void sendDonationEmail(String to, Donation donation) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("New Donation Available Nearby");

        message.setText(
                "Food Donation Available\n\n" +
                        "Food Name: " + donation.getFoodName() + "\n" +
                        "Food Type: " + donation.getFoodType() + "\n" +
                        "Quantity: " + donation.getQuantity() + "\n" +
                        "Address: " + donation.getDonor().getAddress() + "\n" +
                        "Expiry Time: " + donation.getExpiryTime()
        );

        mailSender.send(message);
    }

    // Volunteer Email Sender Method
    public void sendVolunteerAssignmentEmail(
            String to,
            Donation donation,
            Volunteer volunteer
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("New Pickup Assigned");

        message.setText(
                "Hello " + volunteer.getName() + ",\n\n" +
                        "A food pickup has been assigned to you.\n\n" +
                        "Food Name: " + donation.getFoodName() + "\n" +
                        "Food Type: " + donation.getFoodType() + "\n" +
                        "Quantity: " + donation.getQuantity() + "\n" +
                        "Pickup Address: " + donation.getDonor().getAddress() + "\n" +
                        "Expiry Time: " + donation.getExpiryTime() + "\n\n" +
                        "Please collect and deliver the food safely."
        );

        mailSender.send(message);
    }
}