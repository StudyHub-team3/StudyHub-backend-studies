package com.studyhub.studyhub_backend_studies.common.web.context;

import com.studyhub.studyhub_backend_studies.common.exception.NotFound;
import com.studyhub.studyhub_backend_studies.common.exception.Unauthorized;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//사용자 관련 헤더 값을 가져오는 유틸리티
public class GatewayRequestHeaderUtils {

    // 요청 헤더에서 특정 키 값을 문자열로 가져옴
    public static String getRequestHeaderParamAsString(String key) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }

    // 사용자 ID를 요청 헤더에서 가져옴 (없으면 null 반환)
    public static String getUserId() {
        String userId = getRequestHeaderParamAsString("X-Auth-UserId");
        if (userId == null) {
            return null;
        }
        return userId;
    }

    // 사용자 디바이스 정보를 요청 헤더에서 가져옴 (없으면 null 반환)
    public static String getClientDevice() {
        String clientDevice = getRequestHeaderParamAsString("X-Client-Device");
        if (clientDevice == null) {
            return null;
        }
        return clientDevice;
    }

    // 사용자 IP 주소 정보를 요청 헤더에서 가져옴 (없으면 null 반환)
    public static String getClientAddress() {
        String clientAddress = getRequestHeaderParamAsString("X-Client-Address");
        if (clientAddress == null) {
            return null;
        }
        return clientAddress;
    }

    // 사용자 ID를 요청 헤더에서 가져오고, 없으면 예외 발생
    public static Long getUserIdOrThrowException() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String userIdStr = requestAttributes.getRequest().getHeader("X-USER-ID");

        if (userIdStr == null || userIdStr.isBlank()) {
            throw new Unauthorized("인증 정보가 없습니다.");
        }

        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new Unauthorized("잘못된 사용자 ID 형식입니다.");
        }
    }

    // 사용자 디바이스 정보를 요청 헤더에서 가져오고, 없으면 예외 발생
    public static String getClientDeviceOrThrowException() {
        String clientDevice = getClientDevice();
        if (clientDevice == null) {
            throw new NotFound("헤더에 사용자 디바이스 정보가 없습니다.");
        }
        return clientDevice;
    }

    // 사용자 IP 주소 정보를 요청 헤더에서 가져오고, 없으면 예외 발생
    public static String getClientAddressOrThrowException() {
        String clientAddress = getClientAddress();
        if (clientAddress == null) {
            throw new NotFound("헤더에 사용자 IP 주소 정보가 없습니다.");
        }
        return clientAddress;
    }
}