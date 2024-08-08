package JejuDorang.JejuDorang.auth.dto;

import lombok.Data;

@Data
public class KakaoAccessTokenDto {

    private String token_type;
    private String access_token;
    private String id_token;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
