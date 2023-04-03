package id.co.mii.serverapp.registration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.co.mii.serverapp.appuser.AppUser;
import id.co.mii.serverapp.appuser.AppUserRole;
import id.co.mii.serverapp.appuser.AppUserService;
import id.co.mii.serverapp.email.EmailRequest;
import id.co.mii.serverapp.email.EmailService;
import id.co.mii.serverapp.registration.token.ConfirmService;
import id.co.mii.serverapp.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistService {

    private final AppUserService aUserService;
    private final EmailValidator ev;
    private final ConfirmService cService;
    private final EmailService eS;
    private EmailRequest eRq;

    public String register(RegistRequest r) {
        boolean isvalid = ev.test(r.getEmail());
        if (!isvalid) {
            throw new IllegalStateException("email not valid");
        }
        String token = aUserService.signUp(new AppUser(
                r.getFirstName(),
                r.getLastName(),
                r.getEmail(),
                r.getPassw(),
                AppUserRole.USER));

        // eRq.setLink("http://localhost:9000/v1/registration/confirm?token=" + token);
        // eRq.setProps({link = "http://localhost:9000/v1/registration/confirm?token=" +
        // token});
        eS.send(r.getEmail(), eRq);
        return token;
    }

    @Transactional
    public String confirmtoken(String token) {
        ConfirmationToken conftoken = cService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (conftoken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = conftoken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        cService.setConfirmedAt(token);
        aUserService.enableUser(conftoken.getAUser().getEmail());
        return "confirmed";
    }
}
