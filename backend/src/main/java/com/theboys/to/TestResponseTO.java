package com.theboys.to;

import com.theboys.data.entities.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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
