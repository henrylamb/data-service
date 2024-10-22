package com.adp.domain;
import lombok.Data;

@Data
public class JobTransferRequest {
    public int jobId;
    public int fromUserId;
    public int toUserId;
}