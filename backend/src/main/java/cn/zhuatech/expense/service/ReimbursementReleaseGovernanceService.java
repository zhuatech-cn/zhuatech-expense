/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReimbursementReleaseGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.receiptCoverageComplete()) blockers.add("有效票据未覆盖全部报销金额");
        if (request.duplicateRisk()) blockers.add("命中重复报销风险");
        if (!request.withinPolicy()) blockers.add("存在未获批的费用政策例外");
        if (!request.budgetAvailable()) blockers.add("预算余额不足或未完成占用");
        if (request.requesterIsApprover()) blockers.add("申请人与审批人未实现职责分离");
        if (!request.advanceSettled()) actions.add("结清或冲抵员工借款");
        if (!request.managerApproved()) actions.add("取得业务负责人审批");
        if (!request.financeApproved()) actions.add("取得财务合规复核");

        Decision decision = !blockers.isEmpty() ? Decision.BLOCK
                : !actions.isEmpty() ? Decision.REVIEW : Decision.PAY;
        return new Assessment(request.claimNo(), decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String claimNo, @Min(0) long amountCents,
                          boolean receiptCoverageComplete, boolean duplicateRisk,
                          boolean withinPolicy, boolean budgetAvailable,
                          boolean requesterIsApprover, boolean advanceSettled,
                          boolean managerApproved, boolean financeApproved) {}
    public record Assessment(String claimNo, Decision decision, List<String> blockers,
                             List<String> actions) {}
    public enum Decision { PAY, REVIEW, BLOCK }
}
