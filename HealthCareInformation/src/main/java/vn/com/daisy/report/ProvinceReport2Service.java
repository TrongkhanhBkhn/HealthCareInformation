package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;

@ManagedBean(name = "provinceReport2Service")
@ApplicationScoped
public class ProvinceReport2Service {
	public List<ReportObject> createData(int orgId, int[] dataelementId, int periodId) {
		List<ReportObject> listObj = new ArrayList<>();
		int result = 0;
		int index = 0;
		int sum = 0;
		String name = null;
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataValueDAO dataDAO = new DataValueDAO();
		DataelementDAO deDAO = new DataelementDAO();
		List<Integer> childrenIds = new ArrayList<>();
		List<Integer> childrenIds_ = new ArrayList<>();
		childrenIds = orgDAO.getAllChildrenIdFormParentID(orgId);
		
		HashMap<String, String> map;
		if (childrenIds != null) {
			for (int id : childrenIds){
				childrenIds_ = orgDAO.getChildrenIdFormParentID(id, 1);
				if(childrenIds_ !=null)
					sum+=childrenIds_.size();
				
			}
			for (int i = 0; i < dataelementId.length; i++) {
				result = 0;
				map = new HashMap<>();
				index++;
				if (dataelementId[i] != 79) {
					for (int id : childrenIds) {
						System.out.println("khanh" +dataelementId[i] +"  "+ id +"  " + periodId);
						System.out.println(
								"Fiss" + dataDAO.getValueReport(dataelementId[i], id, periodId).get(0).toString());
						result += Integer
								.parseInt(dataDAO.getValueReport(dataelementId[i], id, periodId).get(0).toString());
					}
					name = deDAO.getNameDEFromId(dataelementId[i]);
					map.put(name, String.valueOf(result));
					System.out.println("gsd" + result);

				} else {
					result += Integer
							.parseInt(dataDAO.getValueReport(dataelementId[i], orgId, periodId).get(0).toString());
					name = deDAO.getNameDEFromId(dataelementId[i]);
					map.put(name, String.valueOf(result));
					System.out.println("gsd" + result);

				}
				if(dataelementId[i] == 75){
					map.put("Tổng số trạm y tế xã/phường", String.valueOf(sum));
					listObj.add(new ReportObject(index, "Tổng số trạm y tế xã/phường", map, ""));
					index++;
				}
				listObj.add(new ReportObject(index, name, map, ""));
				
			}
		}
		return listObj;
	}

}
