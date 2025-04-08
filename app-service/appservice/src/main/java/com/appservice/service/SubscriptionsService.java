package com.appservice.service;

import com.appservice.dto.ResponseDTO;
import com.appservice.dto.SubscriptionsDTO;
import com.appservice.exception.BadServiceAlertException;
import com.appservice.exception.RestaurantNotFoundException;
import com.appservice.repository.RestaurantRepository;
import com.appservice.repository.SubscriptionPlanRepository;
import com.appservice.repository.SubscriptionsRepository;
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
import ors.common.model.SubscriptionPlan;
import ors.common.model.Subscriptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class SubscriptionsService {

    private final SubscriptionsRepository subscriptionsRepository;

    private final RestaurantRepository restaurantRepository;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionsService(final SubscriptionsRepository subscriptionsRepository, final RestaurantRepository restaurantRepository, final SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.restaurantRepository = restaurantRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Value("${qr.base.url}")
    private String qrBaseUrl;

    @Value("${qr.output.path}")
    private String qrOutputPath;

    public ResponseDTO generateQRCode(final String tableName, final SubscriptionsDTO subscriptionsDTO) {

        final Restaurant restaurant = this.restaurantRepository.findById(subscriptionsDTO.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException(Constants.RESTAURANT_ID_NOT_FOUND));
        final SubscriptionPlan subscriptionPlan = this.subscriptionPlanRepository.findById(subscriptionsDTO.getSubscriptionPlanId()).orElseThrow(() -> new BadServiceAlertException(Constants.SUBSCRIPTION_PLAN_ID_NOT_FOUND));
        try {

            final String qrText = this.qrBaseUrl + subscriptionsDTO.getRestaurantId();
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

            final Subscriptions subscriptions = new Subscriptions();
            subscriptions.setTableName(tableName);
            subscriptions.setSubscriptionPlan(subscriptionPlan);
            subscriptions.setQrCodeUrl(qrText);
            subscriptions.setRestaurant(restaurant);
            subscriptions.setCreatedBy(subscriptionsDTO.getCreatedBy());
            subscriptions.setUpdatedBy(subscriptionsDTO.getUpdatedBy());

            return new ResponseDTO(Constants.CREATED, this.subscriptionsRepository.save(subscriptions), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR Code" + e);
        }
    }

    public ResponseDTO retrieveAllById(final String restaurantId) {
        final List<Subscriptions> subscriptions = this.subscriptionsRepository.findAllRestaurantById(restaurantId);
        return new ResponseDTO(Constants.RETRIEVED, subscriptions, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO remove(final String id) {
        if (!this.subscriptionsRepository.existsById(id)) {
            throw new BadServiceAlertException(Constants.RESTAURANT_TABLE_ID_NOT_FOUND);
        }
        this.subscriptionsRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }

    public ResponseDTO retrieveById(final String id) {
        final Subscriptions subscriptions = this.subscriptionsRepository.findById(id).orElseThrow(() -> new BadServiceAlertException(Constants.RESTAURANT_TABLE_ID_NOT_FOUND));
        return new ResponseDTO(Constants.RETRIEVED, subscriptions, HttpStatus.OK.getReasonPhrase());
    }
}
