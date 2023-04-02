package id.co.mii.serverapp.registration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.co.mii.serverapp.appuser.AppUser;
import id.co.mii.serverapp.appuser.AppUserRole;
import id.co.mii.serverapp.appuser.AppUserService;
import id.co.mii.serverapp.email.EmailSender;
import id.co.mii.serverapp.registration.token.ConfirmService;
import id.co.mii.serverapp.registration.token.ConfirmationToken;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistService {

    private final AppUserService aUserService;
    private final EmailValidator ev;
    private final ConfirmService cService;
    private final EmailSender ems;

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

        // ems.send(r.getEmail(), msg);
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
