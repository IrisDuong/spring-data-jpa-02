package com.kps.jpa.inventory;

import java.util.List;

public record AllocateRequest(String orderId, List<OrderItem> items) {

}
