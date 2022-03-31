package demo.springboot.utils;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String ALERT_IDENTIFIER = "DEMO_APP_TICKET";

    public enum ALERT_SEVERITY {
        HIGH("high"),
        MEDIUM("medium"),
        LOW("low");

        @Getter
        private final String label;

        ALERT_SEVERITY(String label) {
            this.label = label;
        }
    }

    public enum Action {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        DELETE("DELETE");

        @Getter
        private final String label;

        Action(String label) {
            this.label = label;
        }
    }
}
