//package com.seaky.centralconf.manager.web.controller;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.seaky.centralconf.core.ConfigItem;
//import com.seaky.centralconf.core.ResourceItem;
//import com.seaky.centralconf.core.constant.BaseConstant;
//import com.seaky.centralconf.manager.mapper.CentralConfigManager;
//
//@Controller
//public class Synchronization {
//	@Autowired
//	CentralConfigManager centralConfigManager;
//
//	@RequestMapping("/synResource")
//	@ResponseBody
//	public Map<String, Map<String, Object>> synResource() {
//		String flg = "**************";
//		Map<String, Map<String, Object>> mapAll = new HashMap<String, Map<String, Object>>();
//		InputStream is;
//		try {
//			is = new FileInputStream("d:/12.xls");
//			HSSFWorkbook book = new HSSFWorkbook(is);
//			HSSFSheet hssfSheet = book.getSheetAt(0);
//			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//				if (hssfRow != null) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					HSSFCell cell1 = hssfRow.getCell(0);
//					HSSFCell cell2 = hssfRow.getCell(1);
//					HSSFCell cell3 = hssfRow.getCell(2);
//					map.put(cell2.getStringCellValue(), cell3.getStringCellValue());
//					mapAll.put(cell1.getStringCellValue(), map);
//				}
//			}
//			for (Entry<String, Map<String, Object>> enty : mapAll.entrySet()) {
//				flg = enty.getKey() + " ------------------------------";
//				List<ResourceItem> confItems = centralConfigManager.getResourceItem(enty.getKey());
//				Map<String, Object> value = enty.getValue();
//				for (Entry<String, Object> en : value.entrySet()) {
//					for (ResourceItem r : confItems) {
//						r.setRsc(en.getKey() + "");
//						r.setEnv(en.getValue() + "");
//						centralConfigManager.updateRscEnvItem(r);
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			System.out.println(flg);
//			e.printStackTrace();
//		}
//		return mapAll;
//	}
//
//	@RequestMapping("/synApplication")
//	@ResponseBody
//	public Map<String, Map<String, Object>> synApplication() {
//		Map<String, Map<String, Object>> mapAll = new HashMap<String, Map<String, Object>>();
//		InputStream is;
//		try {
//			is = new FileInputStream("d:/12.xls");
//			HSSFWorkbook book = new HSSFWorkbook(is);
//			HSSFSheet hssfSheet = book.getSheetAt(0);
//			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//				if (hssfRow != null) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					HSSFCell cell1 = hssfRow.getCell(0);
//					HSSFCell cell2 = hssfRow.getCell(1);
//					HSSFCell cell3 = hssfRow.getCell(2);
//					map.put(cell2.getStringCellValue(), cell3.getStringCellValue());
//					mapAll.put(cell1.getStringCellValue(), map);
//				}
//			}
//			List<String> apps = centralConfigManager.getAllApp();
//			for (String app : apps) {
//				List<String> envs = centralConfigManager.getAllEnv(app);
//				if (envs != null && envs.size() > 0) {
//					for (String env : envs) {
//						List<String> repeat = new ArrayList<String>();
//						List<ConfigItem> allItem = centralConfigManager.getExportItem(app, env);
//						for (ConfigItem c : allItem) {
//							String key = c.getKey();
//							if (key.startsWith(BaseConstant.PERFIX_RESOURCE)) {
//								String reSourceKey = key.substring(key.indexOf(BaseConstant.PERFIX_RESOURCE) + 1);
//								Map<String, Object> map = mapAll.get(reSourceKey);
//								for (Entry<String, Object> en : map.entrySet()) {
//									if (!repeat.contains(en.getKey())) {
//										c.setKey(BaseConstant.PERFIX_RESOURCE + en.getKey());
//										c.setValue(en.getValue() + "");
//										centralConfigManager.updateConfig(c);
//										repeat.add(en.getKey());
//									} else {
//										ConfigItem item = centralConfigManager.getItem(app, env, en.getKey());
//										c.setKey(BaseConstant.PERFIX_RESOURCE + en.getKey());
//										c.setValue(item.getValue() + "," + en.getValue());
//										centralConfigManager.updateConfig(c);
//									}
//								}
//							} else {
//								centralConfigManager.updateConfig(c);
//							}
//						}
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapAll;
//	}
//
//	@RequestMapping("/synApplicationCommon")
//	@ResponseBody
//	public Map<String, Map<String, Object>> synApplicationCommon() {
//		Map<String, Map<String, Object>> mapAll = new HashMap<String, Map<String, Object>>();
//		try {
//			List<String> apps = centralConfigManager.getAllApp();
//			for (String app : apps) {
//				List<ConfigItem> confItems = centralConfigManager.getCommonItem(app);
//				if(confItems!=null&&confItems.size()>0){
//					for (ConfigItem c : confItems) {
//						centralConfigManager.updateConfig(c);
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapAll;
//	}
//
//}
