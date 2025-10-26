package com.mariuszilinskas.streamix.media.catalog.exception;

/**
 * This class represents a custom exception to be thrown when an attempt is made
 * to create an entity that already exists.
 *
 * @author Marius Zilinskas
 */
public class EntityExistsException extends RuntimeException {

    /**
     * Creates a new instance of the exception.
     *
     * @param entity The class of the entity where the conflict occurred.
     * @param type The type of the attribute that caused the conflict.
     * @param value The value that caused the conflict.
     */
    public EntityExistsException(Class<?> entity, String type, Object value) {
        super(entity.getSimpleName() + " with " + type + " '" + value + "' already exists");
    }
}

