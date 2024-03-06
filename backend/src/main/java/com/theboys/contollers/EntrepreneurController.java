package com.theboys.contollers;

import com.theboys.security.PersistentUserManager;
import com.theboys.services.OrderService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.OrderResponseTO;
import com.theboys.to.UpdateOrderStatusTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/entrepreneurs")
public class EntrepreneurController {

    private final OrderService orderService;

    private final PersistentUserManager userService;

    @Autowired
    public EntrepreneurController(OrderService orderService, PersistentUserManager userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping(path = "/rents")
    public List<OrderResponseTO> getOrders(Principal principal) {
        return orderService.getOrders();
    }

    @GetMapping(path = "/{entrepreneursId}/rents")
    public List<OrderResponseTO> getOrders(@PathVariable("entrepreneursId") @NotNull Integer entrepreneursId) {
        return orderService.getOrdersByCustomerId(entrepreneursId);
    }

    @PatchMapping(path = "/{entrepreneursId}/rents/{rentId}")
    public CustomHttpResponse updateOrderStatus(@PathVariable("entrepreneursId") Integer entrepreneursId,
                                                @PathVariable("rentId") Integer rentId,
                                                @RequestBody UpdateOrderStatusTO orderStatusTO) {
        orderService.updateOrderStatus(orderStatusTO.getStatus(), rentId);
        return new CustomHttpResponse();
    }

}
