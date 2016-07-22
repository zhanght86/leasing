package com.pqsoft.leeds.verify.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.payment.service.PaymentApplyService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class TechnologicalAdditionalAction extends Action {

	private PaymentApplyService service = new PaymentApplyService();
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aPermission(name = { "业务管理", "放款申请" })
	@aDev(code = "XQ0013", email = "xugm@pqsoft.cn", name = "xgm")
	public Reply execute(){
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		List<Map<String, Object>> tabs = new ArrayList<Map<String, Object>>();
		Map<String,Object> payment=new HashMap<String,Object>();
		Map<String, Object> map=new HashMap<String,Object>();
		System.out.println("...........");
		if(param.containsKey("YHBC")){
			context.put("YHBC", param.get("YHBC"));
		}
		if(param.containsKey("JFBG")){
			context.put("JFBG", param.get("JFBG"));
		}
		context.put("params", param);
		if(param.containsKey("PAYMENT_JBPM_ID")){
			payment = service.getpayment(param);
			map = service.getProject(payment);
			context.put("params", map);
			//add by xingsumin	 YHBC=SCSC 支持上传删除
			if("YHBC".equals(param.get("YHBC"))||"SCSC".equals(param.get("YHBC"))){
				//合同审核节点，右侧显示放款资料
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "放款资料");
				m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID").toString()+"&PHASE=放款前&YHBC="+param.get("YHBC")+"&PROJECT_ID="+map.get("PROJECT_ID").toString());
				List<Map<String, Object>> tabs2 = new ArrayList<Map<String, Object>>();
				tabs2.add(m2);
				context.put("tabs2", JSONArray.fromObject(tabs2));
			}
		}
		String proId="";
		String proCode="";
		String CLIENT_ID="";
		String TYPE="";
		String SCHEME_ID ="";
		String SCHEME_ROW_NUM ="";
		String PLATFORM_TYPE="";
		String hasGuarantee ="";
		String hasJt="";
		String JOINT_TENANT_ID ="";
		String JOINT_TENANT_TYPE ="";
		String TEL_PHONE = "";
		String SCHEME_ID_ACTUAL="";
		if(param.containsKey("PROJECT_ID")){
			proId= param.get("PROJECT_ID").toString();
			List<Map<String, Object>> recs = Dao
					.execSelectSql(""
							+ "SELECT A.CLIENT_ID,B.TEL_PHONE, A.GUARANTEE,A.PRO_CODE,A.JOINT_TENANT,A.JOINT_TENANT_ID,case when A.JOINT_TENANT=2 then 'NP' when A.JOINT_TENANT=3 then 'LP' end JOINT_TENANT_TYPE, "
							+ "A.PLATFORM_TYPE, B.NAME, B.TYPE, " + "C.ID SCHEME_ID_ACTUAL,C.SCHEME_ROW_NUM, C.SCHEME_CODE SCHEME_ID "
							+ "FROM FIL_PROJECT_HEAD A, FIL_CUST_CLIENT B, FIL_PROJECT_SCHEME C  " + " WHERE A.ID='" + proId + "' AND A.CLIENT_ID=B.ID"
							+ " AND A.ID=C.PROJECT_ID(+)");
			Map<String, Object> rec = recs.get(0);
			CLIENT_ID = rec.get("CLIENT_ID").toString();
			TYPE = rec.get("TYPE").toString();
			SCHEME_ID_ACTUAL=rec.get("SCHEME_ID_ACTUAL").toString();
		    SCHEME_ID = rec.get("SCHEME_ID") == null ? "0" : rec.get("SCHEME_ID").toString();
			SCHEME_ROW_NUM = rec.get("SCHEME_ROW_NUM") == null ? "0" : rec.get("SCHEME_ROW_NUM").toString();
			PLATFORM_TYPE = rec.get("PLATFORM_TYPE").toString();
			hasGuarantee = rec.get("GUARANTEE") == null ? "1" : rec.get("GUARANTEE").toString();
			hasJt = rec.get("JOINT_TENANT") == null ? "1" : rec.get("JOINT_TENANT").toString();
			if(!hasJt.trim().equals("1")){
				JOINT_TENANT_ID = rec.get("JOINT_TENANT_ID").toString();
				JOINT_TENANT_TYPE = rec.get("JOINT_TENANT_TYPE").toString();
			}
			proCode=rec.get("PRO_CODE").toString();
			if(rec.containsKey("TEL_PHONE") && !"".equals(rec.get("TEL_PHONE").toString()) && rec.get("TEL_PHONE") !=null){
				TEL_PHONE = "".equals(rec.get("TEL_PHONE").toString())?"":rec.get("TEL_PHONE").toString();
				context.put("TEL_PHONE", TEL_PHONE);
			}
		}
		
		//如果admin用户，失效SHOW_FLAG参数
		if("admin".equalsIgnoreCase(Security.getUser().getCode()) && param.containsKey("SHOW_FLAG")){
			param.put("SHOW_FLAG", "0");
		}
		
		//0无1查看2修改
		//承租人
		if(param.containsKey("CZR")){
			if(param.get("CZR").toString().equals("0")){
				
			}else if(param.get("CZR").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", "承租人");
				m.put("url", "/customers/Customers!toViewCustInfoMain.action?"
						+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
				tabs.add(m);
			}else if(param.get("CZR").toString().equals("2")){
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("title", "承租人");
				m.put("url", "/customers/Customers!toUpdateCustInfoMain.action?"
						+ "CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId);
				tabs.add(m);
			}
		}
		//0无1查看2修改
		//方案
		if(param.containsKey("FA")){
			if(param.get("FA").toString().equals("0")){
				
			}else if(param.get("FA").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "方案");
				m3.put("url", "/project/project!SchemeView.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			}else if(param.get("FA").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "方案");
				m3.put("url", "/project/project!SchemeUpdate.action?LC=LC&"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID+"&SCHEME_ID_ACTUAL="+SCHEME_ID_ACTUAL
						);
				tabs.add(m3);
				
			}
		}
		
		// add gengchangbao 2016年4月21日17:31:06  JZZL-168 start
		//2修改
		//签约时间
		if(param.containsKey("QYSJ")){
			if(param.get("QYSJ").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "签约时间");
				m3.put("url", "/project/project!SignDateUpdate.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			}
		}
		// add gengchangbao 2016年4月21日17:31:06  JZZL-168 end
		
		
		// add gengchangbao 2016年4月21日17:31:06  JZZL-168 start
		//2修改
		//定金管理
		if(param.containsKey("LKGL")){
			if(param.get("LKGL").toString().equals("1")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "来款查看");
				m3.put("url", "/project/project!DepositShow.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			} else if(param.get("LKGL").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "来款管理");
				m3.put("url", "/project/project!DepositUpdate.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			}
		}
		// add gengchangbao 2016年4月21日17:31:06  JZZL-168 end
		
		// add gengchangbao 2016年5月3日14:12:51  JZZL-173 start
		//合同详细信息
		if(param.containsKey("HTXXXX")){
			if(param.get("HTXXXX").toString().equals("1")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "合同详细信息");
				m3.put("url", "/project/project!ContractDetailInfo.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			}
		}
		// add gengchangbao 2016年5月3日14:12:51  JZZL-173 end
		
		//0无1查看2修改
		//请款
		if(param.containsKey("LOAN")){
			if(param.get("LOAN").toString().equals("0")){
				
			}else if(param.get("LOAN").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "请款");
				m3.put("url", "/project/project!toGetQk.action?" + "JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				tabs.add(m3);
			}else if(param.get("LOAN").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "请款");
				m3.put("url", "/project/project!updateGetQk.action?LC=LC&"
						+ "JD=leeds&PROJECT_ID="+proId+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ID="+SCHEME_ID+"&SCHEME_ID_ACTUAL="+SCHEME_ID_ACTUAL
						);
				tabs.add(m3);
				
			}
		}
		//0无1查看2修改
		//保险管理-保险管理
		if(param.containsKey("BXGL")){
			if(param.get("BXGL").toString().equals("0")){
				
			}else if(param.get("BXGL").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "保险管理");
				m3.put("url", "/insure/Insurance.action?PROJECT_ID="+proId+"&LEASE_CODE="+ map.get("LEASE_CODE"));
				tabs.add(m3);
			}else if(param.get("BXGL").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "保险管理");
				m3.put("url", "/insure/Insurance.action?PROJECT_ID="+proId+"&LEASE_CODE="+ map.get("LEASE_CODE"));
				tabs.add(m3);
			}
		}
		//0无1查看2修改
		//担保人
		if(param.containsKey("DBR")){
			if (!hasGuarantee.trim().equals("1") ) {
				Map<String,Object> dbr = Dao.selectOne("project.getDbr", param);
				if (dbr == null) {
					dbr = new HashMap<String,Object>();
				}
				dbr.put("TIME", new Date().getTime());
				if(param.get("DBR").toString().equals("0")){
					
				}else if(param.get("DBR").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "担保人");
					m2.put("url", "/credit/CreditGuarantor!toViewGuarantorInfo.action?"
							+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=view");
					tabs.add(m2);
				}else if(param.get("DBR").toString().equals("2")){
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "担保人");
					m2.put("url", "/credit/CreditGuarantor!toUpdateGuarantorInfo.action?"
							+ "CREDIT_ID="+proId+"&CLIENT_ID="+dbr.get("CLIENT_ID")+"&TYPE="+dbr.get("TYPE")+"&tab=update&date="+dbr.get("TIME"));
					tabs.add(m2);
				}
			}
		}
		//0无1查看2修改
		//共同承租人
		if(param.containsKey("GTCZR")){
			if (!hasJt.trim().equals("1")) {
				if(param.get("GTCZR").toString().equals("0")){
					
				}else if(param.get("GTCZR").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "共同承租人");
					m2.put("url", "/customers/Customers!toViewCustInfoGTCZR.action?"
							+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=view&JD=JBPM&PROJECT_ID="+proId);
					tabs.add(m2);
				}else if(param.get("GTCZR").toString().equals("2")){
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "共同承租人");
					m2.put("url", "/customers/Customers!toUpdateCustInfoGTCZR.action?"
							+ "CLIENT_ID="+JOINT_TENANT_ID+"&TYPE="+JOINT_TENANT_TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+proId);
					tabs.add(m2);
				}
			}
		}
		//0无1查看2修改
		//共同承租人
		if(param.containsKey("PSSQS")){
			if(param.get("PSSQS").toString().equals("0")){
				
			}else if(param.get("PSSQS").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "评审申请书");
				m2.put("url", "/consider/Consider!toApplication.action?TYPE=0&CUST_ID="+JOINT_TENANT_ID+"&PROJECT_ID="+proId);
				tabs.add(m2);
			}else if(param.get("PSSQS").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "评审申请书");
				m2.put("url", "/consider/Consider!toApplication.action?TYPE=1&CUST_ID="+JOINT_TENANT_ID+"&PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		
		//0无1查看2修改
		//共同承租人
		if(param.containsKey("PSYJS")){
			if(param.get("PSYJS").toString().equals("0")){
				
			}else if(param.get("PSYJS").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "立项审议意见表");
				m2.put("url", "/consider/Consider!toConsider.action?TYPE=0&CUST_ID="+JOINT_TENANT_ID+"&PROJECT_ID="+proId);
				tabs.add(m2);
			}else if(param.get("PSYJS").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "立项审议意见表");
				m2.put("url", "/consider/Consider!toConsider.action?TYPE=01&CUST_ID="+JOINT_TENANT_ID+"&PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		//尽职调查
		//0无1任务分配2报告回填3查看
		if(param.containsKey("JZDC")){
			if(param.get("JZDC").toString().equals("0")){
				
			}else if(param.get("JZDC").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "尽职调查任务分配");
				m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?jfym=0&"
						+ "PROJECT_ID="+proId+"&CLIENT_ID="+CLIENT_ID);
				tabs.add(m3);
				param.put("PROJECT_ID", proId);
//				Map<String,Object> visit1 = Dao.selectOne("ReturnVisit.toViewVisit1",param);
				//多条家访记录
				List<Map<String,Object>> visit = Dao.selectList("ReturnVisit.toViewVisit1",param);
				Map<String,Object> visit1 = null;
				if(visit !=null && !visit.isEmpty()){
					visit1 = visit.get(0);
				}
				if(visit1!=null){
					//家访资料
					Map<String, Object> m1 = new HashMap<String, Object>();
					m1.put("title", "承租人尽职调查报告");
					String url="";
					if(param.containsKey("JFBG") && param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}else{
						if(param.containsKey("JFBG")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
						}else if(param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
						}else{
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
						}
					}
					
					m1.put("url", url);
					tabs.add(m1);
					if (!hasJt.trim().equals("1")) {
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "共同承租人尽职调查报告");
						if(param.containsKey("JFBG") && param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}else{
							if(param.containsKey("JFBG")){
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
							}else if(param.containsKey("YHBC")){
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
							}else{
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
							}
						}
						
						m2.put("url", url);
						tabs.add(m2);
					}
					// 担保人
					if (!hasGuarantee.trim().equals("1")) {
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "担保人尽职调查报告");
						if(param.containsKey("JFBG") && param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}else{
							if(param.containsKey("JFBG")){
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
							}else if(param.containsKey("YHBC")){
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
							}else{
								url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
							}
						}
						m2.put("url", url);
						tabs.add(m2);
					}
				}
			}else if(param.get("JZDC").toString().equals("2")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "尽职调查任务分配");
				m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?jfym=1&"
						+ "PROJECT_ID="+proId+"&CLIENT_ID="+CLIENT_ID);
				tabs.add(m3);
				Map<String, Object> m4 = new HashMap<String, Object>();
				m4.put("title", "承租人尽职调查报告");
				String url="";
				if(param.containsKey("JFBG") && param.containsKey("YHBC")){
					url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
				}else{
					if(param.containsKey("JFBG")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}else if(param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}else{
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}
				}
				
				m4.put("url", url);
				tabs.add(m4);
				if (!hasJt.trim().equals("1")) {
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "共同承租人尽职调查报告");
					if(param.containsKey("JFBG") && param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
					}else{
						if(param.containsKey("JFBG")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}else if(param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}else{
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}
					}
					m2.put("url", url);
					tabs.add(m2);
				}
				// 担保人
				if (!hasGuarantee.trim().equals("1")) {
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "担保人尽职调查报告");
					if(param.containsKey("JFBG") && param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
					}else{
						if(param.containsKey("JFBG")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}else if(param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}else{
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}
					}
					m2.put("url", url);
					tabs.add(m2);
				}
				return new ReplyHtml(VM.html("leeds/verify.firstVerify/toJiaFang.vm", context));
			}else if(param.get("JZDC").toString().equals("3")){
				Map<String, Object> m3 = new HashMap<String, Object>();
				m3.put("title", "尽职调查任务分配");
				m3.put("url", "/returnVisit/ReturnVisit!toGetProjectInfo.action?jfym=1&"
						+ "PROJECT_ID="+proId+"&CLIENT_ID="+CLIENT_ID);
				tabs.add(m3);
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("title", "承租人尽职调查报告");
				String url="";
				if(param.containsKey("JFBG") && param.containsKey("YHBC")){
					url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
				}else{
					if(param.containsKey("JFBG")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}else if(param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}else{
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=承租人家访&task=jf&taskStatue=1";
					}
				}
				
				m1.put("url", url);
				tabs.add(m1);
				if (!hasJt.trim().equals("1")) {
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "共同承租人尽职调查报告");
					if(param.containsKey("JFBG") && param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
					}else{
						if(param.containsKey("JFBG")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}else if(param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}else{
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=共同承租人家访&task=jf&taskStatue=1";
						}
					}
					
					m2.put("url", url);
					tabs.add(m2);
				}
				// 担保人
				if (!hasGuarantee.trim().equals("1")) {
					Map<String, Object> m2 = new HashMap<String, Object>();
					m2.put("title", "担保人尽职调查报告");
					if(param.containsKey("JFBG") && param.containsKey("YHBC")){
						url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
					}else{
						if(param.containsKey("JFBG")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?JFBG=JFBG&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}else if(param.containsKey("YHBC")){
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?YHBC=YHBC&PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}else{
							url="/crm/Customer!toMgElectronicPhotoAlbum11.action?PRO_CODE="+proCode+"&PROJECT_ID="+proId+"&PHASE=担保人家访&task=jf&taskStatue=1";
						}
					}
					m2.put("url", url);
					tabs.add(m2);
				}
			}
		}
		
		
		//add gengchangbao 2016年6月7日16:15:39 JZZL-202 Start
//		//合同审核 
//		if(param.containsKey("HTSH")){
//			String htshFlagStr = "SCSC"; //原放款前   资料上传页面         可修改 状态
//			if (!"2".equals(param.get("HTSH").toString())) {
//				htshFlagStr = "YHBC";//原放款前   资料上传页面         只读
//			}
//			Map<String, Object> m2 = new HashMap<String, Object>();
//			m2.put("title", "合同审核");
//			m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?PHASE=放款前&YHBC="+htshFlagStr+"&PROJECT_ID="+proId);
//			tabs.add(m2);
//		}
		//add gengchangbao 2016年6月7日16:15:39 JZZL-202 End	
		
		//放款
		//0无1选择银行2首期款核销3放款后督
		if(param.containsKey("FK")){
			if(param.get("FK").toString().equals("0")){
				
			}else if(param.get("FK").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("title", "附加页面");
				m1.put("url", "/payment/PaymentApply!getAdditional.action?" + "PAYMENT_JBPM_ID=" + param.get("PAYMENT_JBPM_ID")+"&YHBC=YHBC");
				tabs.add(m1);
			}else if(param.get("FK").toString().equals("2")){
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("title", "附加页面");
				m1.put("url", "/payment/PaymentApply!getAdditional1.action?HX=HX&PAYMENT_JBPM_ID=" + param.get("PAYMENT_JBPM_ID"));
				tabs.add(m1);
			}else if(param.get("FK").toString().equals("3")){
				Map<String, Object> m1 = new HashMap<String, Object>();
				m1.put("title", "附加页面");
				m1.put("url", "/payment/PaymentApply!getAdditional1.action?"
						+ "PAYMENT_JBPM_ID="+param.get("PAYMENT_JBPM_ID"));
				tabs.add(m1);
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "放款后");
				m2.put("url", "/crm/Customer!toMgElectronicPhotoAlbum11.action?FK_ID="+map.get("ID")+"&PROJECT_ID="+map.get("PROJECT_ID")+"&PHASE=放款后");
				tabs.add(m2);
			}
		}
		//0无1查看2修改
		//评审纪要
		if(param.containsKey("PSJY")){
			if(param.get("PSJY").toString().equals("0")){
				
			}else if(param.get("PSJY").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "评审会议纪要");
				m2.put("url", "/creditWind/CreditWind!toShowWindView.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}else if(param.get("PSJY").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "评审会议纪要");
				m2.put("url", "/creditWind/CreditWind!AddControlMeetingPage.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		//0无1查看2修改
		//审批批复
		if(param.containsKey("SPPF")){
			if(param.get("SPPF").toString().equals("0")){
				
			}else if(param.get("SPPF").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "审批批复");
				m2.put("url", "/approveComments/ApproveComments.action?project_id="+proId);
				tabs.add(m2);
			}else if(param.get("SPPF").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "审批批复");
				m2.put("url", "/approveComments/ApproveComments!saveApproveComments?project_id="+proId);
				tabs.add(m2);
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		//0无1查看
		//历史车贷
		if(param.containsKey("LSCD")){
			if(param.get("LSCD").toString().equals("0")){
				
			}else if(param.get("LSCD").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "历史车贷信息");
				m2.put("url", "/customers/Customers!findche.action?"+ "CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		//电话调查0无1查看2修改
		if(param.containsKey("DHDC")){
			if(param.get("DHDC").toString().equals("0")){
				
			}else if(param.get("DHDC").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "电话调查");
				m2.put("url", "/customers/CustTel!toCustTelList.action?type=1&PROJECT_ID="+ proId);
				tabs.add(m2);
			}else if(param.get("DHDC").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "电话调查");
				m2.put("url", "/customers/CustTel!toCustTelList.action?type=2&PROJECT_ID="+ proId);
				tabs.add(m2);
			}
		}
		
		/*add by lishuo 11 .17 .2015
		 *	内部匹配
		*/
		if(param.containsKey("NP")){
			if(param.get("NP").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
			Map<String,Object > mm=new HashMap<String,Object>();
			mm.put("title", "内部匹配");
			mm.put("url", "/customers/Customers!toInnerCompare.action?"+ "CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+proId+"&PRO_CODE="+proCode);
			tabs.add(mm);
			}
		}
		
		//百度查询标签
//		ArrayList<Map<String, Object>> listLink = new SysDictionaryMemcached().get("查询标签");
//		if(listLink!=null && !listLink.isEmpty()){
//			Map<String, Object> linkMap =listLink.get(0);
//			context.put("BDLINK", linkMap.get("CODE").toString());
//		}
		if(param.containsKey("BDLINK")){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("title", "百度查询");
			m.put("url", "http://www.baidu.com/s?ie=utf-8&tn=site8_dg&wd="+TEL_PHONE);
			tabs.add(m);
		}
		//操作记录
		//1查看
		if(param.containsKey("CZJL")){
			Map<String, Object> mjl = new HashMap<String, Object>();
			mjl.put("title", "操作记录");
			mjl.put("url", "/customers/Customers!czjlPage.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=view&PROJECT_ID="+proId+"&ISCS=1");
			tabs.add(mjl);
		}
		//信审意见
		if(param.containsKey("XSYJLC")){
			if(param.get("XSYJLC").toString().equals("0")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "信审意见");
				m2.put("url", "/letterOpinion/LetterOpinion!conditionalRolePermission.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}else if(param.get("XSYJLC").toString().equals("1") ){//|| "1".equals(param.get("SHOW_FLAG"))
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "信审意见");
				m2.put("url", "/letterOpinion/LetterOpinion!conditionalPass.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}else if(param.get("XSYJLC").toString()	.equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "信审意见");
				m2.put("url", "/letterOpinion/LetterOpinion!conditionalPassUpdate.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		//实际费用明细
		if(param.containsKey("SJFYMX")){
			if(param.get("SJFYMX").toString().equals("2")){
				Map<String, Object> mjl = new HashMap<String, Object>();
				mjl.put("title", "实际费用明细");
				mjl.put("url", "/customers/Customers!getSify.action?JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				
				tabs.add(mjl);
			}
		}
		
		//add gengchangbao JZZL-85 2016年3月2日15:06:25 start
		//实际费用明细
		if(param.containsKey("KXMXBD")){
			if(param.get("KXMXBD").toString().equals("1")){
				Map<String, Object> mjl = new HashMap<String, Object>();
				mjl.put("title", "款项明细比对");
				mjl.put("url", "/customers/Customers!getLoanDetails.action?JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
						+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID);
				
				tabs.add(mjl);
			}
		}
		//add gengchangbao JZZL-85 2016年3月2日15:06:25 end
		
		// 申请费用页面
		if(param.containsKey("SQFY")){
			Map<String, Object> mjl = new HashMap<String, Object>();
			mjl.put("title", "申请费用");
			mjl.put("url", "/customers/ActualCost!actualApplyFee.action?JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
					+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID + "&sign=" + param.get("SQFY").toString());
			
			tabs.add(mjl);
		}
		// 实际费用登记页面
		if(param.containsKey("SJFK")){
			Map<String, Object> mjl = new HashMap<String, Object>();
			mjl.put("title", "实际费用登记");
			mjl.put("url", "/customers/ActualCost!actualLoan.action?JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
					+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID + "&sign=" + param.get("SJFK").toString());
			
			tabs.add(mjl);
		}
		// 差额处理页面
		if(param.containsKey("SJDJ")){
			Map<String, Object> mjl = new HashMap<String, Object>();
			mjl.put("title", "差额处理");
			mjl.put("url", "/customers/ActualCost!actualCostReg.action?JD=leeds&PROJECT_ID=" + proId + "&SCHEME_ROW_NUM=" + SCHEME_ROW_NUM
					+ "&PLATFORM_TYPE=" + PLATFORM_TYPE + "&SCHEME_ID=" + SCHEME_ID + "&sign=" + param.get("SJDJ").toString());
			
			tabs.add(mjl);
		}
		

		/**
         * add by lishuo 01-18-2016
         * 车务请求交车
         */
        if(param.containsKey("CWQQJC")){
            Map<String, Object> mjl = new HashMap<String, Object>();
            if(param.get("CWQQJC").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
                Map<String, Object> m2 = new HashMap<String, Object>();
                m2.put("title", "车务请求");
                m2.put("url", "/buyCertificate/BuyCertificate!toExecuteForCW.action?PROJECT_ID=" + proId);
                tabs.add(m2);
            }else if (param.get("CWQQJC").toString().equals("2")){
                mjl.put("title", "车务请求交车");
                mjl.put("url","/buyCertificate/BuyCertificate!toExecuteForCW.action?PROJECT_ID=" + proId);
                tabs.add(mjl);
            }else if (param.get("CWQQJC").toString().equals("3")){
                mjl.put("title", "车务请求交车");
                mjl.put("url","/buyCertificate/BuyCertificate!toExecuteForJC.action?PROJECT_ID=" + proId);
                tabs.add(mjl);
            }
        }
		/**
         * add by lishuo 01-04-2016
         * 登记车务手续
         */
        if(param.containsKey("DJHGZ")){
            Map<String, Object> mjl = new HashMap<String, Object>();
            if(param.get("DJHGZ").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
                Map<String, Object> m2 = new HashMap<String, Object>();
                m2.put("title", "登记车务手续");
                m2.put("url", "/buyCertificate/BuyCertificate!goCertificate_eqiupChoose.action?PROJECT_ID=" + proId);
                tabs.add(m2);
            }else if (param.get("DJHGZ").toString().equals("2")){
                mjl.put("title", "登记车务手续");
                mjl.put("url","/buyCertificate/BuyCertificate!goCertificate_eqiupChoose.action?PROJECT_ID=" + proId);
                tabs.add(mjl);
            }
        }
        
		//方案变更申请
		if(param.containsKey("FABGSQ")){
			if(param.get("FABGSQ").toString().equals("0")){
				
			}else if(param.get("FABGSQ").toString().equals("1") || "1".equals(param.get("SHOW_FLAG"))){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "方案变更申请");
				m2.put("url", "/proChange/ProChange!projectChange.action?PROJECT_ID="+proId+"&SCHEME_ROW_NUM="
						+SCHEME_ROW_NUM+"&SCHEME_ID="+SCHEME_ID+"&PAYMENT_JBPM_ID="+param.get("PAYMENT_JBPM_ID"));
				tabs.add(m2);
			}else if(param.get("FABGSQ").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "方案变更申请");
				m2.put("url", "/proChange/ProChange!updateProjectChange.action?PROJECT_ID="+proId+"&SCHEME_ROW_NUM="
						+SCHEME_ROW_NUM+"&SCHEME_ID="+SCHEME_ID+"&PAYMENT_JBPM_ID="+param.get("PAYMENT_JBPM_ID"));
				tabs.add(m2);
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//方案对比
		if(param.containsKey("XMBD")){
					if(param.get("XMBD").toString().equals("0")){
						
					}else if(param.get("XMBD").toString().equals("1")){
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "项目比对");
						m2.put("url", "/project/project!schemeComparison.action?PROJECT_ID="+proId+"&PLATFORM_TYPE="+PLATFORM_TYPE+"&SCHEME_ROW_NUM="
								+SCHEME_ROW_NUM+"&SCHEME_ID="+SCHEME_ID+"&SCHEME_ID_ACTUAL="+SCHEME_ID_ACTUAL);
						tabs.add(m2);
					}
				}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//冲红申请
		if(param.containsKey("CHSQ")){
					if(param.get("CHSQ").toString().equals("0")){
						
					}else if(param.get("CHSQ").toString().equals("1")){
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "冲红查看");
						m2.put("url", "/fiForRed/FiForRed!toMgFiForRedAppCW.action?PROJECT_ID="+proId+"&proCode="+proCode);
						tabs.add(m2);
					}
					else if(param.get("CHSQ").toString().equals("2")){
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "冲红申请");
						m2.put("url", "/fiForRed/FiForRed!toMgFiForRedAppCW.action?PROJECT_ID="+proId+"&proCode="+proCode);
						tabs.add(m2);
					}
				}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//冲红审核
		if(param.containsKey("CHSH")){
					if(param.get("CHSH").toString().equals("0")){
						
					}else if(param.get("CHSH").toString().equals("1")){
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "冲红审核");
						m2.put("url", "/fiForRed/FiForRed!toMgFiForRedConfirmCW.action?PROJECT_ID="+proId+"&proCode="+proCode);
						tabs.add(m2);
					}
					else if(param.get("CHSH").toString().equals("2")){
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("title", "冲红审核");
						m2.put("url", "/fiForRed/FiForRed!toMgFiForRedConfirmCW.action?PROJECT_ID="+proId+"&proCode="+proCode);
						tabs.add(m2);
					}
				}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//电子合同
		if(param.containsKey("DZHT")){
			if(param.get("DZHT").toString().equals("1")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "电子合同");
				m2.put("url", "/project/Contract!getInfo_New.action?PROJECT_ID="+proId);
				tabs.add(m2);
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//电话清单
		if(param.containsKey("DHQD")){
			if(param.get("DHQD").toString().equals("1")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "电话清单");
				m2.put("url", "/telephonenumberlist/TelephoneNumberList!toReport.action?projectId="+proId);
				tabs.add(m2);
			}
		}
		context.put("tabs", JSONArray.fromObject(tabs));
		
		//--
		if(param.containsKey("JCZ")){
			 if(param.get("JCZ").toString().equals("2")){
				Map<String, Object> m2 = new HashMap<String, Object>();
				m2.put("title", "交车中");
				m2.put("url", "/pickCarStock/pickCarStock!showPage.action?PROJECT_ID="+proId+"&proCode="+proCode);
				tabs.add(m2);
			}
		}
context.put("tabs", JSONArray.fromObject(tabs));
		//---
		if(param.containsKey("PAYMENT_JBPM_ID")){
			return new ReplyHtml(VM.html("payment/toMain.vm", context));
		}
		else{
			return new ReplyHtml(VM.html("leeds/cust_info_input/toMain.vm", context));
		}
	}
}
