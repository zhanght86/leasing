package com.pqsoft.pub.p2p.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.pub.p2p.algorithm.model.AlgorithmType;
import com.pqsoft.pub.p2p.algorithm.model.CalculateItem;
import com.pqsoft.pub.p2p.algorithm.model.CarryType;
import com.pqsoft.pub.p2p.algorithm.model.DigitType;
import com.pqsoft.pub.p2p.algorithm.model.IsRatioCalculate;
import com.pqsoft.pub.p2p.algorithm.model.MeasureResult;


/**
 * 
 * 算法组装
 * 
 * @className SubsideAlgorithm
 * @author 刘丽
 * @version V1.0 2016年3月30日 下午5:49:52
 */
public class Algorithm {

	public static void main(String[]args){
		Algorithm a = new Algorithm();
		
		CalculateItem model = new CalculateItem();
		
		model.setA(new BigDecimal("10000"));

		model.setAdditionalCosts(new BigDecimal("2000"));//额外费用
		
		model.setAlgorithmType(AlgorithmType.平息法);
		
		model.setC(new BigDecimal("0.12"));
	
		model.setCarryType(CarryType.四舍五入);
		
		model.setD(12);
		
		model.setDigitType(DigitType.两位小数);
		
		
		model.setClientAdministration(new BigDecimal("1500"));
		
		model.setIsRatioCalculate(IsRatioCalculate.yes);
		
		model.setMonthAdministration(new BigDecimal("1200"));
		
		model.setNblv(new BigDecimal("0.08"));
		
		
		a.getCalculateResult(model);
	}
    /**
     * 得到总的结果
     * [
     * 月租赁费用 yzlfy
     * 融资月利率 rzyll
     * 总合同金额 zhtje
     * 服务费 fwf
     * 
     * List
     * 
     * 
     * 总月租赁费用 zyzlfy
     * 总交易服务费 zjyfwf
     * 总租车服务费 zzcfwf
     * 总车辆本金 zclbj
     * 
     * ]
     * 
     * @author 刘丽
     * @since 2016年3月30日17:50:24
     * @param model 设置表达式的值
     * @return Map<String, Object> 合同总金额 list[期数 租金 本金 利息 剩余本金 管理费] 总期数 总租金 总本金 总利息
     */
    public Map<String, Object> getCalculateResult(CalculateItem model) {
    	 Map<String, Object> map = new HashMap<String, Object>();

         List<MeasureResult> list = new ArrayList<MeasureResult>(); // 列表

         BigDecimal yueZu = new MonthRentalExpense().getResult(model);// 月租赁费用

         BigDecimal rongZiYueLiLv = new FinancingMonthInterestRate().getResult(model);// 融资月利率

         BigDecimal heTongZongJinE = new TotalContractAmount().getResult(model, yueZu, rongZiYueLiLv);// 合同总金额

         BigDecimal fuWuFei = new ServiceCharge().getResult(model, heTongZongJinE);// 总服务费

         BigDecimal zuCheFuWuFei = new CarRentalService().getResult(model, fuWuFei);// 期数-1 租车服务费 最后一期单独计算

         BigDecimal jieKuanYuE = heTongZongJinE;

         BigDecimal JiaoYiServer =
             (new JiaoYiServiceFei().getResult(jieKuanYuE, rongZiYueLiLv)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP); // 交易服务费

         BigDecimal cheLiangBenJin =
             (new CheLiangBenJin().getResult(yueZu, JiaoYiServer, zuCheFuWuFei)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);// 车辆本金

         BigDecimal jieKuanYuES =
             (new CheLiangBenJin().getResult(jieKuanYuE, cheLiangBenJin, zuCheFuWuFei)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);// 借款余额

         BigDecimal oldjieKuanYuES = jieKuanYuES;

         // 总期数 -1 车辆本金总
         BigDecimal cheLiangBenJinTotal = new BigDecimal("0");

         BigDecimal jiaoYiFuWuFeiTotal = new BigDecimal("0");

         BigDecimal zuCheFuWuFeiTotal = new BigDecimal("0");
         
         //每期管理费
         if(model.getMonthAdministration()==null){
         	model.setClientAdministration(new BigDecimal("0"));
         }
         
         //合同上牌费
         
         BigDecimal buFenLiXi = new BigDecimal("0");
         
         BigDecimal shouXuFei  = new BigDecimal("0");
         
         BigDecimal buFenYueZu  = new BigDecimal("0");
         
         if(model.getClientAdministration() ==null || model.getClientAdministration().doubleValue()==0 || (model.getClientAdministration().subtract(model.getMonthAdministration())).doubleValue()<=0){}else{
         	
        	 //加部分利息
             
             BigDecimal neiBuLiLv = model.getNblv().divide(new BigDecimal(12),8, BigDecimal.ROUND_HALF_UP);
             
             buFenLiXi = (model.getClientAdministration().subtract(model.getMonthAdministration())).multiply(new BigDecimal("1").subtract(neiBuLiLv));

             buFenLiXi = buFenLiXi.setScale(2, BigDecimal.ROUND_HALF_UP);
             //加部分手续费
             
             shouXuFei = (model.getClientAdministration().subtract(model.getMonthAdministration())).multiply(neiBuLiLv);
             
             shouXuFei = shouXuFei.setScale(2, BigDecimal.ROUND_HALF_UP);
             
             //部分月租
             
             buFenYueZu = model.getClientAdministration().subtract(model.getMonthAdministration());
             
             System.out.println("buFenLiXi"+buFenLiXi+"shouXuFei"+shouXuFei+"buFenYueZu"+buFenYueZu);
         }
         
         if(model.getD()==null){
        	  new RuntimeException("期次不允许为空");
         }


         
         // 列表
         for (int i = 0; i < model.getD() - 1; i++) {

             MeasureResult mr = new MeasureResult();
             mr.setQs(i + 1);// 期数
             mr.setZj(yueZu);// 租金
             mr.setZcfwf(zuCheFuWuFei);// 租车服务费--
             if (i != 0) {

                 oldjieKuanYuES = jieKuanYuES;

                 JiaoYiServer =
                     (new JiaoYiServiceFei().getResult(oldjieKuanYuES, rongZiYueLiLv)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP); // 交易服务费

                 cheLiangBenJin =
                     (new CheLiangBenJin().getResult(yueZu, JiaoYiServer, zuCheFuWuFei)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);// 车辆本金

                 jieKuanYuES = (new CheLiangBenJin().getResult(oldjieKuanYuES, cheLiangBenJin, zuCheFuWuFei)).divide(new BigDecimal("1"), 2,
                     BigDecimal.ROUND_HALF_UP);// 借款余额
             }

             mr.setSybj(jieKuanYuES);// 借款余额
             mr.setJyfwf(JiaoYiServer);// 交易费--
             mr.setBj(cheLiangBenJin);// 车辆本金
             mr.setGlf(model.getMonthAdministration());//每期管理费

             cheLiangBenJinTotal = cheLiangBenJinTotal.add(cheLiangBenJin);
             jiaoYiFuWuFeiTotal = jiaoYiFuWuFeiTotal.add(JiaoYiServer);
             zuCheFuWuFeiTotal = zuCheFuWuFeiTotal.add(zuCheFuWuFei);
             list.add(mr);
         }

        
       
         MeasureResult zmr = new MeasureResult();
         zmr.setGlf(model.getMonthAdministration());//每期管理费
         zmr.setQs(model.getD());// 期数
         zmr.setZj(yueZu);// 租金
         zmr.setZcfwf(
           (  (fuWuFei.subtract(zuCheFuWuFei.multiply(new BigDecimal(model.getD() - 1)))).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP)));// 最后一期租车服务费--
         zuCheFuWuFeiTotal = zuCheFuWuFeiTotal.add(zmr.getZcfwf());
         // 最后一期车辆本金
         if (model.getA() == null) {
             throw new RuntimeException("融资额不能为空");
         }else {
         	model.setA(model.getA().subtract(model.getAdditionalCosts()));
         }
         
         BigDecimal lastCheLiangBenJin = (model.getA().subtract(cheLiangBenJinTotal)).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);
         cheLiangBenJinTotal = cheLiangBenJinTotal.add(lastCheLiangBenJin);
         zmr.setBj(lastCheLiangBenJin);
         // C12-SUM(D16:D50)
         // 最有一期借款余额
         BigDecimal lastJieKuanYuES = (new CheLiangBenJin().getResult(jieKuanYuES, lastCheLiangBenJin, zmr.getZcfwf())).divide(new BigDecimal("1"), 2,
             BigDecimal.ROUND_HALF_UP);// 借款余额

         System.out.println(lastJieKuanYuES + "-------" + jieKuanYuES + "-----" + cheLiangBenJin + "---" + zuCheFuWuFei);
         zmr.setSybj(lastJieKuanYuES);

         // 最后一期交易服务费
         JiaoYiServer = (yueZu.subtract(lastCheLiangBenJin).subtract(zmr.getZcfwf())).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP); // 交易服务费
         zmr.setJyfwf(JiaoYiServer);//--
         jiaoYiFuWuFeiTotal = jiaoYiFuWuFeiTotal.add(JiaoYiServer);

         list.add(zmr);

         // 月租赁费用 总费用

         
         //-----------------
         
         //租金 + 管理费
         for(int i=0;i<list.size();i++){
          	MeasureResult measureResult = list.get(i);
          	BigDecimal zj = measureResult.getZj();
          	BigDecimal zjAddglf = zj.add(measureResult.getGlf());
          	measureResult.setZj(zjAddglf);
         }
         
         //----------------
         
         zuCheFuWuFeiTotal =new BigDecimal("0");
         jiaoYiFuWuFeiTotal = new BigDecimal("0");
         BigDecimal bdTotalRent = new BigDecimal("0");
         for(int i=0;i<list.size();i++){
            MeasureResult measureResult = list.get(i);
        	measureResult.setZcfwf( measureResult.getZcfwf().add(buFenLiXi));
        	measureResult.setJyfwf( measureResult.getJyfwf().add(shouXuFei));
        	measureResult.setZj(measureResult.getZj().add(buFenYueZu));
        	zuCheFuWuFeiTotal=zuCheFuWuFeiTotal.add(measureResult.getZcfwf());
        	jiaoYiFuWuFeiTotal=jiaoYiFuWuFeiTotal.add(measureResult.getJyfwf());
        	bdTotalRent = bdTotalRent.add(measureResult.getZj());
         }
         
         // list
         map.put("list", list);// 列表

         map.put("yzlfy", yueZu);

         map.put("rzyll", rongZiYueLiLv);

         map.put("zhtje", heTongZongJinE);

         map.put("fwf", fuWuFei);

         // 总月租赁费用 zyzlfy
         map.put("zyzlfy", bdTotalRent);//(yueZu.multiply(new BigDecimal(model.getD()))).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP));
         // 总交易服务费 zjyfwf
         map.put("zjyfwf", jiaoYiFuWuFeiTotal);
         // 总租车服务费 zzcfwf
         map.put("zzcfwf", zuCheFuWuFeiTotal);
         // 总车辆本金 zclbj
         map.put("zclbj", cheLiangBenJinTotal);

         return map;
    }

}
