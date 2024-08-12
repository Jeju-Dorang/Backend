package JejuDorang.JejuDorang.diary.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SecretType {
    @JsonProperty("public")
    PUBLIC,

    @JsonProperty("private")
    PRIVATE
}
