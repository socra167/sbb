package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	@Size(min = 3, max = 25) // 문자열 길이 검증
	@NotEmpty(message = "사용자ID는 필수항목입니다.")
	private String username;

	@NotEmpty(message = "비밀번호는 필수항목입니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
	private String password2;

	@NotEmpty(message = "이메일은 필수항목입니다.")
	@Email // 속성의 값 이메일 형식과 일치하는지 검증
	private String email;
}
