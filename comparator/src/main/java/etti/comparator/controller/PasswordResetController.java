package etti.comparator.controller;

import etti.comparator.dto.UserDto;
import etti.comparator.model.User;
import etti.comparator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        logger.info("Request received for password reset for email: {}", email);

        User user = userService.findUserByEmail(email);
        if (user == null) {
            logger.error("No user found with email: {}", email);
            model.addAttribute("error", "Adresa de email nu este înregistrată");
            return "redirect:/login";
        }

        // Generarea unei noi parole
        String newPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(new UserDto(user.getEmail(), newPassword, "USER", user.getFullName(), user.isActive()));

        // Trimiterea email-ului cu noua parolă
        try {
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("from@example.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Resetare parolă");
            passwordResetEmail.setText("Noua ta parolă este: " + newPassword);

            mailSender.send(passwordResetEmail);
            logger.info("Password reset email sent to: {}", email);

        } catch (Exception e) {
            logger.error("Error sending email", e);
            model.addAttribute("error", "A apărut o eroare la trimiterea email-ului. Vă rugăm să încercați din nou.");
            return "redirect:/login";
        }

        model.addAttribute("message", "O nouă parolă a fost trimisă pe emailul tău");
        return "redirect:/login";
    }

    private String generateRandomPassword() {
        // Generare parolă random
        // Poți folosi diverse metode pentru a genera parole sigure
        return "newRandomPassword123";
    }
}
