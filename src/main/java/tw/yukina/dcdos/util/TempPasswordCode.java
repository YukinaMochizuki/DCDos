package tw.yukina.dcdos.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TempPasswordCode {
    private final String tempPasswordCode;
    private final LocalDateTime createdDateTime = LocalDateTime.now();
}
