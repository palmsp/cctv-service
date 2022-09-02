package org.palms.cctv.controller.endpoints;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for accessing REST api
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiEndpoints {

    public static final String API_V1 = "/api/v1";

    public static final String CAMERA = API_V1 + "/camera";

    public static final String INFO = "/info";
}
