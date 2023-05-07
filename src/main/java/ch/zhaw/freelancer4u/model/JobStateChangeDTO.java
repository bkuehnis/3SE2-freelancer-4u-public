package ch.zhaw.freelancer4u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobStateChangeDTO {
    private String jobId;
    private String freelancerEmail;
}
