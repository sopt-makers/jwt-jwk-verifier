package sopt.makers.jwt.verifier.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.makers.jwt.verifier.common.response.BaseResponse;
import sopt.makers.jwt.verifier.common.util.ResponseUtil;
import sopt.makers.jwt.verifier.security.authentication.MakersAuthentication;

import java.util.Map;

import static sopt.makers.jwt.verifier.user.code.UserSuccess.GET_USER_INFO;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserInfoController {

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getCurrentUserInfo(Authentication authentication) {
        MakersAuthentication makers = (MakersAuthentication) authentication;
        Map<String, Object> response = Map.of(
                "userId", makers.getUserId(),
                "roles", makers.getRoles()
        );

        return ResponseUtil.success(GET_USER_INFO, response);
    }
}

