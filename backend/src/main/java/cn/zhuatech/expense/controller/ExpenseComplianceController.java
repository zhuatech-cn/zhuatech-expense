/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.expense.controller;

import cn.zhuatech.expense.common.ApiResponse;
import cn.zhuatech.expense.service.ExpenseComplianceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/expense-compliance")
public class ExpenseComplianceController {
    private final ExpenseComplianceService service;
    public ExpenseComplianceController(ExpenseComplianceService service) { this.service = service; }
    @PostMapping
    ApiResponse<ExpenseComplianceService.ComplianceResult> assess(
        @Valid @RequestBody ExpenseComplianceService.ComplianceRequest request) {
        return ApiResponse.ok(service.assess(request));
    }
}
