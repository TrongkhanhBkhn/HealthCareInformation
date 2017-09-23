package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "provinceReport1Service")
public class ProvinceReport1Service extends ReportService {

	public List<ReportObject> createData(int orgId, int dataelementId, int periodId, int orgGroupId) {
		List<ReportObject> listObj = new ArrayList<>();

		childrenIds = orgDAO.getAllChildrenIdFormParentID(orgId);
		categoryOpIds = deDAO.getAllCategoryOptionIdFromId(dataelementId);
		int result = 0;
		HashMap<String, String> map;
		if (childrenIds != null) {
			for (int id : childrenIds) {
				System.out.println("IDDD" + id);
				childrenIds1 = orgDAO.getChildrenIdFormParentID(id, orgGroupId);

				if (childrenIds1 != null) {
					for (int id_ : childrenIds1) {
						result = 0;
						index++;
						map = new HashMap<>();
						orgName = orgDAO.getNameOrgFromId(id_);
						results = dataDAO.getValueReport(dataelementId, id_, periodId);
						if (results != null) {
							if (results.size() == categoryOpIds.size()) {
								for (int i = 0; i < results.size(); i++) {
									categoryName = deDAO.getNameCategoryOptionFromId(categoryOpIds.get(i));
									name  = categoryName;
									categoryName = tranDAO.getTranslation("categoryoption", "vi_VN", categoryName);
									if(categoryName ==null)
										categoryName = name;
									map.put(categoryName, results.get(i));
									result += Integer.parseInt(results.get(i));
								}
							}
						}
						map.put("Tổng số", String.valueOf(result));
						listObj.add(new ReportObject(index, orgName, map, ""));
					}
				}
			}
		}

		return listObj;
	}

	public List<ReportObject> createData1(int orgId, int dataelementId, int periodId, int orgGroupId) {
		List<ReportObject> listObj = new ArrayList<>();
		childrenIds = orgDAO.getAllChildrenIdFormParentID(orgId);
		categoryOpIds = deDAO.getAllCategoryOptionIdFromId(dataelementId);
		HashMap<String, String> map;
		index = 0;
		int[] values = null;
		
		if (childrenIds != null) {
			for (int id : childrenIds) {
				childrenIds1 = orgDAO.getChildrenIdFormParentID(id, orgGroupId);				
				orgName = orgDAO.getNameOrgFromId(id);
				values = new int[categoryOpIds.size()];
				index++;
				map = new HashMap<>();
				if (childrenIds1 != null) {
					for (int id_ : childrenIds1) {
						name = orgDAO.getNameOrgFromId(id_);
						results = dataDAO.getValueReport(dataelementId, id_, periodId);
						if (results != null) {
							if (results.size() == categoryOpIds.size()) {
								for (int i = 0; i < results.size(); i++) {					
									name = name + String.valueOf(i);
									map.put(name, results.get(i));
									values[i] += Integer.parseInt(results.get(i));
								}
							}
						}
					}
				}
				if (values != null) {
					map.put("Tổng số", String.valueOf(values[0]));
					map.put("Số lượt khám dự phòng", String.valueOf(values[1]));
				}
				
				listObj.add(new ReportObject(index, orgName, map, ""));

			}
		}

		return listObj;
	}
}
