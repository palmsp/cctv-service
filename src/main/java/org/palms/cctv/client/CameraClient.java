package org.palms.cctv.client;

import java.net.URI;
import java.util.List;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.SourceData;
import org.palms.cctv.dto.TokenData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cameraClient", url = "${camera-service.feign-client.url}")
public interface CameraClient {

    /**
     * Returns available cameras information.
     *
     * @return list of {@link AvailableCamera}
     */
    @GetMapping("${camera-service.feign-client.available-cameras-path}")
    List<AvailableCamera> getAvailableCameras();

    /**
     * Returns source data for camera.
     *
     * @param sourceDataUri uri for source data
     * @return {@link SourceData}
     */
    @GetMapping
    SourceData getSourceData(URI sourceDataUri);


    /**
     * Returns token data for camera.
     *
     * @param tokenDataUri uri for token data
     * @return {@link TokenData}
     */
    @GetMapping
    TokenData getTokenData(URI tokenDataUri);
}
