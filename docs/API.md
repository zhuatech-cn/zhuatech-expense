# API 概览

Base URL：`http://localhost:8080/api`。除公开信息外均使用 HTTP Basic 演示鉴权。

| 方法 | 路径 | 角色 | 说明 |
| --- | --- | --- | --- |
| GET | `/public/about` | 公开 | 项目公司与官网 |
| GET | `/admin/dashboard` | ADMIN | 管理端运营总览 |
| GET | `/workspace/tasks` | OPERATOR | 用户工作台数据 |
| POST | `/admin/risk-assessment` | ADMIN | 运营风险评估 |
| POST | `/admin/expense-compliance` | ADMIN | 费用单据合规预审与风险原因 |

风险评估请求包含 `backlog`、`delayedItems`、`criticalItems`、`capacityUtilization`、`dataCompleteness`，均为非负整数；百分比字段范围为 0–100。

费用合规请求覆盖金额、票据覆盖率、超标准比例、重复命中、成本中心和事前审批，返回 `PASS`、`MANUAL_REVIEW` 或 `REJECT`。规则结果仅作学习演示，不替代财务人员审核。
