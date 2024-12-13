package com.board.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AzureConfig {
	@Value("${api.domain}")
	private String apiDomain;
	
	@Value("${api.key}")
    private String apiKey;
}
