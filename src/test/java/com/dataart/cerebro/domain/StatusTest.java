package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {

    @Test
    void whenIdIs1ThenActiveStatusReturned() {
        Status status = Status.getStatusById(1L);
        assertEquals(Status.ACTIVE, status);
    }

    @Test
    void whenIdDoesNotExistThenThrowException() {
        //given
       Long id = 55L;

        Assertions.assertThrows(NotFoundException.class, () -> {
            Status.getStatusById(id);
        });
    }
}