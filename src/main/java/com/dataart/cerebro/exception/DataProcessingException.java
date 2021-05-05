package com.dataart.cerebro.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(Exception e) {
        super(String.format("Data processing exception: %s", e.getMessage()));
    }

    // FIXME: 5/5/2021 usage example. keep in mind exception text output
    //  try {
    //            authTokenRepository.save(authToken);
    //        } catch (Exception e) {
    //            log.error("Failed to save auth token for user {}", username, e);
    //            throw new DataProcessingException("Error during auth token save", e);
    //        }
}
