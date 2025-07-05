package com.airtribe.news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreferenceRequest {
    @NotBlank
    private String category;
    @NotBlank
    private String language;
    @NotBlank
    private String country;
}
