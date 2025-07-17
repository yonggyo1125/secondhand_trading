package org.koreait.trend.repositories;

import org.koreait.trend.entities.QTrend;
import org.koreait.trend.entities.Trend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Order.desc;

public interface TrendRepository extends JpaRepository<Trend, Long>, QuerydslPredicateExecutor<Trend> {

    default Optional<Trend> getLatest(String category) {
        QTrend trend = QTrend.trend;
        Pageable pageable = PageRequest.of(0, 1, Sort.by(desc("createdAt")));
        Page<Trend> data = findAll(trend.category.eq(category), pageable);
        List<Trend> items = data.getContent();
        return Optional.ofNullable(items.getFirst());
    }

    //@Query("SELECT * FROM TREND WHERE category=:category AND createdAt BETWEEN :sDate AND :eDate ORDER BY createdAt DESC LIMIT 1")
    default Optional<Trend> get(String category, LocalDateTime sDate, LocalDateTime eDate) {

        return null;
    }

    @Query("SELECT * FROM TREND WHERE category=:category AND createdAt BETWEEN :sDate AND :eDate")
    List<Trend> getList(@Param("category") String category, @Param("sDate") LocalDateTime sDate, @Param("eDate") LocalDateTime eDate);
}