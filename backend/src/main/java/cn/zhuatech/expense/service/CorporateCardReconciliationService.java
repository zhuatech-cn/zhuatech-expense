/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.expense.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CorporateCardReconciliationService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.statementImported()) blockers.add("企业卡账单未完整导入");
        if (!request.cardholdersMapped()) blockers.add("卡号令牌与员工责任人未完整映射");
        if (!request.receiptsComplete()) blockers.add("交易票据或无票说明不完整");
        if (!request.merchantDataMatched()) blockers.add("商户、日期或金额未完成三项匹配");
        if (!request.duplicateCheckClear()) blockers.add("交易存在重复报销或重复入账风险");
        if (!request.policyExceptionsApproved()) blockers.add("超标准或禁限类消费未取得例外批准");
        if (!request.taxReviewed()) blockers.add("进项税与费用税务分类未复核");
        if (!request.costCentersAssigned()) blockers.add("成本中心或项目归属不完整");
        if (!request.personalSpendRepaid()) blockers.add("个人消费尚未完成员工归还");
        if (!request.postingPreviewBalanced()) blockers.add("总账过账预览借贷不平");
        if (!request.financeApproved()) blockers.add("财务尚未批准账单结算");
        if (!request.makerCheckerSeparated()) blockers.add("对账经办人与复核人未职责分离");
        if (!request.auditReady()) blockers.add("账单、票据、审批与凭证证据链不完整");
        if (!request.employeeRemindersReady()) actions.add("向缺票或待说明员工发送提醒");
        if (!request.anomalyReviewComplete()) actions.add("完成异常商户、时间和拆单行为复核");
        if (!request.archivePackageReady()) actions.add("生成月结归档包及检索索引");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.SETTLE;
        return new Assessment(request.statementId(), request.transactionCount(), decision,
                List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String statementId, @Min(1) int transactionCount,
                          boolean statementImported, boolean cardholdersMapped,
                          boolean receiptsComplete, boolean merchantDataMatched,
                          boolean duplicateCheckClear, boolean policyExceptionsApproved,
                          boolean taxReviewed, boolean costCentersAssigned,
                          boolean personalSpendRepaid, boolean postingPreviewBalanced,
                          boolean financeApproved, boolean makerCheckerSeparated, boolean auditReady,
                          boolean employeeRemindersReady, boolean anomalyReviewComplete,
                          boolean archivePackageReady) {}
    public record Assessment(String statementId, int transactionCount, Decision decision,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { SETTLE, REVIEW, BLOCKED }
}
