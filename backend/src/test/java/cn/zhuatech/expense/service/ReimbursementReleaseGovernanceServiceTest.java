/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ReimbursementReleaseGovernanceServiceTest {
    private final ReimbursementReleaseGovernanceService service = new ReimbursementReleaseGovernanceService();

    @Test void releasesCompliantClaimForPayment() {
        var result = service.assess(new ReimbursementReleaseGovernanceService.Request(
                "EXP-001", 80_000, true, false, true, true, false, true, true, true));
        assertThat(result.decision()).isEqualTo(ReimbursementReleaseGovernanceService.Decision.PAY);
        assertThat(result.blockers()).isEmpty();
    }

    @Test void blocksHighRiskClaim() {
        var result = service.assess(new ReimbursementReleaseGovernanceService.Request(
                "EXP-002", 180_000, false, true, false, false, true, false, false, false));
        assertThat(result.decision()).isEqualTo(ReimbursementReleaseGovernanceService.Decision.BLOCK);
        assertThat(result.blockers()).hasSize(5);
        assertThat(result.actions()).hasSize(3);
    }
}
