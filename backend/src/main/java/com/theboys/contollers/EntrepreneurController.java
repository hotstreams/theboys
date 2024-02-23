package com.theboys.contollers;

import com.theboys.security.PersistentUserManager;
import com.theboys.services.OrderService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.OrderResponseTO;
import com.theboys.to.UpdateOrderStatusTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/entrepreneurs")
public class EntrepreneurController {

    private final OrderService orderService;

    private final PersistentUserManager userService;

    @Autowired
    public EntrepreneurController(OrderService orderService, PersistentUserManager userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/rents")
    public List<OrderResponseTO> getOrders(Principal principal) {
        int entrepreneurId = userService.loadUserIdByUsername(principal.getName());
        return orderService.getOrders(entrepreneurId);
    }

    @PatchMapping("/{entrepreneursId}/rents/{rentId}")
    public CustomHttpResponse updateOrderStatus(@PathVariable("entrepreneursId") Integer entrepreneursId,
                                                @PathVariable("rentId") Integer rentId,
                                                @Valid @RequestBody UpdateOrderStatusTO orderStatusTO) {
        orderService.updateOrderStatus(orderStatusTO.getStatus(), rentId);
        return new CustomHttpResponse();
    }

}
