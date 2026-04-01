package ivents.ivents_ui_support.config.timezone;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

public class TimezoneConfig {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
