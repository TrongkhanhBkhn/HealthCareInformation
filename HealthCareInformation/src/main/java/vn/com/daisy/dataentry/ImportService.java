package vn.com.daisy.dataentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.dataset.DatasetDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;

@ManagedBean(name = "importService", eager = true)
@ApplicationScoped
public class ImportService {
	/*
	 * Hàm này làm nhiệm vụ tạo ra các đối tượng cho các bảng nhập liệu của các
	 * đơn vị trực thuộc cấp huyện Hàm này sẽ dựa trên thông số datase name của
	 * từ Bean inforSelect gửi sang
	 * 
	 */
	public List<ImportObject> createDataTable(int datasetId, int orgGroupId, int orgId, int periodId) {
		// Khởi tạo đối tượng dataset DAO
		int org_id = 0;
		int dataE_id = 0;
		String result = null;
		List<String> childrenNames = new ArrayList<>();
		DatasetDAO datasetDAO = new DatasetDAO();
		DataelementDAO deDAO = new DataelementDAO();
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataValueDAO dataValueDAO = new DataValueDAO();
		TranslationDAO tranDAO = new TranslationDAO();
		String name = null, nameOld = null;
		Map<String, String> map;
		List<String> categoryOpNames = new ArrayList<>();
		List<Integer> dataElements_id = new ArrayList<>();
		List<Integer> category_options_id = new ArrayList<>();
		List<ImportObject> listObjects = new ArrayList<ImportObject>();
		List<Integer>  category_option_id = new ArrayList<>();
		dataElements_id = datasetDAO.getAllDEIdFromId(datasetId);		
		if (dataElements_id.size() > 0) {
			for (int i = 0; i < dataElements_id.size(); i++) {
				category_options_id = deDAO.getAllCategoryOptionIdFromId(dataElements_id.get(i));				
				for (int j = 0; j < category_options_id.size(); j++) {
					category_option_id.add(category_options_id.get(j));
					nameOld = deDAO.getNameCategoryOptionFromId(category_options_id.get(j));
					name = tranDAO.getTranslation("categoryoption", "vi_VN", nameOld);
					if(name !=null)
						nameOld = name;
					categoryOpNames.add(nameOld);
				}
			}
		}
		childrenNames = orgDAO.getChildrenFormParentID(orgId, orgGroupId);	
		for (int i = 0; i < childrenNames.size(); i++) {
			org_id = orgDAO.getOrgIdFromName(childrenNames.get(i), orgId);
			nameOld = childrenNames.get(i).toString();
			name = tranDAO.getTranslation("organisation", "vi_VN", nameOld);
			if(name !=null)
				nameOld = name;
			map = new HashMap<>();
			for (int j = 0; j<categoryOpNames.size(); j++){		
				dataE_id = deDAO.getDEIdFromCategoryOptionId(category_option_id.get(j));
				result  = dataValueDAO.getValueReport1(dataE_id, org_id, periodId, category_option_id.get(j));
				map.put(categoryOpNames.get(j), result );				
			}
			listObjects.add(new ImportObject(nameOld, map));
		}

		return listObjects;
	}
}
