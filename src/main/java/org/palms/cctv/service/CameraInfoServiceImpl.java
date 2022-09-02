package org.palms.cctv.service;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.palms.cctv.client.CameraClient;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.CameraInfo;
import org.palms.cctv.dto.SourceData;
import org.palms.cctv.dto.TokenData;
import org.palms.cctv.service.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CameraInfoServiceImpl implements CameraInfoService {

    @Autowired
    private CameraClient cameraClient;

    /**
     * {@inheritDoc}
     */
    @Async("threadPoolExecutor")
    public CompletableFuture<CameraInfo> asyncGetCameraInfo(AvailableCamera availableCamera) {
        log.info("asynchronous get camera info with id={} started", availableCamera.getId());
        final CameraInfo cameraInfo = new CameraInfo();
        cameraInfo.setId(availableCamera.getId());

        final SourceData sourceData = getSourceData(availableCamera.getSourceDataUrl(), cameraInfo.getId());
        ofNullable(sourceData).ifPresent(data -> {
            cameraInfo.setUrlType(data.getUrlType());
            cameraInfo.setVideoUrl(data.getVideoUrl());
        });

        final TokenData tokenData = getTokenData(availableCamera.getTokenDataUrl(), cameraInfo.getId());
        ofNullable(tokenData).ifPresent(data -> {
            cameraInfo.setTtl(data.getTtl());
            cameraInfo.setValue(data.getValue());
        });
        log.info("asynchronous get camera info with id={} finished", availableCamera.getId());
        return CompletableFuture.completedFuture(cameraInfo);
    }

    /**
     * {@inheritDoc}
     */
    public List<AvailableCamera> getAvailableCameras() {
        List<AvailableCamera> availableCameras = cameraClient.getAvailableCameras();
        if (availableCameras == null) {
            log.error("Cannot get available cameras info");
            return emptyList();
        }
        return availableCameras;
    }

    private SourceData getSourceData(String sourceDataUrl, Long cameraId) {
        if (isNotEmpty(sourceDataUrl)) {
            try {
                SourceData sourceData = cameraClient.getSourceData(new URI(sourceDataUrl));
                if (sourceData == null) {
                    log.info("Cannot get sourceData for camera with id={}", cameraId);
                }
                return sourceData;
            } catch (URISyntaxException e) {
                log.info("Not valid sourceData url={}", sourceDataUrl);
            }
        }
        return null;
    }

    private TokenData getTokenData(String tokenDataUrl, Long cameraId) {
        if (isNotEmpty(tokenDataUrl)) {
            try {
                TokenData tokenData = cameraClient.getTokenData(new URI(tokenDataUrl));
                if (tokenData == null) {
                    log.info("Cannot get tokenData for camera with id={}", cameraId);
                }
                return tokenData;
            } catch (URISyntaxException e) {
                log.info("Not valid tokenData url={}", tokenDataUrl);
            }
        }
        return null;
    }

}
