package com.theboys.to;

import com.theboys.data.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseTO {
    private Integer orderId;
    private Integer enterpreneurId;
    private String name;
    private String heroDescription;
    private String requestDescription;
    private String startDate;
    private String endDate;
    private String date;
    private OrderStatus status;
}
