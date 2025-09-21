package com.kps.jpa.inventory;

public enum ReservationStatus {
	RESERVED, // giữ chỗ thành công
	ALLOCATED, // đã xuất kho/ghi nhận thành công
	CANCELLED,
	FAILED
}
