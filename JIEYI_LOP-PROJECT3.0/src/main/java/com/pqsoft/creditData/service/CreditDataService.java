package com.pqsoft.creditData.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pqsoft.creditData.model.*;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * 征信数据的业务处理
 *
 * @className CreditDataService
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class CreditDataService {

//    private static final Logger logger = Logger.getLogger(CreditDataService.class);
    private ExcelConfigService excelConfigService = ExcelConfigService.getInstance();
    private static final char[] ELEMENTS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};
    private Map<Character, Integer> columnIndexMap;

    private static CreditDataService creditDataService;

    public static CreditDataService getInstance(){
        if(creditDataService == null){
            synchronized (CreditDataService.class){
                if(creditDataService == null){
                    creditDataService = new CreditDataService();
                }
            }
        }
        return creditDataService;
    }

    private CreditDataService(){
        columnIndexMap = new HashMap<>();
        for(int i = 0; i < ELEMENTS.length; i++){
            columnIndexMap.put(ELEMENTS[i], i + 1);
        }
    }

    /**
     * 通过客户id抽取相应的征信数据
     *
     * @param id 客户id
     * @return 征信数据
     */
    public CreditData extractCreditDataById(Integer id){
        CreditData creditData = new CreditData();
        creditData.setClientId(id);
//        CreditData credit = Dao.selectOne("CreditDataMapper.selectCreditData", id);
        // 身份信息
        List<Identity> identityList = Dao.selectList("IdentityMapper.extractIdentity", id);
        manageIdentity(identityList);
        // 职业信息
        List<Occupation> occupationList = Dao.selectList("OccupationMapper.extractOccupation", id);
        manageOccupation(occupationList);
        // 居住信息
        List<Residence> residenceList = Dao.selectList("ResidenceMapper.extractResidence", id);
        manageResidence(residenceList);
        // 申请信息
        List<LoanApplication> loanApplicationList = Dao.selectList("LoanApplicationMapper.extractApplication", id);
        manageApplication(loanApplicationList);
        // 业务信息
        List<LoanService> loanServiceList = Dao.selectList("LoanServiceMapper.extractService", id);
        manageLoanService(loanServiceList);
        // 合同信息
        List<LoanContract> loanContractList = Dao.selectList("LoanContractMapper.extractContract", id);
        manageContract(loanContractList);
        // 担保信息
        List<Guarantee> guaranteeList = Dao.selectList("GuaranteeMapper.extractGuarantee", id);
        manageGuarantee(guaranteeList);
//        List<Investor> investorList = Dao.selectList("InvestorMapper.extractInvestor");
//        List<SpecialTransaction> specialTransactionList = Dao.selectList("SpecialTransactionMapper.extractTransaction");
        creditData.setIdentityList(identityList);
        creditData.setOccupationList(occupationList);
        creditData.setResidenceList(residenceList);
        creditData.setLoanApplicationList(loanApplicationList);
        creditData.setLoanServiceList(loanServiceList);
        creditData.setLoanContractList(loanContractList);
        creditData.setGuaranteeList(guaranteeList);
//        creditData.setInvestorList(investorList);
//        creditData.setSpecialTransactionList(specialTransactionList);
        return creditData;
    }

    /**
     * 处理担保信息
     *
     * @param guaranteeList 担保信息集合
     */
    private void manageGuarantee(List<Guarantee> guaranteeList) {
        if(CollectionUtils.isEmpty(guaranteeList)){
            return;
        }

        for(Guarantee guarantee : guaranteeList){
            // 担保金额向下去整
            guarantee.setGuaranteeAmount(downRoundWhole(guarantee.getGuaranteeAmount()));
            // 担保人证件号限定最大位数18
            guarantee.setGuarantorCredentialNo(cut(guarantee.getGuarantorCredentialNo(), 18));
            // 担保人名字限定最大位数30
            guarantee.setGuarantorName(cut(guarantee.getGuarantorName(), 30));
            // 默认值:担保，唯一选择
            guarantee.setGuaranteeStatus("1");
        }
    }

    /**
     * 处理合同信息
     *
     * @param loanContractList 合同信息集合
     */
    private void manageContract(List<LoanContract> loanContractList) {
        if(CollectionUtils.isEmpty(loanContractList)){
            return;
        }

        for(LoanContract contract : loanContractList){
            // 合同金额向下取整
            contract.setContractAmount(downRoundWhole(contract.getContractAmount()));
            // 合同号限定最大位数60
            contract.setContractNo(cut(contract.getContractNo(), 60));
            // 货币类型默认人民币
            contract.setCurrency("CNY");
            // 处理合同有效状态
            manageContractStatus(contract);

        }
    }

    /**
     * 处理合同有效状态
     *
     * @param contract 合同对象
     */
    private void manageContractStatus(LoanContract contract) {
        if("100".equals(contract.getValidStatus())){
            contract.setValidStatus("1");
        }else{
            contract.setValidStatus("0");
        }
    }

    /**
     * 处理申请信息
     *
     * @param loanApplicationList 申请信息集合
     */
    private void manageApplication(List<LoanApplication> loanApplicationList) {
        if(CollectionUtils.isEmpty(loanApplicationList)){
            return;
        }

        for(LoanApplication loanApplication : loanApplicationList){
            // 处理申请状态-本地数据和征信数据的转换
            manageApplicationStatus(loanApplication);
            // 申请金额向下取整
            loanApplication.setApplicationAmount(downRoundWhole(loanApplication.getApplicationAmount()));
            // 申请号限定最大位数40
            loanApplication.setApplicationNo(cut(loanApplication.getApplicationNo(), 40));
            // 申请类型默认车贷
            loanApplication.setApplicationType("21");
        }
    }

    /**
     * 处理申请状态
     *
     * @param loanApplication 申请状态
     */
    private void manageApplicationStatus(LoanApplication loanApplication) {
        String applicationStatus = loanApplication.getApplicationStatus();
        //征信接口 1 申请中 2 已批准 3 未通过
        //项目签约
        if("20".equals(applicationStatus)){
            loanApplication.setApplicationStatus("2");
        }
        //评审不通过 项目作废
        else if("27".equals(applicationStatus) || "100".equals(applicationStatus)){
            loanApplication.setApplicationStatus("3");
        }else if(!StringUtils.isEmpty(applicationStatus)){
            loanApplication.setApplicationStatus("1");
        }else{
            throw new RuntimeException("未知的贷款申请状态！");
        }
    }

    /**
     * 处理居住信息
     *
     * @param residenceList 居住信息集合
     */
    private void manageResidence(List<Residence> residenceList) {
        if(CollectionUtils.isEmpty(residenceList)){
            return;
        }

        for(Residence residence : residenceList){
            // 处理居住地邮编
            manageResidentialAddressZipCode(residence);
            // 处理居住地址
            manageResidentialAddress(residence);
            // 居住状况默认未知
            residence.setResidentialStatus("9");
        }
    }

    /**
     * 处理居住地邮编
     *
     * @param residence 居住对象
     */
    private void manageResidentialAddressZipCode(Residence residence) {
        String zipCode = manageZipCode(residence.getResidentialAddressZipCode());
        residence.setResidentialAddressZipCode(zipCode);
    }

    /**
     * 处理居住地址（拼接居住省市区和地址信息）
     *
     * @param residence 居住信息对象
     */
    private void manageResidentialAddress(Residence residence) {
        // 居住省
        String province = residence.getResidentialProvince() == null ? "" : residence.getResidentialProvince();
        // 市
        String city = residence.getResidentialCity() == null ? "" : residence.getResidentialCity();
        // 区（县）
        String county = residence.getResidentialCounty() == null ? "" : residence.getResidentialCounty();
        String address = province;
        // 直辖市在本地地址表中省市信息会重复
        if(!province.equals(city)){
            address += city;
        }
        address += county;
        if(!Strings.isNullOrEmpty(residence.getResidentialAddress())){
            address += residence.getResidentialAddress();
        }
        // 限定最大位数60
        residence.setResidentialAddress(cut(address, 60));
    }

    /**
     * 处理职业信息
     *
     * @param occupationList 职业信息集合
     */
    private void manageOccupation(List<Occupation> occupationList) {
        if(CollectionUtils.isEmpty(occupationList)){
            return;
        }

        for(Occupation occupation : occupationList){
            // 年收入向下取整
            occupation.setAnnualIncome(downRoundWhole(occupation.getAnnualIncome()));
            // 转换入职时间，只取年份
            String startDate = occupation.getStartYearInWorkingPlace();
            if(!Strings.isNullOrEmpty(startDate)){
                occupation.setStartYearInWorkingPlace(startDate.trim().substring(0,4));
            }
            // 限定工作地邮编最大位数6
            manageWorkingAddressZipCode(occupation);
            // 处理工作地址
            manageWorkingAddress(occupation);
            // 处理工作单位
            manageWorkingPlace(occupation);
            // 职业默认未知
            occupation.setOccupation("Z");

            // 行业信息为空是填未知
            if(Strings.isNullOrEmpty(occupation.getWorkingIndustry())){
                occupation.setWorkingIndustry("Z");
            }

            // 职务默认未知
            occupation.setPost("9");
            // 职称默认未知
            occupation.setTitle("9");
        }
    }

    /**
     * 处理工作地址邮编
     *
     * @param occupation 职业对象
     */
    private void manageWorkingAddressZipCode(Occupation occupation) {
        String zipCode = manageZipCode(occupation.getWorkingAddressZipCode());
        occupation.setWorkingAddressZipCode(zipCode);
    }

    /**
     * 处理工作单位
     * 空值填暂缺，限定长度60
     *
     * @param occupation 职业对象
     */
    private void manageWorkingPlace(Occupation occupation) {
        String workingPlace = occupation.getWorkingPlace();
        if(Strings.isNullOrEmpty(workingPlace)){
            occupation.setWorkingPlace("暂缺");
        }else{
            occupation.setWorkingPlace(cut(workingPlace, 60));
        }
    }

    /**
     * 处理工作地址
     * 拼接工作省市区和地址信息，限定最大长度60
     *
     * @param occupation 职业对象
     */
    private void manageWorkingAddress(Occupation occupation) {
        // 工作省
        String province = occupation.getWorkingProvince() == null ? "" : occupation.getWorkingProvince();
        // 工作市
        String city = occupation.getWorkingCity() == null ? "" : occupation.getWorkingCity();
        // 工作区（县）
        String county = occupation.getWorkingCounty() == null ? "" : occupation.getWorkingCounty();
        String address = province;
        // 直辖市在本地地址表中省市信息会重复
        if(!province.equals(city)){
            address += city;
        }
        address += county;
        if(!Strings.isNullOrEmpty(occupation.getWorkingAddress())){
            address += occupation.getWorkingAddress();
        }
        // 限定最大位数60
        occupation.setWorkingAddress(cut(address, 60));
    }

    /**
     * 处理身份信息
     *
     * @param identityList 身份信息集合
     */
    private void manageIdentity(List<Identity> identityList) {
        if(CollectionUtils.isEmpty(identityList)){
            return;
        }

        for(Identity identity : identityList){
            // 处理联系人信息
            manageContactList(identity);
            // 限定配偶联系电话最大位数25
            identity.setSpousePhone(cut(identity.getSpousePhone(), 25));
            // 限定配偶工作单位名称最大位数60
            identity.setSpouseWorkingPlace(cut(identity.getSpouseWorkingPlace(), 60));
            // 限定配偶证件号最大位数18
            identity.setSpouseCredentialNo(cut(identity.getSpouseCredentialNo(), 18));
            // 本地无配偶证件类型信息，所有存在配偶名称的情况下，将证件类型设为身份证
            if(!Strings.isNullOrEmpty(identity.getSpouseName())){
                identity.setSpouseCredentialType("0");
                // 限定配偶名字最大位数30
                identity.setSpouseName(cut(identity.getSpouseName(), 30));
            }
            // 限定户口所在地最大位数60
            identity.setHouseholdRegisterAddress(cut(identity.getHouseholdRegisterAddress(), 60));
            // 处理通信地址邮编
            manageMailingAddressZipCode(identity);
            // 处理通信地址
            manageMailingAddress(identity);
            // 限定电子邮箱最大长度30
            identity.setEmail(cut(identity.getEmail(), 30));
            // 限定单位电话最大长度25
            identity.setWorkingPhone(cut(identity.getWorkingPhone(), 25));
            // 限定手机号最大长度16
            identity.setCellPhone(cut(identity.getCellPhone(), 16));
            // 限定家里电话最大长度25
            identity.setResidencePhone(cut(identity.getResidencePhone(), 25));

            // 婚姻状况缺失为未知
            if(Strings.isNullOrEmpty(identity.getMaritalStatus())){
                identity.setMaritalStatus("90");
            }

            // 处理学位信息
            manageDegree(identity);
            // 处理生日
            manageBirthday(identity);
            // 限定证件号最大长度18
            identity.setCredentialNo(cut(identity.getCredentialNo(), 18));
            // 限定名字最大长度30
            identity.setFullName(cut(identity.getFullName(), 30));
        }
    }

    /**
     * 处理通信地址邮编
     *
     * @param identity 身份信息
     */
    private void manageMailingAddressZipCode(Identity identity) {
        String zipCode = manageZipCode(identity.getMailingAddressZipCode());
        identity.setMailingAddressZipCode(zipCode);
    }

    /**
     * 处理邮编
     * 空值填999999，最大长度限制6
     *
     * @param zipCode 邮编字符串
     * @return 符合需要的字符串
     */
    private String manageZipCode(String zipCode) {
        String result;
        if(Strings.isNullOrEmpty(zipCode)){
            result = "999999";
        }else{
            result = cut(zipCode, 6);
        }
        return result;
    }

    /**
     * 处理生日信息
     * 转换格式为yyyyMMdd, 空值填写19010101
     *
     * @param identity 身份信息对象
     */
    private void manageBirthday(Identity identity) {
        String birthday = identity.getBirthday();
        if(!StringUtils.isEmpty(birthday)){
            StringBuilder sb = new StringBuilder();
            for(String date : birthday.split("-")){
                sb.append(date);
            }
            identity.setBirthday(sb.toString());
        }else{
            identity.setBirthday("19010101");
        }
    }

    /**
     * 根据学历信息获得相应的学位
     *
     * @param identity 身份信息对象
     */
    private void manageDegree(Identity identity) {

        String education = identity.getHighestEducation();
        if(Strings.isNullOrEmpty(education)){
            identity.setHighestEducation("99");
            identity.setHighestDegree("9");
            return;
        }

        //研究生学历
        if("10".equals(identity.getHighestEducation())){
            //硕士学位
            identity.setHighestDegree("3");
        }
        //本科学历
        else if("20".equals(identity.getHighestEducation())){
            //学士学位
            identity.setHighestDegree("4");
        }else{
            //其他
            identity.setHighestDegree("0");
        }
    }

    /**
     * 处理通信地址
     * 拼接通信省市区和地址信息，限定最大长度60
     *
     * @param identity 身份信息
     */
    private void manageMailingAddress(Identity identity) {
        // 通信省
        String province = identity.getMailingProvince() == null ? "" :  identity.getMailingProvince();
        // 通信市
        String city =  identity.getMailingCity() == null ? "" :  identity.getMailingCity();
        // 通信区（县）
        String county =  identity.getMailingCounty() == null ? "" :  identity.getMailingCounty();
        String address = province;
        // 直辖市在本地数据库中省市信息会重复
        if(!province.equals(city)){
            address += city;
        }
        address += county;
        if(!Strings.isNullOrEmpty(identity.getMailingAddress())){
            address += identity.getMailingAddress();
        }
        // 限定最大位数60
        identity.setMailingAddress(cut(address, 60));
    }

    /**
     * 通过所有的联系人信息转换成符合的第一，第二联系人信息
     *
     * @param identity 身份信息
     */
    private void manageContactList(Identity identity) {
        List<Contact> contactList = identity.getContactList();
        if(CollectionUtils.isEmpty(contactList)){
            return;
        }
        // 关系为父母的联系人容器
        List<Contact> parents = new ArrayList<>();
        String relationship;
        String name;
        String phone;
        for(Contact contact : contactList){
            //联系人与客户的关系
            relationship = contact.getRelationship();

            //关系为父母的话放入容器并进入下一轮循环
            if("1".equals(relationship)){//1 父母 2 子女 3 配偶 4 亲属 5 朋友 6 同事 7 其他
                parents.add(contact);
                continue;
            }

            //联系人姓名
            name = cut(contact.getName(), 30);
            //联系人电话
            phone = cut(encryptPhone(contact.getPhone()), 25);

            //联系人信息不完整跳过本轮循环
            if(StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(relationship)){
                continue;
            }

            //如果第一联系人信息为空
            if(StringUtils.isEmpty(identity.getFirstContactName())
                    || StringUtils.isEmpty(identity.getFirstContactPhone())
                    || StringUtils.isEmpty(identity.getFirstContactRelationship())){
                //设置第一联系人信息
                identity.setFirstContactName(name);
                identity.setFirstContactPhone(phone);
                identity.setFirstContactRelationship(getRelationship(relationship));
            }
            //如果第二联系人信息为空
            else if(StringUtils.isEmpty(identity.getSecondContactName())
                    || StringUtils.isEmpty(identity.getSecondContactPhone())
                    || StringUtils.isEmpty(identity.getFirstContactRelationship())){
                //设置第二联系人信息
                identity.setSecondContactName(name);
                identity.setSecondContactPhone(phone);
                identity.setSecondContactRelationship(getRelationship(relationship));
            }
            //如果两位联系人信息都已获取到则退出循环
            else {
                break;
            }
        }

        //如果非父母联系人不存在或不完整导致第一联系人信息依旧为空
        if(StringUtils.isEmpty(identity.getFirstContactName())
                || StringUtils.isEmpty(identity.getFirstContactPhone())
                || StringUtils.isEmpty(identity.getFirstContactRelationship())){

            // 遍历父母联系人
            for(Contact contact : parents){
                name = contact.getName();
                phone = contact.getPhone();
                relationship = contact.getRelationship();

                //第一联系人信息存在则跳出循环
                if(!StringUtils.isEmpty(identity.getFirstContactName())
                        && !StringUtils.isEmpty(identity.getFirstContactPhone())
                        && !StringUtils.isEmpty(identity.getFirstContactRelationship())){
                    break;
                }

                //设置第一联系人信息
                identity.setFirstContactName(name);
                identity.setFirstContactPhone(phone);
                identity.setFirstContactRelationship(getRelationship(relationship));
            }
        }

        //第一联系人信息依旧不存在则为异常数据
//        Preconditions.checkArgument(!(StringUtils.isEmpty(identity.getFirstContactName())
//                || StringUtils.isEmpty(identity.getFirstContactPhone())
//                || StringUtils.isEmpty(identity.getFirstContactRelationship())), "无可用的客户联系人信息！");
    }

    /**
     * 通过本地数据的关系获取符合征信数据要求的关系
     *
     * @param relationship 联系人与客户的关系（本地数据字符串表示）
     * @return 联系人与客户的关系（征信数据字符串表示）
     */
    private String getRelationship(String relationship){

        String standard;
        //内部数据 --- 1 父母 2 子女 3 配偶 4 亲属 5 朋友 6 同事 7 其他
        //征信接口 --- 0 父子（女） 1 母子（女） 2 配偶 3 子女 4 其他亲属 5 同事 6 朋友 7 兄弟姐妹 8 其他
        if("1".equals(relationship)){
            standard = "0";
        }else if("2".equals(relationship)){
            standard = "3";
        }else if("3".equals(relationship)){
            standard = "2";
        }else if("4".equals(relationship)){
            standard = "4";
        }else if("5".equals(relationship)){
            standard = "6";
        }else if("6".equals(relationship)){
            standard = "5";
        }else if("7".equals(relationship)){
            standard = "8";
        }else {
            throw new RuntimeException("联系人与客户的关系未知！");
        }

        return standard;
    }

    /**
     * 处理业务信息
     * 补全逾期信息，月状态等
     *
     * @param loanServiceList 业务信息结合
     */
    private void manageLoanService(List<LoanService> loanServiceList) {
//        Preconditions.checkArgument(!CollectionUtils.isEmpty(loanServiceList), "贷款业务信息为空！");
        if(CollectionUtils.isEmpty(loanServiceList)){
            return;
        }

        // 是否新业务的标记（新合同是指当前日期在签约日期和第一期还款日期之间）
        String hint;
        // fil_project_head表id
        Long projectId;
        // 结算期次
        Integer settlementPeriod;
        // 免息天数，由于判断当期的结算，即结算日期晚于当期应付日期+免息天数的情况下，在当期看来，该结算未生效
        int freeDays = Dao.selectOne("DataMapper.selectFreeDays");
        // 已结算期数
        String settledCount;
        // 总期数
        String totalPeriods;
        // dao参数容器
        Map<String, Object> params = new HashMap<>();

        for(LoanService loanService : loanServiceList){
            // 新业务初始化
            initService(loanService);
            params.clear();
            hint = loanService.getHintForAccountOwner();
            // 不是新业务
            if("1".equals(hint)){

                projectId = loanService.getProjectId();
                totalPeriods = loanService.getAgreedPeriodsToRepay();

                params.put("projectId", projectId);
                params.put("freeDays", freeDays);

                // 结清期数统计 判断是否结清
                settledCount = Dao.selectOne("LoanServiceMapper.selectSettledCount", projectId);

                // 结清
                if(settledCount.equals(totalPeriods)){
                    // 余额归零
                    loanService.setBalance(BigDecimal.ZERO);

                    // 判断是否提前结清
                    settledCount = Dao.selectOne("LoanServiceMapper.selectSettledCountInAdvance", projectId);

                    // 提前结清
                    if(settledCount.equals(totalPeriods)){
                        settleServiceInAdvance(loanService, params);
                    }
                    // 非提前结清
                    else{
                        Date settlementDate = DateUtil.parseDate(loanService.getDeadline(), "yyyyMMdd");
                        Date latestRepaymentDate = DateUtil.parseDate(loanService.getLatestRepaymentDate(), "yyyyMMdd");

                        params.put("settlementDate", settlementDate);
                        params.put("settlementPeriod", loanService.getAgreedPeriodsToRepay());
                        params.put("latestSettlementPeriod", loanService.getAgreedPeriodsToRepay());

                        // 最高逾期期数
                        String maxOverduePeriods = getMaxOverduePeriods(loanService, params);
                        loanService.setMaxOverduePeriods(maxOverduePeriods);
                        // 账户状态 -结清
                        loanService.setAccountStatus("3");

                        // 最后还款日超过最后一期结算日{免息天数}以上 -超期后结清
                        if(DateUtil.diffDate(latestRepaymentDate, settlementDate) > freeDays){
                            settleExtendedService(loanService, params);
                        }
                        // 按时结清
                        else {
                            settleOnTimeService(loanService, params);
                        }
                    }
                }
                // 未结清 -进行中业务
                else{
                    // 当前结算期 -判断是否到期
                    Map<String, Object> result = Dao.selectOne("LoanServiceMapper.selectSettlementPeriodAndDate", projectId);
                    try{
                        settlementPeriod = Integer.parseInt((String) result.get("SETTLEMENT_PERIOD"));
                    }
                    catch (NumberFormatException e){
                        return;
                    }
                    params.put("settlementPeriod", settlementPeriod);
                    params.put("latestSettlementPeriod", settlementPeriod);
                    params.put("settlementDate", result.get("SETTLEMENT_DATE"));

//                    Preconditions.checkNotNull(settlementPeriod, "无法确定结算期次！");

                    // 到期 -超期业务
                    if(String.valueOf(settlementPeriod).equals(loanService.getAgreedPeriodsToRepay())){
                        conductExtendService(loanService, params);
                    }
                    // 没到期 -期内业务
                    else{
                        conductServiceInPeriod(loanService, params);
                    }
                }
            }
        }
    }

    /**
     * 处理到期未结清的业务
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void conductExtendService(LoanService loanService, Map<String, Object> params) {
        // 超期的最近结算和还款日期
        manageExtendedSettlementDate(loanService, params);

        // 超期应还款额和实际还款额
        manageExtendedAmountOfThisMonth(loanService, params);

        // 超期逾期信息
        manageExtendedOverdue(loanService, params);

        // 24月还款状态
        String latest24MonthsStatus = getExtendedLatest24MonthsStatus(loanService, params);

        // 最高逾期期数
        String maxOverduePeriods = getMaxOverduePeriods(loanService, params);

        loanService.setMaxOverduePeriods(maxOverduePeriods);
        loanService.setLatest24MonthsStatus(latest24MonthsStatus);
        // 账户状态-逾期
        loanService.setAccountStatus("2");
    }

    /**
     * 超期结清的业务
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void settleExtendedService(LoanService loanService, Map<String, Object> params) {
        // 更新结算日期
        loanService.setSettlementDate(calculateSettlementDate(params));
        // 结算期的应还款和实际还款
        manageExtendedAmountOfThisMonth(loanService, params);

        // 24月还款状态
        String latest24MonthsStatus = getSettledExtendedMonthsStatus(loanService, params);
        loanService.setLatest24MonthsStatus(latest24MonthsStatus);
    }

    /**
     * 按时结清的业务
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void settleOnTimeService(LoanService loanService, Map<String, Object> params) {
        loanService.setSettlementDate(DateUtil.format((Date)params.get("settlementDate"), "yyyyMMdd"));

        // 月应还款额和实际还款额
        manageAmountOfThisMonth(loanService, params);

        // 24月还款状态
        String latest24MonthsStatus = getSettledLatest24MonthsStatus(loanService, params, loanService.getTotalOverduePeriods().equals("0"));
        loanService.setLatest24MonthsStatus(latest24MonthsStatus);
    }

    /**
     * 进行中的业务处理
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void conductServiceInPeriod(LoanService loanService, Map<String, Object> params) {
        loanService.setSettlementDate(DateUtil.format((Date) params.get("settlementDate"), "yyyyMMdd"));
        // 累计逾期期数判断是否有过逾期
        if(!"0".equals(loanService.getTotalOverduePeriods())){
            conductGeneralService(loanService, params);
        }
        // 从未逾期
        else{
            conductExcellentService(loanService, params);
        }
    }

    /**
     * 进行中的一般业务处理
     *
     * @param loanService 业务信息
     * @param params dao参数集合
     */
    private void conductGeneralService(LoanService loanService, Map<String, Object> params) {
        // 结算月的应还款额和实际还款额
        manageAmountOfThisMonth(loanService, params);

        // 逾期信息
        manageOverdue(loanService, params);

        // 当前结算月状态
        Integer overduePeriods = Integer.valueOf(loanService.getCurrentOverduePeriods());
        String currentStatus = getStatus(overduePeriods);

        // 24月还款状态
        String latest24MonthsStatus = getLatest24MonthsStatus(loanService, params, currentStatus, false);

        // 累计逾期期数
        String maxOverduePeriods = getMaxOverduePeriods(loanService, params);

        loanService.setAccountStatus(currentStatus.equals("N")? "1" : "2");
        loanService.setMaxOverduePeriods(maxOverduePeriods);
        loanService.setLatest24MonthsStatus(latest24MonthsStatus);
    }

    /**
     * 进行中的优秀业务（无逾期和历史逾期）
     * 逾期相关数据为初始化业务的数据，即都为0，不做查询
     *
     * @param loanService 业务信息
     * @param params dao参数
     */
    private void conductExcellentService(LoanService loanService, Map<String, Object> params) {
        // 当前结算月的应还款和实际还款额
        manageAmountOfThisMonth(loanService, params);
        // 账户状态-正常
        loanService.setAccountStatus("1");
        // 24月还款状态
        String latest24MonthsStatus = getExcellentLatest24MonthsStatus(loanService, params);

        loanService.setLatest24MonthsStatus(latest24MonthsStatus);
    }

    /**
     * 提前结清的业务处理
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void settleServiceInAdvance(LoanService loanService, Map<String, Object> params){
        // 结清日期和最近还款日期
        manageSettlementDateInAdvance(loanService, params);

        // 结清当期的应还款额和实际还款额
        manageAmountOfThisMonth(loanService, params);

        // 最近24月还款状态
        String latest24MonthsStatus = getSettledLatest24MonthsStatus(loanService, params, loanService.getTotalOverduePeriods().equals("0"));
        loanService.setLatest24MonthsStatus(latest24MonthsStatus);

        // 最高逾期期数
        String maxOverduePeriods = getMaxOverduePeriods(loanService, params);
        loanService.setMaxOverduePeriods(maxOverduePeriods);

        // 账户状态-结清
        loanService.setAccountStatus("3");
        // 提前结清余额清零
        loanService.setBalance(BigDecimal.ZERO);
    }

    /**
     * 提前结清-结清当期的结算和还款日期
     *
     * @param loanService 业务对象
     * @param params dao参数集合
     */
    private void manageSettlementDateInAdvance(LoanService loanService, Map<String, Object> params) {
        Map<String, String> result = Dao.selectOne("LoanServiceMapper.selectSettlementDateInAdvance", params);
        String settlementDate = result.get("SETTLEMENT_DATE");
        String settlementPeriod = result.get("LATEST_SETTLEMENT_PERIOD");
        loanService.setSettlementDate(settlementDate);

        params.put("settlementDate", DateUtil.parseDate(loanService.getSettlementDate(), "yyyyMMdd"));
        params.put("settlementPeriod", settlementPeriod);
        params.put("latestSettlementPeriod", settlementPeriod);
    }

    /**
     * 超期-结算当月的应还款额和实际还款额
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void manageExtendedAmountOfThisMonth(LoanService loanService, Map<String, Object> params) {
        loanService.setExpectedAmountOfThisMonth(loanService.getCurrentOverdueAmount());

        // 最后一期的结算日
        Date lastPeriodSettlementDate = (Date) params.get("settlementDate");
        // 当前结算日
        Date settlementDate = DateUtil.parseDate(loanService.getSettlementDate(), "yyyyMMdd");
        // 上一个结算日
        Date lastSettlementDate;
        // 当前结算日和最后一期的结算日的月份差是否小于2（即当前结算月的上个月就是最后一期的结算日）
        if(DateUtil.getMonthDifference(settlementDate, lastPeriodSettlementDate) < 2){
            lastSettlementDate = lastPeriodSettlementDate;
        }else{
            lastSettlementDate = DateUtils.addMonths(settlementDate, -1);
        }
        params.put("lastSettlementDate", lastSettlementDate);
        params.put("settlementDate", settlementDate);

        BigDecimal actualAmount = Dao.selectOne("LoanServiceMapper.selectActualAmount", params);
        loanService.setActualAmountOfThisMonth(downRoundWhole(actualAmount));
    }

    /**
     * 超期-逾期数据
     *
     * @param loanService 业务信息
     * @param params dao参数
     */
    private void manageExtendedOverdue(LoanService loanService, Map<String, Object> params) {
        manageExtendedOverdueAmountAndPeriods(loanService, params);
        manageExtendedOverduePrinciple(loanService, params);
    }

    /**
     * 超期-逾期本金
     *
     * @param loanService 业务数据
     * @param params dao参数
     */
    private void manageExtendedOverduePrinciple(LoanService loanService, Map<String, Object> params) {
        Date settlementDate = DateUtil.parseDate(loanService.getSettlementDate(), "yyyyMMdd");
        params.put("settlementDate", settlementDate);

        // 逾期31到60天未归还本金
        params.put("from", 31);
        params.put("to", 60);
        BigDecimal overdue31To60Days = Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params);
        // 61到90天
        params.put("from", 61);
        params.put("to", 90);
        BigDecimal overdue61To90Days = Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params);
        // 91到180天
        params.put("from", 91);
        params.put("to", 180);
        BigDecimal overdue91To180Days = Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params);
        // 180天以上
        params.put("from", 181);
        params.remove("to");
        BigDecimal overdueOver180Days = Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params);

        loanService.setOverdue31To60Days(downRoundWhole(overdue31To60Days));
        loanService.setOverdue61To90Days(downRoundWhole(overdue61To90Days));
        loanService.setOverdue91To180Days(downRoundWhole(overdue91To180Days));
        loanService.setOverdueOver180Days(overdueOver180Days);

    }

    /**
     * 获得最高逾期期数
     *
     * @param loanService 业务对象
     * @param params dao参数
     * @return 最高逾期期数
     */
    private String getMaxOverduePeriods(LoanService loanService, Map<String, Object> params) {
        Integer settlementPeriod = (Integer) params.get("latestSettlementPeriod");
        // 每一期的逾期期数集合
        List<Integer> overduePeriodsList = new ArrayList<>();
        // 将当前结算期的逾期期数加入集合
        overduePeriodsList.add(Integer.parseInt(loanService.getCurrentOverduePeriods()));

        Integer overduePeriods;
        // 遍历每一个结算期
        for(int i = 1; i < settlementPeriod; i++){
            params.put("settlementPeriod", i);
            // 当期的逾期期数
            overduePeriods = Dao.selectOne("LoanServiceMapper.selectOverduePeriods", params);
            // 加入集合
            overduePeriodsList.add(overduePeriods);
        }

        // 排序找到最大值
        Collections.sort(overduePeriodsList);

        return String.valueOf(overduePeriodsList.get(overduePeriodsList.size() - 1));
    }

    /**
     * 获取超期的24月还款状态
     *
     * @param loanService 业务信息
     * @param params dao参数
     * @return 超期的24月还款状态
     */
    private String getExtendedLatest24MonthsStatus(LoanService loanService, Map<String, Object> params) {
        StringBuilder statusWord = new StringBuilder(getExtendedStatus(params));

        // 超期后每个月的还款状态
        Integer extendedCount = extendedStatus(loanService, statusWord, params);

        return padStatusWord(loanService, statusWord, extendedCount, params, false);
    }

    /**
     * 获取每期的还款状态填补24月还款状态
     *
     * @param statusWord 状态字符串
     * @param totalCount 当前总位数
     * @param params dao参数集合
     * @param excellent true-从未逾期
     * @return 24位还款状态字符串或者第一期到当前结算期的还款状态字符串
     */
    private Integer padEachPeriodStatus(StringBuilder statusWord, Integer totalCount, Map<String, Object> params, boolean excellent) {
        Integer settlementPeriod = (Integer) params.get("latestSettlementPeriod");
        Integer overduePeriods;
        String status = "N";
        // 最后一期前推,直到满足24位长度或者到第一个应还款期
        for(int i = settlementPeriod - 1; i > 0; i--){
            if(totalCount == 24){
                break;
            }
            if(!excellent){
                params.put("settlementPeriod", i);
                overduePeriods = Dao.selectOne("LoanServiceMapper.selectOverduePeriods", params);
                status = getStatus(overduePeriods);
            }
            statusWord.append(status);
            totalCount++;
        }
        return totalCount;
    }

    /**
     * 将24月还款状态的字符串补齐至24位
     *
     * @param latest24Months 月还款状态
     * @param totalCount 当前总位数
     * @param monthDifference 第一个结算期与开户日期之间的月份差值
     * @return 24-totalCount位字符串
     */
    private StringBuilder padUpTo24Bit(StringBuilder latest24Months, int totalCount, int monthDifference) {
        StringBuilder differBuilder = new StringBuilder();
        int newCount = totalCount;
        // 签约月和第一个还款月之间均不用还款
        for(int i = 0; i < Math.abs(monthDifference); i++){
            differBuilder.append("*");
            newCount++;
        }

        if(newCount < 24){
            // 签约前的还款状态以“/”补齐
            for(int i = 1 ; i < 24 - newCount; i++){
                latest24Months.append("/");
            }
        }
        // 填补不用还款的月份
        latest24Months.append(differBuilder);
        return latest24Months;
    }

    /**
     * 获取超期的当月状态
     *
     * @param params dao参数
     * @return 月还款状态
     */
    private String getExtendedStatus(Map<String, Object> params){
        String status;
        Integer overdueDays = Dao.selectOne("LoanServiceMapper.selectOverdueDays", params);
        if(overdueDays < 31){
            status = "1";
        }else if(overdueDays < 61){
            status = "2";
        }else if(overdueDays < 91){
            status = "3";
        }else if(overdueDays < 121){
            status = "4";
        }else if(overdueDays < 151){
            status = "5";
        }else if(overdueDays < 181){
            status = "6";
        }else{
            status = "7";
        }
        return status;
    }

    /**
     * 获取正常结清的24月还款状态
     *
     * @param loanService 业务对象
     * @param params dao参数
     * @param excellent true-从未逾期
     * @return 24月还款状态字符串
     */
    private String getSettledLatest24MonthsStatus(LoanService loanService, Map<String, Object> params, boolean excellent) {
        return getLatest24MonthsStatus(loanService, params, "C", excellent);
    }

    /**
     * 获取24月还款状态
     *
     * @param loanService 业务对象
     * @param params dao参数
     * @param currentStatus 当前结算期的还款状态
     * @param excellent true-从未逾期
     * @return 24月还款状态的字符串表示
     */
    private String getLatest24MonthsStatus(LoanService loanService, Map<String, Object> params, String currentStatus, boolean excellent) {

        StringBuilder statusWord = new StringBuilder();
        if(!Strings.isNullOrEmpty(currentStatus)){
            statusWord.append(currentStatus);
        }

        return padStatusWord(loanService, statusWord, 1, params, excellent);
    }

    /**
     * 补全状态字符串
     *
     * @param loanService 业务对象
     * @param statusWord 状态字符串
     * @param totalCount 总字长
     * @param params dao参数
     * @param excellent true-从未逾期
     * @return 24月还款状态的字符串表示
     */
    private String padStatusWord(LoanService loanService, StringBuilder statusWord, Integer totalCount, Map<String, Object> params, boolean excellent) {
        StringBuilder latest24Months = new StringBuilder();
        // 填充每期状态
        int newCount = padEachPeriodStatus(statusWord, totalCount, params, excellent);

        Date signedDate = DateUtil.parseDate(loanService.getAccountEstablishmentDate(), "yyyyMMdd");
        // 补全24位
        padUpTo24Bit(latest24Months, newCount, DateUtil.getMonthDifference(signedDate, loanService.getFirstPeriodSettlementDate()));
        return latest24Months.append(statusWord.reverse()).toString();
    }

    /**
     * 本月应还款额和实际还款额
     *
     * @param loanService 业务对象
     * @param params dao参数集合
     */
    private void manageAmountOfThisMonth(LoanService loanService, Map<String, Object> params) {

        // 查找应还款额和实际还款额
        BigDecimal expectedAmount = Dao.selectOne("LoanServiceMapper.selectExpectedAmount", params);
        loanService.setExpectedAmountOfThisMonth(downRoundWhole(expectedAmount));

        // 签约日
        Date accountEstablishmentDate = DateUtil.parseDate(loanService.getAccountEstablishmentDate(), "yyyyMMdd");
        // 当前结算日
        Date settlementDate = DateUtil.parseDate(loanService.getSettlementDate(), "yyyyMMdd");
        // 上一个结算日
        Date lastSettlementDate;
        // 如果当前结算日和签约日的月份差小于2（即当前结算月和签约月相邻）
        if(DateUtil.getMonthDifference(settlementDate, accountEstablishmentDate) < 2){
            // 上一个结算日为签约日
            lastSettlementDate = accountEstablishmentDate;
        }
        else{
            lastSettlementDate = DateUtils.addMonths(settlementDate, -1);
        }
        params.put("lastSettlementDate", lastSettlementDate);

        BigDecimal actualAmount = Dao.selectOne("LoanServiceMapper.selectActualAmount", params);

        loanService.setActualAmountOfThisMonth(downRoundWhole(actualAmount));
    }

    /**
     * 获取当前结算期的还款状态
     *
     * @param overduePeriods 逾期期数
     * @return 当前还款状态的字符串表示
     */
    private String getStatus(Integer overduePeriods) {
        String status;
        // 没有逾期期数
        if(overduePeriods.equals(0)) {
            status = "N";
        }
        else{
            status = String.valueOf(overduePeriods);
            if(overduePeriods > 7){
                status = "7";
            }
        }
        return status;
    }

    /**
     * 逾期信息
     *
     * @param loanService 业务信息
     * @param params dao参数集合
     */
    private void manageOverdue(LoanService loanService, Map<String, Object> params) {
        // 当前逾期金额和期数
        manageOverdueAmountAndPeriods(loanService, params);
        // 逾期本金
        manageOverduePrinciple(loanService, params);
    }

    /**
     * 当前结算期的逾期金额和期数
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void manageOverdueAmountAndPeriods(LoanService loanService, Map<String, Object> params) {
        LoanService tempLoanService = Dao.selectOne("LoanServiceMapper.selectOverduePeriodsAndAmount", params);
        loanService.setCurrentOverdueAmount(downRoundWhole(tempLoanService.getCurrentOverdueAmount()));
        loanService.setCurrentOverduePeriods(String.valueOf(tempLoanService.getCurrentOverduePeriods()));
    }

    /**
     * 当前结算期的逾期金额和期数（超期）
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void manageExtendedOverdueAmountAndPeriods(LoanService loanService, Map<String, Object> params) {
        LoanService tempLoanService = Dao.selectOne("LoanServiceMapper.selectExtendedOverduePeriodsAndAmount", params);
        loanService.setCurrentOverdueAmount(downRoundWhole(tempLoanService.getCurrentOverdueAmount()));
        loanService.setCurrentOverduePeriods(String.valueOf(tempLoanService.getCurrentOverduePeriods()));
    }

    /**
     * 逾期本金
     * 计算逾期本金为每30天为一个区间，但在未超期的情况下，相差n个结算期的结算日期直接相减得到的天数可能会得出n-1个区间的逾期本金
     * 典型情况就是2月份的28天
     * 例如每月1号为结算日期，客户1月份逾期，2月份逾期，3月份逾期，我们3月份发送数据，1月份的逾期本金应该落在61-90天区间
     * 但若用3月1日减1月1日得到的却是59天（闰年60天）,落在了错误的31-60天区间内
     * 所以获取每个结算期当时具体的逾期期次，遍历获取每个逾期期次与当前结算期的差值
     * 通过差值判断落入的逾期本金区间，即逾期一期为1-30天，逾期两期为31-60天，以此类推
     *
     * @param loanService 业务对象
     * @param params dao参数
     */
    private void manageOverduePrinciple(LoanService loanService, Map<String, Object> params) {
        Integer settlementPeriod = (Integer) params.get("latestSettlementPeriod");

        BigDecimal overdue31To60Days = BigDecimal.ZERO;
        BigDecimal overdue61To90Days = BigDecimal.ZERO;
        BigDecimal overdue91To180Days = BigDecimal.ZERO;
        BigDecimal overdueOver180Days = BigDecimal.ZERO;

        // 当前结算期内具体的逾期期次
        List<Integer> overduePeriodsDetail = Dao.selectList("LoanServiceMapper.selectOverduePeriodsDetail", params);
        for(Integer period : overduePeriodsDetail){
            params.put("period", period);
            if(settlementPeriod - period == 1){
                // 逾期31到60天未归还本金
                overdue31To60Days = overdue31To60Days.add((BigDecimal) Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params));
            }
            else if(settlementPeriod - period == 2){
                // 61到90天
                overdue61To90Days = overdue61To90Days.add((BigDecimal) Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params));
            }
            else if(settlementPeriod - period <= 5){
                // 91到180天
                overdue91To180Days = overdue91To180Days.add((BigDecimal) Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params));
            }
            else if(settlementPeriod - period > 5) {
                // 180天以上
                overdueOver180Days = overdueOver180Days.add((BigDecimal) Dao.selectOne("LoanServiceMapper.selectOverduePrinciple", params));
            }
        }

        loanService.setOverdue31To60Days(downRoundWhole(overdue31To60Days));
        loanService.setOverdue61To90Days(downRoundWhole(overdue61To90Days));
        loanService.setOverdue91To180Days(downRoundWhole(overdue91To180Days));
        loanService.setOverdueOver180Days(downRoundWhole(overdueOver180Days));
    }

    /**
     * 获取优秀业务数据的24月还款状态
     *
     * @param params dao参数集合
     * @return 24月还款状态
     */
    private String getExcellentLatest24MonthsStatus(LoanService loanService, Map<String, Object> params) {
       return getLatest24MonthsStatus(loanService, params, "N", true);
    }

    /**
     * 超期的最近结算和还款日期
     *
     * @param loanService 业务信息
     * @param params dao参数
     */
    private void manageExtendedSettlementDate(LoanService loanService, Map<String, Object> params){
        String settlementDate = calculateSettlementDate(params);
        loanService.setSettlementDate(settlementDate);
    }

    /**
     * 获取超期业务的结算日期，超期后结算日期为结算月的最后一天
     * 结算日必须在上传日之前，所以结算日实为上个月的最后一天
     * 但上个月有可能是最后一期的结算日所在的月，所以在本月与最后一期结算月差值大于1的情况下（即不是相邻的月份）
     * 可直接用本月的上个月的最后一天作为结算日，否则应用最后一期的结算日
     *
     * @param params dao参数
     * @return 结算日期字符串
     */
    private String calculateSettlementDate(Map<String, Object> params) {
        //最后一期的结算日期
        Date settlementDate = (Date) params.get("settlementDate");
        String settlementDateStr = DateUtil.format(settlementDate, "yyyyMMdd");
        Date now = new Date();
        // 如果当前月和最后一期的结算时间月份差超过1
        if(DateUtil.getMonthDifference(now, settlementDate) > 1){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.addMonths(now, -1));
            // 取上个月最后一天
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            settlementDateStr = DateUtil.format(calendar.getTime(), "yyyyMMdd");
        }
        return settlementDateStr;
    }

//    /**
//     * 最近结算日期和还款日期
//     *
//     * @param loanService 业务信息
//     * @param params dao参数集合
//     */
//    private void manageSettlementDate(LoanService loanService, Map<String, Object> params) {
//        String settlementDate = Dao.selectOne("LoanServiceMapper.selectSettlementDate", params);
//
//        if(!Strings.isNullOrEmpty(settlementDate)){
//            loanService.setSettlementDate(settlementDate);
//        }
//    }


    /**
     * 获取结清的超期业务的24月还款状态
     *
     * @param loanService 业务对象
     * @param params dao参数
     * @return 还款状态字符串
     */
    private String getSettledExtendedMonthsStatus(LoanService loanService, Map<String, Object> params) {
        StringBuilder statusWord = new StringBuilder("C");

        // 超期后每个月的还款状态
        Integer extendedCount = extendedStatus(loanService, statusWord, params);
        String result;
        result = statusWord.reverse().toString();
        if(extendedCount < 24){
            result =  padStatusWord(loanService, statusWord, extendedCount, params, false);
        }

        return result;
    }

    /**
     * 超期期间的月状态
     *
     * @param loanService 业务对象
     * @param statusWord 状态字符串
     * @param params dao参数
     * @return 超期月状态字符串的长度
     */
    private Integer extendedStatus(LoanService loanService, StringBuilder statusWord, Map<String, Object> params) {

        Date settlementDate = (Date) params.get("settlementDate");
        String settlementDateStr = loanService.getSettlementDate().substring(0,6);
        String lastPeriodDate = Dao.selectOne("LoanServiceMapper.selectLastPeriodDate", params);

        // 超期后的结算月次数，以1开始是因为已经得到了当前结算期的状态
        Integer extendedCount = 1;

        // 结算日期所在月份减一，直到最后一期应还款期所在月或者满足24位
        while(!settlementDateStr.equals(lastPeriodDate)){
            statusWord.append(getExtendedStatus(params));
            extendedCount++;
            settlementDate = DateUtils.addMonths(settlementDate, -1);
            settlementDateStr = DateUtil.format(settlementDate, "yyyyMMdd").substring(0, 6);
            if(extendedCount == 24){
                break;
            }
        }

        return extendedCount;
    }

    /**
     * 初始化业务数据
     *
     * @param loanService 业务对象
     */
    private void initService(LoanService loanService) {
        // 协定期还款额向下取整
        loanService.setAgreedPeriodAmount(downRoundWhole(loanService.getAgreedPeriodAmount()));
        // 最大负债额向下取整
        loanService.setMaxLiabilities(downRoundWhole(loanService.getMaxLiabilities()));
        // 共享授信额度向下取整
        loanService.setSharedCreditLine(downRoundWhole(loanService.getSharedCreditLine()));
        // 授信额度向下取整
        loanService.setCreditLine(downRoundWhole(loanService.getCreditLine()));
        // 余额向下去整
        loanService.setBalance(downRoundWhole(loanService.getBalance()));
        // 业务号限定最大位数40
        loanService.setServiceNo(cut(loanService.getServiceNo(), 40));
        // 处理担保方式
        manageGuaranteeWay(loanService);

        // 最近结算日期为开户日期（签约日期）
        loanService.setSettlementDate(loanService.getAccountEstablishmentDate());
        // 当前应还款额为零
        loanService.setExpectedAmountOfThisMonth(BigDecimal.ZERO);
        // 当前实际还款额为零
        loanService.setActualAmountOfThisMonth(BigDecimal.ZERO);
        // 当前逾期期数为零
        loanService.setCurrentOverduePeriods("0");
        // 当前逾期金额为零
        loanService.setCurrentOverdueAmount(BigDecimal.ZERO);
        // 逾期本金为零
        loanService.setOverdue31To60Days(BigDecimal.ZERO);
        loanService.setOverdue61To90Days(BigDecimal.ZERO);
        loanService.setOverdue91To180Days(BigDecimal.ZERO);
        loanService.setOverdueOver180Days(BigDecimal.ZERO);
        // 累计逾期期数为零
        loanService.setTotalOverduePeriods("0");
        // 最高逾期期数为零
        loanService.setMaxOverduePeriods("0");
        // 账户状态为正常
        loanService.setAccountStatus("1");
        // 24月还款状态为开户
        loanService.setLatest24MonthsStatus("///////////////////////*");
    }

    /**
     * 处理担保方式，将本地的担保信息转换成符合征信要求的担保方式信息
     *
     * @param loanService 业务信息对象
     */
    private void manageGuaranteeWay(LoanService loanService) {
        // 本地数据 法人担保
        if("3".equals(loanService.getGuaranteeWay())){
            // 征信数据 组合（不含自然人保证）
            loanService.setGuaranteeWay("6");
        }
        // 本地数据 自然人担保
        else if("2".equals(loanService.getGuaranteeWay())){
            // 征信数据 自然人保证
            loanService.setGuaranteeWay("3");
        }
        // 本地数据 无担保
        else{
            // 征信数据 信用/无担保
            loanService.setGuaranteeWay("4");
        }
    }

    /**
     * 隐藏联系人电话的后几位
     *
     * @param phone 原电话
     * @return 屏蔽后的电话
     */
    private String encryptPhone(String phone) {
        String result = phone;
        if(!Strings.isNullOrEmpty(phone)){
            // 11为手机好屏蔽后4位
            if(Pattern.matches("^\\d{11}$", phone)){
                result = phone.substring(0, 7) + "XXXX";
            }
            // 固话屏蔽后3位
            else if(Pattern.matches("^\\d{3,4}-\\d{7,8}$", phone)){
                result = phone.substring(0, 9) + "XXX";
            }
        }
        return result;
    }


    /**
     * 根据数据所在的文件，行号变更映射更新行号
     *
     * @param file 数据文件
     * @param sheetRowNoMap 行号变更映射
     */
    public void updateRowNo(File file, Map<String, Map<Integer, Integer>> sheetRowNoMap) {
        String fileName = StringUtils.substringBeforeLast(file.getName(), ".");
        for(Map.Entry<String, String> entry: excelConfigService.getTitle().entrySet()){
            // 根据行号变更映射里的sheet页名称找到需要修改的数据表
            Map<Integer, Integer> rowNoMap = sheetRowNoMap.get(entry.getValue());
            updateRowNo(fileName, getTableNameByProperty(entry.getKey()), rowNoMap);
        }
    }

    /**
     * 根据配置文件中配置的属性名查找对应的表名
     *
     * @param propertyName 属性名
     * @return 表名
     */
    private String getTableNameByProperty(String propertyName) {
        return "T_CREDIT_" + com.pqsoft.util.StringUtils.camelToUnderLine(propertyName);
    }

    /**
     * 根据数据所在的文件名，表名， 行号变更映射更新行号
     *
     * @param fileName 文件名
     * @param tableName 表名
     * @param rowNoMap 行号变更映射
     */
    private void updateRowNo(String fileName, String tableName, Map<Integer, Integer> rowNoMap){
        Map<String, Object> params = new HashMap<>();
        params.put("generatedFileName", fileName);
        params.put("refTable", tableName);
        for(Map.Entry<Integer, Integer> entry : rowNoMap.entrySet()){
            params.put("formerRowNo", entry.getKey());
            params.put("mergedRowNo", entry.getValue());
            Dao.update("DataMapper.updateRowNo", params);
        }
    }

    /**
     * 插入征信数据
     *
     * @param creditData 征信数据
     * @param generatedFileName 生成文件的文件名
     */
    public void insertCreditData(CreditData creditData, String generatedFileName) {
        Preconditions.checkNotNull(creditData, "征信数据对象为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(generatedFileName), "数据上传对象为空！");

        Long identityId = insertIdentity(creditData.getIdentityList(), generatedFileName);

//        throw new RuntimeException("testRollback");

        insertOccupation(creditData.getOccupationList(), identityId);

        insertResidence(creditData.getResidenceList(), identityId);

        insertLoanApplication(creditData.getLoanApplicationList(), identityId);

        insertLoanContract(creditData.getLoanContractList());

        insertLoanService(creditData.getLoanServiceList(), creditData.getLoanContractList(), identityId);

        insertGuarantee(creditData.getGuaranteeList(), creditData.getLoanServiceList());

        insertInvestor(creditData.getInvestorList(), creditData.getLoanServiceList());

        insertSpecialTransaction(creditData.getSpecialTransactionList(), creditData.getLoanServiceList(), identityId);
    }

    /**
     * 插入特殊交易信息
     *
     * @param specialTransactionList 特殊交易信息集合
     * @param loanServiceList 业务信息集合
     * @param identityId 身份信息id
     */
    private void insertSpecialTransaction(List<SpecialTransaction> specialTransactionList, List<LoanService> loanServiceList, Long identityId) {
        if(CollectionUtils.isEmpty(specialTransactionList)){
            return;
        }
        for(SpecialTransaction specialTransaction : specialTransactionList){
            // 从序列获取主键值
            specialTransaction.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            specialTransaction.getIdentity().setId(identityId);
            // 获取关联的业务信息的id
            for(LoanService loanService : loanServiceList){
                LoanService thisLoanService = specialTransaction.getLoanService();
                if(loanService.getServiceNo().equals(thisLoanService.getServiceNo())){
                    thisLoanService.setId(loanService.getId());
                    break;
                }
            }
        }
        Dao.insert("SpecialTransactionMapper.insertBatch", specialTransactionList);
    }

    /**
     * 插入投资人信息
     *
     * @param investorList 投资人信息集合
     * @param loanServiceList 业务信息集合
     */
    private void insertInvestor(List<Investor> investorList, List<LoanService> loanServiceList) {
        if(CollectionUtils.isEmpty(investorList)){
            return;
        }
        for(Investor investor : investorList){
            // 从序列获取主键值
            investor.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 获取关联的业务信息的id
            for(LoanService loanService : loanServiceList){
                LoanService thisLoanService = investor.getLoanService();
                if(loanService.getServiceNo().equals(thisLoanService.getServiceNo())){
                    thisLoanService.setId(loanService.getId());
                    break;
                }
            }
        }
        Dao.insert("InvestorMapper.insertBatch", investorList);
    }

    /**
     * 插入担保信息
     *
     * @param guaranteeList 担保信息集合
     * @param loanServiceList 业务信息集合
     */
    private void insertGuarantee(List<Guarantee> guaranteeList, List<LoanService> loanServiceList) {
        if(CollectionUtils.isEmpty(guaranteeList)){
            return;
        }
        for(Guarantee guarantee : guaranteeList){
            // 从序列获取主键值
            guarantee.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 获取关联的业务信息的id
            for(LoanService loanService : loanServiceList){
                LoanService thisLoanService = guarantee.getLoanService();
                if(loanService.getServiceNo().equals(thisLoanService.getServiceNo())){
                    thisLoanService.setId(loanService.getId());
                    break;
                }
            }
        }
        Dao.insert("GuaranteeMapper.insertBatch", guaranteeList);
    }

    /**
     * 插入业务信息
     *
     * @param loanServiceList 业务信息集合
     * @param loanContractList 合同信息集合
     * @param identityId 身份id
     */
    private void insertLoanService(List<LoanService> loanServiceList, List<LoanContract> loanContractList, Long identityId) {
        if(CollectionUtils.isEmpty(loanServiceList)){
            return;
        }
        for(LoanService loanService : loanServiceList){
            // 从序列获取主键值
            loanService.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 设置关联的身份信息
            loanService.getIdentity().setId(identityId);

            // 获取关联的合同信息的id
            for(LoanContract loanContract : loanContractList){
                LoanContract thisLoanContract = loanService.getLoanContract();
                if(loanContract.getContractNo().equals(thisLoanContract.getContractNo())){
                    thisLoanContract.setId(loanContract.getId());
                    break;
                }
            }
        }
        Dao.insert("LoanServiceMapper.insertBatch", loanServiceList);
    }

    /**
     * 插入合同信息
     *
     * @param loanContractList 合同信息集合
     */
    private void insertLoanContract(List<LoanContract> loanContractList) {
        if(CollectionUtils.isEmpty(loanContractList)){
            return;
        }
        for(LoanContract loanContract : loanContractList){
            // 从序列获取主键值
            loanContract.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
        }
        Dao.insert("LoanContractMapper.insertBatch", loanContractList);
    }

    /**
     * 插入申请信息
     *
     * @param loanApplicationList 申请信息集合
     * @param identityId 身份信息id
     */
    private void insertLoanApplication(List<LoanApplication> loanApplicationList, Long identityId) {
        if(CollectionUtils.isEmpty(loanApplicationList)){
            return;
        }
        for(LoanApplication loanApplication : loanApplicationList){
            // 从序列获取主键值
            loanApplication.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 设置关联的身份信息
            loanApplication.getIdentity().setId(identityId);
        }
        Dao.insert("LoanApplicationMapper.insertBatch", loanApplicationList);
    }

    /**
     * 插入关联的居住信息
     *
     * @param residenceList 居住信息集合
     * @param identityId 身份信息id
     */
    private void insertResidence(List<Residence> residenceList, Long identityId) {
        if(CollectionUtils.isEmpty(residenceList)){
            return;
        }
        for(Residence residence : residenceList){
            // 从序列获取主键值
            residence.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 设置关联的身份信息
            residence.getIdentity().setId(identityId);
        }
        Dao.insert("ResidenceMapper.insertBatch", residenceList);
    }

    /**
     * 插入职业信息
     *
     * @param occupationList 职业信息集合
     * @param identityId 身份信息id
     */
    private void insertOccupation(List<Occupation> occupationList, Long identityId) {
        if(CollectionUtils.isEmpty(occupationList)){
            return;
        }
        for(Occupation occupation : occupationList){
            // 从序列获取主键值
            occupation.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 设置关联的身份信息
            occupation.getIdentity().setId(identityId);
        }
        Dao.insert("OccupationMapper.insertBatch", occupationList);
    }

    /**
     * 插入身份信息
     *
     * @param identityList 身份信息集合
     * @param generateFileName 生成的文件名
     * @return 身份信息id
     */
    private Long insertIdentity(List<Identity> identityList, String generateFileName) {
        for(Identity identity : identityList){
            // 从序列获取主键值
            identity.setId((Long)Dao.selectOne("DataMapper.selectPrimaryKey"));
            // 设置关联的上传信息
            identity.setGeneratedFileName(generateFileName);
        }
        Dao.insert("IdentityMapper.insertBatch", identityList);
        return identityList.get(0).getId();
    }

    /**
     * 插入上传相关信息
     *
     * @param uploadFileName 上传文件名
     * @return 上传信息对象
     */
    public CreditUpload insertCreditUpload(String uploadFileName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(uploadFileName), "上传文件名为空！");
        CreditUpload creditUpload = new CreditUpload();
        creditUpload.setUploadFileName(uploadFileName);
        creditUpload.setCreationTime(new Date());
        Dao.insert("UploadMapper.insertUpload", creditUpload);
        return creditUpload;
    }

    /**
     * 插入反馈错误信息
     *
     * @param feedbackFileName 错误反馈文件名
     * @param row 错误行信息
     */
    public void insertCreditError(String feedbackFileName, HSSFRow row) {
        if(row == null || Strings.isNullOrEmpty(feedbackFileName)){
            return;
        }
        // sheet页名称
        String sheetName = row.getCell(0).getStringCellValue();
        // 通过sheet页名称找到对应的数据表
        String refTable = getTableName(sheetName);
        // 错误行号
        String rowNo = row.getCell(1).getStringCellValue();
        Map<String, Object> params = new HashMap<>();
        params.put("feedbackFileName", feedbackFileName);
        params.put("refTable", refTable);
        params.put("rowNo", rowNo);
        // 通过反馈文件名、数据表名、错误行号找到对应的错误数据的id
        Long refId = Dao.selectOne("DataMapper.selectRefId", params);

        // 错误列号
        String columnCode = row.getCell(2).getStringCellValue();
        // 错误代号
        String errorCode = row.getCell(3).getStringCellValue();
        // 错误信息
        String errorInfo = row.getCell(4).getStringCellValue();
        // 插入错误信息对象
        CreditError creditError = new CreditError(refTable, getItemName(refTable, columnCode), errorCode, errorInfo, refId);
        Dao.insert("ErrorMapper.insertError", creditError);
    }

    /**
     * 通过涉及的数据表和列号获取具体的错误项
     *
     * @param refTable 数据表名
     * @param columnCode 列号
     * @return 项名称
     */
    private String getItemName(String refTable, String columnCode) {
        if(Strings.isNullOrEmpty(columnCode)){
            return null;
        }
        int index = getItemIndex(columnCode);
        return getItemName(refTable, index);
    }

    /**
     * 通过列号获取错误项的索引
     *
     * @param columnCode 列号
     * @return 项索引
     */
    private int getItemIndex(String columnCode) {
        int index = 0;
        int len = columnCode.length();
        // 列号是一个由大写字母组成的26进制数，需要转换成10进制获取相应的索引
        for(int i = 0; i < len; i++){
            index += columnIndexMap.get(columnCode.charAt(i)) * Math.pow(26, len - 1 - i);
        }
        return index;
    }

    /**
     * 通过涉及的数据表名和列索引获取错误项的名称
     *
     * @param refTable 数据表名
     * @param columnIndex 列号
     * @return 项名称
     */
    private String getItemName(String refTable, int columnIndex){
        // 将表名转换成配置文件中对应的格式（即类名首字母小写）
        String propertyName = StringUtils.substringAfter(refTable, "T_CREDIT_").toLowerCase();
        String[] tokens = propertyName.split("_");
        StringBuilder sb = new StringBuilder(tokens[0]);
        for(int i = 1; i < tokens.length; i++){
            sb.append(StringUtils.capitalize(tokens[i]));
        }
        // 遍历标题映射，通过索引获取对应的项名称
        Iterator<Map.Entry<String, String>> iterator = excelConfigService.getHeadMap().get(sb.toString()).entrySet().iterator();
        String value;
        int i = 1;
        while (iterator.hasNext()){
            value = iterator.next().getValue();
            if(i == columnIndex){
                return value;
            }
            i++;
        }
        throw new RuntimeException("解析错误反馈文件时遇到未知的行（列）位置！");
    }

    /**
     * 通过sheet页名称获取工作表名称
     *
     * @param sheetName sheet页名称
     * @return 工作表名
     */
    private String getTableName(String sheetName) {
        String tableName;
        if(sheetName.contains("身份")){
            tableName = "T_CREDIT_IDENTITY";
        }
        else if(sheetName.contains("职业")){
            tableName = "T_CREDIT_OCCUPATION";
        }
        else if(sheetName.contains("居住")){
            tableName = "T_CREDIT_RESIDENCE";
        }
        else if(sheetName.contains("申请")){
            tableName = "T_CREDIT_LOAN_APPLICATION";
        }
        else if(sheetName.contains("业务")){
            tableName = "T_CREDIT_LOAN_SERVICE";
        }
        else if(sheetName.contains("合同")){
            tableName = "T_CREDIT_LOAN_CONTRACT";
        }
        else if(sheetName.contains("担保")){
            tableName = "T_CREDIT_GUARANTEE";
        }
        else if(sheetName.contains("投资")){
            tableName = "T_CREDIT_INVESTOR";
        }
        else if(sheetName.contains("特殊交易")){
            tableName = "T_CREDIT_SPECIAL_TRANSACTION";
        }
        else{
            throw new RuntimeException("未知的sheet页信息！");
        }
        return tableName;
    }

    /**
     * 通过被合并的文件名批量更新身份信息的uploadId
     *
     * @param uploadId 上传信息id
     * @param mergedFileNames 被合并的文件名集合
     */
    public void updateIdentityBatch(Long uploadId, List<String> mergedFileNames) {
        Preconditions.checkNotNull(uploadId, "上传信息id为空！");
        Preconditions.checkArgument(!CollectionUtils.isEmpty(mergedFileNames), "合并的文件名集合为空！");
        int len = mergedFileNames.size();
        Map<String, Object> params = new HashMap<>();
        if(len > 200){
            int times = len / 200;
            int to;
            for(int i = 0; i <= times; i++){
                to = (i + 1) * 200;
                if(to > len){
                    to = len;
                }
                List<String> subMergedFileNames = mergedFileNames.subList(i * 200, to);
                params.put("mergedFileNames", subMergedFileNames);
                params.put("uploadId", uploadId);
                Dao.update("IdentityMapper.updateIdentityBatch", params);
            }
        }else{
            params.put("mergedFileNames", mergedFileNames);
            params.put("uploadId", uploadId);
            Dao.update("IdentityMapper.updateIdentityBatch", params);
        }
    }

    /**
     * 最终更新上传信息
     *
     * @param uploadFileName 上传文件名
     * @param feedBackFileUrl 错误反馈文件Url
     * @param feedBackFileName 错误反馈文件名
     * @param total 上传文件加载的总数据量
     * @param failed 上传文件中加载错误的数据量
     */
    public void updateUpload(String uploadFileName, String state, String feedBackFileUrl, String feedBackFileName, String total, String failed) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(uploadFileName), "上传文件名为空！");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(state), "上传文件处理状态为空！");
        CreditUpload creditUpload = Dao.selectOne("UploadMapper.selectUploadForUpdate", uploadFileName);
        if(state.equals(creditUpload.getProcessingState())){
            return;
        }
        
        creditUpload.setProcessingState(state);
        if("3".equals(state)){
            creditUpload.setProcessedTime(new Date());
            creditUpload.setTotal(total);
            creditUpload.setFailed(failed);
            if(Strings.isNullOrEmpty(failed)){
                creditUpload.setFailed("0");
            }
        }
        creditUpload.setFeedbackFileUrl(feedBackFileUrl);
        creditUpload.setFeedbackFileName(feedBackFileName);
        Dao.update("UploadMapper.updateUpload", creditUpload);
    }

    /**
     * 更新上传时间
     *
     * @param uploadFileName 上传文件名
     * @return 上传信息对象
     */
    public CreditUpload updateUploadTime(String uploadFileName) {
        CreditUpload creditUpload = new CreditUpload();
        creditUpload.setUploadFileName(uploadFileName);
        creditUpload.setUploadTime(new Date());
        creditUpload.setProcessingState("1");
        Dao.update("UploadMapper.updateUpload", creditUpload);
        return creditUpload;
    }

    /**
     * 持久化征信数据
     *
     * @param creditData 征信数据对象
     * @param generatedFileName 数据所在的文件名
     */
    public void persistCreditData(CreditData creditData, String generatedFileName) {
        Identity identity = Dao.selectOne("IdentityMapper.selectIdentity", generatedFileName);
        if(identity == null){
            insertCreditData(creditData, generatedFileName);
        }else{
            updateCreditData(creditData, generatedFileName, identity.getId());
        }
    }

    /**
     * 更新征信数据
     *
     * @param creditData 征信数据对象
     * @param generatedFileName 数据所在文件名
     * @param identityId 身份id
     */
    private void updateCreditData(CreditData creditData, String generatedFileName, Long identityId) {
        updateIdentity(creditData.getIdentityList(), generatedFileName);

        updateOccupation(creditData.getOccupationList(), identityId);

        updateResidence(creditData.getResidenceList(), identityId);

        updateApplication(creditData.getLoanApplicationList(), identityId);

        updateContract(creditData.getLoanContractList(), identityId);

        updateService(creditData.getLoanServiceList(), creditData.getLoanContractList(), identityId);

        updateGuarantee(creditData.getGuaranteeList(), creditData.getLoanServiceList(), identityId);

        updateInvestor(creditData.getInvestorList(), creditData.getLoanServiceList(), identityId);

        updateSpecialTransaction(creditData.getSpecialTransactionList(), creditData.getLoanServiceList(), identityId);
    }

    /**
     * 更新特殊交易信息
     *
     * @param specialTransactionList 特殊交易信息集合
     * @param loanServiceList 关联业务信息集合
     * @param identityId 关联身份id
     */
    private void updateSpecialTransaction(List<SpecialTransaction> specialTransactionList, List<LoanService> loanServiceList, Long identityId) {
        if(CollectionUtils.isEmpty(specialTransactionList)){
            return;
        }

        Dao.delete("SpecialTransactionMapper.deleteSpecialTransactionByIdentityId", identityId);
        insertSpecialTransaction(specialTransactionList, loanServiceList, identityId);
    }

    /**
     * 更新投资人信息
     *
     * @param investorList 投资人信息集合
     * @param loanServiceList 关联业务信息集合
     * @param identityId 关联身份id
     */
    private void updateInvestor(List<Investor> investorList, List<LoanService> loanServiceList, Long identityId) {
        if(CollectionUtils.isEmpty(investorList)){
            return;
        }

        Dao.delete("InvestorMapper.deleteGuaranteeByIdentityId", identityId);
        insertInvestor(investorList, loanServiceList);
    }

    /**
     * 更新担保信息
     *
     * @param guaranteeList 担保信息集合
     * @param loanServiceList 关联业务信息集合
     * @param identityId 关联身份id
     */
    private void updateGuarantee(List<Guarantee> guaranteeList, List<LoanService> loanServiceList, Long identityId) {
        if(CollectionUtils.isEmpty(guaranteeList)){
            return;
        }

        Dao.delete("GuaranteeMapper.deleteGuaranteeByIdentityId", identityId);
        insertGuarantee(guaranteeList, loanServiceList);
    }

    /**
     * 更新业务信息
     *
     * @param loanServiceList  业务信息集合
     * @param loanContractList 关联合同信息集合
     * @param identityId 关联身份Id
     */
    private void updateService(List<LoanService> loanServiceList, List<LoanContract> loanContractList, Long identityId) {
        if(CollectionUtils.isEmpty(loanServiceList)){
            return;
        }

        Dao.delete("LoanServiceMapper.deleteLoanServiceByIdentityId", identityId);
        insertLoanService(loanServiceList, loanContractList, identityId);
    }

    /**
     * 更新合同信息
     *
     * @param loanContractList 合同信息集合
     * @param identityId 关联身份id
     */
    private void updateContract(List<LoanContract> loanContractList, Long identityId) {
        if(CollectionUtils.isEmpty(loanContractList)){
            return;
        }

        Dao.delete("LoanContractMapper.deleteLoanContractByIdentityId", identityId);
        insertLoanContract(loanContractList);
    }

    /**
     * 更新申请信息
     *
     * @param loanApplicationList 申请信息
     * @param identityId 关联身份id
     */
    private void updateApplication(List<LoanApplication> loanApplicationList, Long identityId) {
        if(CollectionUtils.isEmpty(loanApplicationList)){
            return;
        }

        Dao.delete("LoanApplicationMapper.deleteLoanApplicationByIdentityId", identityId);
        insertLoanApplication(loanApplicationList, identityId);
    }

    /**
     * 更新居住信息
     *
     * @param residenceList 居住信息集合
     * @param identityId 关联身份id
     */
    private void updateResidence(List<Residence> residenceList, Long identityId) {
        if(CollectionUtils.isEmpty(residenceList)){
            return;
        }

        Dao.delete("ResidenceMapper.deleteResidenceByIdentityId", identityId);
        insertResidence(residenceList, identityId);
    }

    /**
     * 更新职业信息
     *
     * @param occupationList 职业信息集合
     * @param identityId 关联身份Id
     */
    private void updateOccupation(List<Occupation> occupationList, Long identityId) {
        if(CollectionUtils.isEmpty(occupationList)){
            return;
        }

        Dao.delete("OccupationMapper.deleteOccupationByIdentityId", identityId);
        insertOccupation(occupationList, identityId);
    }


    /**
     * 根据生成的文件名更新对应身份信息
     *
     * @param identityList 身份信息集合
     * @param generatedFileName 生成的文件名
     */
    private void updateIdentity(List<Identity> identityList, String generatedFileName) {
        if(CollectionUtils.isEmpty(identityList)){
            return;
        }
        for(Identity identity : identityList){
            identity.setGeneratedFileName(generatedFileName);
            Dao.update("IdentityMapper.updateIdentity", identity);
        }
    }

    /**
     * 向上取整
     */
//    public BigDecimal upRoundWhole(BigDecimal bigDecimal){
//        if(bigDecimal != null){
//            return bigDecimal.setScale(0, BigDecimal.ROUND_UP);
//        }
//        return null;
//    }

    /**
     * 四舍五入取整
     */
//    public BigDecimal halfUpRoundWhole(BigDecimal bigDecimal){
//        if(bigDecimal != null){
//            return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
//        }
//        return null;
//    }

    /**
     * 向下取整
     */
    public BigDecimal downRoundWhole(BigDecimal bigDecimal){
        if(bigDecimal != null){
            return bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    /**
     * 获取utf-8编码的字符串长度
     *
     * @param string 源字符串
     * @return 长度
     */
    public int getLength(String string){
        if(string == null){
            return 0;
        }
        return string.getBytes(Charset.forName("utf-8")).length;
    }

    /**
     * 将源字符串截去尾部直至符合指定长度
     *
     * @param originalStr 源字符串
     * @param length 长度
     * @return 符合长度要求的字符串
     */
    public String cut(String originalStr, int length){
        if(originalStr == null){
            return null;
        }
        String str = originalStr;
        if(getLength(str) > length){
            for(int i = str.length() - 1; i > -1; i--){
                str = originalStr.substring(0, i);
                if(getLength(str) <= length){
                    break;
                }
            }
        }
        return str;
    }

}




