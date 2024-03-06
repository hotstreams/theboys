package com.theboys.data.repos;

import com.theboys.data.entities.CultivationOrder;
import com.theboys.data.entities.Order;
import com.theboys.data.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroCultivationRepo extends JpaRepository<CultivationOrder, Integer> {
}
