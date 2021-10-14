package team.streetalk.dto.sms;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SmsResponse {
    private String statusCode;
    private String statusName;
    private String requestId;
    private Timestamp requestTime;
}
