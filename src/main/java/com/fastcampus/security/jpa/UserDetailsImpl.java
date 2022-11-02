package com.fastcampus.security.jpa;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fastcampus.biz.domain.BlogUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	// BlogUser Entity 타입의 참조변수 선언
	private BlogUser user;
	
	public UserDetailsImpl(BlogUser user) {
		super();
		this.user = user;
	}
	
	// user Entity 가 가지고 있는 권한 목록을 저장하여 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 목록을 저장할 컬렉션
		Collection<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
		
		// 권한 설정
		roleList.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_" + user.getRole();
			}
		});
		return roleList;
		
	}

	@Override
	public String getPassword() {
		// "{noop}" : 비밀번호를 자동으로 암호화 하지 않겠다.
		return "{noop}" + user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	public long getUserId() {
		return user.getUserId();
	}

	// 계정이 만료되었는지 여부를 리턴한다.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있는지 여부를 리턴한다
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되었는지 여부를 리턴한다.
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화 되었는지 여부를 리턴한다.
	@Override
	public boolean isEnabled() {
		return true;
	}


}
