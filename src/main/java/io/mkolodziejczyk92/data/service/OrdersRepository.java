package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrdersRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
