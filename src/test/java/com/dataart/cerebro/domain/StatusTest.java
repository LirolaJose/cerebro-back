package com.dataart.cerebro.domain;

import com.dataart.cerebro.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {

    @Test
    void whenIdIs1ThenActiveStatusReturned() {
        Status status = Status.getStatusDTOById(1);
        assertEquals(Status.ACTIVE, status);
    }

    @Test
    void whenIdDoesNotExistThenThrowException() {
        //given
        int id = 55;

        Assertions.assertThrows(NotFoundException.class, () -> {
            Status.getStatusDTOById(id);
        });
    }
}