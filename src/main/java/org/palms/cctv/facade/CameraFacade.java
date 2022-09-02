package org.palms.cctv.facade;

import java.util.List;
import org.palms.cctv.dto.CameraInfo;

/**
 * Facade interface for camera operations.
 */
public interface CameraFacade {

    /**
     * Returns cameras info.
     *
     * @return list of {@link CameraInfo}
     */
    List<CameraInfo> getCamerasInfo();
}
