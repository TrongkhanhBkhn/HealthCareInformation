package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;

@ManagedBean(name = "districtReport1Service")
@ApplicationScoped
public class DistrictReport1Service {
	private int[] list1 = new int[2];
	private int[] list2 = new int[2];

	public List<ReportObject> createData(int orgId, int orgGroupId, int dataelementId, int periodId) {

		List<ReportObject> listObj = new ArrayList<>();
		List<String> datavalue = new ArrayList<>();
		List<Integer> categos = new ArrayList<>();
		DataValueDAO dataDAO = new DataValueDAO();
		int org_id = 0;
		int index = 0;
		String name = null;
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataelementDAO deDAO = new DataelementDAO();

		List<String> childrenNames = new ArrayList<>();
		childrenNames = orgDAO.getChildrenFormParentID(orgId, orgGroupId);
		System.out.println(childrenNames.toString());
		HashMap<String, String> map;
		for (String chidren : childrenNames) {
			index++;
			org_id = orgDAO.getOrgIdFromName(chidren, orgId);
			datavalue = dataDAO.getValueReport(dataelementId, org_id, periodId);
			categos = dataDAO.getCategoryOptionId(dataelementId, org_id, periodId);
			map = new HashMap<>();
			for (int i = 0; i < datavalue.size(); i++) {
				name = deDAO.getNameCategoryOptionFromId(categos.get(i));
				map.put(name, datavalue.get(i));
				if (i < 2)
					list1[i] += Integer.valueOf(datavalue.get(i));
			}
			listObj.add(new ReportObject(index, chidren, map, ""));
		}
		org_id = orgDAO.getOrgIdFromName(childrenNames.get(0), orgId);
		datavalue = dataDAO.getValueReport(dataelementId, org_id, periodId);
		categos = dataDAO.getCategoryOptionId(dataelementId, org_id, periodId);
		name = deDAO.getNameCategoryOptionFromId(categos.get(0));
		index++;
		map = new HashMap<>();
		map.put(name, String.valueOf(list1[0]));
	//	map.put("Số lượt khám dự phòng (định kỳ)", String.valueOf(list1[1]));
		listObj.add(new ReportObject(index, "Tuyến huyện", map, ""));
		return listObj;
	}

	public List<ReportObject> createData1(int orgId, int orgGroupId, int dataelementId, int periodId) {
		List<ReportObject> listObj = new ArrayList<>();
		List<String> datavalue = new ArrayList<>();
		List<Integer> categos = new ArrayList<>();
		DataValueDAO dataDAO = new DataValueDAO();
		int org_id = 0;
		int index = 0;
		String name = null;
		int allvalue = 0;
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataelementDAO deDAO = new DataelementDAO();

		List<String> childrenNames = new ArrayList<>();
		System.out.println(org_id);
		childrenNames = orgDAO.getChildrenFormParentID(orgId, orgGroupId);
		System.out.println(childrenNames.toString());
		HashMap<String, String> map;
		for (String chidren : childrenNames) {
			index++;
			org_id = orgDAO.getOrgIdFromName(chidren, orgId);
			datavalue = dataDAO.getValueReport(dataelementId, org_id, periodId);
			System.out.println("ssss" + org_id + "  " + dataelementId + "  " + periodId);
			System.out.println("ssss" + datavalue.toString());
			categos = dataDAO.getCategoryOptionId(dataelementId, org_id, periodId);

			map = new HashMap<>();
			for (int i = 0; i < datavalue.size(); i++) {
				name = deDAO.getNameCategoryOptionFromId(categos.get(i));
				map.put(name, datavalue.get(i).toString());
				allvalue += Integer.parseInt(datavalue.get(i));
				
			}
			map.put("Tổng số lượt khám bệnh tuyến xã", String.valueOf(allvalue));
			list2[0] +=allvalue;
			listObj.add(new ReportObject(index, chidren, map, ""));
			allvalue = 0;
			
		}
		index++;
		org_id = orgDAO.getOrgIdFromName(childrenNames.get(0), orgId);
		datavalue = dataDAO.getValueReport(dataelementId, org_id, periodId);
		categos = dataDAO.getCategoryOptionId(dataelementId, org_id, periodId);
		map = new HashMap<>();
		map.put("Tổng số lượt khám bệnh tuyến xã", String.valueOf(list2[0]));
		listObj.add(new ReportObject(index, "Tuyến xã", map, ""));
		index++;
		map = new HashMap<>();
		map.put("Tổng số lượt khám bệnh tuyến xã", String.valueOf(list1[0] + list2[0]));
		listObj.add(new ReportObject(index, "Tổng số", map, ""));
		
		return listObj;
	}

}
