# 架构说明

```text
Vue 3 管理端 / 响应式 H5
          │ HTTP / JSON
Spring Security → Controller → Service → Spring Data JPA → MySQL 8
                                  │
                    费用合规预审与异常识别规则引擎
```

当前版本以单体分层架构保证易运行与易理解。`DomainCatalog` 管理费用协同样例，`ExpenseComplianceService` 执行票据和费用规则，`WorkItem` 承载审批事项。生产化时建议连接预算、发票、税务、ERP 和银企直联服务，并增加职责分离、付款复核、凭证一致性和审计留痕。
