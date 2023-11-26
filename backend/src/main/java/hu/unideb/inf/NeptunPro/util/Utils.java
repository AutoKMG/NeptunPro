package hu.unideb.inf.NeptunPro.util;


import hu.unideb.inf.NeptunPro.domain.model.user.User;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.tinylog.Logger;

import java.security.Principal;

@NoArgsConstructor
public final class Utils {

    // Http Verbs
    public static final String HTTP_CREATE = "CREATE";
    public static final String HTTP_UPDATE  = "UPDATE";

    public static short INITIAL_VERSION = 0;

    public static String getCreator(User user) {
        return String.format("%s %s - [%s]",
                user.getFirstname(),
                user.getLastname(),
                user.getRole());
    }

    public static void logApi(
            final HttpMethod httpMethod,
            final Principal principal,
            final String methodDetails
    ) {
        Logger.info(
                String.format("(%s) (%s) -> %s",
                httpMethod,
                principal == null ? "null" : principal.getName(),
                methodDetails)
        );
    }

}
