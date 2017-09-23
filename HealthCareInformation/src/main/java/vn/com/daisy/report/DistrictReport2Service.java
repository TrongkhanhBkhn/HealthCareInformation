package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
@ManagedBean(name="districtReport2Service")
public class DistrictReport2Service {
	public List<ReportObject> createData(int orgId, int[] orgGroupId, int[] dataelementId, int periodId) {
		List<ReportObject> listObj = new ArrayList<>();
		List<String> datavalue = new ArrayList<>();
		List<String> datavalue1 = new ArrayList<>();
		List<String> datavalue2 = new ArrayList<>();
		int[] result = new int[4];
		
		List<Integer> categos = new ArrayList<>();
		DataValueDAO dataDAO = new DataValueDAO();
		int org_id = 0;
		int index = 0;
		String name = null;
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataelementDAO deDAO = new DataelementDAO();
		List<String> childrenNames = new ArrayList<>();
		childrenNames = orgDAO.getChildrenFormParentID(orgId, orgGroupId[0]);
		HashMap<String, String> map;
		for(String chidren :childrenNames){
			index ++;
			org_id = orgDAO.getOrgIdFromName(chidren, orgId);
			datavalue = dataDAO.getValueReport(dataelementId[0], org_id, periodId);
			categos = dataDAO.getCategoryOptionId(dataelementId[0], org_id, periodId);
			map = new HashMap<>();
			for(int i = 0; i<datavalue.size(); i++)
			{
				name = deDAO.getNameCategoryOptionFromId(categos.get(i));			
				map.put(name, datavalue.get(i));
				result[i]+=Integer.parseInt(datavalue.get(i));
			}
			listObj.add(new ReportObject(index, chidren, map, ""));
		}
		childrenNames = orgDAO.getChildrenFormParentID(orgId, orgGroupId[1]);
		for(String chidren :childrenNames){
			index ++;
			map = new HashMap<>();
			org_id = orgDAO.getOrgIdFromName(chidren, orgId);
			datavalue = dataDAO.getValueReport(dataelementId[1], org_id, periodId);
			datavalue1 = dataDAO.getValueReport(dataelementId[2], org_id, periodId);
			datavalue2 = dataDAO.getValueReport(dataelementId[3], org_id, periodId);
			name ="Tổng số phụ nữ đẻ";
			map.put(name, datavalue.get(0));
			result[0] +=Integer.parseInt(datavalue.get(0));
			name = "Phụ nữ đẻ được nhân viên y tế đỡ";
			map.put(name, datavalue1.get(0));
			result[1] +=Integer.parseInt(datavalue1.get(0));
			name = "Số trạm y tế xã/phường";
			map.put(name, "1");

			name="Trạm Y tế có bác sỹ làm việc";
			map.put(name, datavalue2.get(0));
			result[3] +=Integer.parseInt(datavalue2.get(0));
			listObj.add(new ReportObject(index, chidren, map, ""));
			
		}
		index++;
		map = new HashMap<>();
		name ="Tổng số phụ nữ đẻ";
		map.put(name, String.valueOf(result[0]));
		name = "Phụ nữ đẻ được nhân viên y tế đỡ";
		map.put(name, String.valueOf(result[1]));
		name = "Số trạm y tế xã/phường";
		map.put(name, String.valueOf(childrenNames.size()));
		name="Trạm Y tế có bác sỹ làm việc";
		map.put(name, String.valueOf(result[3]));
		listObj.add(new ReportObject(index, "Tổng số", map, ""));
		
		return listObj;
	}
}
