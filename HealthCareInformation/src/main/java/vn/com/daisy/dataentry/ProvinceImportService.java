package vn.com.daisy.dataentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.dataset.DatasetDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
import vn.com.daisy.translation.TranslationDAO;
@ManagedBean(name="#{pimportService}")
public class ProvinceImportService{

	public List<ImportObject> createDataTable(int datasetId, int orgId, int periodId){
		DatasetDAO datasetDAO = new DatasetDAO();
		DataelementDAO deDAO = new DataelementDAO();
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		TranslationDAO tranDAO = new TranslationDAO();
		DataValueDAO dataValueDAO = new DataValueDAO();
		String name = null, nameOld = null;
		int dataE_id = 0;
		String result = null;
		Map<String, String> map;
		List<Integer>  category_option_id = new ArrayList<>();
		List<String> categoryOpNames = new ArrayList<>();
		List<Integer> dataElements_id = new ArrayList<>();
		List<Integer> category_options_id = new ArrayList<>();
		List<ImportObject> listObjects = new ArrayList<ImportObject>();
		dataElements_id = datasetDAO.getAllDEIdFromId(datasetId);
		if (dataElements_id.size() > 0){
			for (int i = 0; i < dataElements_id.size(); i++){
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
		nameOld = orgDAO.getNameOrgFromId(orgId);
		name = tranDAO.getTranslation("organisation", "vi_VN", nameOld);
		if(name !=null)
			nameOld = name;
		map = new HashMap<>();
		for (int j = 0; j<categoryOpNames.size(); j++){	
			dataE_id = deDAO.getDEIdFromCategoryOptionId(category_option_id.get(j));
			result  = dataValueDAO.getValueReport1(dataE_id, orgId, periodId, category_option_id.get(j));
			map.put(categoryOpNames.get(j),result);				
		}
		listObjects.add(new ImportObject(nameOld, map));
		return listObjects;
	}

}
