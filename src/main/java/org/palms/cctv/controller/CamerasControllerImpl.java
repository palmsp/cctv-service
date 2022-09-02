package org.palms.cctv.controller;

import static org.palms.cctv.controller.endpoints.ApiEndpoints.CAMERA;
import static org.palms.cctv.controller.endpoints.ApiEndpoints.INFO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palms.cctv.controller.CamerasController;
import org.palms.cctv.dto.CameraInfo;
import org.palms.cctv.facade.CameraFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Cameras information")
@RestController
@RequestMapping(CAMERA)
@RequiredArgsConstructor
@Slf4j
public class CamerasControllerImpl implements CamerasController {

    @Autowired
    private CameraFacade cameraFacade;

    @GetMapping(INFO)
    @ApiOperation("Get cameras information")
    public List<CameraInfo> getCamerasInfo() {
        return cameraFacade.getCamerasInfo();
    }
}
