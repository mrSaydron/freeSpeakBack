package ru.mrak.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.ZonedDateTime;

@Component
@RequestScope
@Data
@Accessors(chain = true)
public class UserContext {
    private ZonedDateTime localTime;
}
