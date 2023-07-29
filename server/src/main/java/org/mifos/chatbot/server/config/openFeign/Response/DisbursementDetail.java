package org.mifos.chatbot.server.config.openFeign.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DisbursementDetail {
    public int id;
    public List<Integer>expectedDisbursementDate;
    public List<Integer>actualDisbursementDate;
    public double principal;
}
