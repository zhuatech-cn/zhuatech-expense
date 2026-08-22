/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseComplianceService {
    public ComplianceResult assess(ComplianceRequest request) {
        int riskScore = Math.max(0, 95 - request.receiptCoverage())
            + Math.min(30, request.policyOverage() * 2)
            + Math.min(40, request.duplicateMatches() * 20)
            + (request.costCenterValid() ? 0 : 20)
            + (request.preApprovalRequired() && !request.preApproved() ? 25 : 0);
        riskScore = Math.min(100, riskScore);
        List<String> reasons = new ArrayList<>();
        if (request.receiptCoverage() < 95) reasons.add("票据覆盖率低于自动审核标准");
        if (request.policyOverage() > 0) reasons.add("申报金额存在费用标准超额");
        if (request.duplicateMatches() > 0) reasons.add("发票或费用明细疑似重复");
        if (!request.costCenterValid()) reasons.add("成本中心无效或不可用");
        if (request.preApprovalRequired() && !request.preApproved()) reasons.add("缺少必要的事前费用申请");
        String decision = request.duplicateMatches() > 1 || !request.costCenterValid() ? "REJECT"
            : riskScore >= 25 ? "MANUAL_REVIEW" : "PASS";
        if (reasons.isEmpty()) reasons.add("单据满足当前费用规则，可进入审批流程");
        return new ComplianceResult(request.amount(), riskScore, decision, reasons);
    }

    public record ComplianceRequest(@NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull @Min(0) @Max(100) Integer receiptCoverage,
        @NotNull @Min(0) @Max(100) Integer policyOverage,
        @NotNull @Min(0) @Max(100) Integer duplicateMatches,
        @NotNull Boolean costCenterValid, @NotNull Boolean preApprovalRequired,
        @NotNull Boolean preApproved) {}
    public record ComplianceResult(BigDecimal amount, int riskScore, String decision, List<String> reasons) {}
}
