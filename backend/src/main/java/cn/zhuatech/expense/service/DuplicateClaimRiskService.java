/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.expense.service;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.math.*; import java.time.*; import java.time.temporal.ChronoUnit; import java.util.*;
@Service public class DuplicateClaimRiskService {
 public Result evaluate(Request r){int best=0;List<String> matches=new ArrayList<>();for(Candidate c:r.candidates()){long days=Math.abs(ChronoUnit.DAYS.between(r.expenseDate(),c.expenseDate()));boolean merchant=r.merchant().trim().equalsIgnoreCase(c.merchant().trim());BigDecimal delta=r.amount().subtract(c.amount()).abs();int score=merchant&&delta.signum()==0&&days==0?100:merchant&&delta.compareTo(BigDecimal.ONE)<=0&&days<=2?75:0;if(score>0){matches.add(c.claimId());best=Math.max(best,score);}}String status=best>=100?"BLOCK":best>=75?"REVIEW":"CLEAR";return new Result(best,status,matches,status.equals("CLEAR")?"未发现相似报销记录":"核对原始票据、支付流水和报销事由");}
 public record Request(@NotBlank String claimId,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotBlank String merchant,@NotNull LocalDate expenseDate,@NotNull List<@Valid Candidate> candidates){}
 public record Candidate(@NotBlank String claimId,@NotNull @DecimalMin("0.01") BigDecimal amount,@NotBlank String merchant,@NotNull LocalDate expenseDate){}
 public record Result(int confidenceScore,String status,List<String> matchedClaimIds,String action){}
}
