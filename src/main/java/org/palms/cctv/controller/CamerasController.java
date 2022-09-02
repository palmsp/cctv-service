package org.palms.cctv.controller;

import static org.palms.cctv.controller.endpoints.ApiEndpoints.INFO;

import java.util.List;
import org.palms.cctv.dto.CameraInfo;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Comtroller for cameras operation.
 */
public interface CamerasController {

    /**
     * Returns cameras info.
     *
     * @return list of {@link CameraInfo}
     */
    @GetMapping(INFO)
    public List<CameraInfo> getCamerasInfo();
}
