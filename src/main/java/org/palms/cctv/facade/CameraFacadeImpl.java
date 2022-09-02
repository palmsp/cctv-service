package org.palms.cctv.facade;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.CameraInfo;
import org.palms.cctv.facade.CameraFacade;
import org.palms.cctv.service.CameraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Facade for camera operations.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CameraFacadeImpl implements CameraFacade {

    @Autowired
    private CameraInfoService cameraInfoService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CameraInfo> getCamerasInfo() {
        List<AvailableCamera> availableCameras = cameraInfoService.getAvailableCameras();

        List<CompletableFuture<CameraInfo>> futures = availableCameras.stream()
                .map(camera -> cameraInfoService.asyncGetCameraInfo(camera))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[futures.size()]));
        CompletableFuture<List<CameraInfo>> cameraInfosFuture = allFutures.thenApply(
                v -> futures.stream().map(future -> future.join()).collect(Collectors.toList()));
        try {
            return cameraInfosFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during get camera info async execution", e);
        }
        return Collections.emptyList();
    }
}
