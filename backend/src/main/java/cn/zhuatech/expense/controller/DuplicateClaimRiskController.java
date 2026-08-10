/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.expense.controller;
import cn.zhuatech.expense.common.ApiResponse; import cn.zhuatech.expense.service.DuplicateClaimRiskService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/expense/insights/duplicate-claim") public class DuplicateClaimRiskController {private final DuplicateClaimRiskService service;public DuplicateClaimRiskController(DuplicateClaimRiskService service){this.service=service;}@PostMapping ApiResponse<DuplicateClaimRiskService.Result> evaluate(@Valid @RequestBody DuplicateClaimRiskService.Request request){return ApiResponse.ok(service.evaluate(request));}}
