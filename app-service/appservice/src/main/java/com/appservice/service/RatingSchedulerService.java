package com.appservice.service;

import com.appservice.repository.FeedBackRepository;
import com.appservice.repository.RestaurantRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingSchedulerService {

    private final RestaurantRepository restaurantRepository;

    private final FeedBackRepository feedBackRepository;

    public RatingSchedulerService(final RestaurantRepository restaurantRepository, final FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata") // second, minute, hour, day, month, day-of-week  12:00 AM India time
    public void calculateAndUpdateRatings() {

        List<String> restaurantIds = restaurantRepository.findAllIds();

        for (String restaurantId : restaurantIds) {
            Double avgRating = feedBackRepository.findAverageRatingByRestaurantId(restaurantId);

            if (avgRating == null) {
                avgRating = 0.0;
            }
            double roundRating = Math.round(avgRating * 10.0) / 10.0;

            Double avgAppRating = feedBackRepository.findAverageAppRatingByRestaurantId(restaurantId);
            if (avgAppRating == null) {
                avgAppRating = 0.0;
            }
            double appRating = Math.round(avgAppRating * 10.0) / 10.0;

            restaurantRepository.updateAverageRatings(restaurantId, roundRating, appRating);
        }

        System.out.println("Ratings calculated and updated successfully.");
    }
}
