package com.example.note.utils;

/**
 * Utility class for checking preconditions.
 */
public final class Assert {

    private Assert() {
        throw new AssertionError("This class should not be instantiated.");
    }

    /**
     * Checks that the specified object reference is not null.
     *
     * @param object the object reference to check for nullity
     * @param <T>    the type of the object
     * @return the non-null object reference
     * @throws IllegalArgumentException if the object is null
     */
    public static <T> T requireNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }
}
