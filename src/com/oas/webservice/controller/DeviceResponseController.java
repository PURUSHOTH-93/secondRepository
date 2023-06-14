/**
 * 
 */
package com.oas.webservice.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

import com.oas.webservice.dao.DeviceResponseDao;
import com.oas.webservice.dao.daoImp.DeviceResponseDaoImpl;
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
import com.oas.webservice.vo.MhpdsShopVisitVo;
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

/**
 * @author Muthukumar
 *
 */
@Path("/response")
@XmlRootElement
public class DeviceResponseController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEmpLoginDetails")
	public EmpLoginVo getEmpLoginDetails1(@Context UriInfo info) {
		EmpLoginVo loginDetails = new EmpLoginVo();
		String uName = info.getQueryParameters().getFirst("uName");
		String password = info.getQueryParameters().getFirst("password");
		String androidid = info.getQueryParameters().getFirst("androidid");
		DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
		loginDetails = deviceResponseDao.empLoginDetails(uName, password,
				androidid);
		return loginDetails;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertAttendance")
	public String insertAttendance(@Context UriInfo info) {
		int rowCount = 0;
		String empId = info.getQueryParameters().getFirst("empId");
		String intime = info.getQueryParameters().getFirst("intime");
		String latitude = info.getQueryParameters().getFirst("latitude");
		String longitude = info.getQueryParameters().getFirst("longitude");
		String att_date = info.getQueryParameters().getFirst("att_date");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertAttendanceDetails(empId, intime,
					latitude, longitude, att_date);
		} catch (Exception e) {

			e.printStackTrace();
			// //
			// System.out.println("insert Attendance details --> insertAttendance()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCheckinTime")
	public CheckinVo getCheckinTime(@Context UriInfo info) {
		CheckinVo checkinVo = new CheckinVo();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			checkinVo = deviceResponseDao.getCheckInData(employee_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return checkinVo;
	}

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("/insertAttendance") public String insertAttendance(@Context
	 * UriInfo info){ String rowCount = null; String empId =
	 * info.getQueryParameters().getFirst("empId"); String intime =
	 * info.getQueryParameters().getFirst("intime"); String latitude =
	 * info.getQueryParameters().getFirst("latitude"); String longitude =
	 * info.getQueryParameters().getFirst("longitude"); String att_date =
	 * info.getQueryParameters().getFirst("att_date"); try { DeviceResponseDao
	 * deviceResponseDao = new DeviceResponseDaoImpl(); rowCount =
	 * deviceResponseDao
	 * .insertAttendanceDetails(empId,intime,latitude,longitude,att_date); }
	 * catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * //System.out.println("insert Attendance details --> insertAttendance()" +
	 * e); } if (rowCount != null) { return rowCount; } else { return "Failure";
	 * } }
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertCheckoutAttendance")
	public String insertCheckOutAttendance(@Context UriInfo info) {
		int rowCount = 0;
		String empId = info.getQueryParameters().getFirst("empId");
		String outtime = info.getQueryParameters().getFirst("outtime");
		String outlatitude = info.getQueryParameters().getFirst("outlatitude");
		String outlongitude = info.getQueryParameters()
				.getFirst("outlongitude");
		String intime = info.getQueryParameters().getFirst("intime");
		String inlatitude = info.getQueryParameters().getFirst("inlatitude");
		String inlongitude = info.getQueryParameters().getFirst("inlongitude");
		String attendancedate = info.getQueryParameters().getFirst(
				"attendancedate");
		String insync = info.getQueryParameters().getFirst("insync");
		String outsync = info.getQueryParameters().getFirst("outsync");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertOutAttendanceDetails(empId,
					outtime, outlatitude, outlongitude, intime, inlatitude,
					inlongitude, attendancedate, insync, outsync);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert CheckOut Attendance details -->
			// insertCheckOutAttendance()"+ e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertTrackingDetails")
	public String insertTrackingDetails(@Context UriInfo info) {
		int rowCount = 0;
		String dev_id = info.getQueryParameters().getFirst("dev_id");
		String tra_datetime = info.getQueryParameters()
				.getFirst("tra_datetime");
		String tra_lat = info.getQueryParameters().getFirst("tra_lat");
		String tra_long = info.getQueryParameters().getFirst("tra_long");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertTracking(dev_id, tra_datetime,
					tra_lat, tra_long);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Attendance details --> insertAttendance()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Path("/insertTrackingLatLong")
	public String insertlatLong(@Context UriInfo info) {
		int rowCount = 0;
		String array = info.getQueryParameters().getFirst("str_ary");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertlatLong(array);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Tracking LatLong details --> insertlatLong()"+
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";

		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTaskList")
	public List<TaskVo> taskDetails(@Context UriInfo info) {
		List<TaskVo> taskDetailsList = new ArrayList<TaskVo>();
		// SvcComplaintsVo svcComplaintList = new SvcComplaintsVo();
		String emp_id = info.getQueryParameters().getFirst("emp_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			taskDetailsList = deviceResponseDao.fetchTaskList(emp_id);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch the service complaints info details -->
			// svcComplaints()"+ e);
		}
		return taskDetailsList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getNewTaskLists")
	public List<TaskVo> getSvcNewListOfTasks(@Context UriInfo info) {
		List<TaskVo> taskDetailsList = new ArrayList<TaskVo>();
		String emp_id = info.getQueryParameters().getFirst("emp_id");
		String maxDate = info.getQueryParameters().getFirst("maxDate");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			taskDetailsList = deviceResponseDao.fetchNewTaskLists(emp_id,
					maxDate);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch Svc complaint details --> getSvcNewListOfTasks()"+
			// e);
		}
		return taskDetailsList;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateTaskStatus")
	public String updateTaskStatus(@Context UriInfo info) {
		int rowCount = 0;
		String tas_id = info.getQueryParameters().getFirst("tas_id");
		String tas_status = info.getQueryParameters().getFirst("tas_status");
		String tas_com_datetime = info.getQueryParameters().getFirst(
				"tas_com_datetime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateTaskDetails(tas_id, tas_status,
					tas_com_datetime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("update Task Status --> updateTaskStatus()" +
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getFpsCode")
	public List<FpsCodeVo> getfpscode(@Context UriInfo info) {
		List<FpsCodeVo> fpsCodeList = new ArrayList<FpsCodeVo>();
		// SvcComplaintsVo svcComplaintList = new SvcComplaintsVo();
		String emp_id = info.getQueryParameters().getFirst("emp_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsCodeList = deviceResponseDao.fetchFpsCode(emp_id);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch FpsCode info details --> getfpscode()"
			// + e);
		}
		return fpsCodeList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getNewFpsCode")
	public List<FpsCodeVo> getnewfpscode(@Context UriInfo info) {
		List<FpsCodeVo> fpsCodeList = new ArrayList<FpsCodeVo>();
		// SvcComplaintsVo svcComplaintList = new SvcComplaintsVo();
		String emp_id = info.getQueryParameters().getFirst("emp_id");
		String maxDate = info.getQueryParameters().getFirst("maxDate");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsCodeList = deviceResponseDao.fetchNewFpsCode(emp_id, maxDate);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch new FpsCode info details --> getfpscode()"
			// + e);
		}
		return fpsCodeList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getProvider")
	public List<ProviderVo> getFeedback(@Context UriInfo info) {
		List<ProviderVo> provider = new ArrayList<ProviderVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			provider = deviceResponseDao.fetchProvider();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return provider;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertSurvey")
	public String insertSurvey(@Context UriInfo info) {
		int rowCount = 0;
		String fps_code = info.getQueryParameters().getFirst("fps_code");
		String fps_name = info.getQueryParameters().getFirst("fps_name");
		String owner_name = info.getQueryParameters().getFirst("owner_name");
		String owner_mobile = info.getQueryParameters()
				.getFirst("owner_mobile");
		String shopkeeper = info.getQueryParameters().getFirst("shopkeeper");
		String shopkeeper_mobile = info.getQueryParameters().getFirst(
				"shopkeeper_mobile");
		String address = info.getQueryParameters().getFirst("address");
		String signal_strength = info.getQueryParameters().getFirst(
				"signal_strength");
		String simno = info.getQueryParameters().getFirst("simno");
		String pro_id = info.getQueryParameters().getFirst("pro_id");
		String prefered_nw = info.getQueryParameters().getFirst("prefered_nw");
		String sur_lat = info.getQueryParameters().getFirst("sur_lat");
		String sur_long = info.getQueryParameters().getFirst("sur_long");
		String emp_code = info.getQueryParameters().getFirst("emp_code");
		String created_ts = info.getQueryParameters().getFirst("created_ts");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertSurveyDetails(fps_code,
					fps_name, owner_name, owner_mobile, shopkeeper,
					shopkeeper_mobile, address, signal_strength, simno, pro_id,
					prefered_nw, sur_lat, sur_long, emp_code, created_ts);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Attendance details --> insertAttendance()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTickets")
	public List<TicketsVo> getTicketissue(@Context UriInfo info) {
		List<TicketsVo> tickets = new ArrayList<TicketsVo>();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			tickets = deviceResponseDao.fetchTickets(employee_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch tickets details --> getTicketissue()" +
			// e);
		}
		return tickets;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getNewTickets")
	public List<TicketsVo> getNewTicketissue(@Context UriInfo info) {
		List<TicketsVo> newtickets = new ArrayList<TicketsVo>();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String maxDate = info.getQueryParameters().getFirst("maxDate");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			newtickets = deviceResponseDao.fetchNewTickets(employee_code,
					maxDate);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch New Tickets details --> getNewTicketissue()"
			// + e);
		}
		return newtickets;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertDwlStatus")
	public String insertDownloadStatus(@Context UriInfo info) {
		int rowCount = 0;
		String ticket_no = info.getQueryParameters().getFirst("ticket_no");
		String ticket_dwlstatus = info.getQueryParameters().getFirst(
				"ticket_dwlstatus");
		String ticket_dwltime = info.getQueryParameters().getFirst(
				"ticket_dwltime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertDownloadDetails(ticket_no,
					ticket_dwlstatus, ticket_dwltime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Download Status --> insertDownloadStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateViewStatus")
	public String updateViewedStatus(@Context UriInfo info) {
		int rowCount = 0;
		String ticket_no = info.getQueryParameters().getFirst("ticket_no");
		String ticket_viwtime = info.getQueryParameters().getFirst(
				"ticket_viwtime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertViewedDetails(ticket_no,
					ticket_viwtime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Download Status --> updateViewedStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateResponseStatus")
	public String updateResponseStatus(@Context UriInfo info) {
		int rowCount = 0;
		String ticket_no = info.getQueryParameters().getFirst("ticket_no");
		String ticket_status = info.getQueryParameters().getFirst(
				"ticket_status");
		String rep_datetime = info.getQueryParameters()
				.getFirst("rep_datetime");
		String rep_comments = info.getQueryParameters()
				.getFirst("rep_comments");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateResponseDetails(ticket_no,
					ticket_status, rep_datetime, rep_comments);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Download Status --> updateViewedStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateResolvedStatus")
	public String updateResolvedStatus(@Context UriInfo info) {
		int rowCount = 0;
		String ticket_no = info.getQueryParameters().getFirst("ticket_no");
		String ticket_status = info.getQueryParameters().getFirst(
				"ticket_status");
		String rep_datetime = info.getQueryParameters()
				.getFirst("rep_datetime");
		String rep_comments = info.getQueryParameters()
				.getFirst("rep_comments");
		String res_datetime = info.getQueryParameters()
				.getFirst("res_datetime");
		String res_comments = info.getQueryParameters()
				.getFirst("res_comments");
		String rea_comments = info.getQueryParameters()
				.getFirst("rea_comments");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateResolvedStatus(ticket_no,
					ticket_status, rep_datetime, rep_comments, res_datetime,
					res_comments, rea_comments);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Download Status --> updateViewedStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSurveyCount")
	public SurveyCountVo getSurveyCount(@Context UriInfo info) {
		SurveyCountVo surveyCountVo = new SurveyCountVo();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			surveyCountVo = deviceResponseDao
					.getTotalSurveyCount(employee_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return surveyCountVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertMacId")
	public String insertMacid(@Context UriInfo info) {
		int rowCount = 0;
		String fps_code = info.getQueryParameters().getFirst("fps_code");
		String mac_id = info.getQueryParameters().getFirst("mac_id");
		String serial_no = info.getQueryParameters().getFirst("serial_no");
		String created_datetme = info.getQueryParameters().getFirst(
				"created_datetme");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertMacidDetails(fps_code, mac_id,
					serial_no, created_datetme, employee_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Macid details --> insertMacid()" + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMacFpsCode")
	public List<FpsCodeVo> getmacfpscode(@Context UriInfo info) {
		List<FpsCodeVo> fpsCodeList = new ArrayList<FpsCodeVo>();
		// SvcComplaintsVo svcComplaintList = new SvcComplaintsVo();
		String emp_id = info.getQueryParameters().getFirst("emp_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsCodeList = deviceResponseDao.fetchmacFpsCode(emp_id);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch FpsCode info details --> getfpscode()"
			// + e);
		}
		return fpsCodeList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertAepdsdetails")
	public String insertAepds(@Context UriInfo info) {
		int rowCount = 0;
		String fps_code = info.getQueryParameters().getFirst("fps_code");
		String provider = info.getQueryParameters().getFirst("provider");
		String deviceid = info.getQueryParameters().getFirst("deviceid");
		String status = info.getQueryParameters().getFirst("status");
		String created_datetme = info.getQueryParameters().getFirst(
				"created_datetme");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String remarks = info.getQueryParameters().getFirst("remarks");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertAepdsvalues(fps_code, provider,
					deviceid, status, created_datetme, employee_code, remarks);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Macid details --> insertMacid()" + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateReasonStatus")
	public String updateReasonStatus(@Context UriInfo info) {
		int rowCount = 0;
		String ticket_no = info.getQueryParameters().getFirst("ticket_no");
		String rea_comments = info.getQueryParameters()
				.getFirst("rea_comments");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateReasonDetails(ticket_no,
					rea_comments);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Reason Status --> updateReasonStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSlaTickets")
	public List<SlaTicketsVo> getSlaTickets(@Context UriInfo info) {
		List<SlaTicketsVo> slaTicketsList = new ArrayList<SlaTicketsVo>();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			slaTicketsList = deviceResponseDao
					.getSlaTicketsDetails(employee_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Get SLA Status --> getSlaTickets()" + e);
		}
		return slaTicketsList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSiteFpsCode")
	public List<SiteCodeVo> getSitefps(@Context UriInfo info) {
		List<SiteCodeVo> siteFpsCodeList = new ArrayList<SiteCodeVo>();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String maxDatetime = info.getQueryParameters().getFirst("maxDatetime");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			siteFpsCodeList = deviceResponseDao.getSiteFpscodeDetails(
					employee_code, maxDatetime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Get Site Fps Status --> getSiteFpsCode()" +
			// e);
		}
		return siteFpsCodeList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertSiteVisit")
	public String insertSiteVisitDetails(@Context UriInfo info) {
		int rowCount = 0;
		String fpscode = info.getQueryParameters().getFirst("fpscode");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String sparerequested = info.getQueryParameters().getFirst(
				"sparerequested");
		String sparereplaced = info.getQueryParameters().getFirst(
				"sparereplaced");
		String simid = info.getQueryParameters().getFirst("simid");
		String providerid = info.getQueryParameters().getFirst("providerid");
		String lattitude = info.getQueryParameters().getFirst("lattitude");
		String longitude = info.getQueryParameters().getFirst("longitude");
		String createdtime = info.getQueryParameters().getFirst("createdtime");
		String installation = info.getQueryParameters()
				.getFirst("installation");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao
					.insertSiteDetails(fpscode, employee_code, sparerequested,
							sparereplaced, simid, providerid, lattitude,
							longitude, createdtime, installation);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Attendance details --> insertSiteVisitDetails()"+
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTicketsCount")
	public TicStatusVo getTicketCount(@Context UriInfo info) {
		TicStatusVo ticStatusVo = new TicStatusVo();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String taluk_name = info.getQueryParameters().getFirst("taluk_name");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			ticStatusVo = deviceResponseDao.getTicketsCountdetails(
					employee_code, taluk_name);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return ticStatusVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getopentickets")
	public List<OpenTicketsVo> getopenticket(@Context UriInfo info) {
		List<OpenTicketsVo> openTicketsList = new ArrayList<OpenTicketsVo>();
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String taluk_name = info.getQueryParameters().getFirst("taluk_name");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			openTicketsList = deviceResponseDao.getOpenTicketsDetails(
					employee_code, taluk_name);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Get Site Fps Status --> getSiteFpsCode()" +
			// e);
		}
		return openTicketsList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAppVersion")
	public VersionVo getAppVersion(@Context UriInfo info) {
		VersionVo versionVo = new VersionVo();
		String name = info.getQueryParameters().getFirst("name");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			versionVo = deviceResponseDao.getAppVersionDetails(name);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("App Version --> getAppVersion()" + e);
		}
		return versionVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getsimerror")
	public SimVo getsimerrordetail(@Context UriInfo info) {
		SimVo simVo = new SimVo();
		String fpscode = info.getQueryParameters().getFirst("fpscode");
		String sim_no = info.getQueryParameters().getFirst("sim_no");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			simVo = deviceResponseDao.getsimerrdetail(fpscode, sim_no);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Sim Error --> getsimerrordetail()" + e);
		}
		return simVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertdailyshoplocation")
	public String inserthppdsdailyshoplocationDetails(@Context UriInfo info) {
		int rowCount = 0;
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String sparerequested = info.getQueryParameters()
				.getFirst("complients");
		String actiontaken = info.getQueryParameters().getFirst("actiontaken");
		String newsimid = info.getQueryParameters().getFirst("new_simid");
		String others = info.getQueryParameters().getFirst("others");
		String lattitude = info.getQueryParameters().getFirst("sv_lat");
		String longitude = info.getQueryParameters().getFirst("sv_long");
		String createdtime = info.getQueryParameters().getFirst(
				"created_datetime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertdailyshoplocation(fpscode,
					employee_code, sparerequested, actiontaken, newsimid,
					others, lattitude, longitude, createdtime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Attendance details --> insertSiteVisitDetails()"+
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkfpscode")
	public FpsVo getCheckfpscode(@Context UriInfo info) {
		FpsVo fpsvo = new FpsVo();
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsvo = deviceResponseDao.getCheckfpscode(fpscode);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fps Code --> getCheckfpscode()" + e);
		}
		return fpsvo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkupfpscode")
	public FpsVo getCheckupfpscode(@Context UriInfo info) {
		FpsVo fpsvo = new FpsVo();
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsvo = deviceResponseDao.getCheckupfpscode(fpscode);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fps Code --> getCheckfpscode()" + e);
		}
		return fpsvo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertupdailyshoplocation")
	public String insertuppdsdailyshoplocationDetails(@Context UriInfo info) {
		int rowCount = 0;
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String sparerequested = info.getQueryParameters()
				.getFirst("complients");
		String actiontaken = info.getQueryParameters().getFirst("actiontaken");
		String newsimid = info.getQueryParameters().getFirst("new_simid");
		String others = info.getQueryParameters().getFirst("others");
		String lattitude = info.getQueryParameters().getFirst("sv_lat");
		String longitude = info.getQueryParameters().getFirst("sv_long");
		String createdtime = info.getQueryParameters().getFirst(
				"created_datetime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertupdailyshoplocation(fpscode,
					employee_code, sparerequested, actiontaken, newsimid,
					others, lattitude, longitude, createdtime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert up daily details -->
			// insertuppdsdailyshoplocationDetails()"+ e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Path("/insertmhdailyshoplocation")
	@Produces(MediaType.APPLICATION_JSON)
	public String insertmhpdsdailyshoplocationDetails(@Context UriInfo info)
			throws IOException {
		int rowCount = 0;
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String sparerequested = info.getQueryParameters()
				.getFirst("complients");
		String actiontaken = info.getQueryParameters().getFirst("actiontaken");
		String newsimid = info.getQueryParameters().getFirst("new_simid");
		String others = info.getQueryParameters().getFirst("others");
		String lattitude = info.getQueryParameters().getFirst("sv_lat");
		String longitude = info.getQueryParameters().getFirst("sv_long");
		String createdtime = info.getQueryParameters().getFirst(
				"created_datetime");
		// String faceImage = info.getQueryParameters().getFirst("faceImage");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertmhdailyshoplocationOld(fpscode,
					employee_code, sparerequested, actiontaken, newsimid,
					others, lattitude, longitude, createdtime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert up daily details -->
			// insertuppdsdailyshoplocationDetails()"+ e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/inserttsdailyshoplocation")
	public String inserttspdsdailyshoplocationDetails(@Context UriInfo info) {
		int rowCount = 0;
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		String employee_code = info.getQueryParameters().getFirst(
				"employee_code");
		String sparerequested = info.getQueryParameters()
				.getFirst("complients");
		String actiontaken = info.getQueryParameters().getFirst("actiontaken");
		String newsimid = info.getQueryParameters().getFirst("new_simid");
		String others = info.getQueryParameters().getFirst("others");
		String lattitude = info.getQueryParameters().getFirst("sv_lat");
		String longitude = info.getQueryParameters().getFirst("sv_long");
		String createdtime = info.getQueryParameters().getFirst(
				"created_datetime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.inserttsdailyshoplocation(fpscode,
					employee_code, sparerequested, actiontaken, newsimid,
					others, lattitude, longitude, createdtime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert up daily details -->
			// insertuppdsdailyshoplocationDetails()"+ e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checktsfpscode")
	public FpsVo getChecktsfpscode(@Context UriInfo info) {
		FpsVo fpsvo = new FpsVo();
		String fpscode = info.getQueryParameters().getFirst("fps_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			fpsvo = deviceResponseDao.getChecktsfpscode(fpscode);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fps Code --> getCheckfpscode()" + e);
		}
		return fpsvo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getcooptexCode")
	public List<CooptexCodeVo> getcooptexCode(@Context UriInfo info) {
		List<CooptexCodeVo> CooptexCodeList = new ArrayList<CooptexCodeVo>();
		// SvcComplaintsVo svcComplaintList = new SvcComplaintsVo();
		// String emp_id = info.getQueryParameters().getFirst("emp_code");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			CooptexCodeList = deviceResponseDao.fetchcooptexCode();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch FpsCode info details --> getfpscode()"
			// + e);
		}
		return CooptexCodeList;
	}

	@GET
	@Path("/insertCooptexSurveyDetails")
	public String insertCooptexSurveyDetails(@Context UriInfo info) {
		int rowCount = 0;

		String code = info.getQueryParameters().getFirst("code");
		String emp_code = info.getQueryParameters().getFirst("emp_code");
		String inc_name = info.getQueryParameters().getFirst("inc_name");
		String inc_contactno = info.getQueryParameters().getFirst(
				"inc_contactno");
		String eng_name = info.getQueryParameters().getFirst("eng_name");
		String eng_contactno = info.getQueryParameters().getFirst(
				"eng_contactno");
		String created_ts = info.getQueryParameters().getFirst("created_ts");
		String lattitude = info.getQueryParameters().getFirst("shop_lat");
		String longitude = info.getQueryParameters().getFirst("shop_long");
		String array = info.getQueryParameters().getFirst("str_ary");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertCooptexSurvey(code, emp_code,
					inc_name, inc_contactno, eng_name, eng_contactno,
					created_ts, lattitude, longitude, array);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Tracking LatLong details --> insertlatLong()"+
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";

		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUppclCodeDetails")
	public UppclCodeVo getUppclCodeDetails(@Context UriInfo info) {
		UppclCodeVo uppclDetails = new UppclCodeVo();
		String fpsid = info.getQueryParameters().getFirst("fpsid");
		DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
		uppclDetails = deviceResponseDao.uppclCodeDetails(fpsid);
		return uppclDetails;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getRechargereason")
	public List<RechargereasonVo> getRechargereason(@Context UriInfo info) {
		List<RechargereasonVo> rechargereasonList = new ArrayList<RechargereasonVo>();
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rechargereasonList = deviceResponseDao.fetchrechargereason();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch FpsCode info details --> getfpscode()"
			// + e);
		}
		return rechargereasonList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTxnreason")
	public List<TxnreasonVo> getTxnreason(@Context UriInfo info) {
		List<TxnreasonVo> txnreasonList = new ArrayList<TxnreasonVo>();
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			txnreasonList = deviceResponseDao.fetchtxnreason();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch FpsCode info details --> getfpscode()"
			// + e);
		}
		return txnreasonList;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertUppclDetails")
	public String insertUppclDetails(@Context UriInfo info) {
		int rowCount = 0;
		String fpsid = info.getQueryParameters().getFirst("fpsid");
		String fpsname = info.getQueryParameters().getFirst("fpsname");
		String village_name = info.getQueryParameters()
				.getFirst("village_name");
		String fpsowner_name = info.getQueryParameters().getFirst(
				"fpsowner_name");
		String fps_mobile = info.getQueryParameters().getFirst("fps_mobile");
		String recharge_amt = info.getQueryParameters()
				.getFirst("recharge_amt");
		String recharge_reason = info.getQueryParameters().getFirst(
				"recharge_reason");
		String recharge_remarks = info.getQueryParameters().getFirst(
				"recharge_remarks");
		String txt_amt = info.getQueryParameters().getFirst("txt_amt");
		String txt_reason = info.getQueryParameters().getFirst("txt_reason");
		String txn_remarks = info.getQueryParameters().getFirst("txn_remarks");
		String empid = info.getQueryParameters().getFirst("empid");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertUppclDetails(fpsid, fpsname,
					village_name, fpsowner_name, fps_mobile, recharge_amt,
					recharge_reason, recharge_remarks, txt_amt, txt_reason,
					txn_remarks, empid);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert uppcl details --> insertUppclDetails()"+
			// e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUppclMasterdata")
	public String updateUppclMasterdata(@Context UriInfo info) {
		int rowCount = 0;
		String fpsid = info.getQueryParameters().getFirst("fpsid");
		String fpsmobileno = info.getQueryParameters().getFirst("fpsmobileno");
		String fpsname = info.getQueryParameters().getFirst("fpsname");

		String village_name = info.getQueryParameters()
				.getFirst("village_name");
		String fpsowner_name = info.getQueryParameters().getFirst(
				"fpsowner_name");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateUppclMasterdata(fpsid,
					fpsmobileno, fpsname, village_name, fpsowner_name);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Download Status --> updateUppclMasterdata()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertleaveapply")
	public String insertleaveapply(@Context UriInfo info) {
		int rowCount = 0;
		String emp_code = info.getQueryParameters().getFirst("emp_code");
		String from_date = info.getQueryParameters().getFirst("from_date");
		String to_date = info.getQueryParameters().getFirst("to_date");
		String total_days = info.getQueryParameters().getFirst("total_days");
		String reason = info.getQueryParameters().getFirst("reason");
		String session = info.getQueryParameters().getFirst("session");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertleaveapply(emp_code, from_date,
					to_date, total_days, reason, session);
		} catch (Exception e) {

			e.printStackTrace();
			// System.out.println("insert leaveapply details --> insertleaveapply()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLeaveList")
	public List<LeavelistVo> getLeavelist(@Context UriInfo info) {
		List<LeavelistVo> leavelist = new ArrayList<LeavelistVo>();
		String employee_code = info.getQueryParameters().getFirst("emp_code");
		String roleid = info.getQueryParameters().getFirst("roleid");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			leavelist = deviceResponseDao.fetchleavelist(employee_code, roleid);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch tickets details --> getTicketissue()" +
			// e);
		}
		return leavelist;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateleaveStatus")
	public String updateleaveStatus(@Context UriInfo info) {
		int rowCount = 0;
		String id = info.getQueryParameters().getFirst("id");
		String emp_code = info.getQueryParameters().getFirst("emp_code");
		String leave_status = info.getQueryParameters()
				.getFirst("leave_status");
		String evaluated_by = info.getQueryParameters()
				.getFirst("evaluated_by");
		String rejected_reason = info.getQueryParameters().getFirst(
				"rejected_reason");
		String evaluated_datetime = info.getQueryParameters().getFirst(
				"evaluated_datetime");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.updateleaveStatusDetails(id, emp_code,
					leave_status, evaluated_by, rejected_reason,
					evaluated_datetime);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Reason Status --> updateReasonStatus()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLeaveCount")
	public LeavecountVo getLeaveCount(@Context UriInfo info) {
		LeavecountVo leavecountVo = new LeavecountVo();
		String employee_code = info.getQueryParameters().getFirst("emp_code");
		String roleid = info.getQueryParameters().getFirst("role_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			leavecountVo = deviceResponseDao.getLeaveCountdetails(
					employee_code, roleid);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return leavecountVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAttendanceempreportList")
	public List<AttendanceempreportlistVo> getAttendanceempreportpList(
			@Context UriInfo info) {
		List<AttendanceempreportlistVo> attendanceempreportlist = new ArrayList<AttendanceempreportlistVo>();
		String att_type = info.getQueryParameters().getFirst("att_type");
		String Date = info.getQueryParameters().getFirst("att_date");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			attendanceempreportlist = deviceResponseDao
					.fetchattendanceempreportlist(att_type, Date);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch Attendancermpreport details --> getTicketissue()"
			// +
			// e);
		}
		return attendanceempreportlist;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAttendanceempreportListNew")
	public List<AttendanceempreportlistVo> getAttendanceempreportpListNew(
			@Context UriInfo info) {
		List<AttendanceempreportlistVo> attendanceempreportlist = new ArrayList<AttendanceempreportlistVo>();
		String att_type = info.getQueryParameters().getFirst("att_type");
		String Date = info.getQueryParameters().getFirst("att_date");
		String role_id = info.getQueryParameters().getFirst("role_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			attendanceempreportlist = deviceResponseDao
					.fetchattendanceempreportlistnew(att_type, Date, role_id);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch Attendancermpreport details --> getTicketissue()"
			// +
			// e);
		}
		return attendanceempreportlist;
	}

	@GET
	@Path("/insertTrackingLatLongNew")
	public String insertlatLongNew(@Context UriInfo info) {
		int rowCount = 0;
		String array = info.getQueryParameters().getFirst("str_ary");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertlatLongNew(array);

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert Tracking LatLongNew details -->
			// insertlatLongNew()"+ e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";

		}
	}

	@POST
	@Path("/insertmhdailyshoplocationNew")
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertmhdailyshoplocationNew(MhpdsShopVisitVo mhpdsShopVisitVo)
			throws IOException {
		int rowCount = 0;
		Date today = new Date();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		String date = DATE_FORMAT.format(today);
		// System.out.println("Working Directory = " +
		// System.getProperty("user.dir"));
		String imagePath = "http://108.170.29.66:9144/PDSFSWSTEST/Images/"
				+ mhpdsShopVisitVo.getEmpCode() + "_" + date + ".jpg";
		// String
		// imagePath="Images/"+mhpdsShopVisitVo.getEmpCode()+"_"+date+".jpg";
		String imageurl = "/home/pnap2715/TomcatApacheServer/apache-tomcat-oasysProject/PDSFS_WSTEST/webapps/PDSFSWSTEST/Images/"
				+ mhpdsShopVisitVo.getEmpCode() + "_" + date + ".jpg";
		// String
		// imagePath="D:/MHPDSWSPHOTO/"+mhpdsShopVisitVo.getEmpCode()+".jpg";
		// byte[] decodedString = Base64.getDecoder().decode(new
		// String(mhpdsShopVisitVo.getPhotoString()).getBytes("UTF-8"));

		byte[] decodedString = Base64.decodeBase64(new String(mhpdsShopVisitVo
				.getPhotoString()));
		ByteArrayInputStream bis = new ByteArrayInputStream(decodedString);
		BufferedImage bImage2 = ImageIO.read(bis);
		File output_file = new File(imageurl);
		// File output_file = new File(imagePath);
		// ImageIO.write(bImage2, "jpg", output_file);
		ImageIO.write(bImage2, "png", output_file);
		DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
		rowCount = deviceResponseDao.insertmhdailyshoplocation(
				mhpdsShopVisitVo.getFpsCode(), mhpdsShopVisitVo.getEmpCode(),
				mhpdsShopVisitVo.getComplients(),
				mhpdsShopVisitVo.getActiontaken(),
				mhpdsShopVisitVo.getNew_simid(), mhpdsShopVisitVo.getOthers(),
				mhpdsShopVisitVo.getSv_lat(), mhpdsShopVisitVo.getSv_long(),
				mhpdsShopVisitVo.getCreated_datetime(), imagePath);
		// rowCount =
		// deviceResponseDao.updatePhotoUpload(imagePath,photoUploadVo.getEmpCode());
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";

		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getShoptype")
	public List<ShopTypeVo> getShoptype(@Context UriInfo info) {
		List<ShopTypeVo> shopType = new ArrayList<ShopTypeVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			shopType = deviceResponseDao.fetchShopType();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return shopType;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLicencetype")
	public List<LicenceVo> getLicencetype(@Context UriInfo info) {
		List<LicenceVo> licenceType = new ArrayList<LicenceVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			licenceType = deviceResponseDao.fetchLicenceType();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return licenceType;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDivision")
	public List<DivisionVo> getDivision(@Context UriInfo info) {
		List<DivisionVo> divisionName = new ArrayList<DivisionVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			divisionName = deviceResponseDao.fetchDivisionName();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return divisionName;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getupexcisedistrict")
	public List<DistrictVo> getDistrict(@Context UriInfo info) {
		List<DistrictVo> districtName = new ArrayList<DistrictVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			districtName = deviceResponseDao.fetchDistrictName();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return districtName;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getupexcisetaluk")
	public List<TalukVo> getTalukName(@Context UriInfo info) {
		List<TalukVo> talukVo = new ArrayList<TalukVo>();
		String district = info.getQueryParameters().getFirst("districtname");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			talukVo = deviceResponseDao.gettalukdetails(district);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return talukVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getupexcisecircle")
	public List<CircleVo> getCircle(@Context UriInfo info) {
		List<CircleVo> circleName = new ArrayList<CircleVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			circleName = deviceResponseDao.fetchCircleName();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return circleName;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getupexcisesector")
	public List<SectorVo> getSector(@Context UriInfo info) {
		List<SectorVo> sectorName = new ArrayList<SectorVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			sectorName = deviceResponseDao.fetchSectorName();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("fetch provider details --> getFeedback()" +
			// e);
		}
		return sectorName;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getupexciseshopinfo")
	public ShopCodeVo getShopInfo(@Context UriInfo info) {
		ShopCodeVo shopcode = new ShopCodeVo();
		String shop_code = info.getQueryParameters().getFirst("shopcode");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			shopcode = deviceResponseDao.getshopinfodetails(shop_code);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Survey Count --> getSurveyCount()" + e);
		}
		return shopcode;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/insertUpExiseSurvey")
	public String insertUpExiseSurvey(@Context UriInfo info) {
		int rowCount = 0;
		String shopcode = info.getQueryParameters().getFirst("shopcode");
		String deviceid = info.getQueryParameters().getFirst("deviceid");
		String deviceid1 = info.getQueryParameters().getFirst("deviceid1");
		String shopname = info.getQueryParameters().getFirst("shopname");
		String shoptype = info.getQueryParameters().getFirst("shoptype");
		String shopaddress = info.getQueryParameters().getFirst("shopaddress");
		String shopcontactno = info.getQueryParameters().getFirst(
				"shopcontactno");
		String salespersonname = info.getQueryParameters().getFirst(
				"salespersonname");
		String salespersonno = info.getQueryParameters().getFirst(
				"salespersonno");
		String licencename = info.getQueryParameters().getFirst("licencename");
		String licencecontactno = info.getQueryParameters().getFirst(
				"licencecontactno");
		String licencetype = info.getQueryParameters().getFirst("licencetype");
		String licenceno = info.getQueryParameters().getFirst("licenceno");
		String exciseinspectorname = info.getQueryParameters().getFirst(
				"exciseinspectorname");
		String exciseinspectorno = info.getQueryParameters().getFirst(
				"exciseinspectorno");
		String circletype = info.getQueryParameters().getFirst("circletype");
		String sectortype = info.getQueryParameters().getFirst("sectortype");
		String division = info.getQueryParameters().getFirst("division");
		String district = info.getQueryParameters().getFirst("district");
		String taluk = info.getQueryParameters().getFirst("taluk");
		String block = info.getQueryParameters().getFirst("block");
		String pincode = info.getQueryParameters().getFirst("pincode");
		String latitude = info.getQueryParameters().getFirst("latitude");
		String longitude = info.getQueryParameters().getFirst("longitude");
		String empcode = info.getQueryParameters().getFirst("empcode");

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			rowCount = deviceResponseDao.insertUpExisesurveyDetails(shopcode,
					deviceid, deviceid1, shopname, shoptype, shopaddress,
					shopcontactno, salespersonname, salespersonno, licencename,
					licencecontactno, licencetype, licenceno,
					exciseinspectorname, exciseinspectorno, circletype,
					sectortype, division, district, taluk, block, pincode,
					latitude, longitude, empcode);
		} catch (Exception e) {

			e.printStackTrace();
			// //
			// System.out.println("insert Attendance details --> insertAttendance()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkdeviceid")
	public String getCheckupdeviceid(@Context UriInfo info) {
		String result = "";
		String deviceid = info.getQueryParameters().getFirst("device_id");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			result = deviceResponseDao.getCheckdeviceid(deviceid);
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (result.length() > 0) {
			return "Success";
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getshopidhint")
	public HintVo getshopidhint(@Context UriInfo info) {
		HintVo hintVo = new HintVo();
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			hintVo = deviceResponseDao.getshopidhintdetails();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return hintVo;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/timeDiffIn")
	public String getTimeDiff(@Context UriInfo info) {
		int rowCount = 0;

		String empId = info.getQueryParameters().getFirst("empId");
		String intime = info.getQueryParameters().getFirst("intime");
		String latitude = info.getQueryParameters().getFirst("latitude");
		String longitude = info.getQueryParameters().getFirst("longitude");
		String att_date = info.getQueryParameters().getFirst("att_date");
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format.format(currentDate);
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			Date start = new Date();

			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(intime);

			long totalMinutes = getMinutesBetweenTwoDates(start, date1);
			// System.out.println(totalMinutes);
			/*
			 * long diff = date1.getTime() - start.getTime(); long
			 * difference_In_Minutes = (diff / (1000 * 60)) % 60; long
			 * difference_In_Hours = (diff / (1000 * 60 * 60)) % 24; long
			 * difference_In_Days= (diff / (1000 * 60 * 60 * 24)) % 365;
			 * 
			 * long minutes =difference_In_Days*24*60+ difference_In_Hours * 60
			 * + difference_In_Minutes;
			 */
			// if(difference_In_Minutes==1 || difference_In_Minutes==0 ||
			// difference_In_Minutes == -1){
			if (totalMinutes >= -10 && totalMinutes <= 10) {
				rowCount = deviceResponseDao.insertAttendanceDetailsNew(empId,
						intime, latitude, longitude, att_date, sqlDate);
			} else {
				return "invalidInTime";
			}
		} catch (Exception e) {

			e.printStackTrace();
			// //
			// System.out.println("insert Attendance details --> insertAttendance()"
			// + e);
		}
		if (rowCount > 0) {
			return "Success" + "@" + sqlDate;
		} else {
			return "Failure";
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/timeDiffOut")
	public String getTimeDiffOut(@Context UriInfo info) {
		int rowCount = 0;
		String empId = info.getQueryParameters().getFirst("empId");
		String outtime = info.getQueryParameters().getFirst("outtime");
		String outlatitude = info.getQueryParameters().getFirst("outlatitude");
		String outlongitude = info.getQueryParameters()
				.getFirst("outlongitude");
		String intime = info.getQueryParameters().getFirst("intime");
		String inlatitude = info.getQueryParameters().getFirst("inlatitude");
		String inlongitude = info.getQueryParameters().getFirst("inlongitude");
		String attendancedate = info.getQueryParameters().getFirst(
				"attendancedate");
		String insync = info.getQueryParameters().getFirst("insync");
		String outsync = info.getQueryParameters().getFirst("outsync");
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = format.format(currentDate);
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();

			Date start = new Date();

			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(outtime);
			long totalMinutes = getMinutesBetweenTwoDates(start, date1);
			/*
			 * long diff = date1.getTime() - start.getTime(); long
			 * difference_In_Minutes = (diff / (1000 * 60)) % 60; long
			 * difference_In_Hours = (diff / (1000 * 60 * 60)) % 24;
			 * 
			 * long minutes = difference_In_Hours * 60 + difference_In_Minutes;
			 */
			// if(difference_In_Minutes==1 || difference_In_Minutes==0 ||
			// difference_In_Minutes == -1){

			if (totalMinutes >= -10 && totalMinutes <= 10) {

				rowCount = deviceResponseDao.insertOutAttendanceDetailsNew(
						empId, outtime, outlatitude, outlongitude, intime,
						inlatitude, inlongitude, attendancedate, insync,
						outsync, sqlDate);
			} else {
				return "invalidOutTime";
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("insert CheckOut Attendance details -->
			// insertCheckOutAttendance()"+ e);
		}
		if (rowCount > 0) {
			return "Success" + "@" + sqlDate;
		} else {
			return "Failure";
		}
	}

	public static long getMinutesBetweenTwoDates(Date start, Date end) {
		long diff = Math.abs(end.getTime() - start.getTime());
		return (long) (diff / 1000 / 60);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getIssueType")
	public List<SurveyMasterVo> getIssueType(@Context UriInfo info) {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();

		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			surveyMaster = deviceResponseDao.getIssueType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surveyMaster;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getObservation")
	public List<SurveyMasterVo> getObservation(@Context UriInfo info) {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();
		String issueType = info.getQueryParameters().getFirst("issue_type");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			surveyMaster = deviceResponseDao.getObservation(issueType);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return surveyMaster;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getActionStatus")
	public List<SurveyMasterVo> getActionStatus(@Context UriInfo info) {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();
		String issueType = info.getQueryParameters().getFirst("issue_type");
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			surveyMaster = deviceResponseDao.getActionStatus(issueType);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return surveyMaster;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEntityType")
	public List<EntityTypeVo> getEntityType(@Context UriInfo info) {
		List<EntityTypeVo> entityTypeVos = new ArrayList<EntityTypeVo>();
		try {
			DeviceResponseDao deviceResponseDao = new DeviceResponseDaoImpl();
			entityTypeVos = deviceResponseDao.getEntityType();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return entityTypeVos;
	}
}
