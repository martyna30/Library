package com.library.domain.model;

public interface Admin {

    public default Librarians addLibrarian() {
        return null;
    }
}
