package JejuDorang.JejuDorang.auth.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import JejuDorang.JejuDorang.component.JwtTokenProvider;
import JejuDorang.JejuDorang.member.data.Member;
import JejuDorang.JejuDorang.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean isUserType = Member.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginUserAnnotation && isUserType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) webRequest.getNativeRequest());
		String keyCode = jwtTokenProvider.getUserPk(token);
		return memberRepository.findByKeyCode(keyCode)
			.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
	}
}
