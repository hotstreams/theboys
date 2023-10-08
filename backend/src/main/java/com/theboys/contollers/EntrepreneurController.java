package com.theboys.contollers;

import com.theboys.services.OrderService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.OrderResponseTO;
import com.theboys.to.UpdateOrderStatusTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrepreneurs")
public class EntrepreneurController {

    private final OrderService orderService;

    @Autowired
    public EntrepreneurController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/rents")
    public List<OrderResponseTO> getOrders() {
        return orderService.getOrders();
    }

    @PatchMapping("/{entrepreneursId}/rents/{rentId}")
    public CustomHttpResponse updateOrderStatus(@PathVariable("entrepreneursId") Integer entrepreneursId,
                                                @PathVariable("rentId") Integer rentId,
                                                @Valid @RequestBody UpdateOrderStatusTO orderStatusTO) {
        orderService.updateOrderStatus(orderStatusTO.getStatus(), rentId);
        return new CustomHttpResponse();
    }

}
