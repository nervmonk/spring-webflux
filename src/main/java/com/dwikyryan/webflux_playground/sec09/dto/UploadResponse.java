package com.dwikyryan.webflux_playground.sec09.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationId, Long productsCount) {

}
