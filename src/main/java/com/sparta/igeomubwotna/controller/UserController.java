package com.sparta.igeomubwotna.controller;

import com.sparta.igeomubwotna.dto.Response;
import com.sparta.igeomubwotna.dto.SigninRequestDto;
import com.sparta.igeomubwotna.dto.SignupRequestDto;
import com.sparta.igeomubwotna.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Response> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        ResponseEntity<Response> returnError = checkError("회원가입에 실패하였습니다.", bindingResult);

        if (returnError != null) {
            return returnError;
        }

        // UserService의 signup 메서드에 데이터 넘겨 줌
        return userService.signup(requestDto);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Response> signin(@RequestBody @Valid SigninRequestDto requestDto, BindingResult bindingResult) {
        ResponseEntity<Response> returnError = checkError("회원가입에 실패하였습니다.", bindingResult);

        if (returnError != null) {
            return returnError;
        }
        return null;
    }

    // 클라이언트에서 입력받아 오는 값을 유효성 검사하는 로직
    public ResponseEntity<Response> checkError(String message, BindingResult bindingResult) {
        // 유효성 검사 예외 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        // 유효성 검사에 오류가 있으면
        if (fieldErrors.size() > 0) {
            // 모든 필드 오류에 대해 로그 기록 및 오류 메시지 수집
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String errorMessage = fieldError.getField() + " 필드: " + fieldError.getDefaultMessage();
                log.error(errorMessage);
                errorMessages.add(errorMessage);
            }
            // 오류 메시지와 상태 코드 반환
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), message, errorMessages);
            return ResponseEntity.badRequest().body(response);
        }

        return null;
    }
}
