/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.domain;
import org.springframework.stereotype.Component;
import java.util.List;
@Component public class DomainCatalog {
    public String systemName(){return "知华 Expense 企业费用与报销管理平台";}
    public String sceneName(){return "费用申请、预算占用、发票验真、智能审核、报销与付款";}
    public List<SeedItem> seedItems(){return List.of(
        new SeedItem("EXP-20260801-001","华东客户拜访差旅报销","处理中","销售运营部","中"),
        new SeedItem("EXP-20260801-002","研发云资源费用超预算复核","待处理","财务共享中心","高"),
        new SeedItem("EXP-20260801-003","市场活动发票重复风险核验","处理中","费用审核组","紧急"),
        new SeedItem("EXP-20260801-004","七月员工费用付款批次","已完成","资金结算组","高"));}
    public List<String> recommendedActions(){return List.of("优先核验重复发票与超标准费用","确认预算、成本中心和事前申请","对异常供应商与高频报销发起人工复核");}
    public record SeedItem(String recordNo,String title,String status,String owner,String priority){}
}
