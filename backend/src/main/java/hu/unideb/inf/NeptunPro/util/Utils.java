package hu.unideb.inf.NeptunPro.util;


import hu.unideb.inf.NeptunPro.domain.model.user.User;
import lombok.NoArgsConstructor;

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

}
