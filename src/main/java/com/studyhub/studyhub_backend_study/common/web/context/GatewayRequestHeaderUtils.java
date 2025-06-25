package com.studyhub.studyhub_backend_study.common.web.context;

import com.studyhub.studyhub_backend_study.common.exception.NotFound;
import com.studyhub.studyhub_backend_study.common.exception.Unauthorized;
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



    // 사용자 ID를 요청 헤더에서 가져오고, 없으면 예외 발생
    public static Long getUserIdOrThrowException() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String userIdStr = requestAttributes.getRequest().getHeader("X-Auth-UserId");

        if (userIdStr == null || userIdStr.isBlank()) {
            throw new Unauthorized("인증 정보가 없습니다.");
        }

        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new Unauthorized("잘못된 사용자 ID 형식입니다.");
        }
    }



    public static String getUserName() {
        return getRequestHeaderParamAsString("X-Auth-UserName");
    }

    public static String getUserNameOrThrowException() {
        String userName = getRequestHeaderParamAsString("X-Auth-UserName");
        if (userName == null || userName.isBlank()) {
            throw new Unauthorized("사용자 이름 정보가 없습니다.");
        }
        return userName;
    }
}