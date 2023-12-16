package com.danielbohry.dnser.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubdomainRequest {

    private String target = "0.0.0.0";
    private Boolean proxied = false;

}
