package hu.unideb.inf.NeptunPro.auth;

import hu.unideb.inf.NeptunPro.model.Bag;
import hu.unideb.inf.NeptunPro.model.BagKey;
import hu.unideb.inf.NeptunPro.repository.BagRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class TokenSecretService {

    private final BagRepository bagRepository;

    @Value("${info.build.version:0.0.0}")
    private String currentVersion;

    @Getter
    private String tokenSecret;

    @Scheduled(cron = "0 0 2 * * *")
    public void generateTokenSecret() {
        generateNewAndSave();
    }

    @PostConstruct
    public void init() {
        var bag = bagRepository.findByBkey(BagKey.TOKEN_SECRET);
        if (bag != null) {
            handleExisting(bag.getBvalue());
        } else {
            generateNewAndSave();
        }
    }

    private void generateNewAndSave() {
        var random = new SecureRandom();
        var secretBytes = new byte[64];

        random.nextBytes(secretBytes);

        tokenSecret = Base64.getUrlEncoder().withoutPadding().encodeToString(secretBytes);
        save(tokenSecret);
    }

    private void handleExisting(final String valueInDb) {
        var arr = valueInDb.split(";");
        var version = arr[0];
        var secret = arr[1];

        if (!version.equals(currentVersion)) {
            generateNewAndSave();
        } else {
            this.tokenSecret = secret;
        }
    }

    private void save(final String secret) {
        var bag = bagRepository.findByBkey(BagKey.TOKEN_SECRET);
        if (bag == null) {
            bag = new Bag();
            bag.setBkey(BagKey.TOKEN_SECRET);
        }

        bag.setBvalue(currentVersion + ";" + secret);
        bagRepository.save(bag);
    }

}
