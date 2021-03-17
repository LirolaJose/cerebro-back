package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.StatusDTO;

public interface StatusDAO {
    StatusDTO getStatusDTOById (int statusId);
}
