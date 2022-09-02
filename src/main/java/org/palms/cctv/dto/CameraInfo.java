package org.palms.cctv.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "CameraInfo", description = "DTO for general camera information")
public class CameraInfo {

    @ApiModelProperty("Camera ID")
    private Long id;

    @ApiModelProperty("Url type for video stream")
    private String urlType;

    @ApiModelProperty("Url for video stream")
    private String videoUrl;

    @ApiModelProperty("Token value")
    private String value;

    @ApiModelProperty("Token ttl")
    private Long ttl;
}
