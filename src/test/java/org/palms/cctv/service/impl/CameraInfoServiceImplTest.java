package org.palms.cctv.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.palms.cctv.client.CameraClient;
import org.palms.cctv.dto.AvailableCamera;
import org.palms.cctv.dto.CameraInfo;
import org.palms.cctv.dto.SourceData;
import org.palms.cctv.dto.TokenData;
import org.palms.cctv.service.CameraInfoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CameraInfoServiceImplTest {

    @InjectMocks
    private CameraInfoServiceImpl cameraInfoService;
    @Mock
    private CameraClient cameraClient;

    @Test
    public void shouldReturnCameraInfo() throws ExecutionException, InterruptedException {
        AvailableCamera availableCamera1 = AvailableCamera.builder()
                .id(1L)
                .sourceDataUrl("http://www.mocky.io/v2/1")
                .tokenDataUrl("http://www.mocky.io/v2/11")
                .build();

        SourceData sourceData1 = SourceData.builder()
                .urlType("LIVE")
                .videoUrl("someUrl1")
                .build();

        TokenData tokenData1 = TokenData.builder()
                .ttl(110L)
                .value("someValue1")
                .build();

        when(cameraClient.getSourceData(any(URI.class))).thenReturn(sourceData1);
        when(cameraClient.getTokenData(any(URI.class))).thenReturn(tokenData1);

        //when
        CompletableFuture<CameraInfo> camera = cameraInfoService.asyncGetCameraInfo(availableCamera1);
        CameraInfo cameraInfo = camera.get();

        //then
        assertThat(cameraInfo).isNotNull();
        assertThat(cameraInfo.getId()).isEqualTo(availableCamera1.getId());
        assertThat(cameraInfo.getUrlType()).isEqualTo(sourceData1.getUrlType());
        assertThat(cameraInfo.getValue()).isEqualTo(tokenData1.getValue());
        assertThat(cameraInfo.getTtl()).isEqualTo(tokenData1.getTtl());
    }

    @Test
    public void shouldReturnAvailableCameras() {
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
        List<AvailableCamera> expectedAvailableCameras = List.of(availableCamera1, availableCamera2);

        when(cameraClient.getAvailableCameras()).thenReturn(expectedAvailableCameras);

        //when
        List<AvailableCamera> availableCameras = cameraInfoService.getAvailableCameras();

        //then
        assertThat(availableCameras).hasSize(2);
        AvailableCamera camera1 = availableCameras.get(0);
        assertSoftly(softly -> {
            softly.assertThat(camera1.getId()).isEqualTo(availableCamera1.getId());
            softly.assertThat(camera1.getSourceDataUrl()).isEqualTo(availableCamera1.getSourceDataUrl());
            softly.assertThat(camera1.getTokenDataUrl()).isEqualTo(availableCamera1.getTokenDataUrl());
        });
        AvailableCamera camera2 = availableCameras.get(1);
        assertSoftly(softly -> {
            softly.assertThat(camera2.getId()).isEqualTo(availableCamera2.getId());
            softly.assertThat(camera2.getSourceDataUrl()).isEqualTo(availableCamera2.getSourceDataUrl());
            softly.assertThat(camera2.getTokenDataUrl()).isEqualTo(availableCamera2.getTokenDataUrl());
        });
    }

    @Test
    public void shouldReturnEmptyAvailableCameras() {
        List<AvailableCamera> availableCameras = cameraInfoService.getAvailableCameras();

        assertThat(availableCameras).hasSize(0);
    }
}