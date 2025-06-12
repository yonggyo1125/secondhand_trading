package org.koreait.restaurant.services;

import lombok.RequiredArgsConstructor;
import org.koreait.restaurant.entities.Restaurant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
@RequiredArgsConstructor
public class RestaurantInfoService {

    /**
     * 주어진 좌표 근처의 cnt개 만큼의 식당 조회
     *
     * @param lat
     * @param lon
     * @param cnt
     * @return
     */
    public List<Restaurant> getNestest(double lat, double lon, int cnt) {

    }
}
