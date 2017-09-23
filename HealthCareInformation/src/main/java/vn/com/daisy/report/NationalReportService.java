package vn.com.daisy.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import vn.com.daisy.dataelement.DataelementDAO;
import vn.com.daisy.datavalue.DataValueDAO;
import vn.com.daisy.organisation.OrganisationUnitDAO;
@ManagedBean
@ViewScoped
public class NationalReportService {
	public List<ReportObject> createData(int[] dataelementId, int[] periodId, int orgId) {
		List<ReportObject> listObj = new ArrayList<>();
		List<String> listResult = new ArrayList<>();
		List<String> listResult1 = new ArrayList<>();
		List<String> listResult2 = new ArrayList<>();
		float percent = 0;
		int result = 0;
		int result1 = 0;
		int result2 = 0;
		int result3 = 0;
		int result4 = 0;
		int all_ward = 0;
		String name = null;
		OrganisationUnitDAO orgDAO = new OrganisationUnitDAO();
		DataValueDAO dataDAO = new DataValueDAO();
		DataelementDAO deDAO = new DataelementDAO();
		List<Integer> childrenIds = new ArrayList<>();
		List<Integer> childrenIds1 = new ArrayList<>();
		childrenIds = orgDAO.getAllChildrenIdFormParentID(orgId);
		HashMap<String, String> map;
		if (childrenIds != null) {
			
			for (int id : childrenIds) {
				map = new HashMap<>();
				for (int id_ : periodId) {
					System.out.println("Name" + id);
					childrenIds1 = orgDAO.getAllChildrenIdFormParentID(id);
					System.out.println("SIZE" +childrenIds1.size());
					for(int k:childrenIds1){
						listResult = dataDAO.getValueReport(dataelementId[0],k, id_);
						if(listResult !=null){
							for(String result_: listResult){
								result +=Integer.parseInt(result_);
							}
						}else{
							result  += 0;
						}
						listResult = dataDAO.getValueReport(dataelementId[1],k , id_);	
						
						if(listResult !=null){
							for(String result_: listResult){
								result1 +=Integer.parseInt(result_);
							}
						}else{
							result1 += 0;
						}
						listResult = dataDAO.getValueReport(dataelementId[2],k , id_);
						listResult1 = dataDAO.getValueReport(dataelementId[3],k , id_);
						listResult2 = dataDAO.getValueReport(dataelementId[4],k , id_);
						if(listResult !=null){
							result2 +=Integer.parseInt(listResult.get(0)) + Integer.parseInt(listResult1.get(0));
							result3 +=Integer.parseInt(listResult.get(1)) + Integer.parseInt(listResult2.get(0));				
						}else{
							result2 +=0;
							result3 +=0;
						}
						all_ward +=orgDAO.getChildrenFormParentID(k, 1).size();
							
					}	
					
					map.put(deDAO.getNameDEFromId(dataelementId[0])+String.valueOf(id_), String.valueOf(result));
					percent = ((float)result1/(float)all_ward)*100;
					
					map.put("Tỉ lệ TYT xã có BS" +String.valueOf(id_), String.valueOf((double)Math.round(percent*100)/100));
					percent = 0;
					listResult = dataDAO.getValueReport(dataelementId[5],id , id_);
					result = 0;
					result1 = 0;
					if(listResult !=null){
						for(String result_: listResult){
							result +=Integer.parseInt(result_);
						}
					}else{
						result  += 0;
					}
					listResult = dataDAO.getValueReport(dataelementId[6],id , id_);	
			
					if(listResult !=null){
						for(String result_: listResult){
							result1 +=Integer.parseInt(result_);
						}
					}else{
						result1 += 0;
					}
					if(result2 !=0){
						percent = ((float)(result3 + result1)/(float)(result2 +result))*100;
						map.put("Tỉ lệ phụ nữ đẻ được nhân viê y tế đỡ" +String.valueOf(id_),  String.valueOf((double)Math.round(percent*100)/100));
					}
					else{
						map.put("Tỉ lệ phụ nữ đẻ được nhân viê y tế đỡ" +String.valueOf(id_), "0.0");
					}	
					listResult = dataDAO.getValueReport(dataelementId[5], id, id_);
					if(listResult !=null){
						for(String result_: listResult){
							result4 +=Integer.parseInt(result_);
						}
					}else{
						result4  += 0;
					}
					map.put(deDAO.getNameDEFromId(dataelementId[5])+String.valueOf(id_), String.valueOf(result4));
					all_ward = 0;
					result =result1 =result2 =result3 =result4 = 0;
				}
				all_ward = 0;
				result =result1 =result2 =result3 =result4 = 0;
				name = orgDAO.getNameOrgFromId(id);
				listObj.add(new ReportObject(0,name , map, "0.0"));
			}
		}
		return listObj;
	}
}
