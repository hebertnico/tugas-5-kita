package id.co.mii.serverapp.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmService {
    private final ConfirmRepo conRepo;

    public void saveToken(ConfirmationToken token) {
        conRepo.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return conRepo.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return conRepo.updateconfirmed(token, LocalDateTime.now());
    }
}
