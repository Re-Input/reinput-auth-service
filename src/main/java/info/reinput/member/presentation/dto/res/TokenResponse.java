package info.reinput.member.presentation.dto.res;

import lombok.Builder;

@Builder
public record TokenResponse (
        String accessToken,
        String refreshToken
) {
}
