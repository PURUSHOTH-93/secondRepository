package com.oas.webservice.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.oas.webservice.vo.AttendanceempreportlistVo;
import com.oas.webservice.vo.CheckinVo;
import com.oas.webservice.vo.CircleVo;
import com.oas.webservice.vo.CooptexCodeVo;
import com.oas.webservice.vo.DistrictVo;
import com.oas.webservice.vo.DivisionVo;
import com.oas.webservice.vo.EmpLoginVo;
import com.oas.webservice.vo.EntityTypeVo;
import com.oas.webservice.vo.FpsCodeVo;
import com.oas.webservice.vo.FpsVo;
import com.oas.webservice.vo.HintVo;
import com.oas.webservice.vo.LeavecountVo;
import com.oas.webservice.vo.LeavelistVo;
import com.oas.webservice.vo.LicenceVo;
import com.oas.webservice.vo.OpenTicketsVo;
import com.oas.webservice.vo.ProviderVo;
import com.oas.webservice.vo.RechargereasonVo;
import com.oas.webservice.vo.SectorVo;
import com.oas.webservice.vo.ShopCodeVo;
import com.oas.webservice.vo.ShopTypeVo;
import com.oas.webservice.vo.SimVo;
import com.oas.webservice.vo.SiteCodeVo;
import com.oas.webservice.vo.SlaTicketsVo;
import com.oas.webservice.vo.SurveyCountVo;
import com.oas.webservice.vo.SurveyMasterVo;
import com.oas.webservice.vo.TalukVo;
import com.oas.webservice.vo.TaskVo;
import com.oas.webservice.vo.TicStatusVo;
import com.oas.webservice.vo.TicketsVo;
import com.oas.webservice.vo.TxnreasonVo;
import com.oas.webservice.vo.UppclCodeVo;
import com.oas.webservice.vo.VersionVo;



public interface DeviceResponseDao {

	EmpLoginVo empLoginDetails(String uName, String password, String androidid);

	int insertAttendanceDetails(String empId, String intime, String latitude,
			String longitude, String att_date);
	/*String insertAttendanceDetails(String empId, String intime, String latitude,
			String longitude, String att_date);*/

	int insertTracking(String dev_id, String tra_datetime, String tra_lat,
			String tra_long);
	

	int insertlatLong(String array);

	List<TaskVo> fetchTaskList(String emp_id);

	List<TaskVo> fetchNewTaskLists(String emp_id, String maxDate);

	int updateTaskDetails(String tas_id, String tas_status,String tas_com_datetime);

	int insertOutAttendanceDetails(String empId, String outtime,
			String outlatitude, String outlongitude, String intime,
			String inlatitude, String inlongitude, String attendancedate,
			String insync, String outsync);

	List<FpsCodeVo> fetchFpsCode(String emp_id);

	List<ProviderVo> fetchProvider();

	int insertSurveyDetails(String fps_code, String fps_name,
			String owner_name, String owner_mobile, String shopkeeper,
			String shopkeeper_mobile, String address, String signal_strength,
			String simno, String pro_id, String prefered_nw, String sur_lat,
			String sur_long, String emp_code, String created_ts);

	List<FpsCodeVo> fetchNewFpsCode(String emp_id, String maxDate);

	List<TicketsVo> fetchTickets(String employee_code);

	List<TicketsVo> fetchNewTickets(String employee_code, String maxDate);

	int insertDownloadDetails(String ticket_no,String ticket_dwlstatus,String ticket_dwltime);

	int insertViewedDetails(String ticket_no, String ticket_viwtime);

	int updateResponseDetails(String ticket_no, String ticket_status,
			String rep_datetime, String rep_comments);

	int updateResolvedStatus(String ticket_no, String ticket_status,
			String rep_datetime, String rep_comments, String res_datetime,
			String res_comments, String rea_comments);

	SurveyCountVo getTotalSurveyCount(String employee_code);

	int insertMacidDetails(String fps_code, String mac_id, String serial_no,
			String created_datetme,String employee_code);

	List<FpsCodeVo> fetchmacFpsCode(String emp_id);

	int updateReasonDetails(String ticket_no, String rea_comments);

	List<SlaTicketsVo> getSlaTicketsDetails(String employee_code);

	List<SiteCodeVo> getSiteFpscodeDetails(String employee_code ,String maxDatetime);

	int insertSiteDetails(String fpscode, String employee_code,
			String sparerequested, String sparereplaced, String simid,
			String providerid, String lattitude, String longitude,
			String createdtime, String installation);

	int insertAepdsvalues(String fps_code, String provider, String deviceid,
			String status, String created_datetme, String employee_code,
			String remarks);

	TicStatusVo getTicketsCountdetails(String employee_code, String taluk_name);

	List<OpenTicketsVo> getOpenTicketsDetails(String employee_code,
			String taluk_name);

	VersionVo getAppVersionDetails(String name);

	CheckinVo getCheckInData(String employee_code);

	SimVo getsimerrdetail(String fpscode, String sim_no);

	int insertdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime);

	FpsVo getCheckfpscode(String fpscode);

	FpsVo getCheckupfpscode(String fpscode);

	int insertupdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime);

	int insertmhdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime, String imagePath);

	int inserttsdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime);

	FpsVo getChecktsfpscode(String fpscode);

	List<CooptexCodeVo> fetchcooptexCode();

	int insertCooptexSurvey(String code,String emp_code, String inc_name, String inc_contactno, String eng_name, String eng_contactno, String created_ts, String lattitude, String longitude, String array);

	UppclCodeVo uppclCodeDetails(String fpsid);

	List<RechargereasonVo> fetchrechargereason();

	List<TxnreasonVo> fetchtxnreason();

	int insertUppclDetails(String fpsid, String fpsname, String village_name,
			String fpsowner_name, String fps_mobile, String recharge_amt,
			String recharge_reason, String recharge_remarks, String txt_amt,
			String txt_reason, String txn_remarks, String empid);

	int updateUppclMasterdata(String fpsid, String fpsmobileno, String fpsname,String village_name,String fpsowner_name);

	int insertleaveapply(String emp_code, String from_date, String to_date,
			String total_days, String reason,String session);

	List<LeavelistVo> fetchleavelist(String employee_code,String roleid);

	int updateleaveStatusDetails(String id, String emp_code,
			String leave_status, String evaluated_by, String rejected_reason,
			String evaluated_datetime);

	LeavecountVo getLeaveCountdetails(String employee_code, String roleid);

	List<AttendanceempreportlistVo> fetchattendanceempreportlist(
			String att_type, String date);

	List<AttendanceempreportlistVo> fetchattendanceempreportlistnew(
			String att_type, String date, String role_id);

	int insertlatLongNew(String array);

	int updatePhotoUpload(String imagePath, String empCode);

	int insertmhdailyshoplocationOld(String fpscode, String employee_code, String sparerequested, String actiontaken,
			String newsimid, String others, String lattitude, String longitude, String createdtime);

	List<ShopTypeVo> fetchShopType();

	List<LicenceVo> fetchLicenceType();

	List<DivisionVo> fetchDivisionName();

	List<DistrictVo> fetchDistrictName();

	List<TalukVo> gettalukdetails(String district);

	List<CircleVo> fetchCircleName();

	List<SectorVo> fetchSectorName();


	ShopCodeVo getshopinfodetails(String shop_code);

	int insertUpExisesurveyDetails(String shopcode, String deviceid,
			String deviceid1, String shopname, String shoptype,
			String shopaddress, String shopcontactno, String salespersonname,
			String salespersonno, String licencename, String licencecontactno,
			String licencetype, String licenceno, String exciseinspectorname,
			String exciseinspectorno, String circletype, String sectortype,
			String division, String district, String taluk, String block,
			String pincode, String latitude, String longitude, String empcode);

	String getCheckdeviceid(String deviceid);

	HintVo getshopidhintdetails();

	int insertAttendanceDetailsNew(String empId, String intime,
			String latitude, String longitude, String att_date,
			String sqlDate);

	int insertOutAttendanceDetailsNew(String empId, String outtime,
			String outlatitude, String outlongitude, String intime,
			String inlatitude, String inlongitude, String attendancedate,
			String insync, String outsync, String sqlDate);

	List<SurveyMasterVo> getIssueType();

	List<SurveyMasterVo> getObservation(String issueType);

	List<SurveyMasterVo> getActionStatus(String issueType);

	List<EntityTypeVo> getEntityType();






	



}
