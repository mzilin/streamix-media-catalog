package com.mariuszilinskas.vsp.media.catalog.exception;

public class ResourceNotFoundException extends RuntimeException {

    public <T> ResourceNotFoundException(Class<T> entity, String identifierType, Object identifierValue) {
        super(String.format("No %s found with %s = '%s'. Please check the %s and try again.",
                entity.getSimpleName(), identifierType, identifierValue, identifierType));
    }

}
