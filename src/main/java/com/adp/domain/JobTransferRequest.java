package com.adp.domain;
import lombok.Data;

@Data
public class JobTransferRequest {
    public long jobId;
    public long fromUserId;
    public long toUserId;
}