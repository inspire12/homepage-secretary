package com.inspire12.secretary.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class Chicken {
    private Long id;

    @JsonProperty("user_id")
    @JsonView({View.Consumer.class, View.Repository.class})
    private String userId;

    @JsonProperty("passwordEncrypted")
    @JsonView({View.Repository.class})
    private String passwordEncrypted;

}
