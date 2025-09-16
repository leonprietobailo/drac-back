package com.leonbros.drac.repository;

import com.leonbros.drac.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query("select coalesce(max(i.invoiceNumber), 0) from Order i where i.invoiceYear = :year")
  int findMaxNumberByYear(@Param("year") int year);
}
