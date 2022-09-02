package org.palms.cctv.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.CameraInfo;

/**
 * Service for camera info searching.
 */
public interface CameraInfoService {

    /**
     * Returns available cameras.
     *
     * @return list of {@link AvailableCamera}
     */
    List<AvailableCamera> getAvailableCameras();

    /**
     * Returns camera info asynchronously.
     *
     * @param availableCamera {@link AvailableCamera}
     * @return completable result
     */
    CompletableFuture<CameraInfo> asyncGetCameraInfo(AvailableCamera availableCamera);
}
