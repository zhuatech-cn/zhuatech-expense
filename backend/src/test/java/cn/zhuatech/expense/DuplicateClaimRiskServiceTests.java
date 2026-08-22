/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense;
import cn.zhuatech.expense.service.DuplicateClaimRiskService;import org.junit.jupiter.api.Test;import java.math.*;import java.time.*;import java.util.*;import static org.junit.jupiter.api.Assertions.*;
class DuplicateClaimRiskServiceTests {private final DuplicateClaimRiskService service=new DuplicateClaimRiskService();
 @Test void blocksExactDuplicate(){var day=LocalDate.of(2026,8,1);var r=service.evaluate(new DuplicateClaimRiskService.Request("C2",new BigDecimal("88.00"),"知华酒店",day,List.of(new DuplicateClaimRiskService.Candidate("C1",new BigDecimal("88.00"),"知华酒店",day))));assertEquals("BLOCK",r.status());}
 @Test void clearsDifferentMerchant(){var day=LocalDate.of(2026,8,1);var r=service.evaluate(new DuplicateClaimRiskService.Request("C2",new BigDecimal("88"),"酒店",day,List.of(new DuplicateClaimRiskService.Candidate("C1",new BigDecimal("88"),"餐厅",day))));assertEquals("CLEAR",r.status());}}
