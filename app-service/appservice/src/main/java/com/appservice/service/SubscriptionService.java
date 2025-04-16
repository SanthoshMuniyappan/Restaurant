package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.RestaurantRepository;
import com.appservice.repository.SubscriptionPlanRepository;
import com.appservice.repository.SubscriptionRepository;
import com.appservice.util.AuthenticationService;
import com.appservice.util.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ors.common.model.Restaurant;
import ors.common.model.Subscription;
import ors.common.model.SubscriptionPlan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final RestaurantRepository restaurantRepository;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final AuthenticationService authenticationService;

    public SubscriptionService(final SubscriptionRepository subscriptionRepository, final RestaurantRepository restaurantRepository, final SubscriptionPlanRepository subscriptionPlanRepository, final AuthenticationService authenticationService) {
        this.subscriptionRepository = subscriptionRepository;
        this.restaurantRepository = restaurantRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.authenticationService = authenticationService;
    }

    @Value("${qr.base.url}")
    private String qrBaseUrl;

    @Value("${qr.output.path}")
    private String qrOutputPath;

    public ResponseDTO generateQRCode(final String tableName, final SubscriptionDTO subscriptionDTO) {

        final Restaurant restaurant = this.restaurantRepository.findById(subscriptionDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND, "api/v1/restaurant-table/generate/{tableName}", authenticationService.getUserId()));
        final SubscriptionPlan subscriptionPlan = this.subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId()).orElseThrow(() -> new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND, "api/v1/restaurant-table/generate/{tableName}", authenticationService.getUserId()));
        try {

            final String qrText = this.qrBaseUrl + subscriptionDTO.getRestaurantId();
            final String path = this.qrOutputPath;

            final int width = 300;
            final int height = 300;

            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            final BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);

            BufferedImage rgbQrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = rgbQrImage.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.drawImage(qrImage, 0, 0, null);
            g.dispose();

            BufferedImage logoImage = ImageIO.read(new File("C:/Users/Santhosh M/Desktop/RestaurantUtils/RestaurantImg.png"));

            int logoScaledWidth = qrImage.getWidth() / 5;
            int logoScaledHeight = qrImage.getHeight() / 5;

            int finalImageHeight = (qrImage.getHeight() - logoScaledHeight) / 2;
            int finalImageWidth = (qrImage.getWidth() - logoScaledWidth) / 2;

            BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, finalImage.getWidth(), finalImage.getHeight());

            graphics.drawImage(qrImage, 0, 0, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            graphics.drawImage(logoImage, finalImageHeight, finalImageWidth, logoScaledWidth, logoScaledHeight, Color.white, null);
            graphics.dispose();

            ImageIO.write(finalImage, "PNG", new File(path));

            final Subscription subscription = new Subscription();
            subscription.setTableName(tableName);
            subscription.setSubscriptionPlan(subscriptionPlan);
            subscription.setQrCodeUrl(qrText);
            subscription.setRestaurant(restaurant);
            subscription.setCreatedBy(authenticationService.getUserId());
            subscription.setUpdatedBy(authenticationService.getUserId());

            return new ResponseDTO(Constants.CREATED, this.subscriptionRepository.save(subscription), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e) {
            throw new RuntimeException(Constants.ERROR_GENERATE + e);
        }
    }

    public ResponseDTO retrieveAllById(final String restaurantId) {
        final List<Subscription> subscriptions = this.subscriptionRepository.findAllRestaurantById(restaurantId);
        return new ResponseDTO(Constants.RETRIEVED, subscriptions, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subscriptionRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.RESTAURANT_TABLE_ID_NOT_FOUND, "api/v1/restaurant-table/remove/{id}", authenticationService.getUserId());
        }
        this.subscriptionRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveById(final String id) {
        final Subscription subscription = this.subscriptionRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.RESTAURANT_TABLE_ID_NOT_FOUND, "api/v1/restaurant-table/retrieve/{id}", authenticationService.getUserId()));
        return new ResponseDTO(Constants.RETRIEVED, subscription, HttpStatus.OK.getReasonPhrase());
    }
}
