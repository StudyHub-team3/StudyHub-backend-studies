package com.studyhub.studyhub_backend_study.remote.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "RemoteUserService",
        url = "http://studyhub-backend-user-service:8080",
        path = "/backend/user/v1")

public interface RemoteUserService {

}
