package com.danielbohry.dnser.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSubdomainRequest {

    private String name;
    private String address = "0.0.0.0";

}
