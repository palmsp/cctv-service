package org.palms.cctv.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.CameraInfo;
import org.palms.cctv.service.CameraInfoService;

@ExtendWith(MockitoExtension.class)
public class CameraFacadeTest {

    @InjectMocks
    private CameraFacadeImpl cameraFacade;

    @Mock
    private CameraInfoService cameraInfoService;

    @Test
    public void shouldReturnCamerasInfo() {
        List<AvailableCamera> availableCameras = availableCameras();

        CameraInfo expectedCamera1 = CameraInfo.builder()
                .id(availableCameras.get(0).getId())
                .urlType("LIVE")
                .videoUrl("someUrl1")
                .value("someValue1")
                .ttl(110L)
                .build();

        CameraInfo expectedCamera2 = CameraInfo.builder()
                .id(availableCameras.get(1).getId())
                .urlType("LIVE")
                .videoUrl("someUrl1")
                .value("someValue1")
                .ttl(110L)
                .build();

        when(cameraInfoService.getAvailableCameras()).thenReturn(availableCameras);
        when(cameraInfoService.asyncGetCameraInfo(any(AvailableCamera.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedCamera1),
                        CompletableFuture.completedFuture(expectedCamera2));

        //when
        List<CameraInfo> cameraInfos = cameraFacade.getCamerasInfo();

        //then
        assertThat(cameraInfos).hasSize(2);
        CameraInfo cameraInfo1 = cameraInfos.get(0);
        assertSoftly(softly -> {
            softly.assertThat(cameraInfo1.getId()).isEqualTo(expectedCamera1.getId());
            softly.assertThat(cameraInfo1.getVideoUrl()).isEqualTo(expectedCamera1.getVideoUrl());
            softly.assertThat(cameraInfo1.getUrlType()).isEqualTo(expectedCamera1.getUrlType());
            softly.assertThat(cameraInfo1.getValue()).isEqualTo(expectedCamera1.getValue());
            softly.assertThat(cameraInfo1.getTtl()).isEqualTo(expectedCamera1.getTtl());
        });
        CameraInfo cameraInfo2 = cameraInfos.get(1);
        assertSoftly(softly -> {
            softly.assertThat(cameraInfo2.getId()).isEqualTo(expectedCamera2.getId());
            softly.assertThat(cameraInfo2.getVideoUrl()).isEqualTo(expectedCamera2.getVideoUrl());
            softly.assertThat(cameraInfo2.getUrlType()).isEqualTo(expectedCamera2.getUrlType());
            softly.assertThat(cameraInfo2.getValue()).isEqualTo(expectedCamera2.getValue());
            softly.assertThat(cameraInfo2.getTtl()).isEqualTo(expectedCamera2.getTtl());
        });
    }

    @Test
    public void shouldReturnEmptyCamerasInfo() {
        List<CameraInfo> cameraInfos = cameraFacade.getCamerasInfo();

        assertThat(cameraInfos).hasSize(0);
    }

    @Test
    public void shouldReturnOneCameraInCamerasInfo() {
        List<AvailableCamera> availableCameras = availableCameras();
        CameraInfo expectedCamera2 = CameraInfo.builder()
                .id(availableCameras.get(1).getId())
                .urlType("LIVE")
                .videoUrl("someUrl1")
                .value("someValue1")
                .ttl(110L)
                .build();

        when(cameraInfoService.getAvailableCameras()).thenReturn(availableCameras);
        when(cameraInfoService.asyncGetCameraInfo(any(AvailableCamera.class)))
                .thenReturn(null,
                        CompletableFuture.completedFuture(expectedCamera2));

        List<CameraInfo> cameraInfos = cameraFacade.getCamerasInfo();

        assertThat(cameraInfos).hasSize(1);
    }

    private List<AvailableCamera> availableCameras() {
        AvailableCamera availableCamera1 = AvailableCamera.builder()
                .id(1L)
                .sourceDataUrl("http://www.mocky.io/v2/1")
                .tokenDataUrl("http://www.mocky.io/v2/11")
                .build();
        AvailableCamera availableCamera2 = AvailableCamera.builder()
                .id(2L)
                .sourceDataUrl("http://www.mocky.io/v2/2")
                .tokenDataUrl("http://www.mocky.io/v2/22")
                .build();
        return List.of(availableCamera1, availableCamera2);
    }
}
