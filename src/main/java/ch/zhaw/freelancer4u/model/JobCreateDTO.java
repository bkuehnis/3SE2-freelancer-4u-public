package ch.zhaw.freelancer4u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class JobCreateDTO {
    private String description;
    private JobType jobType;
    private Double earnings;
}
