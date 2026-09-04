/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CorporateCardReconciliationServiceTest {
    private final CorporateCardReconciliationService service = new CorporateCardReconciliationService();

    @Test void settlesBalancedCorporateCardStatement() {
        var result = service.assess(request(true, true, true));
        assertEquals(CorporateCardReconciliationService.Decision.SETTLE, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    @Test void reviewsStatementWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(CorporateCardReconciliationService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    @Test void blocksUncontrolledStatementSettlement() {
        var result = service.assess(new CorporateCardReconciliationService.Request("CARD-003", 45,
                false, false, false, false, false, false, false, false, false, false,
                false, false, false, true, true, true));
        assertEquals(CorporateCardReconciliationService.Decision.BLOCKED, result.decision());
        assertEquals(13, result.blockers().size());
    }

    private CorporateCardReconciliationService.Request request(boolean reminders, boolean anomaly, boolean archive) {
        return new CorporateCardReconciliationService.Request("CARD-001", 45, true, true, true, true,
                true, true, true, true, true, true, true, true, true, reminders, anomaly, archive);
    }
}
