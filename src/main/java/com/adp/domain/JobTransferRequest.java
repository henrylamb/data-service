package com.adp.domain;
import lombok.Data;

@Data
public class JobTransferRequest {
    public Long jobId;
    public Long fromUserId;
    public Long toUserId;
}