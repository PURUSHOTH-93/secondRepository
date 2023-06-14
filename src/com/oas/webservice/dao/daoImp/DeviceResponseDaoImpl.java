package com.oas.webservice.dao.daoImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oas.webservice.dao.DeviceResponseDao;
import com.oas.webservice.dbutil.CommonDbUtil;
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

public class DeviceResponseDaoImpl implements DeviceResponseDao {

	public static final int MYSQL_DUPLICATE_PK = 1062;
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public EmpLoginVo empLoginDetails(String uName, String password,
			String androidid) {

		EmpLoginVo loginVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "{call USP_GET_Check_User('" + uName + "','" + password
						+ "','" + androidid + "')}";

				// System.out.println(Inside the svcLoginDetails Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					loginVo = new EmpLoginVo();
					loginVo.setEmpName(resultSet.getString("sEmpname"));
					loginVo.setRoleID(resultSet.getString("sRoleID"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return loginVo;
	}

	@Override
	public int insertAttendanceDetails(String empId, String intime,
			String latitude, String longitude, String att_date) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_attendance(employee_code,att_in_datetime,att_in_lat,att_in_long,att_date) values ('"
					+ empId
					+ "','"
					+ intime
					+ "','"
					+ latitude
					+ "','"
					+ longitude + "','" + att_date + "')";
			// System.out.println(the insert feedback details Query " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Atttendance Detail for
			// Dao Impl ----->insertAttendanceDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public CheckinVo getCheckInData(String employee_code) {
		CheckinVo checkinVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select * from tbl_attendance where employee_code='"
						+ employee_code + "' AND att_date = DATE(NOW())";

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					checkinVo = new CheckinVo();
					checkinVo.setEmpId(resultSet.getString("employee_code"));
					checkinVo.setIntime(resultSet.getString("server_ts"));
					checkinVo.setLatitude(resultSet.getString("att_in_lat"));
					checkinVo.setLongitude(resultSet.getString("att_in_long"));
					checkinVo.setOuttime(resultSet.getString("server_outtime"));
					checkinVo
							.setOutlatitude(resultSet.getString("att_out_lat"));
					checkinVo.setOutlongitude(resultSet
							.getString("att_out_long"));
					checkinVo.setAtt_date(resultSet.getString("att_date"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return checkinVo;

	}

	/*
	 * @Override public String insertAttendanceDetails(String empId, String
	 * intime, String latitude, String longitude, String att_date) { int result
	 * = 0; String attendanceInTime = null; String returnValue = null;
	 * CommonDbUtil commonDbUtil = new CommonDbUtil(); try { String sql =
	 * "Insert into tbl_attendance(employee_code,att_in_datetime,att_in_lat,att_in_long,att_date) values ('"
	 * +empId+"','"+intime+ "','"+ latitude+ "','"+ longitude +
	 * "','"+att_date+"')"; //System.out.println(Inside the insert feedback
	 * details Query " + sql); Class.forName(commonDbUtil.getJdbcDriver()); if
	 * (commonDbUtil.isDbConnectionCheck()) { connection =
	 * commonDbUtil.openConnectionGO(connection);
	 * 
	 * statement = connection.createStatement();
	 * 
	 * preparedStatement = connection.prepareStatement(sql);
	 * 
	 * result = preparedStatement.executeUpdate();
	 * 
	 * } } catch (Exception e) { String sql1 =
	 * "SELECT DATE_FORMAT(att_in_datetime,'%T') AS in_time FROM tbl_attendance WHERE employee_code='"
	 * +empId+"' AND att_date = DATE(NOW())"; try { PreparedStatement
	 * statementSelectQuery; //System.out.println(Inside the insert feedback
	 * details Query " + sql1); Class.forName(commonDbUtil.getJdbcDriver()); if
	 * (commonDbUtil.isDbConnectionCheck()) { connection =
	 * commonDbUtil.openConnectionGO(connection); statementSelectQuery =
	 * connection.prepareStatement(sql1); resultSet =
	 * statementSelectQuery.executeQuery(); while(resultSet.next()){
	 * attendanceInTime = resultSet.getString("in_time"); } } } catch (Exception
	 * ex) { ex.printStackTrace(); } e.printStackTrace(); System.out.println(
	 * "Error while insert the Atttendance Detail  for Dao Impl ----->insertAttendanceDetails()"
	 * + e); } finally { commonDbUtil.closeAllConnections(resultSet,
	 * preparedStatement, statement, connection); }
	 * 
	 * if(result > 0){ returnValue = result+""; } else if (attendanceInTime !=
	 * null ){ returnValue = attendanceInTime; } else{ returnValue = "0"; }
	 * 
	 * 
	 * return returnValue; }
	 */

	@Override
	public int insertTracking(String dev_id, String tra_datetime,
			String tra_lat, String tra_long) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_tracking(dev_id,tra_datetime,tra_lat,tra_long) values ('"
					+ dev_id
					+ "','"
					+ tra_datetime
					+ "','"
					+ tra_lat
					+ "','"
					+ tra_long + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Tracking Detail for Dao
			// Impl ----->insertTracking()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;

	}

	@Override
	public int insertlatLong(String array) {
		int result = 0;
		String sql = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(array
				.split("\\]")));
		String[] stockArr = new String[myList.size()];
		stockArr = myList.toArray(stockArr);
		for (int i = 0; i < stockArr.length; i++)
		// for (int i = 0; i < myList; i++)
		{
			if (stockArr[i].contains(",")) {
				String[] types = stockArr[i].split(",");

				try {
					if (i >= 1) {
						sql = "Insert into tbl_tracking(dev_id,tra_datetime,tra_lat,tra_long)values('"
								+ types[1].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[2].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[3].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[4].replace("[", "").replace("]", "")
										.trim() + "')";
					} else {

						sql = "Insert into tbl_tracking(dev_id,tra_datetime,tra_lat,tra_long)values('"
								+ types[0].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[1].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[2].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[3].replace("[", "").replace("]", "")
										.trim() + "')";
					}
					// System.out.println(Inside the insert latlong details
					// Query " + sql);
					if (commonDbUtil.isDbConnectionCheck()) {
						connection = commonDbUtil.openConnectionGO(connection);

						statement = connection.createStatement();

						preparedStatement = connection.prepareStatement(sql);

						result = preparedStatement.executeUpdate();

					}

				} catch (SQLException e) {

					if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
						result = 1;
					}

					// System.out.println(Error while insert the Tracking
					// latlong details for Dao Impl ---->insertlatLong() "+ e);
				} finally {
					commonDbUtil.closeAllConnections(resultSet,
							preparedStatement, statement, connection);
				}

			}
		}
		return result;
	}

	@Override
	public List<TaskVo> fetchTaskList(String emp_id) {

		List<TaskVo> taskLists = new ArrayList<TaskVo>();
		TaskVo taskListsVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT  a.fps_code,ticket,a.created_ts,ticket_id,a.status,contact_name,contact_mobile FROM tbl_ticket a,tbl_fps b "
						+ " WHERE a.fps_code = b.code AND b.employee_code = '"
						+ emp_id + "' AND a.status = '1'	";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					taskListsVo = new TaskVo();
					taskListsVo.setFps_code(resultSet.getString(1));
					taskListsVo.setTicket(resultSet.getString(2));
					taskListsVo.setCreated_ts(resultSet.getString(3));
					taskListsVo.setTicket_id(resultSet.getString(4));
					taskListsVo.setStatus(resultSet.getString(5));
					taskListsVo.setContact_name(resultSet.getString(6));
					taskListsVo.setContact_mobile(resultSet.getString(7));
					taskLists.add(taskListsVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Task list the details for
			// Dao Impl ----->fetchTaskList()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return taskLists;
	}

	@Override
	public List<TaskVo> fetchNewTaskLists(String emp_id, String maxDate) {
		List<TaskVo> svcListOfTasks = new ArrayList<TaskVo>();
		TaskVo taskVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT tas_id,employee_code,tas_asg_datetime, tas_task,tas_status,tas_assignedby FROM  tbl_task"
						+ " WHERE employee_code='"
						+ emp_id
						+ "' AND tas_asg_datetime > '"
						+ maxDate
						+ "' AND tas_status != 'Completed' ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					taskVo = new TaskVo();
					taskVo.setFps_code(resultSet.getString(1));
					taskVo.setTicket(resultSet.getString(2));
					taskVo.setCreated_ts(resultSet.getString(3));
					taskVo.setTicket_id(resultSet.getString(4));
					taskVo.setStatus(resultSet.getString(5));
					taskVo.setContact_name(resultSet.getString(6));
					taskVo.setContact_mobile(resultSet.getString(7));

					svcListOfTasks.add(taskVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch New Task Lists for Dao Impl
			// ----->fetchNewTaskLists()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return svcListOfTasks;
	}

	@Override
	public int updateTaskDetails(String tas_id, String tas_status,
			String tas_com_datetime) {

		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_task set tas_status ='" + tas_status
					+ "', tas_com_datetime = '" + tas_com_datetime
					+ "' where tas_id = '" + tas_id + "'";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while update Task Details for Dao Impl
			// ----->updateTaskDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertOutAttendanceDetails(String empId, String outtime,
			String outlatitude, String outlongitude, String intime,
			String inlatitude, String inlongitude, String attendancedate,
			String insync, String outsync) {
		int result = 0;
		String sql = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			if (insync.equals("0") && outsync.equals("0")) {
				sql = "Insert into tbl_attendance(employee_code,att_in_datetime,att_out_datetime,att_in_lat,att_in_long,att_out_lat,att_out_long,att_date,server_outtime) "
						+ "values ('"
						+ empId
						+ "','"
						+ intime
						+ "','"
						+ outtime
						+ "','"
						+ inlatitude
						+ "','"
						+ inlongitude
						+ "'"
						+ ",'"
						+ outlatitude
						+ "','"
						+ outlongitude
						+ "','" + attendancedate + "',now()";
			} else {
				sql = "Update tbl_attendance set att_out_datetime = '"
						+ outtime + "', att_out_lat = '" + outlatitude
						+ "' , att_out_long = '" + outlongitude
						+ "', server_outtime = now()"
						+ " where employee_code = '" + empId
						+ "' AND att_date = '" + attendancedate + "'  ";
			}

			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			sql = "Update tbl_attendance set att_out_datetime = '" + outtime
					+ "' , att_out_lat = '" + outlatitude
					+ "' , att_out_long = '" + outlongitude
					+ "', server_outtime = now()" + " where employee_code = '"
					+ empId + "' AND att_date = '" + attendancedate + "'  ";

			try {
				Class.forName(commonDbUtil.getJdbcDriver());
				if (commonDbUtil.isDbConnectionCheck()) {
					connection = commonDbUtil.openConnectionGO(connection);

					statement = connection.createStatement();

					preparedStatement = connection.prepareStatement(sql);

					result = preparedStatement.executeUpdate();

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
			// System.out.println(Error while insert the Atttendance Detail for
			// Dao Impl ----->insertAttendanceDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<FpsCodeVo> fetchFpsCode(String emp_id) {
		List<FpsCodeVo> fpsCodeLists = new ArrayList<FpsCodeVo>();
		FpsCodeVo fpscodeVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT a.code,a.name AS tehsil,b.name AS district,a.created_ts FROM tbl_fps a,tbl_district b WHERE a.district_id = b.id AND employee_code = '"
						+ emp_id + "' AND status = '0' ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpscodeVo = new FpsCodeVo();
					fpscodeVo.setCode(resultSet.getString(1));
					fpscodeVo.setTehsil(resultSet.getString(2));
					fpscodeVo.setDistrict(resultSet.getString(3));
					fpscodeVo.setCreatedDate(resultSet.getString(4));

					fpsCodeLists.add(fpscodeVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Task list the details for
			// Dao Impl ----->fetchTaskList()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return fpsCodeLists;
	}

	@Override
	public List<ProviderVo> fetchProvider() {

		List<ProviderVo> providers = new ArrayList<ProviderVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_provider ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					ProviderVo providerVo = new ProviderVo();
					providerVo.setId(resultSet.getString(1));
					providerVo.setProvider(resultSet.getString(2));

					providers.add(providerVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return providers;
	}

	@Override
	public int insertSurveyDetails(String fps_code, String fps_name,
			String owner_name, String owner_mobile, String shopkeeper,
			String shopkeeper_mobile, String address, String signal_strength,
			String simno, String pro_id, String prefered_nw, String sur_lat,
			String sur_long, String emp_code, String created_ts) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_survey(fps_code,fps_name,owner_name,owner_mobile,shopkeeper,shopkeeper_mobile,address,"
					+ "signal_strength,simno,pro_id,prefered_nw,sur_lat,sur_long,emp_code,created_ts) values "
					+ "('"
					+ fps_code
					+ "','"
					+ fps_name
					+ "','"
					+ owner_name
					+ "','"
					+ owner_mobile
					+ "','"
					+ shopkeeper
					+ "'"
					+ ",'"
					+ shopkeeper_mobile
					+ "','"
					+ address
					+ "','"
					+ signal_strength
					+ "','"
					+ simno
					+ "','"
					+ pro_id
					+ "','"
					+ prefered_nw
					+ "','"
					+ sur_lat
					+ "','"
					+ sur_long
					+ "','"
					+ emp_code + "','" + created_ts + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

				if (result > 0) {
					try {
						String sql1 = "Update tbl_fps set status = '1' where code = '"
								+ fps_code + "' ";
						Class.forName(commonDbUtil.getJdbcDriver());
						if (commonDbUtil.isDbConnectionCheck()) {
							connection = commonDbUtil
									.openConnectionGO(connection);

							statement = connection.createStatement();

							preparedStatement = connection
									.prepareStatement(sql1);

							result = preparedStatement.executeUpdate();
						}
					} catch (Exception e) {
						// System.out.println(Error while insert the Survey
						// Detail for Dao Impl ----->insertSurveyDetails()"+ e);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Survey Detail for Dao
			// Impl ----->insertSurveyDetails()"+ e);
			try {
				String sql1 = "Update tbl_survey set fps_code = '" + fps_code
						+ "' , fps_name = '" + fps_name + "' , owner_name = '"
						+ owner_name + "' , owner_mobile = '" + owner_mobile
						+ "'" + " , shopkeeper = '" + shopkeeper
						+ "' , shopkeeper_mobile = '" + shopkeeper_mobile
						+ "' , address = '" + address
						+ "' , signal_strength = '" + signal_strength + "'"
						+ ", simno = '" + simno + "', pro_id = '" + pro_id
						+ "', prefered_nw = '" + prefered_nw + "'"
						+ ", sur_lat = '" + sur_lat + "', sur_long = '"
						+ sur_long + "', emp_code = '" + emp_code
						+ "', created_ts = '" + created_ts + "'"
						+ " where fps_code = '" + fps_code + "'  ";

				// System.out.println(Inside the update Survey details Query " +
				// sql1);
				Class.forName(commonDbUtil.getJdbcDriver());
				if (commonDbUtil.isDbConnectionCheck()) {
					connection = commonDbUtil.openConnectionGO(connection);

					statement = connection.createStatement();

					preparedStatement = connection.prepareStatement(sql1);

					result = preparedStatement.executeUpdate();
					if (result > 0) {
						try {
							String sqlCatchUpdate = "Update tbl_fps set status = '1' where code = '"
									+ fps_code + "' ";
							Class.forName(commonDbUtil.getJdbcDriver());
							if (commonDbUtil.isDbConnectionCheck()) {
								connection = commonDbUtil
										.openConnectionGO(connection);

								statement = connection.createStatement();

								preparedStatement = connection
										.prepareStatement(sqlCatchUpdate);

								result = preparedStatement.executeUpdate();
							}
						} catch (Exception e1) {
							// System.out.println(Error while insert the Survey
							// Detail for Dao Impl ----->insertSurveyDetails()"+
							// e1);
						}
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				// System.out.println(Error while insert the Survey Detail for
				// Dao Impl ----->insertSurveyDetails()"+ e);
			}

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<FpsCodeVo> fetchNewFpsCode(String emp_id, String maxDate) {

		List<FpsCodeVo> fpsCodeLists = new ArrayList<FpsCodeVo>();
		FpsCodeVo fpscodeVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT a.code,a.name AS tehsil,b.name AS district,a.created_ts FROM tbl_fps a,tbl_district b WHERE a.district_id = b.id AND employee_code = '"
						+ emp_id
						+ "' AND a.created_ts > '"
						+ maxDate
						+ "'  AND  status = '0' ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpscodeVo = new FpsCodeVo();
					fpscodeVo.setCode(resultSet.getString(1));
					fpscodeVo.setTehsil(resultSet.getString(2));
					fpscodeVo.setDistrict(resultSet.getString(3));
					fpscodeVo.setCreatedDate(resultSet.getString(4));

					fpsCodeLists.add(fpscodeVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Task list the details for
			// Dao Impl ----->fetchTaskList()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return fpsCodeLists;
	}

	@Override
	public List<TicketsVo> fetchTickets(String employee_code) {

		List<TicketsVo> ticketLists = new ArrayList<TicketsVo>();
		TicketsVo ticketsVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT a.ticket_no,a.fps_code,a.name,a.Mobile,a.ticket_status,a.ticket_issue,a.employee_code,a.created_datetime,a.ticket_dwlstatus,a.rep_comments,a.rep_datetime,c.name AS district,"
						+ "b.name AS tehsil FROM tbl_ticket a,tbl_fps b,tbl_district c WHERE a.fps_code=b.code AND b.district_id=c.id AND a.employee_code = '"
						+ employee_code
						+ "' AND (a.ticket_status = '1' OR a.ticket_status = '2') ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					ticketsVo = new TicketsVo();
					ticketsVo.setTicketNo(resultSet.getString(1));
					ticketsVo.setFpsCode(resultSet.getString(2));
					ticketsVo.setName(resultSet.getString(3));
					ticketsVo.setMobile(resultSet.getString(4));
					ticketsVo.setTicketStatus(resultSet.getString(5));
					ticketsVo.setTicketIssue(resultSet.getString(6));
					ticketsVo.setEmployeecode(resultSet.getString(7));
					ticketsVo.setCreatedDate(resultSet.getString(8));
					ticketsVo.setDownloadStatus(resultSet.getString(9));
					ticketsVo.setResponse_comments(resultSet.getString(10));
					ticketsVo.setResponse_datetime(resultSet.getString(11));
					ticketsVo.setDistrict(resultSet.getString(12));
					ticketsVo.setTehsil(resultSet.getString(13));

					ticketLists.add(ticketsVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Tickets list the details for
			// Dao Impl ----->fetchTickets()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return ticketLists;
	}

	@Override
	public List<TicketsVo> fetchNewTickets(String employee_code, String maxDate) {

		List<TicketsVo> ticketLists = new ArrayList<TicketsVo>();
		TicketsVo ticketsVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT a.ticket_no,a.fps_code,a.name,a.Mobile,a.ticket_status,a.ticket_issue,a.employee_code,a.created_datetime,a.ticket_dwlstatus,c.name AS district,"
						+ "b.name AS tehsil FROM tbl_ticket a,tbl_fps b,tbl_district c WHERE a.fps_code=b.code AND b.district_id=c.id AND a.employee_code = '"
						+ employee_code
						+ "' AND a.created_datetime > '"
						+ maxDate
						+ "'  AND (a.ticket_status = '1' OR a.ticket_status = '2') ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					ticketsVo = new TicketsVo();
					ticketsVo.setTicketNo(resultSet.getString(1));
					ticketsVo.setFpsCode(resultSet.getString(2));
					ticketsVo.setName(resultSet.getString(3));
					ticketsVo.setMobile(resultSet.getString(4));
					ticketsVo.setTicketStatus(resultSet.getString(5));
					ticketsVo.setTicketIssue(resultSet.getString(6));
					ticketsVo.setEmployeecode(resultSet.getString(7));
					ticketsVo.setCreatedDate(resultSet.getString(8));
					ticketsVo.setDownloadStatus(resultSet.getString(9));
					ticketsVo.setDistrict(resultSet.getString(10));
					ticketsVo.setTehsil(resultSet.getString(11));

					ticketLists.add(ticketsVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Tickets list the details for
			// Dao Impl ----->fetchTickets()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return ticketLists;
	}

	@Override
	public int insertDownloadDetails(String ticket_no, String ticket_dwlstatus,
			String ticket_dwltime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_ticket set ticket_dwlstatus = '"
					+ ticket_dwlstatus + "' , ticket_dwltime = '"
					+ ticket_dwltime + "' where ticket_no = '" + ticket_no
					+ "'";
			// System.out.println(Inside the insertDownload Details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertViewedDetails(String ticket_no, String ticket_viwtime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_ticket set ticket_dwlstatus = '6' , ticket_viwtime = '"
					+ ticket_viwtime
					+ "' where ticket_no = '"
					+ ticket_no
					+ "'";
			// System.out.println(Inside the insertViewed Details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int updateResponseDetails(String ticket_no, String ticket_status,
			String rep_datetime, String rep_comments) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_ticket set ticket_status = '2' , rep_datetime = '"
					+ rep_datetime
					+ "' , rep_comments = '"
					+ rep_comments
					+ "'  where ticket_no = '" + ticket_no + "'";
			// System.out.println(Inside the insertViewed Details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int updateResolvedStatus(String ticket_no, String ticket_status,
			String rep_datetime, String rep_comments, String res_datetime,
			String res_comments, String rea_comments) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_ticket set ticket_status = '3' , rep_datetime = '"
					+ rep_datetime
					+ "' , rep_comments = '"
					+ rep_comments
					+ "'"
					+ " , res_datetime = '"
					+ res_datetime
					+ "' , res_comments = '"
					+ res_comments
					+ "', rea_comments = '"
					+ rea_comments
					+ "'  "
					+ "where ticket_no = '" + ticket_no + "'";
			// System.out.println(Inside the update Resolved Status Details
			// Query " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public SurveyCountVo getTotalSurveyCount(String employee_code) {
		SurveyCountVo surveyCountVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "SELECT COUNT(*) AS acount,SUM(CASE WHEN STATUS=1 THEN 1 ELSE 0 END) AS ccount,(COUNT(*)-SUM(CASE WHEN STATUS=1 THEN 1 ELSE 0 END)) AS rcount FROM tbl_fps WHERE employee_code='"
						+ employee_code + "'";

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					surveyCountVo = new SurveyCountVo();
					surveyCountVo.setTotalSurvey(resultSet.getString("acount"));
					surveyCountVo.setCompletedSurvey(resultSet
							.getString("ccount"));
					surveyCountVo.setRemainingSurvey(resultSet
							.getString("rcount"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return surveyCountVo;
	}

	@Override
	public int insertMacidDetails(String fps_code, String mac_id,
			String serial_no, String created_datetme, String employee_code) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_macid(fps_code,mac_id,serial_no,created_datetme,employee_code) values ('"
					+ fps_code
					+ "','"
					+ mac_id
					+ "','"
					+ serial_no
					+ "','"
					+ created_datetme + "','" + employee_code + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

				if (result > 0) {
					try {
						String sql1 = "Update tbl_fps set mac_status = '1' where code = '"
								+ fps_code + "' ";
						Class.forName(commonDbUtil.getJdbcDriver());
						if (commonDbUtil.isDbConnectionCheck()) {
							connection = commonDbUtil
									.openConnectionGO(connection);

							statement = connection.createStatement();

							preparedStatement = connection
									.prepareStatement(sql1);

							result = preparedStatement.executeUpdate();
						}
					} catch (Exception e) {
						// System.out.println(Error while insert the Survey
						// Detail for Dao Impl ----->insertSurveyDetails()"+ e);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Macid Details for Dao Impl
			// ----->insertMacidDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<FpsCodeVo> fetchmacFpsCode(String emp_id) {
		List<FpsCodeVo> fpsCodeLists = new ArrayList<FpsCodeVo>();
		FpsCodeVo fpscodeVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT a.code,a.name AS tehsil,b.name AS district FROM tbl_fps a,tbl_district b WHERE a.district_id = b.id AND a.employee_code = '"
						+ emp_id + "' AND a.mac_status = '0' ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpscodeVo = new FpsCodeVo();
					fpscodeVo.setCode(resultSet.getString(1));
					fpscodeVo.setTehsil(resultSet.getString(2));
					fpscodeVo.setDistrict(resultSet.getString(3));

					fpsCodeLists.add(fpscodeVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch mac fps list the details for
			// Dao Impl ----->fetchmacFpsCode()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return fpsCodeLists;
	}

	@Override
	public int updateReasonDetails(String ticket_no, String rea_comments) {

		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_ticket set rea_comments = '"
					+ rea_comments + "'  where ticket_no = '" + ticket_no + "'";
			// System.out.println(Inside the update Reason Status Details Query
			// " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<SlaTicketsVo> getSlaTicketsDetails(String employee_code) {
		List<SlaTicketsVo> slaTicketLists = new ArrayList<SlaTicketsVo>();
		SlaTicketsVo slaTicketsVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "{call USP_GET_ESC_Tickets('" + employee_code
						+ "')}";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					slaTicketsVo = new SlaTicketsVo();
					slaTicketsVo.setSlaTicketNo(resultSet.getString(1));
					slaTicketsVo.setSlaFpsCode(resultSet.getString(2));
					slaTicketsVo.setSlaName(resultSet.getString(3));
					slaTicketsVo.setSlaMobile(resultSet.getString(4));
					slaTicketsVo.setSlaTicketStatus(resultSet.getString(5));
					slaTicketsVo.setSlaTicketIssue(resultSet.getString(6));
					slaTicketsVo.setSlaEmployeeCode(resultSet.getString(7));
					slaTicketsVo.setSlaCreatedTime(resultSet.getString(8));
					slaTicketsVo.setSlaReaComments(resultSet.getString(9));
					slaTicketsVo.setSlaResDesg(resultSet.getString(10));
					slaTicketsVo.setSlaRepDesg(resultSet.getString(11));
					slaTicketsVo.setSlateshil(resultSet.getString(12));
					slaTicketsVo.setSlaDistrict(resultSet.getString(13));
					slaTicketsVo.setSlaEmpname(resultSet.getString(14));

					slaTicketLists.add(slaTicketsVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch mac fps list the details for
			// Dao Impl ----->fetchmacFpsCode()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return slaTicketLists;
	}

	@Override
	public List<SiteCodeVo> getSiteFpscodeDetails(String employee_code,
			String maxDatetime) {

		List<SiteCodeVo> siteFpsCodesLists = new ArrayList<SiteCodeVo>();
		SiteCodeVo siteCodeVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "{call USP_GET_FPSCODE('" + employee_code
						+ "' , '" + maxDatetime + "')}";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					siteCodeVo = new SiteCodeVo();
					siteCodeVo.setCode(resultSet.getString(1));
					siteCodeVo.setTehsil(resultSet.getString(2));
					siteCodeVo.setDistrict(resultSet.getString(3));
					siteCodeVo.setOldsim(resultSet.getString(4));
					siteCodeVo.setCreatedtime(resultSet.getString(5));

					siteFpsCodesLists.add(siteCodeVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while Site Fpscode Detailsfor Dao Impl
			// ----->getSiteFpscodeDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return siteFpsCodesLists;
	}

	@Override
	public int insertSiteDetails(String fpscode, String employee_code,
			String sparerequested, String sparereplaced, String simid,
			String providerid, String lattitude, String longitude,
			String createdtime, String installation) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_sitevisit(fps_code,employee_code,spare_required,spare_replaced,simid,provider,latitude,"
					+ "longitude,created_datetime,Installation) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ sparereplaced
					+ "','"
					+ simid
					+ "'"
					+ ",'"
					+ providerid
					+ "','"
					+ lattitude
					+ "','"
					+ longitude
					+ "','" + createdtime + "','" + installation + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();
				if (!providerid.equals("0")) {

					if (result > 0) {
						String sql1 = "Update tbl_fps_tnpds set simid = '"
								+ simid + "' , created_ts = '" + createdtime
								+ "' where code = '" + fpscode + "'";
						// System.out.println(Inside the insertSiteDetails " +
						// sql);
						Class.forName(commonDbUtil.getJdbcDriver());
						if (commonDbUtil.isDbConnectionCheck()) {
							connection = commonDbUtil
									.openConnectionGO(connection);

							statement = connection.createStatement();

							preparedStatement = connection
									.prepareStatement(sql1);

							result = preparedStatement.executeUpdate();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Survey Detail for Dao
			// Impl ----->insertSurveyDetails()"+ e);

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertAepdsvalues(String fps_code, String provider,
			String deviceid, String status, String created_datetme,
			String employee_code, String remarks) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_aepds(fps_code,provider_id,device_id,status,created_ts,employee_code,remarks) values ('"
					+ fps_code
					+ "','"
					+ provider
					+ "','"
					+ deviceid
					+ "','"
					+ status
					+ "','"
					+ created_datetme
					+ "','"
					+ employee_code
					+ "','" + remarks + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

				if (result > 0) {
					try {
						String sql1 = "Update tbl_fps set mac_status = '1' where code = '"
								+ fps_code + "' ";
						Class.forName(commonDbUtil.getJdbcDriver());
						if (commonDbUtil.isDbConnectionCheck()) {
							connection = commonDbUtil
									.openConnectionGO(connection);

							statement = connection.createStatement();

							preparedStatement = connection
									.prepareStatement(sql1);

							result = preparedStatement.executeUpdate();
						}
					} catch (Exception e) {
						// System.out.println(Error while insert the Survey
						// Detail for Dao Impl ----->insertSurveyDetails()"+ e);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Macid Details for Dao Impl
			// ----->insertMacidDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public TicStatusVo getTicketsCountdetails(String employee_code,
			String taluk_name) {
		TicStatusVo ticStatusVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "SELECT COUNT(b.code) AS tot_shops, SUM(CASE WHEN a.status='1' THEN 1 ELSE 0 END) AS "
						+ "tot_open FROM tbl_tnpds_ticket a RIGHT JOIN tbl_fps_tnpds b ON a.fps_code=b.code  "
						+ "INNER JOIN tbl_employee c ON b.district_id=c.district_id   WHERE "
						+ " taluk_name= '"
						+ taluk_name
						+ "' AND c.code='"
						+ employee_code + "'";

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					ticStatusVo = new TicStatusVo();
					ticStatusVo.setShops(resultSet.getString("tot_shops"));
					ticStatusVo.setOpenticket(resultSet.getString("tot_open"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return ticStatusVo;
	}

	@Override
	public List<OpenTicketsVo> getOpenTicketsDetails(String employee_code,
			String taluk_name) {
		List<OpenTicketsVo> openTicketsLists = new ArrayList<OpenTicketsVo>();
		OpenTicketsVo openTicketsVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = " SELECT a.* FROM tbl_tnpds_ticket a INNER JOIN tbl_fps_tnpds b "
						+ "ON a.fps_code=b.code INNER JOIN tbl_employee c ON b.district_id=c.district_id"
						+ " WHERE  taluk_name= '"
						+ taluk_name
						+ "' AND c.code= '"
						+ employee_code
						+ "' AND a.status='1' ";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					openTicketsVo = new OpenTicketsVo();
					openTicketsVo.setId(resultSet.getString(1));
					openTicketsVo.setFpscode(resultSet.getString(2));
					openTicketsVo.setTicketno(resultSet.getString(3));
					openTicketsVo.setCreateddate(resultSet.getString(4));
					openTicketsVo.setProblemreported(resultSet.getString(5));
					openTicketsVo.setPriority(resultSet.getString(6));
					openTicketsVo.setStatus(resultSet.getString(6));

					openTicketsLists.add(openTicketsVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch mac fps list the details for
			// Dao Impl ----->fetchmacFpsCode()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return openTicketsLists;
	}

	@Override
	public VersionVo getAppVersionDetails(String name) {
		VersionVo versionVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "SELECT version from tbl_appversion where name = '"
						+ name + "'";

				// System.out.println(Inside the getAppVersionDetails Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					versionVo = new VersionVo();
					versionVo.setVersion(resultSet.getString("version"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the version number Details
			// for Dao Impl ---->getAppVersionDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return versionVo;
	}

	@Override
	public SimVo getsimerrdetail(String fpscode, String sim_no) {
		SimVo simVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String sql = "CALL USP_GET_CHECK_TNSIM('" + fpscode + "','"
						+ sim_no + "')";

				// System.out.println(Inside the getsimerrdetail Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					simVo = new SimVo();
					simVo.setSim(resultSet.getString("simstatus"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the SIM error Details for
			// Dao Impl ---->getsimerrdetail() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return simVo;
	}

	@Override
	public int insertdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_hpepds_shopvisit(fps_code,employee_code,complients,actiontaken,new_simid,others,sv_lat,sv_long,created_datetime) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ actiontaken
					+ "','"
					+ newsimid
					+ "','"
					+ others
					+ "'"
					+ ",'"
					+ lattitude
					+ "','"
					+ longitude + "','" + createdtime + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the daily shoplocation for
			// Dao Impl ----->insertdailyshoplocation()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public FpsVo getCheckfpscode(String fpscode) {
		FpsVo fpsVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select code from tbl_fps_hppds where code = '" + fpscode
						+ "'";

				// System.out.println(Inside the getCheckfpscode Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpsVo = new FpsVo();
					fpsVo.setFpscode(resultSet.getString("code"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return fpsVo;

	}

	@Override
	public FpsVo getCheckupfpscode(String fpscode) {
		FpsVo fpsVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select code from tbl_fps_uppds where code = '" + fpscode
						+ "'";

				// System.out.println(Inside the getCheckfpscode Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpsVo = new FpsVo();
					fpsVo.setFpscode(resultSet.getString("code"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return fpsVo;
	}

	@Override
	public int insertupdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_uppds_shopvisit(fps_code,employee_code,complients,actiontaken,new_simid,others,sv_lat,sv_long,created_datetime) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ actiontaken
					+ "','"
					+ newsimid
					+ "','"
					+ others
					+ "'"
					+ ",'"
					+ lattitude
					+ "','"
					+ longitude + "','" + createdtime + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the daily shoplocation for
			// Dao Impl ----->insertupdailyshoplocation()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertmhdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime, String imagePath) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_mhepds_shopvisit(fps_code,employee_code,complients,actiontaken,new_simid,others,sv_lat,sv_long,created_datetime,photo_path) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ actiontaken
					+ "','"
					+ newsimid
					+ "','"
					+ others
					+ "'"
					+ ",'"
					+ lattitude
					+ "','"
					+ longitude
					+ "','"
					+ createdtime
					+ "','"
					+ imagePath
					+ "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the daily shoplocation for
			// Dao Impl ----->insertupdailyshoplocation()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int inserttsdailyshoplocation(String fpscode, String employee_code,
			String sparerequested, String actiontaken, String newsimid,
			String others, String lattitude, String longitude,
			String createdtime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_tsepds_shopvisit(fps_code,employee_code,complients,actiontaken,new_simid,others,sv_lat,sv_long,created_datetime) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ actiontaken
					+ "','"
					+ newsimid
					+ "','"
					+ others
					+ "'"
					+ ",'"
					+ lattitude
					+ "','"
					+ longitude + "','" + createdtime + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the daily shoplocation for
			// Dao Impl ----->insertupdailyshoplocation()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public FpsVo getChecktsfpscode(String fpscode) {
		FpsVo fpsVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select code from tbl_fps_tspds where code = '" + fpscode
						+ "'";

				// System.out.println(Inside the getCheckfpscode Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					fpsVo = new FpsVo();
					fpsVo.setFpscode(resultSet.getString("code"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return fpsVo;
	}

	@Override
	public List<CooptexCodeVo> fetchcooptexCode() {
		List<CooptexCodeVo> cooptexcodes = new ArrayList<CooptexCodeVo>();
		CooptexCodeVo cooptexCodeVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "SELECT code,region,created_ts FROM tbl_fse_cooptex";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					cooptexCodeVo = new CooptexCodeVo();
					cooptexCodeVo.setCode(resultSet.getString(1));
					cooptexCodeVo.setRegion(resultSet.getString(2));
					cooptexCodeVo.setCreated_ts(resultSet.getString(3));
					cooptexcodes.add(cooptexCodeVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Code list the details for
			// Dao Impl ----->fetchcooptexCode()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return cooptexcodes;
	}

	@Override
	public int insertCooptexSurvey(String code, String emp_code,
			String inc_name, String inc_contactno, String eng_name,
			String eng_contactno, String created_ts, String lattitude,
			String longitude, String array) {
		int result = 0;
		String sql = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(array
				.split("\\]")));
		if (myList.size() == 23) {
			String[] stockArr = new String[myList.size()];
			stockArr = myList.toArray(stockArr);
			for (int i = 0; i < stockArr.length; i++)
			// for (int i = 0; i < myList; i++)
			{
				if (stockArr[i].contains(",")) {
					String[] types = stockArr[i].split(",");

					try {

						if (i >= 1) {

							sql = "Insert into tbl_fse_checklist(code,emp_code,inc_name,inc_contact,eng_name,eng_contact,det_name,det_status,det_remarks,det_type,created_ts,shop_lat,shop_long)values('"
									+ code
									+ "','"
									+ emp_code
									+ "','"
									+ inc_name
									+ "','"
									+ inc_contactno
									+ "','"
									+ eng_name
									+ "','"
									+ eng_contactno
									+ "','"
									+ types[1].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[2].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[3].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[4].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ created_ts
									+ "','"
									+ lattitude
									+ "','"
									+ longitude + "')";
						} else {
							sql = "Insert into tbl_fse_checklist(code,emp_code,inc_name,inc_contact,eng_name,eng_contact,det_name,det_status,det_remarks,det_type,created_ts,shop_lat,shop_long)values('"
									+ code
									+ "','"
									+ emp_code
									+ "','"
									+ inc_name
									+ "','"
									+ inc_contactno
									+ "','"
									+ eng_name
									+ "','"
									+ eng_contactno
									+ "','"
									+ types[0].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[1].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[2].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ types[3].replace("[", "")
											.replace("]", "").trim()
									+ "','"
									+ created_ts
									+ "','"
									+ lattitude
									+ "','"
									+ longitude + "')";
						}

						// System.out.println(Inside the insert latlong details
						// Query "+ sql);
						if (commonDbUtil.isDbConnectionCheck()) {
							connection = commonDbUtil
									.openConnectionGO(connection);

							statement = connection.createStatement();

							preparedStatement = connection
									.prepareStatement(sql);

							result = preparedStatement.executeUpdate();

						}

					} catch (SQLException e) {

						/*
						 * if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
						 * ////System.out.println(); result = 1; }
						 */

						// System.out.println(Error while insert the Tracking
						// latlong details for Dao Impl ---->insertlatLong() "+
						// e);
					} finally {
						commonDbUtil.closeAllConnections(resultSet,
								preparedStatement, statement, connection);
					}

				}
			}
		}
		return result;
	}

	@Override
	public UppclCodeVo uppclCodeDetails(String fpsid) {
		UppclCodeVo uppclCodeVo = null;
		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select * from tbl_uppcl_fpsmaster where fpsid = '"
						+ fpsid + "'";

				// System.out.println(Inside the uppclCodeDetails Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					uppclCodeVo = new UppclCodeVo();
					uppclCodeVo.setDistrict(resultSet.getString("district"));
					uppclCodeVo.setDivision(resultSet.getString("division"));
					uppclCodeVo.setCategory(resultSet.getString("category"));
					uppclCodeVo.setFpsname(resultSet.getString("fpsname"));
					uppclCodeVo.setFpsid(resultSet.getString("fpsid"));
					uppclCodeVo.setFpsmobileno(resultSet
							.getString("fpsmobileno"));
					uppclCodeVo.setBlockname(resultSet.getString("blockname"));
					uppclCodeVo.setVillage_name(resultSet
							.getString("village_name"));
					uppclCodeVo.setFpsowner_name(resultSet
							.getString("fpsowner_name"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the Uppclcode Details for
			// Dao Impl ---->uppclCodeDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return uppclCodeVo;
	}

	@Override
	public List<RechargereasonVo> fetchrechargereason() {
		List<RechargereasonVo> rechargereasonVos = new ArrayList<RechargereasonVo>();
		RechargereasonVo rechargereasonVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "Select * from tbl_recharge_reason";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					rechargereasonVo = new RechargereasonVo();
					rechargereasonVo.setSno(resultSet.getString("sno"));
					rechargereasonVo.setWallet_reason(resultSet
							.getString("wallet_reason"));

					rechargereasonVos.add(rechargereasonVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Task list the details for
			// Dao Impl ----->fetchTaskList()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return rechargereasonVos;
	}

	@Override
	public List<TxnreasonVo> fetchtxnreason() {
		List<TxnreasonVo> txnreasonVos = new ArrayList<TxnreasonVo>();
		TxnreasonVo txnreasonVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				String query = "Select * from tbl_txn_reason";

				// System.out.println(Inside the driver details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					txnreasonVo = new TxnreasonVo();
					txnreasonVo.setSno(resultSet.getString("sno"));
					txnreasonVo
							.setTxn_reason(resultSet.getString("txn_reason"));

					txnreasonVos.add(txnreasonVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Task list the details for
			// Dao Impl ----->fetchTaskList()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return txnreasonVos;
	}

	@Override
	public int insertUppclDetails(String fpsid, String fpsname,
			String village_name, String fpsowner_name, String fps_mobile,
			String recharge_amt, String recharge_reason,
			String recharge_remarks, String txt_amt, String txt_reason,
			String txn_remarks, String empid) {

		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_uppcl_sitevisit(fpsid,fpsname,village_name,fpsowner_name,"
					+ "fps_mobile,recharge_amt,recharge_reason,recharge_remarks,txt_amt,txt_reason,"
					+ "txn_remarks,empid,created_datetime) values ('"
					+ fpsid
					+ "','"
					+ fpsname
					+ "','"
					+ village_name
					+ "','"
					+ fpsowner_name
					+ "','"
					+ fps_mobile
					+ "','"
					+ recharge_amt
					+ "','"
					+ recharge_reason
					+ "'"
					+ ",'"
					+ recharge_remarks
					+ "','"
					+ txt_amt
					+ "','"
					+ txt_reason
					+ "'"
					+ ",'"
					+ txn_remarks + "','" + empid + "',NOW())";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the insertUppclDetails for
			// Dao Impl ----->insertUppclDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int updateUppclMasterdata(String fpsid, String fpsmobileno,
			String fpsname, String village_name, String fpsowner_name) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_uppcl_fpsmaster set fpsname = '" + fpsname
					+ "' , fpsmobileno = '" + fpsmobileno
					+ "', village_name = '" + village_name
					+ "', fpsowner_name = '" + fpsowner_name
					+ "' where fpsid = '" + fpsid + "'";
			// System.out.println(Inside the insertDownload Details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert Download Details for Dao
			// Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertleaveapply(String emp_code, String from_date,
			String to_date, String total_days, String reason, String session) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_leaveapply(emp_code,from_date,to_date,total_days,session,reason,created_datetime,leave_status) values ('"
					+ emp_code
					+ "','"
					+ from_date
					+ "','"
					+ to_date
					+ "','"
					+ total_days
					+ "','"
					+ session
					+ "','"
					+ reason
					+ "',NOW(), '1')";
			// System.out.println(Inside the leaveapply details Query " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the leaveapply details for
			// Dao Impl ----->insertleaveapplydetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<LeavelistVo> fetchleavelist(String employee_code, String roleid) {

		List<LeavelistVo> leavelists = new ArrayList<LeavelistVo>();
		LeavelistVo leavelistVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		String query = "";
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				if (roleid.equals("R6") || roleid.equals("R7")
						|| roleid.equals("R8") || roleid.equals("R9")) {

					query = "SELECT b.name,b.code,de.designation,b.role_id,b.district_id,c.name AS district_name,d.name AS reporting_name, "
							+ "d.role_id AS reporting_role_id, "
							+ "b.reporting_to,a.from_date,a.to_date,a.total_days,a.session,a.reason,a.created_datetime ,a.leave_status "
							+ ",a.evaluated_by,a.rejected_reason, a.evaluated_datetime,a.id "
							+ " FROM tbl_leaveapply a "
							+ "JOIN tbl_employee AS b ON a.emp_code=b.code JOIN tbl_employee AS d "
							+ "ON d.code=b.reporting_to JOIN tbl_designation as de ON de.id=b.designation_id JOIN tbl_district c "
							+ " JOIN tbl_app_role AS ar ON c.state_id=ar.state_id AND ar.id = '"
							+ roleid
							+ "' WHERE c.id=b.district_id AND a.to_date >= CURDATE() ORDER BY a.created_datetime ASC";

				} else {
					query = "SELECT * FROM (SELECT b.name,b.code,de.designation,b.role_id,b.district_id,c.name AS district_name,"
							+ "d.name AS reporting_name,d.role_id AS reporting_role_id,b.reporting_to,a.from_date,"
							+ "a.to_date,a.total_days,a.session,a.reason,a.created_datetime , a.leave_status"
							+ ", a.evaluated_by, a.rejected_reason, a.evaluated_datetime,a.id FROM tbl_leaveapply a "
							+ "JOIN tbl_employee AS b ON a.emp_code=b.code JOIN tbl_employee AS d ON "
							+ "d.code=b.reporting_to JOIN tbl_designation AS de ON de.id=b.designation_id JOIN tbl_district c WHERE c.id=b.district_id AND "
							+ "a.to_date >= CURDATE())AS emp WHERE '"
							+ employee_code + "' IN (reporting_to,code)";

				}

				// System.out.println(Inside the leavelist details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					leavelistVo = new LeavelistVo();
					leavelistVo.setId(resultSet.getString("id"));
					leavelistVo.setEmpname(resultSet.getString("name"));
					leavelistVo.setEmpcode(resultSet.getString("code"));
					leavelistVo.setDesignation(resultSet
							.getString("designation"));
					leavelistVo.setRoleid(resultSet.getString("role_id"));
					leavelistVo.setDistrict(resultSet
							.getString("district_name"));
					leavelistVo.setReportingto(resultSet
							.getString("reporting_name"));
					leavelistVo.setReportingtoid(resultSet
							.getString("reporting_to"));
					leavelistVo.setFromdate(resultSet.getString("from_date"));
					leavelistVo.setTodate(resultSet.getString("to_date"));
					leavelistVo.setTotaldays(resultSet.getString("total_days"));
					leavelistVo.setSession(resultSet.getString("session"));
					leavelistVo.setReason(resultSet.getString("reason"));
					leavelistVo.setCreateddate(resultSet
							.getString("created_datetime"));
					leavelistVo.setLeavestatus(resultSet
							.getString("leave_status"));
					leavelistVo.setEvaluatedby(resultSet
							.getString("evaluated_by"));
					leavelistVo.setRejectedreason(resultSet
							.getString("rejected_reason"));
					leavelistVo.setEvaluateddate(resultSet
							.getString("evaluated_datetime"));

					leavelists.add(leavelistVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Tickets list the details for
			// Dao Impl ----->fetchTickets()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return leavelists;
	}

	@Override
	public int updateleaveStatusDetails(String id, String emp_code,
			String leave_status, String evaluated_by, String rejected_reason,
			String evaluated_datetime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_leaveapply set leave_status = '"
					+ leave_status + "',evaluated_by='" + evaluated_by
					+ "',rejected_reason='" + rejected_reason
					+ "',evaluated_datetime='" + evaluated_datetime
					+ "' where id = '" + id + "' and emp_code='" + emp_code
					+ "'";
			// System.out.println(Inside the update Reason Status Details Query
			// " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert updateleaveStatusDetails
			// for Dao Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public LeavecountVo getLeaveCountdetails(String employee_code, String roleid) {
		LeavecountVo leavecountVo = null;
		String query;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;

				if (roleid.equals("R6") || roleid.equals("R7")
						|| roleid.equals("R8") || roleid.equals("R9")) {
					query = "SELECT Count(*) AS leavecount FROM tbl_leaveapply AS l "
							+ "JOIN tbl_employee AS e ON l.emp_code=e.code JOIN tbl_district AS d ON e.district_id=d.id "
							+ "JOIN tbl_app_role AS ar ON d.state_id=ar.state_id and ar.id = '"
							+ roleid
							+ "' WHERE leave_status ='1' AND to_date >= CURDATE() ";
				} else {
					query = "SELECT COUNT(*) as leavecount FROM (SELECT b.code,b.role_id,b.reporting_to FROM tbl_leaveapply As a JOIN "
							+ "tbl_employee AS b ON a.emp_code=b.code JOIN tbl_employee AS c ON c.code=b.reporting_to AND a.to_date >= CURDATE() "
							+ "AND leave_status ='1' WHERE '"
							+ employee_code
							+ "' IN(b.code,b.reporting_to)) AS LEAVETABLE";

				}

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					leavecountVo = new LeavecountVo();
					leavecountVo.setLeavecount(resultSet
							.getString("leavecount"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return leavecountVo;
	}

	@Override
	public List<AttendanceempreportlistVo> fetchattendanceempreportlist(
			String att_type, String date) {
		List<AttendanceempreportlistVo> leavelists = new ArrayList<AttendanceempreportlistVo>();
		AttendanceempreportlistVo attendanceempreportlistVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		String query = "";
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				if (att_type.equals("ALL")) {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.server_ts)='"
							+ date
							+ "' WHERE dt.id=a.district_id "
							+ "AND dt.state_id='2' AND a.status='1' ORDER by `intime` DESC ";
				} else if (att_type.equals("Present")) {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.server_ts)='"
							+ date
							+ "' WHERE dt.id=a.district_id "
							+ "AND dt.state_id='2' AND a.status='1' AND c.att_in_datetime IS NOT NULL ORDER by `intime` DESC ";
				} else {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.server_ts)='"
							+ date
							+ "' WHERE dt.id=a.district_id "
							+ "AND dt.state_id='2' AND a.status='1' AND c.att_in_datetime IS NULL";

				}

				// System.out.println(Inside the Attendanceempreportlist details
				// Query " + query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					attendanceempreportlistVo = new AttendanceempreportlistVo();
					attendanceempreportlistVo.setEmpname(resultSet
							.getString("name"));
					attendanceempreportlistVo.setEmpintime(resultSet
							.getString("intime"));
					attendanceempreportlistVo.setEmpouttime(resultSet
							.getString("outtime"));
					attendanceempreportlistVo.setEmpdistrictname(resultSet
							.getString("districtname"));

					leavelists.add(attendanceempreportlistVo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Tickets list the details for
			// Dao Impl ----->fetchTickets()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return leavelists;
	}

	@Override
	public List<AttendanceempreportlistVo> fetchattendanceempreportlistnew(
			String att_type, String date, String role_id) {

		List<AttendanceempreportlistVo> leavelistsnew = new ArrayList<AttendanceempreportlistVo>();
		AttendanceempreportlistVo attendanceempreportlistVo = null;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		String query = "";
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				if (att_type.equals("ALL")) {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_app_role ar,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.att_in_datetime)='"
							+ date
							+ "' WHERE dt.id=a.district_id "
							+ "AND dt.state_id=ar.state_id AND ar.id = '"
							+ role_id
							+ "' AND a.status='1' ORDER by `intime` DESC ";
				} else if (att_type.equals("Present")) {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_app_role ar,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.att_in_datetime)='"
							+ date
							+ "'  WHERE dt.id=a.district_id "
							+ "AND dt.state_id=ar.state_id AND ar.id = '"
							+ role_id
							+ "' AND a.status='1' AND c.att_in_datetime IS NOT NULL ORDER by `intime` DESC ";
				} else {
					query = "SELECT a.name,DATE_FORMAT(c.server_ts,'%T')AS intime,DATE_FORMAT(c.server_outtime,'%T')AS outtime,"
							+ "dt.name AS districtname,a.code AS employeeCode FROM tbl_district dt,tbl_app_role ar,tbl_attendance c RIGHT JOIN "
							+ "tbl_employee a ON c.employee_code=a.code AND DATE(c.att_in_datetime)='"
							+ date
							+ "' WHERE dt.id=a.district_id "
							+ "AND dt.state_id=ar.state_id AND ar.id = '"
							+ role_id
							+ "' AND a.status='1' AND c.att_in_datetime IS NULL";

				}

				// System.out.println(Inside the Attendanceempreportlistnew
				// details Query " + query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					attendanceempreportlistVo = new AttendanceempreportlistVo();
					attendanceempreportlistVo.setEmpname(resultSet
							.getString("name"));
					attendanceempreportlistVo.setEmpintime(resultSet
							.getString("intime"));
					attendanceempreportlistVo.setEmpouttime(resultSet
							.getString("outtime"));
					attendanceempreportlistVo.setEmpdistrictname(resultSet
							.getString("districtname"));

					leavelistsnew.add(attendanceempreportlistVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch Tickets list the details for
			// Dao Impl ----->fetchTickets()"+ e);

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return leavelistsnew;

	}

	@Override
	public int insertlatLongNew(String array) {
		int result = 0;
		String sql = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}
		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(array
				.split("\\]")));
		String[] stockArr = new String[myList.size()];
		stockArr = myList.toArray(stockArr);
		for (int i = 0; i < stockArr.length; i++)
		// for (int i = 0; i < myList; i++)
		{
			if (stockArr[i].contains(",")) {
				String[] types = stockArr[i].split(",");

				try {
					if (i >= 1) {
						sql = "Insert into tbl_tracking_new(dev_id,tra_datetime,tra_lat,tra_long)values('"
								+ types[1].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[2].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[3].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[4].replace("[", "").replace("]", "")
										.trim() + "')";
					} else {

						sql = "Insert into tbl_tracking_new(dev_id,tra_datetime,tra_lat,tra_long)values('"
								+ types[0].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[1].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[2].replace("[", "").replace("]", "")
										.trim()
								+ "','"
								+ types[3].replace("[", "").replace("]", "")
										.trim() + "')";
					}
					// System.out.println(Inside the insert latlongnew details
					// Query " + sql);
					if (commonDbUtil.isDbConnectionCheck()) {
						connection = commonDbUtil.openConnectionGO(connection);

						statement = connection.createStatement();

						preparedStatement = connection.prepareStatement(sql);

						result = preparedStatement.executeUpdate();

					}

				} catch (SQLException e) {

					if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
						// System.out.println();
						result = 1;
					}

					// System.out.println(Error while insert the Tracking
					// latlongnew details for Dao Impl ---->insertlatLongNew()
					// "+ e);
				} finally {
					commonDbUtil.closeAllConnections(resultSet,
							preparedStatement, statement, connection);
				}

			}
		}
		return result;
	}

	@Override
	public int updatePhotoUpload(String imagePath, String empCode) {

		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Update tbl_mhepds_shopvisit set photo_path = '"
					+ imagePath + "' where employee_code='" + empCode + "'";
			// System.out.println(Inside the update Reason Status Details Query
			// " + sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert updateleaveStatusDetails
			// for Dao Impl ----->insertDownloadDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public int insertmhdailyshoplocationOld(String fpscode,
			String employee_code, String sparerequested, String actiontaken,
			String newsimid, String others, String lattitude, String longitude,
			String createdtime) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_mhepds_shopvisit(fps_code,employee_code,complients,actiontaken,new_simid,others,sv_lat,sv_long,created_datetime) values "
					+ "('"
					+ fpscode
					+ "','"
					+ employee_code
					+ "','"
					+ sparerequested
					+ "','"
					+ actiontaken
					+ "','"
					+ newsimid
					+ "','"
					+ others
					+ "'"
					+ ",'"
					+ lattitude
					+ "','"
					+ longitude + "','" + createdtime + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the daily shoplocation for
			// Dao Impl ----->insertupdailyshoplocation()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<ShopTypeVo> fetchShopType() {
		List<ShopTypeVo> shoptypes = new ArrayList<ShopTypeVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_shoptype ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					ShopTypeVo shopTypeVo = new ShopTypeVo();
					shopTypeVo.setId(resultSet.getString(1));
					shopTypeVo.setShopType(resultSet.getString(2));

					shoptypes.add(shopTypeVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return shoptypes;
	}

	@Override
	public List<LicenceVo> fetchLicenceType() {
		List<LicenceVo> licencetypes = new ArrayList<LicenceVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_licencetype ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					LicenceVo licenceTypeVo = new LicenceVo();
					licenceTypeVo.setId(resultSet.getString(1));
					licenceTypeVo.setLicenceType(resultSet.getString(2));

					licencetypes.add(licenceTypeVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return licencetypes;
	}

	@Override
	public List<DivisionVo> fetchDivisionName() {
		List<DivisionVo> divisionnames = new ArrayList<DivisionVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_division ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					DivisionVo divisionVo = new DivisionVo();
					divisionVo.setId(resultSet.getString(1));
					divisionVo.setDivisionName(resultSet.getString(2));

					divisionnames.add(divisionVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return divisionnames;
	}

	@Override
	public List<DistrictVo> fetchDistrictName() {
		List<DistrictVo> districtnames = new ArrayList<DistrictVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_district ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					DistrictVo districtVo = new DistrictVo();
					districtVo.setId(resultSet.getString(1));
					districtVo.setDistrictName(resultSet.getString(2));

					districtnames.add(districtVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return districtnames;
	}

	@Override
	public List<TalukVo> gettalukdetails(String district) {
		List<TalukVo> taluklists = new ArrayList<TalukVo>();

		TalukVo talukVo = null;
		String query;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				query = "SELECT talukname FROM tbl_upexcise_taluk tt join tbl_upexcise_district"
						+ " td ON tt.district_id=td.id where td.districtname = '"
						+ district + "'";

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					talukVo = new TalukVo();
					talukVo.setTalukName(resultSet.getString("talukname"));
					taluklists.add(talukVo);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return taluklists;

	}

	@Override
	public List<CircleVo> fetchCircleName() {
		List<CircleVo> circlenames = new ArrayList<CircleVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_circle ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					CircleVo circleVo = new CircleVo();
					circleVo.setId(resultSet.getString(1));
					circleVo.setCircleName(resultSet.getString(2));

					circlenames.add(circleVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return circlenames;

	}

	@Override
	public List<SectorVo> fetchSectorName() {
		List<SectorVo> sectornames = new ArrayList<SectorVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT * FROM tbl_upexcise_sector ORDER BY id ";
				// System.out.println(Inside the complaint details Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					SectorVo sectorVo = new SectorVo();
					sectorVo.setId(resultSet.getString(1));
					sectorVo.setSectorName(resultSet.getString(2));

					sectornames.add(sectorVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the provider details for Dao
			// Impl ---->fetchProvider() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return sectornames;

	}

	@Override
	public ShopCodeVo getshopinfodetails(String shop_code) {
		ShopCodeVo shopinfoVo = null;
		String query;
		String device1 = "";
		String device2 = "";
		String device3 = "";

		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				// query =
				// "SELECT di.device_id,di.device_id1,si.shopname FROM tbl_upexcise_deviceinfo di join tbl_upexcise_shopinfo"
				// +
				// " si ON di.shopcode=si.shopcode where di.shopcode = "+shop_code+"";

				query = "SELECT di.device_id,di.device_id1,si.shopname FROM tbl_upexcise_shopinfo si left join tbl_upexcise_deviceinfo"
						+ " di ON si.shopcode=di.shopcode where si.shopcode = '"
						+ shop_code + "'";

				// System.out.println(Inside the getTotalSurveyCount Query " +
				// query);
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				shopinfoVo = new ShopCodeVo();

				while (resultSet.next()) {
					if (device1.equals("")) {
						device1 = resultSet.getString("device_id");
						shopinfoVo.setDeviceId1(device1);
					} else if (device2.equals("")) {
						device2 = resultSet.getString("device_id");
						shopinfoVo.setDeviceId2(device2);
					} else if (device3.equals("")) {
						device3 = resultSet.getString("device_id");
						shopinfoVo.setDeviceId3(device3);
					}

					// shopinfoVo.setDeviceId1(resultSet.getString("device_id"));
					// shopinfoVo.setDeviceId2(resultSet.getString("device_id1"));
					shopinfoVo.setShopName(resultSet.getString("shopname"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return shopinfoVo;
	}

	@Override
	public int insertUpExisesurveyDetails(String shopcode, String deviceid,
			String deviceid1, String shopname, String shoptype,
			String shopaddress, String shopcontactno, String salespersonname,
			String salespersonno, String licencename, String licencecontactno,
			String licencetype, String licenceno, String exciseinspectorname,
			String exciseinspectorno, String circletype, String sectortype,
			String division, String district, String taluk, String block,
			String pincode, String latitude, String longitude, String empcode) {
		int result = 0;
		String deviceid_new = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			if (deviceid.length() == 5) {
				deviceid_new = "TEG9300MTOC" + deviceid;
			} else {
				deviceid_new = deviceid;
			}

			String sql = "INSERT INTO tbl_upexcise_survey(shopcode, deviceid, deviceid1, shopname, shoptype, shopaddress, shopcontactno, "
					+ "salespersonname, salespersonno, licencename, licencecontactno, licencetype, licenceno, exciseinspectorname,"
					+ " exciseinspectorno, circletype, sectortype, division, district, taluk, block, pincode, latitude, longitude, empcode, created_dt, status,device_id_new) VALUES  ('"
					+ shopcode
					+ "','"
					+ deviceid_new
					+ "','"
					+ deviceid1
					+ "','"
					+ shopname
					+ "','"
					+ shoptype
					+ "','"
					+ shopaddress
					+ "','"
					+ shopcontactno
					+ "','"
					+ salespersonname
					+ "','"
					+ salespersonno
					+ "','"
					+ licencename
					+ "','"
					+ licencecontactno
					+ "','"
					+ licencetype
					+ "','"
					+ licenceno
					+ "','"
					+ exciseinspectorname
					+ "','"
					+ exciseinspectorno
					+ "','"
					+ circletype
					+ "','"
					+ sectortype
					+ "','"
					+ division
					+ "','"
					+ district
					+ "','"
					+ taluk
					+ "','"
					+ block
					+ "','"
					+ pincode
					+ "','"
					+ latitude
					+ "','"
					+ longitude
					+ "','"
					+ empcode
					+ "',now(),1,'" + deviceid + "')";
			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Tracking Detail for Dao
			// Impl ----->insertTracking()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public String getCheckdeviceid(String deviceid) {
		String sql;
		String result = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "SELECT device_id FROM tbl_upexcise_devicemaster WHERE device_id = '"
						+ deviceid + "'";

				// System.out.println(Inside the getCheckfpscode Query " + sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {

					result = resultSet.getString("device_id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public HintVo getshopidhintdetails() {

		HintVo hintVo = null;

		String sql;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				sql = "Select * from tbl_upexcise_hint where status = 1";
				// sql = "Select * from tbl_provider";
				// System.out.println(Inside the getTotalSurveyCount Query " +
				// sql);
				statement = connection.prepareStatement(sql);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					hintVo = new HintVo();
					hintVo.setShophintname(resultSet.getString("shopidhint"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while fetch the emp Login Details for
			// Dao Impl ---->empLoginDetails() "+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return hintVo;
	}

	@Override
	public int insertAttendanceDetailsNew(String empId, String intime,
			String latitude, String longitude, String att_date, String sqlDate) {
		int result = 0;
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			String sql = "Insert into tbl_attendance(employee_code,att_in_datetime,att_in_lat,att_in_long,att_date,server_ts) values ('"
					+ empId
					+ "','"
					+ intime
					+ "','"
					+ latitude
					+ "','"
					+ longitude + "','" + att_date + "','" + sqlDate + "')";
			// System.out.println(sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(Error while insert the Atttendance Detail for
			// Dao Impl ----->insertAttendanceDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;

	}

	@Override
	public int insertOutAttendanceDetailsNew(String empId, String outtime,
			String outlatitude, String outlongitude, String intime,
			String inlatitude, String inlongitude, String attendancedate,
			String insync, String outsync, String sqlDate) {
		int result = 0;
		String sql = "";
		CommonDbUtil commonDbUtil = new CommonDbUtil();
		try {
			if (insync.equals("0") && outsync.equals("0")) {
				sql = "Insert into tbl_attendance(employee_code,att_in_datetime,att_out_datetime,att_in_lat,att_in_long,att_out_lat,att_out_long,att_date,server_outtime) "
						+ "values ('"
						+ empId
						+ "','"
						+ intime
						+ "','"
						+ outtime
						+ "','"
						+ inlatitude
						+ "','"
						+ inlongitude
						+ "'"
						+ ",'"
						+ outlatitude
						+ "','"
						+ outlongitude
						+ "','" + attendancedate + "','" + sqlDate + "'";
			} else {
				sql = "Update tbl_attendance set att_out_datetime = '"
						+ outtime + "', att_out_lat = '" + outlatitude
						+ "' , att_out_long = '" + outlongitude
						+ "', server_outtime = '" + sqlDate + "' "
						+ " where employee_code = '" + empId
						+ "' AND att_date = '" + attendancedate + "'  ";
			}

			// System.out.println(Inside the insert feedback details Query " +
			// sql);
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);

				statement = connection.createStatement();

				preparedStatement = connection.prepareStatement(sql);

				result = preparedStatement.executeUpdate();

			}
		} catch (Exception e) {
			sql = "Update tbl_attendance set att_out_datetime = '" + outtime
					+ "' , att_out_lat = '" + outlatitude
					+ "' , att_out_long = '" + outlongitude
					+ "', server_outtime = '" + sqlDate + "' "
					+ " where employee_code = '" + empId + "' AND att_date = '"
					+ attendancedate + "'  ";

			try {
				Class.forName(commonDbUtil.getJdbcDriver());
				if (commonDbUtil.isDbConnectionCheck()) {
					connection = commonDbUtil.openConnectionGO(connection);

					statement = connection.createStatement();

					preparedStatement = connection.prepareStatement(sql);

					result = preparedStatement.executeUpdate();

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
			// System.out.println(Error while insert the Atttendance Detail for
			// Dao Impl ----->insertAttendanceDetails()"+ e);
		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}

		return result;
	}

	@Override
	public List<SurveyMasterVo> getIssueType() {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT id,issuetype FROM tbl_upexcise_field_survey_master GROUP BY issuetype ORDER BY id";
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					SurveyMasterVo surveyMasterVo = new SurveyMasterVo();
					surveyMasterVo.setId(resultSet.getString(1));
					surveyMasterVo.setIssuetype(resultSet.getString(2));

					surveyMaster.add(surveyMasterVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return surveyMaster;
	}

	@Override
	public List<SurveyMasterVo> getObservation(String issueType) {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT id,observation FROM tbl_upexcise_field_survey_master where issuetype='"
						+ issueType + "'";
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					SurveyMasterVo surveyMasterVo = new SurveyMasterVo();
					surveyMasterVo.setId(resultSet.getString(1));
					surveyMasterVo.setObservation(resultSet.getString(2));
					surveyMaster.add(surveyMasterVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return surveyMaster;
	}

	@Override
	public List<SurveyMasterVo> getActionStatus(String issueType) {
		List<SurveyMasterVo> surveyMaster = new ArrayList<SurveyMasterVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "SELECT distinct action,status FROM tbl_upexcise_field_survey_master where issuetype='"
						+ issueType
						+ "' "
						+ "union select distinct action1,status1 from tbl_upexcise_field_survey_master where issuetype='"
						+ issueType + "' ";
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					SurveyMasterVo surveyMasterVo = new SurveyMasterVo();
					surveyMasterVo.setAction(resultSet.getString(1));
					surveyMasterVo.setStatus(resultSet.getString(2));
					surveyMaster.add(surveyMasterVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return surveyMaster;
	}

	@Override
	public List<EntityTypeVo> getEntityType() {
		List<EntityTypeVo> entityTypeVos = new ArrayList<EntityTypeVo>();

		CommonDbUtil commonDbUtil = new CommonDbUtil();

		try {
			Class.forName(commonDbUtil.getJdbcDriver());
			if (commonDbUtil.isDbConnectionCheck()) {
				connection = commonDbUtil.openConnectionGO(connection);
				PreparedStatement statement;
				String query = "select entity_type from tbl_upexcise_entity_type";
				statement = connection.prepareStatement(query);
				resultSet = statement.executeQuery();
				while (resultSet.next()) {
					EntityTypeVo entityTypeVo = new EntityTypeVo();
					entityTypeVo.setEntityType(resultSet.getString(1));
					entityTypeVos.add(entityTypeVo);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			commonDbUtil.closeAllConnections(resultSet, preparedStatement,
					statement, connection);
		}
		return entityTypeVos;
	}

}
