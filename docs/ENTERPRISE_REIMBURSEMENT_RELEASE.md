# 企业级报销付款放行治理

报销进入付款前统一检查票据覆盖、重复风险、费用政策、预算、职责分离、借款冲抵及业务/财务审批。

`POST /api/enterprise/expense/reimbursement-release` 返回 `PAY / REVIEW / BLOCK` 决策。生产使用应关联影像票据、预算占用、审批身份、付款批次和全程审计日志。
