package com.example.order.scheduler;

import com.example.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PendingOrderPromoter {
    private static final Logger logger = LoggerFactory.getLogger(PendingOrderPromoter.class);
    private final OrderService orderService;

    public PendingOrderPromoter(OrderService orderService) {
        this.orderService = orderService;
    }

    // runs every 5 minutes (300000 ms)
    @Scheduled(fixedRateString = "${order.promote.rate:300000}")
    public void promote() {
        logger.info("Running scheduled promotion of PENDING orders to PROCESSING");
        orderService.promotePendingToProcessing();
    }
}
