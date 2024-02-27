package com.theboys.to;

import com.theboys.data.entities.Test;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseTO {
    private Integer id;
    private String name;
    private String result;
    private LocalDate requestDate;
    private LocalDate eventDate;

    public TestResponseTO(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.result = test.getResult();
        this.requestDate = test.getRequestDate();
        this.eventDate = test.getEventDate();
    }
}
