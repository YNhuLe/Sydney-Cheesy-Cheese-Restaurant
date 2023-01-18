package ca.sheridancollege.nhuyle.a4.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userId;
	@NonNull
	private String userName;

	private String email;
	@NonNull
	private String password;
	private boolean enabled;

}
