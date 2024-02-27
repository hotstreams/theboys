package com.theboys.to;

import com.theboys.data.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusTO {
    @NotNull
    private OrderStatus status;
}
