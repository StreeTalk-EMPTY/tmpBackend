package team.streetalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class MessageOnly {
    private Integer status;
    private Boolean success;
    private String message;
}
