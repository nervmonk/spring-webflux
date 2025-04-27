package com.dwikyryan.webflux_playground.sec08.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationId, Long productsCount) {

}
